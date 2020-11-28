package TRMS.pojos;

public class DirectSupervisor extends Employee {
	
	private String authority = "Direct Supervisor";
	private boolean approveRequest;
	
	

	


	public DirectSupervisor(String firstName, String lastName, String phoneNumber, String address,
			double availableAmount, String authority, int employeeId, String emailAdd, String city, String state,
			String postalCode, String username, String password) {
		super(firstName, lastName, phoneNumber, address, availableAmount, authority, employeeId, emailAdd, city, state,
				postalCode, username, password);
	}




	public String getAuthority() {
		return authority;
	}




	public void setAuthority(String authority) {
		this.authority = authority;
	}




	public boolean isApproveRequest() {
		return approveRequest;
	}




	public void setApproveRequest(boolean approveRequest) {
		this.approveRequest = approveRequest;
	}




	public DirectSupervisor() {
		
	}
	
}
