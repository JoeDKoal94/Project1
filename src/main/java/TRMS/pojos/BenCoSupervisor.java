package TRMS.pojos;

public class BenCoSupervisor extends Employee {

	private String authority = "BenCo Direct Supervisor";
	


	public BenCoSupervisor(String firstName, String lastName, String phoneNumber, String address,
			double availableAmount, String authority, int employeeId, String emailAdd, String city, String state,
			String postalCode, String username, String password) {
		super(firstName, lastName, phoneNumber, address, availableAmount, authority, employeeId, emailAdd, city, state,
				postalCode, username, password);
		// TODO Auto-generated constructor stub
	}



	public BenCoSupervisor() {
		
	}

}
