package TRMS;

import java.util.Timer;

import TRMS.controller.AuthController;
import TRMS.controller.TrmsController;
import TRMS.dao.TimerDao;
import io.javalin.Javalin;

public class ServiceDriver {

	private static TrmsController trmsController = new TrmsController();
	private static AuthController authController = new AuthController();
	private static final String LOG_IN = "/auth";
	private static final String FORM_PATH = "/form";
	private static final String EMP_MENU = "/menu";
	private static final String EMP_INFO = "/menu/employee";
	public static void main(String[] args) {
TimerDao schedUpdate = new TimerDao("postgres");
		
//up to date

		Timer t = new Timer();
		t.scheduleAtFixedRate(schedUpdate, 0, 30000);
		Javalin app = Javalin.create( config -> {
			config.addStaticFiles("/public");
	}).start(9090);
		
		app.post(LOG_IN, ctx -> authController.login(ctx));
		app.get(LOG_IN, ctx -> authController.checkUser(ctx));
		app.post(FORM_PATH, ctx -> trmsController.createForm(ctx));
		app.get(EMP_INFO, ctx -> trmsController.viewFormLists(ctx));
		app.post(EMP_INFO, ctx -> trmsController.deleteTheForm(ctx));
		app.get(EMP_INFO + "/view", ctx -> trmsController.viewForm(ctx));
		app.post(EMP_INFO + "/view", ctx -> trmsController.updateForm(ctx));
		app.get(EMP_INFO + "/view/application-status", ctx -> trmsController.viewAppStatus(ctx));
		app.post(EMP_INFO + "/view/application-status", ctx -> trmsController.updateTheConsent(ctx));
		app.post(EMP_INFO + "/view/application-status/after-form", ctx -> trmsController.applyAfterForm(ctx));
		app.get(EMP_INFO + "/view/application-status/after-form", ctx -> trmsController.viewWaitForm(ctx));
		app.get(EMP_MENU + "/ds", ctx -> trmsController.viewDSList(ctx));
		app.get(EMP_MENU + "/dsDhV", ctx -> trmsController.viewWaitForm(ctx));
		app.post(EMP_MENU + "/dsDhV", ctx -> trmsController.updateTheWaitlists(ctx));
		app.get(EMP_MENU + "/dh", ctx -> trmsController.viewDHList(ctx));
		app.get(EMP_MENU + "/benco", ctx -> trmsController.viewBenCoList(ctx));
		app.get(EMP_MENU + "/bencoV", ctx -> trmsController.viewWaitForm(ctx));
		app.post(EMP_MENU + "/bencoV", ctx -> trmsController.updateTheBencoWaitList(ctx));
		app.get(EMP_MENU + "/viewEmp", ctx -> trmsController.viewEmployee(ctx));
		app.get(EMP_MENU + "/dsDhV/viewEmpApp", ctx -> trmsController.viewEmpByApp(ctx));
	}

}


