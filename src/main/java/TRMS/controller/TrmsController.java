package TRMS.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import TRMS.pojos.*;
import TRMS.service.ManipulationInterface;
import TRMS.service.TRMSFullStack;
import io.javalin.http.Context;

public class TrmsController {
	ManipulationInterface theStack = new TRMSFullStack();
	Employee emp;

	public void createForm(Context ctx) {
		LocalDate date = LocalDate.parse(ctx.formParam("eventDate"));
		LocalTime time = LocalTime.parse(ctx.formParam("eventTime"));
		String location = ctx.formParam("eventLocation");
		String description = ctx.formParam("description");
		String grading = ctx.formParam("gradingFormat");
		String typeEvent = ctx.formParam("typeOfEvent");
		double projectedAmount = Double.parseDouble(ctx.formParam("projectedAmount"));
		boolean isUrgent = Boolean.parseBoolean(ctx.formParam("isUrgent"));
		
		theStack.createForm(date, time, location, description, grading, typeEvent, projectedAmount, isUrgent, emp.getEmployeeId());
		ctx.html("Form Created.");
		
	}
	
	
	public Employee logInEmp(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		if(theStack.logInEmp(username, password)) {
			emp = theStack.fetchEmployee(username);
			ctx.html("Log In Successful");
		}
		else{ctx.html("Invalid Username or Password");}
		return emp;
	}
	
	public TrmsController() {
		
	}

}
