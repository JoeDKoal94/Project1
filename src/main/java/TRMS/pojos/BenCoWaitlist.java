package TRMS.pojos;

public class BenCoWaitlist extends WaitList {

	
	private boolean amountExceed;
	private String reasonExceed;
	private double amountAwarded;
	
	



	public BenCoWaitlist(int priorityNumber, int approvalId, boolean dsApproval, String reasonForDenial,
			boolean infoReq, boolean isUrgent, String target, boolean amountExceed, String reasonExceed, double amountAwarded) {
		super(priorityNumber, approvalId, dsApproval, reasonForDenial, infoReq, isUrgent, target);

		this.amountExceed = amountExceed;
		this.reasonExceed = reasonExceed;
		this.amountAwarded = amountAwarded;
	}



	public BenCoWaitlist() {
		
	}



	public boolean isAmountExceed() {
		return amountExceed;
	}



	public void setAmountExceed(boolean amountExceed) {
		this.amountExceed = amountExceed;
	}



	public String getReasonExceed() {
		return reasonExceed;
	}



	public void setReasonExceed(String reasonExceed) {
		this.reasonExceed = reasonExceed;
	}



	public double getAmountAwarded() {
		return amountAwarded;
	}



	public void setAmountAwarded(double amountAwarded) {
		this.amountAwarded = amountAwarded;
	}

	
	
}
