package TRMS.service;

public interface AuthStack {
		
		public boolean authenticateUser(String username, String password);
		
		public String createToken(String username);
		
		public String validateToken(String token);

}
