package TRMS.pojos;

import java.sql.Blob;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Form {

	private int formNumber;
	private int employeeId;
	private LocalDate eventDate;
	private LocalTime eventTime;
	private String eventLocation;
	private String description;
	private String gradingFormat;
	private String typeOfEvent;
	private Blob attachments;
	private double projectedAmount;
	private boolean approvalStatus;
	private boolean isUrgent;
	
	
	
	
	public Form(LocalDate eventDate, LocalTime eventTime, String eventLocation,
			String description, String gradingFormat, String typeOfEvent, Blob attachments, double projectedAmount,
			boolean approvalStatus, boolean isUrgent, int employeeId) {
		super();
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.eventLocation = eventLocation;
		this.description = description;
		this.gradingFormat = gradingFormat;
		this.typeOfEvent = typeOfEvent;
		this.attachments = attachments;
		this.projectedAmount = projectedAmount;
		this.approvalStatus = approvalStatus;
		this.isUrgent = isUrgent;
		this.employeeId = employeeId;
	}


	public Form() {
		
	}


	public int getFormNumber() {
		return formNumber;
	}


	public void setFormNumber(int formNumber) {
		this.formNumber = formNumber;
	}


	public int getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}


	public LocalDate getEventDate() {
		return eventDate;
	}


	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}


	public LocalTime getEventTime() {
		return eventTime;
	}


	public void setEventTime(LocalTime eventTime) {
		this.eventTime = eventTime;
	}


	public String getEventLocation() {
		return eventLocation;
	}


	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getGradingFormat() {
		return gradingFormat;
	}


	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}


	public String getTypeOfEvent() {
		return typeOfEvent;
	}


	public void setTypeOfEvent(String typeOfEvent) {
		this.typeOfEvent = typeOfEvent;
	}


	public Blob getAttachments() {
		return attachments;
	}


	public void setAttachments(Blob attachments) {
		this.attachments = attachments;
	}


	public double getProjectedAmount() {
		return projectedAmount;
	}


	public void setProjectedAmount(double projectedAmount) {
		this.projectedAmount = projectedAmount;
	}


	public boolean isApprovalStatus() {
		return approvalStatus;
	}


	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}


	public boolean isUrgent() {
		return isUrgent;
	}


	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	
	
}
