package auction.notifieradapter;

import auction.Auction;
import products.Product;

import java.util.List;
import java.util.Map;

public interface INotifierMail {
    void sendEmail(String email, String textMail);
    void sendMailToBrokers(Map<Integer, List<String>> mapBrokers, Auction auction, Product product);
}
