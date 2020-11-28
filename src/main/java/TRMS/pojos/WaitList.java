package TRMS.pojos;

public class WaitList {

	private int priorityNumber;
	private int approvalId;
	private boolean dsApproval;
	private String reasonForDenial;
	private boolean infoReq;
	private boolean isUrgent;
	private String target;
	
	
	public WaitList() {
		
	}

	

	public WaitList(int priorityNumber, int approvalId, boolean dsApproval, String reasonForDenial, boolean infoReq,
			boolean isUrgent, String target) {
		super();
		this.priorityNumber = priorityNumber;
		this.approvalId = approvalId;
		this.dsApproval = dsApproval;
		this.reasonForDenial = reasonForDenial;
		this.infoReq = infoReq;
		this.isUrgent = isUrgent;
		this.target = target;
	}




	public int getPriorityNumber() {
		return priorityNumber;
	}


	public void setPriorityNumber(int priorityNumber) {
		this.priorityNumber = priorityNumber;
	}


	public int getApprovalId() {
		return approvalId;
	}


	public void setApprovalId(int approvalId) {
		this.approvalId = approvalId;
	}


	public boolean isDsApproval() {
		return dsApproval;
	}


	public void setDsApproval(boolean dsApproval) {
		this.dsApproval = dsApproval;
	}


	public String getReasonForDenial() {
		return reasonForDenial;
	}


	public void setReasonForDenial(String reasonForDenial) {
		this.reasonForDenial = reasonForDenial;
	}


	public boolean isInfoReq() {
		return infoReq;
	}


	public void setInfoReq(boolean infoReq) {
		this.infoReq = infoReq;
	}


	public boolean isUrgent() {
		return isUrgent;
	}


	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}



	public String getTarget() {
		return target;
	}



	public void setTarget(String target) {
		this.target = target;
	}

	
	
}
