import java.io.IOException;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String username = "easypdf.help@gmail.com";
		final String password = "htlanichstr";
		
		String emailuser;
		String user;
		
		try {
			DBManager db = new DBManager();
			emailuser = request.getParameter("email");
			
//			String checkEMail = db.getEmailByUser(user); 
//			String checkUser = db.getUserByEmail(emailuser);
//			String getUser = db.getUser(user);
//			String getEMail = db.getEmail(emailuser);

			//emailuser = "sari.hindelang@gmail.com";
			
			TokenGenerator tg = new TokenGenerator();
			String authcode = tg.generateCode();
			
			DBManager dbm = new DBManager();
			dbm.saveHash(authcode,emailuser);
	
		response.setContentType("text/html");

		//		if((getuser == null) || (getemail == null)){
		//			request.getSession().setAttribute("false", "Email und Userkennung stimmen nich Überein!");
		//		System.out.println("User existiert nicht, Mail kann nicht versendet werden. . . ");
		//			response.sendRedirect("checkforgotpassword.jsp");
		//		}else{

		System.out.println("User existiert, Mail kann versendet werden. . . ");

		Properties props = new Properties();

		props.put("mail.smtp.user","username"); 
		props.put("mail.smtp.host", "smtp.gmail.com"); 
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.EnableSSL.enable","true");

		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		props.setProperty("mail.smtp.socketFactory.fallback", "false");   
		props.setProperty("mail.smtp.port", "465");   
		props.setProperty("mail.smtp.socketFactory.port", "465"); 

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("easypdf.help@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailuser));
			message.setSubject("Passwort zurücksetzen EasyPDF");
			message.setText("Lieber EasyPDF Nutzer, um dein Passwort zurückzusetzten bitte folgenden Link öffnen: "
					+ "\n\n http://localhost:8080/DiplomPdf/Login.jsp"
					+"\n\n Hier klicken für Reset: CheckReset?authcode="+authcode
					+"\n\n  Viel Spaß bei der weiteren Nutzung von EasyPDF wünscht das TEAM: "
					+ "\n\n \n\n \t Thomas Kerber, Verena Gurtner & Sara Hindelang"); //TODO noch ändern in JSP PWzuruck

			Transport.send(message);

			System.out.println("Email wurde versendet");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	//	}

	class GMailAuthenticator extends Authenticator {
		String user;
		String pw;
		public GMailAuthenticator (String username, String password)
		{
			super();
			this.user = username;
			this.pw = password;
		}
		public PasswordAuthentication getPasswordAuthentication()
		{
			System.out.println("GMail Auth OK");
			return new PasswordAuthentication(user, pw);
		}
	}
}
