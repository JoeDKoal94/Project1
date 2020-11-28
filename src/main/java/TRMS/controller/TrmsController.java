package TRMS.controller;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import TRMS.pojos.*;
import TRMS.service.AuthStack;
import TRMS.service.AuthenStack;
import TRMS.service.ManipulationInterface;
import TRMS.service.TRMSFullStack;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

public class TrmsController {
	ManipulationInterface theStack = new TRMSFullStack();
	AuthStack authStack = new AuthenStack();
	Employee emp;
	Form form;
	int aformNumber;
	

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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		double cost = ctx.formParam("cost", Double.class).get();
		boolean isUrgent = Boolean.parseBoolean(ctx.formParam("isUrgent"));
		double projectedAmount = theStack.calculatePendingMoney(cost, typeEvent);
		if(projectedAmount > emp.getAvailableAmount()) {projectedAmount = emp.getAvailableAmount();}
		theStack.createForm(date, time, location, description, grading, typeEvent, attachment, projectedAmount, isUrgent, emp.getEmployeeId(), cost);
		System.out.println("Form Created.");
		
	}
	
	public void viewForm(Context ctx) {
		
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		form = theStack.fetchForm(emp.getEmployeeId());
		ctx.json(form);
	}
	
	public void viewEmployee(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		ctx.json(emp);
	}
	
	
	public void updateForm(Context ctx) {
		String area = ctx.formParam("area");
		String change = ctx.formParam("change");
		
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		form = theStack.fetchForm(emp.getEmployeeId());
		
		theStack.updateForm(form.getFormNumber(), area, change);
		
		if(area.equals("costs")) {
			theStack.updateForm(form.getFormNumber(), "projected_reimbursement", String.valueOf(theStack.calculatePendingMoney(Double.parseDouble(change), form.getTypeOfEvent())));
		}
			
		
	}
	
	
	public void viewDSList(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		if(emp.getAuthority() == "Direct Supervisor") {
			
			List <WaitList> waitList = theStack.fetchWaitList(emp.getAuthority(), emp.getEmployeeId());
			ctx.json(waitList);
			
		}
	}
	
	public void viewDHList(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		if(emp.getAuthority().equals("Department Head")) {
			
			List <WaitList> waitList = theStack.fetchWaitList(emp.getAuthority(), emp.getEmployeeId());
			ctx.json(waitList);
			
		}
	}
	
	public void viewBenCoList(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		if(emp.getAuthority().equals("Benefits Coordinator")) {
			
			List <WaitList> waitList = theStack.fetchWaitList(emp.getAuthority(), emp.getEmployeeId());
			ctx.json(waitList);
			
		}
	}
	
	
	public void viewWaitForm(Context ctx) {
		aformNumber = Integer.parseInt(ctx.cookie("numApp"));
		form = theStack.fetchAppForm(aformNumber);
		ctx.json(form);
	}
	
	public void viewEmpByApp(Context ctx) {
		aformNumber = Integer.parseInt(ctx.cookie("numApp"));
		emp = theStack.fetchAppEmp(aformNumber);
		ctx.json(emp);
	}
	
	public void showAllForms(){
		
	}
	
	public void updateTheWaitlists(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		boolean apApprove = Boolean.parseBoolean(ctx.formParam("apApprove"));
		boolean addInfo = Boolean.parseBoolean(ctx.formParam("needInfo"));
		String reason = ctx.formParam("reason");
		String target = ctx.formParam("target");
		theStack.updateTheWaitlist(aformNumber, apApprove, addInfo, reason, emp.getAuthority(), target);
	}
	
	public void updateTheBencoWaitList(Context ctx) {
		emp = theStack.fetchEmployee(authStack.validateToken(ctx.cookieStore("security")));
		boolean apApprove = Boolean.parseBoolean(ctx.formParam("apApprove"));
		boolean addInfo = Boolean.parseBoolean(ctx.formParam("needInfo"));
		String reason = ctx.formParam("reason");
		String target = ctx.formParam("target");
		boolean willExceed = Boolean.parseBoolean(ctx.formParam("boolAmount"));
		String reasonMon = ctx.formParam("reasonMon");
		double amountExceed = Double.parseDouble(ctx.formParam("exceedAmount"));
		theStack.updateTheBencoWaitlist(aformNumber, apApprove, addInfo, reason, emp.getAuthority(), target, willExceed, reasonMon, amountExceed);
	}
	
	
	public TrmsController() {
		
	}

}
