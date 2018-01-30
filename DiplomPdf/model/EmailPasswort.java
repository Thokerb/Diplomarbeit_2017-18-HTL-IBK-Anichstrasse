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

		String emailuser = "";
		String user;

		try {
			DBManager db = new DBManager();

			Class.forName(JDBC_DRIVER);
			Connection conn=db.getConnection();
			emailuser = request.getParameter("email");
			user = request.getParameter("user");

			//			String checkEMail = db.getEmailByUser(conn, user); 
			//			String checkUser = db.getUserByEmail(conn, emailuser);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			String getUser = db.getUser(conn, user);
			String getEMail = db.getEmail(conn, emailuser);

			String subject = "Passwort zurücksetzen EasyDocs";
			String message = "";

			TokenGenerator tg = new TokenGenerator();
			String authcode = tg.generateCode();

			db.saveHash(conn,authcode,emailuser);

			response.setContentType("text/html");

			if((getUser == null) || (getEMail == null)){

				request.getSession().setAttribute("false", "Email und Userkennung stimmen nich Überein!");
				System.out.println("User existiert nicht, Mail kann nicht versendet werden. . . ");
				response.sendRedirect("ErrorPage.jsp");

			}else{

				System.out.println("User existiert, Mail kann versendet werden. . . ");

				SendEMail mailer = new SendEMail();

				try {
					message = "Lieber EasyPDF Nutzer, um dein Passwort zurückzusetzten bitte folgenden Link öffnen: "

					+"\n\n http://localhost:8080/DiplomPdf/CheckReset?authcode="+authcode
					+"\n\n  Viel Spaß bei der weiteren Nutzung von EasyPDF wünscht das TEAM: "
					+ "\n\n \n\n \t Thomas Kerber, Verena Gurtner & Sara Hindelang";

					mailer.sendPlainTextEmail( emailuser, subject, message);
					System.out.println("Email wurde gesendet.");
				} catch (Exception ex) {
					System.out.println("Email konnte nicht gesnendet werden");
					ex.printStackTrace();
				}

			}

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
