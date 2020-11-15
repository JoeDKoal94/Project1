package TRMS.dao;

import TRMS.pojos.*;


public interface TrmsDao {
		
	public void createForm(Form paper);
		
	public Form retrieveForm(int formNumber);
	
	public Employee retrieveEmployee(String username);
	
	public Form updateForm(int formNumber);
	
	public void deleteForm(int formNumber);
}
