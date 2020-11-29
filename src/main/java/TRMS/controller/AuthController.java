package TRMS.controller;

import org.apache.log4j.Logger;

import TRMS.service.AuthStack;
import TRMS.service.AuthenStack;
import io.javalin.http.Context;


public class AuthController {
	
	private static Logger log = Logger.getRootLogger();
	
	private AuthStack theAuth = new AuthenStack();

	public void login(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		boolean authenticated = theAuth.authenticateUser(username, password);
		if (authenticated) {
			//ctx.status(200);
			ctx.cookieStore("security", theAuth.createToken(username));
			ctx.redirect("formMenu.html");
			log.info("Username " + username + " has logged in");
			log.info("Log in successful");
		} else {
			//ctx.status(401);
			ctx.redirect("login.html?error=failed-login");
			log.info("Log in attempt failed");
		}
	}
	
	public void checkUser(Context ctx) {
		ctx.html(theAuth.validateToken(ctx.cookieStore("security")));
		
	}
	

}
