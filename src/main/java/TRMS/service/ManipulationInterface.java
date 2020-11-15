package TRMS.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import TRMS.pojos.Employee;
import TRMS.pojos.Form;

public interface ManipulationInterface {

	public Form createForm(LocalDate date, LocalTime time, String location, String description, String grading, String typeOfEvent, double projectedAmount, boolean isUrgent, int employeeId);
	
	public boolean logInEmp(String username, String password);
	
	public Employee fetchEmployee(String username);
}
