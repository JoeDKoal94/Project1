package TRMS.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import TRMS.pojos.AfterEventForm;
import TRMS.pojos.ApplicationStatus;
import TRMS.pojos.Employee;
import TRMS.pojos.Form;
import TRMS.pojos.WaitList;

public interface ManipulationInterface {

	public Form createForm(LocalDate date, LocalTime time, String location, String description, String grading, String typeOfEvent, byte[] attachments, double cost, boolean isUrgent, int employeeId, double projectedAmount);
	
	public AfterEventForm createPress(int appId, String grade, boolean passing);
	
	public boolean checkPass(int formNumber);
	
	public Employee fetchEmployee(String username);
	
	public boolean updateForm(int formNumber, String area, String change);
	
	public double calculatePendingMoney(double availableFunds, String type);
	
	public Employee updateEmployee(int employeeId, String area, String change);

	public Form fetchForm(int formNumber);
	
	public ApplicationStatus fetchAppStatus(int formNumber);
	
	public void deleteForm(int formNumber);
	
	public void updateMyConsent(int formNumber, boolean update);
	
	public List<WaitList> fetchWaitList(String authority, int emp);
	
	public List<Form> fetchForms(int emp);
	
	public Form fetchAppForm(int appNum);
	
	public Employee fetchAppEmp(int appNum);
	
	public byte[] readFully(InputStream input) throws IOException;
	
	public boolean updateTheWaitlist (int apNumber, boolean dsApproval, boolean addInfo, String reason, String authority, String target);

	public boolean updateTheBencoWaitlist (int apNumber, boolean dsApproval, boolean addInfo, String reason, String authority, String target, boolean willExceed, String reasonMon, double amountExceed);
}
