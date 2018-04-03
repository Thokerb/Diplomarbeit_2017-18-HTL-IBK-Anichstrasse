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
 * 
 * Servlet dient dazu, eine Email durch die Klasse welche es ermöglicht Emails zu erstellen, mit den zugehörigen Parametern zu senden 
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

		response.setContentType("text/html");  
		RequestDispatcher rd = request.getRequestDispatcher("PasswordHelp.jsp");
		String emailuser = "";
		String user;
		
		DBManager db = null;
		Connection conn = null;

		try {
			db = new DBManager();
			conn=db.getConnection();

			emailuser = request.getParameter("email");
			String checkUser = db.getUserByEmail(conn, emailuser);
			String getUser = db.getUser(conn, checkUser);
			String checkMail = db.getEmailByUser(conn, getUser);
			String getMail = db.getEmail(conn, checkMail);

			String subject = "Passwort zurücksetzen EasyDocs";
			String message = "";

			TokenGenerator tg = new TokenGenerator();
			String authcode = tg.generateMD5Hash();

			db.saveHash(conn,authcode,getMail);

			if( (getMail != null)){

				System.out.println("User existiert, Mail kann versendet werden. . . ");  
				SendEMail mailer = new SendEMail();

				try {
					message = "Lieber EasyDocs Nutzer, du hast diesen Link angefordert, um Dein Passwort zurückzusetzten."
							+ " Unter folgender Seite kannst Du für deinen alten Account ein neues Passwort setzen: "

					+"\n\n http://localhost:8080/DiplomPdf/CheckReset?authcode="+authcode
					
					+"\n\n  Viel Spaß bei der weiteren Nutzung von EasyDocs wünscht das TEAM: "
					+ "\n\n \n\n \t Thomas Kerber, Verena Gurtner & Sara Hindelang"
					+ "\n\n falls Du noch Fragen hast oder uns gerne etwas mitteilen würdest kannst Du uns immer unter dieser Emailaddress erreichen :) ";

					mailer.sendPlainTextEmail( emailuser, subject, message);

					request.setAttribute("message", "Die Email wurde versendet, bitte öffne dein Postfach" );
					rd.include(request, response);

					System.out.println("Email wurde gesendet.");

				} catch (Exception ex) {
					System.out.println("Email konnte nicht gesendet werden");
					request.setAttribute("message", "Unsere Server sind derzeit nicht erreichbar. Bitte versuche es später noch einmal." );
					rd.include(request, response);
					ex.printStackTrace();
				}

			}else{
				request.setAttribute("message", "Email kann nicht verwendet werden, Email nicht registriert!");
				rd.include(request, response);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.releaseConnection(conn);
		}
	}

}