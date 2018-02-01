import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBManager;

/**
 * Servlet implementation class EmailPasswort
 */
@WebServlet("/EmailPasswort")
public class EmailPasswort extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost/diplomarbeit";

	static final String USER = "postgres";
	static final String PASS = "password";

	static Connection conn = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("PasswordHelp.jsp");
		String emailuser = "";
		String user;

		try {
			DBManager db = new DBManager();

			Class.forName(JDBC_DRIVER);
			Connection conn=db.getConnection();
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			emailuser = request.getParameter("email");
			//			user = request.getParameter("user");

			String checkUser = db.getUserByEmail(conn, emailuser);
			//			String checkMail = db.getEmailByUser(conn, user);

			String getUser = db.getUser(conn, checkUser);
			//			String getMail = db.getEmail(conn, checkMail);
			String checkMail = db.getEmailByUser(conn, getUser);
			String getMail = db.getEmail(conn, checkMail);

			String subject = "Passwort zurücksetzen EasyDocs";
			String message = "";

			TokenGenerator tg = new TokenGenerator();
			String authcode = tg.generateCode();

			db.saveHash(conn,authcode,getMail);

			response.setContentType("text/html");

			if( (getMail != null)){



				System.out.println("User existiert, Mail kann versendet werden. . . "); // Bei erstaufruf jsp seite kein modal? 
				SendEMail mailer = new SendEMail();



				try {
					message = "Lieber EasyPDF Nutzer, um dein Passwort zurückzusetzten bitte folgenden Link öffnen: "

					+"\n\n http://localhost:8080/DiplomPdf/CheckReset?authcode="+authcode
					+"\n\n  Viel Spaß bei der weiteren Nutzung von EasyPDF wünscht das TEAM: "
					+ "\n\n \n\n \t Thomas Kerber, Verena Gurtner & Sara Hindelang";

					mailer.sendPlainTextEmail( emailuser, subject, message);

					request.setAttribute("message", "Die Email wurde versendet, bitte öffne dein Postfach" );
					rd.include(request, response);

					rd.include(request, response); 
					System.out.println("Email wurde gesendet.");

				} catch (Exception ex) {
					System.out.println("Email konnte nicht gesendet werden");
					request.setAttribute("message", "Unsere Server sind derzeit nicht erreichbar. Bitte versuche es später noch einmal." );
					ex.printStackTrace();
				}

			}else{

				request.setAttribute("message", "Email kann nicht verwendet werden, Email nicht registriert!");
				rd.include(request, response);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}