

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost/diplomarbeit";

	static final String USER = "postgres";
	static final String PASS = "password";

	static Connection conn = null;

	static int minPW = 8; 
	static int maxPW = 16; 

	static int digit = 2;
	static int upCount = 1;
	static int loCount = 1;

	static int specialUN = 0;

	public RegisterServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String pwd = request.getParameter("password");
		String pwdwh = request.getParameter("passwordrepeat");

		PrintWriter out = response.getWriter();  
		response.setContentType("text/html");  

		try{  
			Class.forName("JDBC_DRIVER");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Connecting DB successful");

			PreparedStatement ps = conn.prepareStatement( "INSERT into registeruser values(?,?,?,?)");  

			ps.setString(1,username);  
			if (pwdIsValid(pwd)) {
				ps.setString(2,pwd);  
				ps.setString(3,pwdwh); 
			}


			int i = ps.executeUpdate(); 

			if(i>0)  
				out.print("Sie wurden erfolgreich angemeldet...");  

		}catch (Exception e) {

			System.out.println("Registrier - Fehlermeldung: " +e);
		}  

		out.close();  
	}  


	public static boolean usernIsValid(String username) {

		//TODO Aus DB abfragen, bereits vorhanden

		for(int i = 0; i < username.length(); i++){

			char c = username.charAt(i);

			if(c >= 33 && c <= 46 ||c == 64){

				specialUN++;
				System.out.println("Achtung, bitte keine Sonderzeichen im Benutzername verwenden");
				return false;
			}
		}

		if(username.length() >= 3 && username.length() <= 15 && specialUN == 0 )
		{
			System.out.println("Username "+ username +" darf verwendet werden!");
			return true; 

		}else {
			
			if(specialUN < 0 ) {
				
//				PrintWriter out = response.getWriter();  
//				response.setContentType("text/html");  
//				out.println("<script type=\"text/javascript\">");  
//				out.println("alert(' \"Achtung! Username oder Passwort sind nicht korrekt\"');");  
//				out.println("</script>");

				
				System.out.println("Achtung es duerfen keine Sonderzeichen verwendet werden!");
			}
			System.out.println("Username ungueltig");
			return false;
		}
	}

	public static boolean pwdIsValid(String password) {

		if(password.length() >= minPW && password.length() <= maxPW){

			for(int i = 0; i < password.length(); i++){
				char c = password.charAt(i);
				if(Character.isUpperCase(c)){
					upCount++;
				}
				if(Character.isLowerCase(c)){
					loCount++;
				}
				if(Character.isDigit(c)){
					digit++;
				}
			}

			if(loCount >= 1 && upCount >= 1 && digit >= 1){
				System.out.println("Passwort ist OK ");
			}

		}

		if(password.length() < minPW){

			for(int i = 0;i < password.length(); i++){
				char c = password.charAt(i);
				if(Character.isLowerCase(c)){
					loCount++;
				}
			}

			if(loCount > 0){
				System.out.println(" Password must be atleast "+minPW+" characters:");
				System.out.println(" You need atleast one upper case chracter:");
				System.out.println(" You need atleast one digit:");
			}
		}

		else if(password.length() < minPW && upCount > 1){

			for(int i = 0; i < password.length(); i++){
				
				char c = password.charAt(i);
				
				if(Character.isLowerCase(c)){
					loCount++;
				}
				if(Character.isUpperCase(c)){
					upCount++;

				}
			}
			if(loCount > 0 && upCount > 0){
				System.out.println(" Password must be atleast "+minPW+" chracters:");
				System.out.println(" You need atleast one digit:");
				return false;
			}
		}

		if(password.length() > maxPW|| password.length() >= maxPW && upCount > 1 &&loCount > 1 && digit > 1){

			System.out.println(" Password is too long.Limit is "+maxPW+" chracters:");
			return false;
		}

		if(password.length() >= minPW && password.length() <= maxPW && loCount > 0 && upCount > 0 && digit == 0){
			System.out.println(" You need atleast one digit:");
			return false;
		}
		return false;
	}

}
