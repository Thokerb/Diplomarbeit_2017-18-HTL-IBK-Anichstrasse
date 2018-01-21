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
		
		final String emailuser = "kerber.tom98@gmail.com ";

		Properties props = new Properties();

		props.put("mail.smtps.user","username"); 
		props.put("mail.smtps.host", "smtp.gmail.com"); 
		props.put("mail.smtps.port", "587"); 
		props.put("mail.debug", "true"); 
		props.put("mail.smtps.auth", "true"); 
		props.put("mail.smtps.starttls.enable","true"); 
		props.put("mail.smtps.EnableSSL.enable","true");

		Session session2 = Session.getInstance(props, new GMailAuthenticator(username, password));

		try {

			Message message = new MimeMessage(session2);
			message.setFrom(new InternetAddress("easypdf.help@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("kerber.tom98@gmail.com")); //TODO email von antwort
			message.setSubject("Passwort zurücksetzen EasyPDF");
			message.setText("Lieber EasyPDF Nutzer, um dein Passwort zurückzusetzten bitte folgenden Link öffnen: "
					+ "\n\n http://localhost:8080/DiplomPdf/Login.jsp"
					+"\n\n  Viel Spaß bei der weiteren Nutzun von EasyPDF wünscht das TEAM: "
					+ "\n\n \n\n \t Thomas Kerber, Verena Gurtner & Sara Hindelang"); //TODO noch ändern in JSP PWzuruck

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

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
			return new PasswordAuthentication(user, pw);
		}
	}
}
