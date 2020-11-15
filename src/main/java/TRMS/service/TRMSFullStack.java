package TRMS.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import TRMS.dao.*;
import TRMS.pojos.Employee;
import TRMS.pojos.Form;

public class TRMSFullStack implements ManipulationInterface{

	TrmsDao theDao = new TrmsDaoPostgres();
	
	@Override
	public Form createForm(LocalDate date, LocalTime time, String location, String description, String grading,
			String typeOfEvent, double projectedAmount, boolean isUrgent, int employeeId) {
		
		Form form = new Form(date, time, location, description, grading, typeOfEvent, null, projectedAmount, false, isUrgent, employeeId);
		theDao.createForm(form);
		return form;
	}

	
	
	@Override
	public boolean logInEmp(String username, String password) {
		if(theDao.retrieveEmployee(username).getUsername().equals(username)  && theDao.retrieveEmployee(username).getPassword().equals(password) ) {
			System.out.println("Log In Successful.");
			return true;
	}
	else {
		System.out.println("Invalid Username or Password.");
	}
		return false;
	}

	
	@Override
	public Employee fetchEmployee(String username) {
		return theDao.retrieveEmployee(username);
	}



	public TRMSFullStack() {
		
	}

}
