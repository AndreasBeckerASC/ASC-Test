
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailTLS {

	public static void main(String[] args) {

		final String username = "andreas.becker.asc@gmail.com";
		final String password = "Opelix13";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("andreas.becker.asc@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("eckhard.geissler@asc-ag.com"));
			message.setSubject("Java Test");
			
			// create the message part 
            MimeBodyPart messageBodyPart = 
              new MimeBodyPart();
        //fill message
            messageBodyPart.setText("Hallo Sabine,"
				+ "\n\n gesendet von Java aus ...");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
         // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = 
              new FileDataSource("c:/temp/t.xml");
            messageBodyPart.setDataHandler(
              new DataHandler(source));
            messageBodyPart.setFileName("t.xml");
            multipart.addBodyPart(messageBodyPart);
        // Put parts in message
            message.setContent(multipart);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}