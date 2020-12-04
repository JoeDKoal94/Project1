package TRMS.controller;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.apache.log4j.Logger;

import TRMS.pojos.*;
import TRMS.service.AuthStack;
import TRMS.service.AuthenStack;
import TRMS.service.ManipulationInterface;
import TRMS.service.TRMSFullStack;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

public class TrmsController {
	private static Logger log = Logger.getRootLogger();
	ManipulationInterface theStack = new TRMSFullStack();
	AuthStack authStack = new AuthenStack();
	Employee emp;
	Form form;
	int aformNumber;
	int bFormNumber;
	

	public void createForm(Context ctx) throws NullPointerException, IOException {
		
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		LocalDate date = LocalDate.parse(ctx.formParam("eventDate"));
		LocalTime time = LocalTime.parse(ctx.formParam("eventTime"));
		String location = ctx.formParam("eventLocation");
		String description = ctx.formParam("description");
		String grading = ctx.formParam("gradingFormat");
		String typeEvent = ctx.formParam("typeOfEvent");
		UploadedFile attachments = ctx.uploadedFile("files");
		byte[] attachment = null;
		try {
			if(attachments != null) {
			attachment = theStack.readFully(attachments.getContent());
			log.info("UploadedFile has converted into byte array");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		double cost = ctx.formParam("cost", Double.class).get();
		boolean isUrgent = Boolean.parseBoolean(ctx.formParam("isUrgent"));
		double projectedAmount = theStack.calculatePendingMoney(cost, typeEvent);
		if(projectedAmount > emp.getAvailableAmount()) {projectedAmount = emp.getAvailableAmount();}
		theStack.createForm(date, time, location, description, grading, typeEvent, attachment, projectedAmount, isUrgent, emp.getEmployeeId(), cost);
		log.info("Attempting to communicate with Stack to connect to database.");
		log.info("Form Created.");
		
	}
	
	public void viewForm(Context ctx) {
		bFormNumber = Integer.parseInt(ctx.cookie("numForm"));
		log.info("Fetched cookie " + bFormNumber + " to access desired Form");
		form = theStack.fetchForm(bFormNumber);
		ctx.json(form);
		log.info("Form Number" + bFormNumber + "has been fetched");
	}
	
	public void viewEmployee(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		log.info("retrieved Employee ID " + emp.getEmployeeId() + ", personal info accessed");
		ctx.json(emp);
	}
	
	public void viewAppStatus(Context ctx) {
		bFormNumber = Integer.parseInt(ctx.cookie("numForm"));
		log.info("Fetched cookie " + bFormNumber + " to access desired Application Form Details");
		ApplicationStatus app = theStack.fetchAppStatus(bFormNumber);
		ctx.json(app);
	}
	
	
	public void updateForm(Context ctx) {
		String area = ctx.formParam("area");
		String change = ctx.formParam("change");
		
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		log.info("Validating User before proceeding to update");
		form = theStack.fetchForm(emp.getEmployeeId());
		log.info("proceeding to update form");
		theStack.updateForm(form.getFormNumber(), area, change);
		
		if(area.equals("costs")) {
			theStack.updateForm(form.getFormNumber(), "projected_reimbursement", String.valueOf(theStack.calculatePendingMoney(Double.parseDouble(change), form.getTypeOfEvent())));
		}
		log.info("Successfully updated form");	
		
	}
	
	public void viewFormLists(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		log.info("Validating user before fetching list from database");
		List<Form> myForms = theStack.fetchForms(emp.getEmployeeId());
		log.info("Sending Data to be viewed in HTML");
		ctx.json(myForms);
	}
	
	public void viewDSList(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		log.info("Checking user if authorized to access feature");
		if(emp.getAuthority() == "Direct Supervisor") {
			
			List <WaitList> waitList = theStack.fetchWaitList(emp.getAuthority(), emp.getEmployeeId());
			ctx.json(waitList);
			
		}
		else {
			ctx.redirect("formMenu.html?error=unautorized-access");
			log.info("User not authorized to access feature");
		}
	}
	
	public void viewDHList(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		log.info("Checking user if authorized to access feature");
		if(emp.getAuthority().equals("Department Head")) {
			
			List <WaitList> waitList = theStack.fetchWaitList(emp.getAuthority(), emp.getEmployeeId());
			ctx.json(waitList);
			
		}
		else {
			ctx.redirect("formMenu.html?error=unautorized-access");
			log.info("User not authorized to access feature");
		}
	}
	
	public void viewBenCoList(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		log.info("Checking user if authorized to access feature");
		if(emp.getAuthority().equals("Benefits Coordinator")) {
			
			List <WaitList> waitList = theStack.fetchWaitList(emp.getAuthority(), emp.getEmployeeId());
			ctx.json(waitList);
			
		}
		else {
			ctx.redirect("formMenu.html?error=unautorized-access");
			log.info("User not authorized to access feature");
		}
	}
	
	
	public void viewWaitForm(Context ctx) {
		aformNumber = Integer.parseInt(ctx.cookie("numApp"));
		log.info("Retrieving Application form number " + aformNumber);
		form = theStack.fetchAppForm(aformNumber);
		ctx.json(form);
	}
	
	public void viewEmpByApp(Context ctx) {
		aformNumber = Integer.parseInt(ctx.cookie("numApp"));
		log.info("Retrieving Employee info from form number " + aformNumber);
		emp = theStack.fetchAppEmp(aformNumber);
		ctx.json(emp);
	}
	
	public void showAllForms(){
		
	}
	
	public void updateTheWaitlists(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		log.info("Checking user if authorized to access feature");
		boolean apApprove = Boolean.parseBoolean(ctx.formParam("apApprove"));
		boolean addInfo = Boolean.parseBoolean(ctx.formParam("needInfo"));
		String reason = ctx.formParam("reason");
		String target = ctx.formParam("target");
		theStack.updateTheWaitlist(aformNumber, apApprove, addInfo, reason, emp.getAuthority(), target);
		log.info("Updated The Waitlists");
	}
	
	public void updateTheBencoWaitList(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		log.info("Checking user if authorized to access feature");
		boolean apApprove = Boolean.parseBoolean(ctx.formParam("apApprove"));
		boolean addInfo = Boolean.parseBoolean(ctx.formParam("needInfo"));
		String reason = ctx.formParam("reason");
		String target = ctx.formParam("target");
		boolean willExceed = Boolean.parseBoolean(ctx.formParam("boolAmount"));
		String reasonMon = ctx.formParam("reasonMon");
		double amountExceed = ctx.formParam("exceedAmount", Double.class).get();
		theStack.updateTheBencoWaitlist(aformNumber, apApprove, addInfo, reason, emp.getAuthority(), target, willExceed, reasonMon, amountExceed);
		log.info("Updated the Waitlist");
	}
	
	
	public void deleteTheForm(Context ctx) {
		bFormNumber = Integer.parseInt(ctx.cookie("numForm"));
		log.info("Retrieving info on Form");
		theStack.deleteForm(bFormNumber);
		log.info("Form deleted");
	}
	
	public void updateTheConsent(Context ctx) {
		boolean update = Boolean.parseBoolean(ctx.formParam("update"));
		bFormNumber = Integer.parseInt(ctx.cookie("numForm"));
		theStack.updateMyConsent(bFormNumber, update);
		log.info("Application updated");
	}
	
	public void applyAfterForm(Context ctx) {
		bFormNumber = Integer.parseInt(ctx.cookie("numForm"));
		log.info("Fetched cookie " + bFormNumber + " to access desired Application Form Details");
		ApplicationStatus app = theStack.fetchAppStatus(bFormNumber);
		if(theStack.checkPass(bFormNumber)) {
		int appId = app.getApprovalId();
		String grade = ctx.formParam("grade");
		boolean apPass = Boolean.parseBoolean(ctx.formParam("passing"));
		theStack.createPress(appId, grade, apPass);
		}
	}
	
	public TrmsController() {
		
	}

}
