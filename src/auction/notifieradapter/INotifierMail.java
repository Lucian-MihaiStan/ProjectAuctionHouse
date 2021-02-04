package auction.notifieradapter;

import auction.Auction;
import org.apache.commons.lang3.tuple.Pair;
import products.Product;

import java.util.List;
import java.util.Map;

public interface INotifierMail {
    void sendEmail(String email, String textMail);
    void sendMailToBrokers(Map<Integer, List<Pair<String, Double>>> mapBrokers, Auction auction, Product product);
    void sendWinnerEmail(String winnerEmail, Product productInfo);

    void toParticipants(String email, int idAuction);
}
