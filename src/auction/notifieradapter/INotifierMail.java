package auction.notifieradapter;

import auction.Auction;
import org.apache.commons.lang3.tuple.Pair;
import products.Product;

import java.util.List;
import java.util.Map;

/**
 * Email notifier interface
 * <pre>
 *     The email is sent from auctionhouseroyal@gmail.com
 * </pre>
 */
public interface INotifierMail {
    /**
     * Send mail to function
     * @param email email address of recipient
     * @param textMail text of email
     */
    void sendEmail(String email, String textMail);

    /**
     * Send mail to brokers to notify them the auction started and their clients distributed
     * @param mapBrokers brokers
     * @param auction auction that is started
     * @param product product info that is included in auction
     */
    void sendMailToBrokers(Map<Integer, List<Pair<String, Double>>> mapBrokers, Auction auction, Product product);

    /**
     * Send mail to winner of auction
     * @param winnerEmail email of the winner
     * @param productInfo information of product
     */
    void sendWinnerEmail(String winnerEmail, Product productInfo);

    /**
     * Send mail to participant auction started and was won by someone
     * @param email email of user
     * @param idAuction id auction
     */
    void toPWasWon(String email, int idAuction);

    /**
     * Send mail to participant auction started, but wasn't win
     * @param email email of user
     * @param idAuction id auction
     */
    void toPNotWon(String email, int idAuction);
}
