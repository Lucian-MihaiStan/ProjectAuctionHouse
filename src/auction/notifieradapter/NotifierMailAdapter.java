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

/**
 * Email notifier interface
 * <pre>
 *     The email is sent from auctionhouseroyal@gmail.com
 * </pre>
 */
public class NotifierMailAdapter implements INotifierMail {
    /**
     * credentials of auction house email
     */
    private static final String USER = "auctionhouseroyal@gmail.com";
    private static final String PASSWORD = "Lucian1234mihai";

    /**
     * Send mail to function
     * @param email email address of recipient
     * @param textMail text of email
     */
    @Override
    public synchronized void sendEmail(String email, String textMail) {

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

    /**
     * Send mail to brokers to notify them the auction started and their clients distributed
     * @param mapBrokers brokers
     * @param auction auction that is started
     * @param product product info that is included in auction
     */
    @Override
    public synchronized void sendMailToBrokers(Map<Integer, List<Pair<String, Double>>> mapBrokers, Auction auction, Product product) {
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

    /**
     * Send mail to winner of auction
     * @param winnerEmail email of the winner
     * @param productInfo information of product
     */
    @Override
    public synchronized void sendWinnerEmail(String winnerEmail, Product productInfo) {
        sendEmail(winnerEmail, "You won " + productInfo.toString());
    }

    /**
     * Send mail to participant auction started and was won by someone
     * @param email email of user
     * @param idAuction id auction
     */
    @Override
    public synchronized void toPWasWon(String email, int idAuction) {
        sendEmail(email, "Auction " + idAuction + " ended!");
    }

    /**
     * Send mail to participant auction started, but wasn't win
     * @param email email of user
     * @param idAuction id auction
     */
    @Override
    public void toPNotWon(String email, int idAuction) {
        sendEmail(email, "Auction " + idAuction + " was not won! Bids were to low");
    }

}
