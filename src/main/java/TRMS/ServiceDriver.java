package TRMS;

import TRMS.controller.TrmsController;
import io.javalin.Javalin;

public class ServiceDriver {

	private static TrmsController trmsController = new TrmsController();
	private static final String LOG_IN = "/auth";
	private static final String FORM_PATH = "/form";
	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(9090);
		
		app.post(LOG_IN, ctx -> trmsController.logInEmp(ctx));
		app.post(FORM_PATH, ctx -> trmsController.createForm(ctx));
		
	}

}
