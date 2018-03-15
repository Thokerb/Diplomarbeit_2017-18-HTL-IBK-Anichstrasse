import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBManager;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final int minPW = 8; 
	static final int maxPW =  20;
	
	static int digit;
	int code = -1;

	static int specialUN;

	public RegisterServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PasswordHash pwh = new PasswordHash();
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String pwd = request.getParameter("password");
		
		response.setContentType("text/html");  
		boolean registerok = false;

		if(pwdIsValid(pwd) && usernIsValid(username)) {
			if(userDB(username) == false) {
				try {
					DBManager m = new DBManager();
					Connection conn = m.getConnection();
					
					String hashpw = pwh.passwordToHash(pwd);
					System.out.println("Hash: "+ hashpw);
					
					m.RegisterBenutzer(conn,username, email, hashpw);
					code = 0;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					code = 1;
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					code = 3;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		else {
			code = -1;
		}

		RequestDispatcher rd = request.getRequestDispatcher("Register.jsp");

		switch(code) {
		case 0: 
			request.setAttribute("message", "Du wurdest erfolgreich registriert");
			rd.include(request, response);

			String subject = "Anmeldung EasyDocs";
			String message = "";

			SendEMail mailer = new SendEMail();

			try {
				message = "Herzlich willkommen lieber neuer EasyPDF Nutzer, "
						+"\n\n  Viel Spaß bei der Nutzung von EasyPDF wünscht das TEAM: "
						+ "\n\n \n\n \t Thomas Kerber, Verena Gurtner & Sara Hindelang";

				mailer.sendPlainTextEmail( email, subject, message);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			break;
		case 1: 
			System.out.println("Fehlercode von Servlet: "+ code);
			request.setAttribute("message", "Registrieren fehlgeschlagen, DB Fehler" );
			rd.include(request, response); 
			break;
		case 2:
			request.setAttribute("message", "Registrieren fehlgeschlagen, Passwort inkorrekt" );
			rd.include(request, response); 
			break;
		case 3: 
			request.setAttribute("message", "Registrieren fehlgeschlagen, Email oder Username bereits registriert" );
			rd.include(request, response); 
			break;

		default:
			request.setAttribute("message", "Ein unbekannter Fehler ist aufgetreten");
			rd.include(request, response);
			break;
		}
	}

	public static boolean usernIsValid(String username) {
		boolean unok; 

		for(int i = 0; i < username.length(); i++){

			char c = username.charAt(i);
			if(c >= 33 && c <= 46 ||c == 64){

				specialUN++;
				System.out.println("Achtung, bitte keine Sonderzeichen im Benutzername verwenden");
				unok = false; 
				break;
			}
		}

		if(username.length() >= 3 && username.length() <= 15 && specialUN == 0) //null ok? laut DBManager zuerst null 
		{
			System.out.println("Gültigkeit: Username "+ username +" darf verwendet werden!");
			unok = true;
		}else {
			System.out.println("Gültigkeit: Username ungültig, bitte erneut eingeben");
			unok = false;
		}
		return unok; 
	}

	public static boolean userDB(String username) {
		boolean userDB = false; 
		try {
			DBManager dbm = new DBManager();
			Connection conn = dbm.getConnection();

			if( dbm.getUser(conn, username) != null) {
				System.out.println("Username "+ username +" darf nicht verwendet werden, er existiert bereits!");
				userDB = true; 
				return userDB;
			}
			else{
				System.out.println("Username "+ username +" darf verwendet werden, er existiert noch nicht!");
				userDB = false; 
				return userDB;
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return userDB; 
	}

	public static boolean pwdIsValid(String password) {

		boolean pwvalid = false; 
		if(password.length() >= minPW && password.length() <= maxPW){
			for(int i = 0; i < password.length(); i++){
				char c = password.charAt(i);

				if(Character.isDigit(c)){
					digit++;
				}
			}

			if( digit >= 1){
				pwvalid = true; 
				return pwvalid; 
			}
		}
		else {

			System.out.println("Passwortlänge falsch!");
			pwvalid = false; 
			return pwvalid; 
		}
		return pwvalid; 
	}
}
