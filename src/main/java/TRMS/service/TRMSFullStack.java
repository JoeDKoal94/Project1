package TRMS.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import TRMS.dao.*;
import TRMS.pojos.Employee;
import TRMS.pojos.Form;
import TRMS.pojos.WaitList;

public class TRMSFullStack implements ManipulationInterface{

	TrmsDao theDao = new TrmsDaoPostgres();
	
	@Override
	public Form createForm(LocalDate date, LocalTime time, String location, String description, String grading,
			String typeOfEvent, byte[] attachments, double cost, boolean isUrgent, int employeeId, double projectedAmount) {
		
		
		Form form = new Form(date, time, location, description, grading, typeOfEvent, attachments, cost, false, isUrgent, employeeId, projectedAmount, LocalTime.now());
		theDao.createForm(form);
		return form;
	}


	
	@Override
	public Employee fetchEmployee(String username) {
		return theDao.retrieveEmployee(username);
	}



	@Override
	public boolean updateForm(int formNumber, String area, String change) {
		return theDao.updateForm(formNumber, area, change);
	}


	@Override
	public double calculatePendingMoney(double availableFunds, String type) {
		switch(type) {
		
		case "University Courses":
			return availableFunds * .80;
		case "Seminars":
			return availableFunds * .60;
		case "Certification Preparation Classes":
			return availableFunds * .75;
		case "Certification":
			return availableFunds;
		case "Technical Training":
			return availableFunds * .90;
		case "Other":
			return availableFunds * .30;
			
		}
		return 0;
	}

	@Override
	public Employee updateEmployee(int employeeId, String area, String change) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Form fetchForm(int employeeNumber) {
		return theDao.retrieveForm(employeeNumber);
	}

	@Override
	public List<WaitList> fetchWaitList(String authority, int emp){
		return theDao.retrieveWaitList(authority, emp);
	}
	

	@Override
	public Form fetchAppForm(int appNum) {
		return theDao.retrieveAppForm(appNum);
	}



	@Override
	public Employee fetchAppEmp(int appNum) {
		return theDao.retrieveEmployeeApp(appNum);
	}



	@Override
	public byte[] readFully(InputStream input) throws IOException {
		 byte[] buffer = new byte[8192];
		    int bytesRead;
		    ByteArrayOutputStream output = new ByteArrayOutputStream();
		    while ((bytesRead = input.read(buffer)) != -1)
		    {
		        output.write(buffer, 0, bytesRead);
		    }
		    return output.toByteArray();
	}


	@Override
	public boolean updateTheWaitlist(int apNumber, boolean dsApproval, boolean addInfo, String reason, String authority, String target) {
		return theDao.updateWaitlist(apNumber, dsApproval, addInfo, reason, authority, target);
	}



	@Override
	public boolean updateTheBencoWaitlist(int apNumber, boolean dsApproval, boolean addInfo, String reason,
			String authority, String target, boolean willExceed, String reasonMon, double amountExceed) {
		return theDao.updateBencoWaitlist(apNumber, dsApproval, addInfo, reason, authority, target, willExceed, reasonMon, amountExceed);
	}



	public TRMSFullStack() {
		
	}

}
