

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

	public RegisterServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		String pwdwh = request.getParameter("passwordrepeat");

		PrintWriter out = response.getWriter();  
		response.setContentType("text/html");  

		try{  
			Class.forName("JDBC_DRIVER");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting successful");

			PreparedStatement ps = conn.prepareStatement( "INSERT into registeruser values(?,?,?,?)");  

			ps.setString(1,username);  
			if (pwdIsValid(pwd)) {
				ps.setString(2,pwd);  
			}
			ps.setString(3,pwdwh);    

			int i = ps.executeUpdate(); 
			
			if(i>0)  
				out.print("Sie wurden erfolgreich angemeldet...");  


		}catch (Exception e) {

			System.out.println("Register- Fehlermeldung: " +e);
		}  

		out.close();  
	}  

	
	public static boolean usernIsValid(String username) {
		
		return false;
		
	}

	public static boolean pwdIsValid(String password) {

		if (password.length() < 8) { 
			
			return false;
			
		} else {    
			char c;
			int count = 1; 
			for (int i = 0; i < password.length() - 1; i++) {
				c = password.charAt(i);
				if (!Character.isLetterOrDigit(c)) {        
					return false;
				} else if (Character.isDigit(c)) {
					count++;
					if (count < 2)   {   
						return false;
					}   
				}
			}
		}
		return true;
	}

}
