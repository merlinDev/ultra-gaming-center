/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import properties.Settings;

/**
 *
 * @author Isu
 */
public class Esender {

    public static synchronized void emailsender(String to_Email_Address, String subject, String msg) {

        Settings settings = new Settings();
        String username = settings.getValue(Settings.EMAIL_ADDRESS);
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, settings.getValue(Settings.EMAIL_PASSWORD));

            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_Email_Address));

            message.setSubject(subject);
            message.setText(msg);
            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
