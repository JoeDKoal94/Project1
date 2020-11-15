package TRMS.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import TRMS.pojos.*;
import TRMS.pojos.Form;
import TRMS.util.ConnectionUtil;

public class TrmsDaoPostgres implements TrmsDao{

	private Statement statement;
	private ConnectionUtil connUtil = new ConnectionUtil();

	
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}
	
	@Override
	public void createForm(Form paper) {
		String sql = "INSERT INTO \"TRMS\".form "
				+ "(employee_id, event_date, event_time, event_location, description, grading_format, type_of_event, attachments, projected_reimbursement, approval_status, is_urgent) "
				+ "VALUES(?, ?::date, ?::time, ?, ?, ?, ?, null, ?, null, ?);";
		
		try (Connection conn = connUtil.createConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, paper.getEmployeeId());
			preparedStatement.setString(2, paper.getEventDate().toString());
			preparedStatement.setString(3, paper.getEventTime().toString());
			preparedStatement.setString(4, paper.getEventLocation());
			preparedStatement.setString(5, paper.getDescription());
			preparedStatement.setString(6, paper.getGradingFormat());
			preparedStatement.setString(7, paper.getTypeOfEvent());
			preparedStatement.setDouble(8, paper.getProjectedAmount());
			preparedStatement.setBoolean(9, paper.isUrgent());
			
			int rowsAffected = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}

	@Override
	public Form retrieveForm(int formNumber) {
		String query = "select * from \"TRMS\".form where form_number = '"+ formNumber +"'";
		Form form = new Form();
		try(Connection conn = connUtil.createConnection()){
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
			LocalDate date = rs.getObject("event_date", LocalDate.class);
			LocalTime time = rs.getObject("event_time", LocalTime.class);
			String location = rs.getString("event_location");
			String description = rs.getString("description");
			String grading = rs.getString("grading_format");
			String type = rs.getString("type_of_event");
			Blob attachments = rs.getBlob("attachments");
			double projectedMoney = rs.getDouble("projected_reimbursement");
			boolean approvalStatus = rs.getBoolean("approval_status");
			boolean isUrgent = rs.getBoolean("is_urgent");
			int passId = rs.getInt("employee_id");
			
			form = new Form(date, time, location, description, grading, type, attachments, projectedMoney, approvalStatus, isUrgent, passId);}}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public Form updateForm(int formNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteForm(int formNumber) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public Employee retrieveEmployee(String username) {
		String query = "select * from \"TRMS\".employee where username = '"+ username +"'";
		Employee emp = new Employee();
		try(Connection conn = connUtil.createConnection()){
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
			String firstnames = rs.getString("firstname"); 
			String lastnames = rs.getString("lastname");
			String usernames = rs.getString("username");
			String phoneNumber = rs.getString("phonenumber");
			String password = rs.getString("password");
			String addresse = rs.getString("address");
			String city = rs.getString("city");
			String state = rs.getString("state");
			String postalCode = rs.getString("postal_code");
			int empId = rs.getInt("employee_id");
			double availableAmount = rs.getDouble("available_amount");
			String superuser = rs.getString("authority");
			String emailAdd = rs.getString("email_add");
			switch(superuser) {
			case "Employee":
				emp = new Employee(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, username, password);
				break;
			case "Direct Supervisor":
				emp = new DirectSupervisor(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, username, password);
				break;
			case "Department Head":
				emp = new DepartmentHead(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, username, password);
				break;
			case "Benefits Coordinator":
				emp = new BenCo(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, username, password);
				break;
			case "BenCo Direct Supervisor":
				emp = new BenCoSupervisor(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, username, password);
			}
			}}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	public TrmsDaoPostgres() {
		
	}

	
	
}
