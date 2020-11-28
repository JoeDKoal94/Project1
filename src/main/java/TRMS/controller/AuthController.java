package TRMS.controller;

import TRMS.service.AuthStack;
import TRMS.service.AuthenStack;
import io.javalin.http.Context;

public class AuthController {
	

	
	private AuthStack theAuth = new AuthenStack();

	public void login(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		boolean authenticated = theAuth.authenticateUser(username, password);
		if (authenticated) {
			//ctx.status(200);
			ctx.cookieStore("security", theAuth.createToken(username));
			ctx.redirect("formMenu.html");
			System.out.println("Log in successful");
		} else {
			//ctx.status(401);
			ctx.redirect("login.html?error=failed-login");
		}
	}
	
	public void checkUser(Context ctx) {
		ctx.html(theAuth.validateToken(ctx.cookieStore("security")));
		
	}
	

}
