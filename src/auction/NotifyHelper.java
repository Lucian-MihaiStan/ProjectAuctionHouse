package auction;

import auction.notifieradapter.INotifierMail;
import auction.notifieradapter.NotifierMailAdapter;
import auction_house.AuctionHouse;
import client.User;
import employee.Broker;
import products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotifyHelper {

    public synchronized void notifyPAuctionStart(Map<Integer, Broker> brokers, int idAuction) {
        Product productInfo = AuctionHouse.getInstance()
                .getProductsList()
                .stream()
                .filter(product -> product.getId() == idAuction)
                .collect(Collectors.toList()).get(0);

        String notify = "Auction for product " + productInfo.toString() + " has started please join at the table";

        List<String> usersEnrolled = new ArrayList<>();

        brokers.forEach((integer, broker) -> {
            if (broker.getAuctionAndUserAssigned().containsKey(idAuction)) {
                broker.getAuctionAndUserAssigned().get(idAuction).forEach((user, bid) -> usersEnrolled.add(user));
            }
        });

        usersEnrolled.forEach(
                username -> {
                    User user = AuctionHouse.getInstance().getUserList().stream().filter(
                            userIt -> username.equals(userIt.getUsername())
                    ).collect(Collectors.toList()).get(0);
                    String email = user.getEmail();
                    INotifierMail iNotifierMail = new NotifierMailAdapter();
                    iNotifierMail.sendEmail(email, notify);
                }
        );
    }

    public void notifyWinner(User winner, Product productInfo) {
        INotifierMail iNotifierMail = new NotifierMailAdapter();
        iNotifierMail.sendWinnerEmail(winner.getEmail(), productInfo);
    }

    public void notifyPAuctionEnd(List<User> clientsParticipating, int idAuction) {
        INotifierMail iNotifierMail = new NotifierMailAdapter();
        clientsParticipating.forEach(user -> iNotifierMail.toPNotWon(user.getEmail(), idAuction));
    }

    public void notifyPAuctionWasWon(List<User> clientsParticipating, int idAuction) {
        INotifierMail iNotifierMail = new NotifierMailAdapter();
        clientsParticipating.forEach(user -> iNotifierMail.toPWasWon(user.getEmail(), idAuction));
    }
}
