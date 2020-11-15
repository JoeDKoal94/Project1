package TRMS.pojos;

public class Grading {

	private String gradingFormat;
	private char passingGrade;
	private boolean requiredPresentation;
	private char employeeCutOff;
	
	
	
	public Grading(String gradingFormat, char passingGrade, boolean requiredPresentation, char employeeCutOff) {
		super();
		this.gradingFormat = gradingFormat;
		this.passingGrade = passingGrade;
		this.requiredPresentation = requiredPresentation;
		this.employeeCutOff = employeeCutOff;
	}

	public Grading() {
		
	}

	public String getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public char getPassingGrade() {
		return passingGrade;
	}

	public void setPassingGrade(char passingGrade) {
		this.passingGrade = passingGrade;
	}

	public boolean isRequiredPresentation() {
		return requiredPresentation;
	}

	public void setRequiredPresentation(boolean requiredPresentation) {
		this.requiredPresentation = requiredPresentation;
	}

	public char getEmployeeCutOff() {
		return employeeCutOff;
	}

	public void setEmployeeCutOff(char employeeCutOff) {
		this.employeeCutOff = employeeCutOff;
	}
	
	

}
