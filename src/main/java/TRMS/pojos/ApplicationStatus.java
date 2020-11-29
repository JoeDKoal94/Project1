package TRMS.pojos;

public class ApplicationStatus {

	private int approvalId;
	private int formNumber;
	private boolean dsApproval;
	private boolean dhApproval;
	private boolean bencoApproval;
	private String appStatus;
	private boolean isUrgent;
	private boolean amExceed;
	private double amAwarded;
	private String reaExceed;
	private boolean conExceed;
	
	
	
	
	
	public ApplicationStatus(int approvalId, int formNumber, boolean dsApproval, boolean dhApproval,
			boolean bencoApproval, String appStatus, boolean isUrgent, boolean amExceed, double amAwarded,
			String reaExceed, boolean conExceed) {
		super();
		this.approvalId = approvalId;
		this.formNumber = formNumber;
		this.dsApproval = dsApproval;
		this.dhApproval = dhApproval;
		this.bencoApproval = bencoApproval;
		this.appStatus = appStatus;
		this.isUrgent = isUrgent;
		this.amExceed = amExceed;
		this.amAwarded = amAwarded;
		this.reaExceed = reaExceed;
		this.conExceed = conExceed;
	}





	public int getApprovalId() {
		return approvalId;
	}





	public void setApprovalId(int approvalId) {
		this.approvalId = approvalId;
	}





	public int getFormNumber() {
		return formNumber;
	}





	public void setFormNumber(int formNumber) {
		this.formNumber = formNumber;
	}





	public boolean isDsApproval() {
		return dsApproval;
	}





	public void setDsApproval(boolean dsApproval) {
		this.dsApproval = dsApproval;
	}





	public boolean isDhApproval() {
		return dhApproval;
	}





	public void setDhApproval(boolean dhApproval) {
		this.dhApproval = dhApproval;
	}





	public boolean isBencoApproval() {
		return bencoApproval;
	}





	public void setBencoApproval(boolean bencoApproval) {
		this.bencoApproval = bencoApproval;
	}





	public String getAppStatus() {
		return appStatus;
	}





	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}





	public boolean isUrgent() {
		return isUrgent;
	}





	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}





	public boolean isAmExceed() {
		return amExceed;
	}





	public void setAmExceed(boolean amExceed) {
		this.amExceed = amExceed;
	}





	public double getAmAwarded() {
		return amAwarded;
	}





	public void setAmAwarded(double amAwarded) {
		this.amAwarded = amAwarded;
	}





	public String getReaExceed() {
		return reaExceed;
	}





	public void setReaExceed(String reaExceed) {
		this.reaExceed = reaExceed;
	}





	public boolean isConExceed() {
		return conExceed;
	}





	public void setConExceed(boolean conExceed) {
		this.conExceed = conExceed;
	}





	public ApplicationStatus() {
		
	}

}
