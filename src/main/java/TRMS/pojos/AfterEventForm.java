package TRMS.pojos;

public class AfterEventForm {

	private int appId;
	private String grade;
	private boolean passing;
	private boolean amountAwarded;
	
	
	
	public AfterEventForm(int appId, String grade, boolean passing, boolean amountAwarded) {
		super();
		this.appId = appId;
		this.grade = grade;
		this.passing = passing;
		this.amountAwarded = amountAwarded;
	}

	public AfterEventForm() {
		// TODO Auto-generated constructor stub
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public boolean isPassing() {
		return passing;
	}

	public void setPassing(boolean passing) {
		this.passing = passing;
	}

	public boolean getAmountAwarded() {
		return amountAwarded;
	}

	public void setAmountAwarded(boolean amountAwarded) {
		this.amountAwarded = amountAwarded;
	}
	
	
	
}
