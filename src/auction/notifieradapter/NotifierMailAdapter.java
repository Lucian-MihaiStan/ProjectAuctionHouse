package auction.notifieradapter;

import auction.Auction;
import org.apache.commons.lang3.tuple.Pair;
import products.Product;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class NotifierMailAdapter implements INotifierMail {

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

    @Override
    public void sendMailToBrokers(Map<Integer, List<Pair<String, Double>>> mapBrokers, Auction auction, Product product) {
        Set<Integer> keys = mapBrokers.keySet();
        keys.forEach(key -> {
            StringBuilder usersAssigned = new StringBuilder();
            List<Pair<String, Double>> usersInfo = mapBrokers.get(key);
            usersInfo.forEach(userInfo -> {
                usersAssigned.append(userInfo.getLeft());
                usersAssigned.append(" With Bid ");
                usersAssigned.append(userInfo.getRight() + '\n');
            });
            String message = "Hello!" + "\n\n" + "You are assigned as broker to '\n'" +
                    "========Auction========'\n'" +
                    product.toString() +
                    "\n\n" +
                    "Your users assigned are: '\n" +
                    usersAssigned;
            sendEmail("brokerroyal" + (key + 1) + "@gmail.com", message);
        });
    }
}
