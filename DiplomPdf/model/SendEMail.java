import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 *Klasse ermöglicht durch javax Library Emails mittels Google Account zu versenden, es müssen nur die richtigen Parameter mitgegeben werden
 *
 */
public class SendEMail {

	public void sendPlainTextEmail( String toAddress, String subject, String message) throws AddressException,
	MessagingException {

		final String mail = "easypdf.help@gmail.com";
		final String password = "htlanichstr";

		/** setzt SMTP server properties **/
		
		Properties properties = new Properties();

		properties.put("mail.smtp.user","username"); 
		properties.put("mail.smtp.host", "smtp.gmail.com"); 
		properties.put("mail.smtp.auth", "true"); 
		properties.put("mail.smtp.starttls.enable","true"); 
		properties.put("mail.smtp.EnableSSL.enable","true");

		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");   
		properties.setProperty("mail.smtp.port", "465");   
		properties.setProperty("mail.smtp.socketFactory.port", "465"); 

		Session session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mail, password);
			}
		});

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(mail));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setText(message);
		Transport.send(msg);

		System.out.println("Email wurde versendet");

	}	
}
