package TRMS.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.TimerTask;

import org.apache.log4j.Logger;


import TRMS.util.ConnectionUtil;

public class TimerDao extends TimerTask {

	String name;

private ConnectionUtil connUtil = new ConnectionUtil();
private static Logger log = Logger.getRootLogger();




	EmailNotify notify = new EmailNotify();
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}
	
	public TimerDao(String n) {
		this.name = n;
	}

	@Override
	public void run() {
		
		String sql = "select \"TRMS\".approval_form.approval_id, ds_approval, dh_approval, benco_approval, time_posted from \"TRMS\".approval_form inner join \"TRMS\".form on \"TRMS\".form.form_number = \"TRMS\".approval_form.form_number \r\n"
				+ "where (\"TRMS\".form.time_posted + interval '3 minute' <= localtime) \r\n"
				+ "and \"TRMS\".form.approval_status is not true;";
		String sentBackDS = "update \"TRMS\".ds_waitlist set ds_approval = true where approval_id = ";
		String sentBackDH = "update \"TRMS\".dh_waitlist set dh_approval = true where approval_id = ";
		String notifyBenCo = "update \"TRMS\".benco_waitlist set notify_me = true where approval_id = ";
		
		try (Connection conn = connUtil.createConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				int shouldSend = 0;
				if(LocalTime.now().compareTo(resultSet.getTime(5).toLocalTime().plusMinutes(3)) == 1 && resultSet.getBoolean(2) == false) {
					int number = resultSet.getInt(1);
					PreparedStatement preparedStatement = conn.prepareStatement(sentBackDS + number);
					int rowsAffected = preparedStatement.executeUpdate();
					log.info("Direct Supervisor has been auto approved");
				}
				if(LocalTime.now().compareTo(resultSet.getTime(5).toLocalTime().plusMinutes(6)) == 1 && resultSet.getBoolean(3) == false) {
					int number = resultSet.getInt(1);
					PreparedStatement preparedStatement = conn.prepareStatement(sentBackDH + number);
					int rowsAffected = preparedStatement.executeUpdate();
					log.info("Department Head has been auto approved");
				}
				if(LocalTime.now().compareTo(resultSet.getTime(5).toLocalTime().plusMinutes(8)) == 1 && resultSet.getBoolean(4) == false) {
					System.out.println("Right Here");
					int number = resultSet.getInt(1);
					PreparedStatement preparedStatement = conn.prepareStatement(notifyBenCo + number);
					int rowsAffected = preparedStatement.executeUpdate();
					shouldSend = 1;
					log.info("Benefits Coordinator has been Notified and sent an email to.");
				}
				//if(shouldSend == 1) {notify.send();}
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}

		
	}
}
