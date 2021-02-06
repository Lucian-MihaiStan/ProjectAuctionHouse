package commander.auctions;

import auction.Auction;
import client.User;
import commander.ICommand;
import employee.Broker;
import socketserver.Main;
import socketserver.ServerClientThread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


public class EnrollToAuction implements ICommand {
    private final int idAuction;
    private final double maximumPrice;

    private ServerClientThread sct;

    public EnrollToAuction(int idAuction, double maximumPrice) {
        this.idAuction = idAuction;
        this.maximumPrice = maximumPrice;
    }

    @Override
    public synchronized void execute(ServerClientThread sct) {
        this.sct = sct;
        Auction auction = sct.getAuctionHouse().getAuctionsActive().get(idAuction);
        ServerClientThread.Helper helper = ServerClientThread.Helper.getInstance();
        if (auction == null) {
            helper.setCommandResult(helper.getCommandResult().append("Auction Id is wrong"));
            return;
        }

        if (!checkIsNotEnrolled()) {
            helper.setCommandResult(helper.getCommandResult().append("You were already registered to this auction"));
            return;
        }

        if (auction.getNoCurrentParticipants() >= auction.getNoParticipants()) {
            helper.setCommandResult(helper.getCommandResult().append("There are already maximum number of participants"));
            return;
        }

        auction.setNoCurrentParticipants(auction.getNoCurrentParticipants() + 1);
        assignToBroker();

        helper.setCommandResult(helper.getCommandResult().append("You have been added to auction"));

        User userInfo = sct.getAuctionHouse().getUserList()
                .stream()
                .filter(user -> user.getUsername().equals(sct.getMySQLConnection().getUsername()))
                .collect(Collectors.toList()).get(0);
        userInfo.getAuctionAndMaxBid().put(idAuction, (double) -1);

        if (auction.getNoParticipants() == auction.getNoCurrentParticipants()) {
            sct.getAuctionHouse().notifyBrokers(idAuction);
            auction.start(sct.getAuctionHouse().getBrokers(), sct.getAuctionHouse().getUserList());
        }
    }

    private void assignToBroker() {
        Map<Integer, Broker> brokers = sct.getAuctionHouse().getBrokers();
        int randomIdBroker = Main.random.nextInt(brokers.keySet().size()) + 1;

        Broker brokerAssigned = brokers.get(randomIdBroker - 1);
        if(!brokerAssigned.getAuctionAndUserAssigned().containsKey(idAuction))
            brokerAssigned.getAuctionAndUserAssigned().put(idAuction, new HashMap<>());
        Map<String, Double> userAndBids = brokerAssigned.getAuctionAndUserAssigned().get(idAuction);
        userAndBids.put(sct.getMySQLConnection().getUsername(), maximumPrice);
    }

    private boolean checkIsNotEnrolled() {
        AtomicBoolean result = new AtomicBoolean(true);
        Map<Integer, Broker> brokers = sct.getAuctionHouse().getBrokers();
        brokers.forEach((integer, broker) -> {
            if (broker.getAuctionAndUserAssigned().containsKey(idAuction)) {
                Map<String, Double> usersAndBid = broker.getAuctionAndUserAssigned().get(idAuction);
                if (usersAndBid.containsKey(sct.getMySQLConnection().getUsername())) {
                    result.set(false);
                }
            }
        });
        return result.get();
    }
}
