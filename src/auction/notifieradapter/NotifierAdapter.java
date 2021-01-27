package auction.notifieradapter;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class NotifierAdapter implements INotifier {

    String host = "4999";
    private static final String USER = "auctionhouseroyal@gmail.com";
    private static final String PASSWORD = "Lucian1234mihai";

    @Override
    public void sendEmail(String email, String textMail) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER, PASSWORD);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
            message.setSubject("AuctionHouse Royal");
            message.setText(textMail);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
