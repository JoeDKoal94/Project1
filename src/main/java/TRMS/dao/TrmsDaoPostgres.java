package TRMS.dao;

import java.io.IOException;
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
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import TRMS.pojos.*;
import TRMS.util.ConnectionUtil;
import io.javalin.http.UploadedFile;

public class TrmsDaoPostgres implements TrmsDao{
	private static Logger log = Logger.getRootLogger();
	private Statement statement;
	private ConnectionUtil connUtil = new ConnectionUtil();
	private DataSource ds;
	
	public TrmsDaoPostgres(DataSource ds) {
		this.ds = ds;
	}
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}
	
	@Override
	public void createForm(Form paper) {
		String sql = "INSERT INTO \"TRMS\".form "
				+ "(employee_id, event_date, event_time, event_location, description, grading_format, type_of_event, attachments, costs, approval_status, is_urgent, projected_reimbursement, time_posted) "
				+ "VALUES(?, ?::date, ?::time, ?, ?, ?, ?, ?, ?, null, ?, ?, ?::time);";
	
		
		
		try (Connection conn = connUtil.createConnection()) {
			
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, paper.getEmployeeId());
			preparedStatement.setString(2, paper.getEventDate().toString());
			preparedStatement.setString(3, paper.getEventTime().toString());
			preparedStatement.setString(4, paper.getEventLocation());
			preparedStatement.setString(5, paper.getDescription());
			preparedStatement.setString(6, paper.getGradingFormat());
			preparedStatement.setString(7, paper.getTypeOfEvent());
			preparedStatement.setBytes(8, paper.getAttachments());
			preparedStatement.setDouble(9, paper.getCost());
			preparedStatement.setBoolean(10, paper.isUrgent());
			preparedStatement.setDouble(11, paper.getProjectedAmount());
			preparedStatement.setString(12, paper.getTimePosted().toString());
			
			int rowsAffected = preparedStatement.executeUpdate();
			log.info("Database Updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}
	
	

	@Override
	public void createEmployee(Employee emp) {
		String sql = "insert into \"TRMS\".employee values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
try (Connection conn = connUtil.createConnection()) {
			
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, emp.getEmployeeId());
			preparedStatement.setString(2, emp.getFirstName());
			preparedStatement.setString(3, emp.getLastName());
			preparedStatement.setString(4, emp.getPhoneNumber());
			preparedStatement.setString(5, emp.getAddress());
			preparedStatement.setDouble(6, emp.getAvailableAmount());
			preparedStatement.setString(7, emp.getAuthority());
			preparedStatement.setString(8, emp.getCity());
			preparedStatement.setString(9, emp.getState());
			preparedStatement.setString(10, emp.getPostalCode());
			preparedStatement.setString(11, emp.getEmailAdd());
			preparedStatement.setString(12, emp.getUsername());
			preparedStatement.setString(13, emp.getPassword());
			
			int rowsAffected = preparedStatement.executeUpdate();
			log.info("Database Updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	@Override
	public Form retrieveForm(int formNumber) {
		String query = "select * from \"TRMS\".form where form_number = " + formNumber;
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
			byte[] attachments= rs.getBytes("attachments");
			double projectedMoney = rs.getDouble("projected_reimbursement");
			double cost = rs.getDouble("costs");
			boolean approvalStatus = rs.getBoolean("approval_status");
			boolean isUrgent = rs.getBoolean("is_urgent");
			int passId = rs.getInt("employee_id");
			int formNumbers = rs.getInt("form_number");
			LocalTime timePosted = rs.getObject("time_posted", LocalTime.class);
			
			form = new Form(date, time, location, description, grading, type, attachments, projectedMoney, approvalStatus, isUrgent, passId, cost, timePosted);
			form.setFormNumber(formNumbers);
			log.info("Data retrieved from Database");}}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return form;
	}
	
	
	@Override
	public ApplicationStatus retrieveStatus(int formNumber) {
		String query = "select * from \"TRMS\".approval_form where form_number = " + formNumber;
		ApplicationStatus app = null;
		try(Connection conn = connUtil.createConnection()){
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				app = new ApplicationStatus(rs.getInt(1), rs.getInt(2), rs.getBoolean(3),
						rs.getBoolean(4), rs.getBoolean(5), rs.getString(6), rs.getBoolean(7),
						rs.getBoolean(8), rs.getDouble(11), rs.getString(9), rs.getBoolean(10));
				log.info("Data retrieved from Database");
			}}
		catch(SQLException e) {
			
		}
		return app;
	}

	@Override
	public boolean updateForm(int formNumber, String area, String change) {
		String query = "";
		
		if(area.equals("event_date")) {
			query = "update \"TRMS\".form set event_date =  ?::date where form_number = " + formNumber;
		}
		else if(area.equals("event_time")) {
			query = "update \"TRMS\".formform set event_time =  ?::time where form_number = " + formNumber;
		}
		else {
			query = "update \"TRMS\".form set " + area + " =  ? where form_number = " + formNumber;
		}
		Form form = new Form();
		try(Connection conn = connUtil.createConnection()){
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			if(area.equals("costs") || area.equals("projected_reimbursement")) {
			preparedStatement.setDouble(1, Double.parseDouble(change));
			
			}
			else if (area.equals("isUrgent")) {
				preparedStatement.setBoolean(1, Boolean.parseBoolean(change));
			}
			else {
				preparedStatement.setString(1, change);
			}
			int rowsAffected = preparedStatement.executeUpdate();
			log.info("Data Updated");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	 return true;
	}

	@Override
	public void deleteForm(int formNumber) {
		String query = "DELETE FROM \"TRMS\".form WHERE form_number = " + formNumber;
		try(Connection conn = connUtil.createConnection()){
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			int rowsAffected = preparedStatement.executeUpdate();
			log.info("Data Deleted from Database");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public void updateConsent(int formNumber, boolean update) {
		String query = "update \"TRMS\".approval_form set consent_to_exceed = ? where form_number = " + formNumber;
		try(Connection conn = connUtil.createConnection()){
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setBoolean(1, update);
			int rowsAffected = preparedStatement.executeUpdate();
			log.info("Data Updated");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Employee retrieveEmployee(String username) {
		String query = "select * from \"TRMS\".employee where username = '"+ username +"';";
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
				emp = new Employee(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
				break;
			case "Direct Supervisor":
				emp = new DirectSupervisor(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
				break;
			case "Department Head":
				emp = new DepartmentHead(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
				break;
			case "Benefits Coordinator":
				emp = new BenCo(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
				break;
			case "BenCo Direct Supervisor":
				emp = new BenCoSupervisor(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
			}
			}log.info("Data retrieved from Database");}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	

	@Override
	public Form retrieveAppForm(int appNumber) {
		String query = "select * from \"TRMS\".form where form_number = (select form_number from \"TRMS\".approval_form where approval_id = " + appNumber + ");";
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
			byte[] attachments = rs.getBytes("attachments");
			double projectedMoney = rs.getDouble("projected_reimbursement");
			double cost = rs.getDouble("costs");
			boolean approvalStatus = rs.getBoolean("approval_status");
			boolean isUrgent = rs.getBoolean("is_urgent");
			int passId = rs.getInt("employee_id");
			int formNumber = rs.getInt("form_number");
			LocalTime timePosted = rs.getObject("time_posted", LocalTime.class);
			
			form = new Form(date, time, location, description, grading, type, attachments, projectedMoney, approvalStatus, isUrgent, passId, cost, timePosted);
			form.setFormNumber(formNumber);}log.info("Data retrieved from Database");}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return form;
		
	}
	
	

	@Override
	public Employee retrieveEmployeeApp(int appNumber) {
		String query = "select * from \"TRMS\".employee where employee_id = (select employee_id from \"TRMS\".form where form_number = (select form_number from \"TRMS\".approval_form where approval_id = " + appNumber + "));";
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
				emp = new Employee(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
				break;
			case "Direct Supervisor":
				emp = new DirectSupervisor(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
				break;
			case "Department Head":
				emp = new DepartmentHead(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
				break;
			case "Benefits Coordinator":
				emp = new BenCo(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
				break;
			case "BenCo Direct Supervisor":
				emp = new BenCoSupervisor(firstnames, lastnames, phoneNumber, addresse, availableAmount, superuser, empId, emailAdd, city, state, postalCode, usernames, password);
			}
			}log.info("Data retrieved from Database");}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public List<WaitList> retrieveWaitList(String authority, int emp) {
		int counter = 0;
		String sql ="";
		if(authority.equals("Direct Supervisor")) {
		sql = "SELECT \"TRMS\".ds_waitlist.priority_number, \"TRMS\".ds_waitlist.approval_id, \"TRMS\".ds_waitlist.ds_approval, \"TRMS\".ds_waitlist.reason_for_denial, \"TRMS\".ds_waitlist.add_info_req, \"TRMS\".ds_waitlist.is_urgent, \"TRMS\".ds_waitlist.add_info_target\r\n"
				+ "FROM \"TRMS\".ds_waitlist inner join \"TRMS\".approval_form on \"TRMS\".ds_waitlist.approval_id = \"TRMS\".approval_form.approval_id\r\n"
				+ "inner join \"TRMS\".form on \"TRMS\".approval_form.form_number = \"TRMS\".form.form_number inner join \"TRMS\".employee on \"TRMS\".form.employee_id = \"TRMS\".employee.employee_id\r\n"
				+ "WHERE \"TRMS\".employee.employee_id <> " + emp;
		counter = 1;
		}
		else if (authority.equals("Department Head")) {
			sql = "SELECT \"TRMS\".dh_waitlist.priority_number, \"TRMS\".dh_waitlist.approval_id, \"TRMS\".dh_waitlist.ds_approval, \"TRMS\".dh_waitlist.dh_approval, \"TRMS\".dh_waitlist.reason_for_denial, \"TRMS\".dh_waitlist.add_info_req, \"TRMS\".dh_waitlist.add_info_target, \"TRMS\".dh_waitlist.isurgent\r\n"
					+ "FROM \"TRMS\".dh_waitlist inner join \"TRMS\".approval_form on \"TRMS\".dh_waitlist.approval_id = \"TRMS\".approval_form.approval_id\r\n"
					+ "inner join \"TRMS\".form on \"TRMS\".approval_form.form_number = \"TRMS\".form.form_number inner join \"TRMS\".employee on \"TRMS\".form.employee_id = \"TRMS\".employee.employee_id\r\n"
					+ "WHERE \"TRMS\".employee.employee_id <> " + emp;
			counter = 2;
		}
		else {
			sql = "SELECT \"TRMS\".benco_waitlist.priority_number, \"TRMS\".benco_waitlist.approval_id, \"TRMS\".benco_waitlist.ds_approval, \"TRMS\".benco_waitlist.dh_approval, \"TRMS\".benco_waitlist.reason_for_denial, \"TRMS\".benco_waitlist.add_info_req, \"TRMS\".benco_waitlist.add_info_target, \"TRMS\".benco_waitlist.amount_awarded, \"TRMS\".benco_waitlist.amount_exceeded, \"TRMS\".benco_waitlist.reason_to_exceed, \"TRMS\".benco_waitlist.benco_approval, \"TRMS\".benco_waitlist.is_urgent\r\n"
					+ "FROM \"TRMS\".benco_waitlist inner join \"TRMS\".approval_form on \"TRMS\".benco_waitlist.approval_id = \"TRMS\".approval_form.approval_id\r\n"
					+ "inner join \"TRMS\".form on \"TRMS\".approval_form.form_number = \"TRMS\".form.form_number inner join \"TRMS\".employee on \"TRMS\".form.employee_id = \"TRMS\".employee.employee_id\r\n"
					+ "WHERE \"TRMS\".employee.employee_id <> " + emp;
			counter = 3;
		}
		
		List<WaitList> waitList = new ArrayList<>();

		try (Connection conn = connUtil.createConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				waitList.add(makeList(resultSet, counter));
			}
			log.info("Data retrieved from Database");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return waitList;
	}
	
	
	
	@Override
	public List<Form> retrieveSubmittedForms(int emp) {
			String query = "select * from \"TRMS\".form where employee_id = " + emp;
			List<Form> myForms = new ArrayList<>();
			try (Connection conn = connUtil.createConnection()) {

				PreparedStatement pstmt = conn.prepareStatement(query);

				ResultSet resultSet = pstmt.executeQuery();
				
				while (resultSet.next()) {
					myForms.add(makeForms(resultSet));
				}
				log.info("Data retrieved from Database");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return myForms;
	}

	@Override
	public boolean updateWaitlist(int employeeNum, boolean apApprove, boolean addInfo, String reason, String authority, String target) {
		String query = "";
		boolean check = false;
		if(authority.equals("Direct Supervisor")){
		query = "update \"TRMS\".ds_waitlist set ds_approval = ?, reason_for_denial = ?, add_info_req = ? where approval_id = " + employeeNum;
		}
		else if(authority.equals("Department Head")) {
			if(target != null) {
			if(target.equals("Employee") || target.equals("Direct Supervisor")) {check = true;}
			}
			query = "update \"TRMS\".dh_waitlist set dh_approval = ?, reason_for_denial = ?, add_info_req = ?" + addString(check) + " where approval_id = " + employeeNum;
		}
		
		else {return false;}
		
		try(Connection conn = connUtil.createConnection()){
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setBoolean(1, apApprove);
			preparedStatement.setString(2, reason);
			preparedStatement.setBoolean(3, addInfo);
			if(authority.equals("Department Head") && check == true) {
				preparedStatement.setString(4, target);
			}
			int rowsAffected = preparedStatement.executeUpdate();
			log.info("Data Updated");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	 return true;
		
	}

	
	
	
	@Override
	public boolean updateBencoWaitlist(int employeeNum, boolean apApprove, boolean addInfo, String reason,
			String authority, String target, boolean willExceed, String reasonMon, double amountExceed) {
		String query = "";
		boolean checkOne = false;
		if(authority.equals("Benefits Coordinator")) {
			if(target != null) {
			if(target.equals("Employee") || target.equals("Direct Supervisor") || target.equals("Department Head")) {checkOne = true;}
			}
			query = "update \"TRMS\".benco_waitlist set benco_approval = ?, reason_for_denial = ?, add_info_req = ?" + addBencoString(checkOne, willExceed) + " where approval_id = " + employeeNum;
		}
		
		else {return false;}
		
		try(Connection conn = connUtil.createConnection()){
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setBoolean(1, apApprove);
			preparedStatement.setString(2, reason);
			preparedStatement.setBoolean(3, addInfo);
			if(checkOne == true) {
				preparedStatement.setString(4, target);
				if(willExceed == true) {
					preparedStatement.setBoolean(5, willExceed);
					preparedStatement.setString(6, reasonMon);
					preparedStatement.setDouble(7, amountExceed);
				}
			}
			if(willExceed == true && checkOne == false) {
				preparedStatement.setBoolean(4, willExceed);
				preparedStatement.setString(5, reasonMon);
				preparedStatement.setDouble(6, amountExceed);
				
			}
			int rowsAffected = preparedStatement.executeUpdate();
			log.info("Data Updated");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public TrmsDaoPostgres() {
		
	}

	
	public WaitList makeList(ResultSet rs, int autoCounter) throws SQLException{
		if(autoCounter == 1) {
		return new WaitList(rs.getInt(1), rs.getInt(2), rs.getBoolean(3), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6), rs.getString(7));
		}else if(autoCounter == 2) {
		return new WaitList(rs.getInt(1), rs.getInt(2), rs.getBoolean(4), rs.getString(5), rs.getBoolean(6), rs.getBoolean(8), rs.getString(7));
		}
		return new BenCoWaitlist(rs.getInt(1), rs.getInt(2), rs.getBoolean(11), rs.getString(5), rs.getBoolean(6), rs.getBoolean(12), rs.getString(7), rs.getBoolean(9), rs.getString(10), rs.getDouble(8));
	}
	
	
	public Form makeForms(ResultSet rs)throws SQLException{
		Form app = new Form(rs.getObject(3, LocalDate.class), rs.getObject(4, LocalTime.class), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getBytes(9), rs.getDouble(10), rs.getBoolean(11), rs.getBoolean(12), rs.getInt(2), rs.getDouble(13), rs.getObject(14, LocalTime.class));
		app.setFormNumber(rs.getInt(1));
		return app;
		
	}
	
	public String addString(boolean check) {
		if(check == true) {
			return ", add_info_target = ?::\"TRMS\".job";
		}
		return "";
	}
	
	public String addBencoString(boolean checkOne, boolean checkTwo) {
		String additionals = "";
		if(checkOne == true) {
			additionals = additionals + ", add_info_target = ?::\"TRMS\".job";
		}
		if(checkTwo == true) {
			additionals = additionals + ", amount_exceeded = ?, reason_to_exceed = ?, amount_awarded = ?";
		}
		return additionals;
	}
	
	
}
