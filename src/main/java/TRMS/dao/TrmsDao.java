package TRMS.dao;

import java.util.List;

import TRMS.pojos.*;


public interface TrmsDao {
		
	public void createForm(Form paper);
		
	public Form retrieveForm(int employeeNumber);
	
	public Employee retrieveEmployee(String username);
	
	public boolean updateForm(int formNumber, String area, String change);
	
	public void deleteForm(int formNumber);
	
	public Form retrieveAppForm(int appNumber);
	
	public Employee retrieveEmployeeApp(int appNumber);
	
	public List<WaitList> retrieveWaitList(String authority, int emp);
	
	public boolean updateWaitlist(int employeeNum, boolean apApprove, boolean addInfo, String reason, String authority, String target);

	public boolean updateBencoWaitlist(int employeeNum, boolean apApprove, boolean addInfo, String reason, String authority, String target, boolean willExceed, String reasonMon, double amountExceed);
}
