package client;


import socketserver.Main;
import client.strategy.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * class that implements functionalities of user class
 */
public abstract class User {
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private int noParticipation;
    private int wonAuctions;
    private String email;
    private final Map<Integer, Double> auctionAndMaxBid = new HashMap<>();

    /**
     * ask the new bid
     * @param maxCurrentBid maximum current bid
     * @param maxBid maximum bid imposed by user
     * @return the new bid
     */
    public double askBid(double maxCurrentBid, Double maxBid) {
        int randomStrategy = Main.random.nextInt(3) + 1;
        BidContext context;
        Strategy strategy;
        if (randomStrategy == 1) strategy = new CallDouble(maxCurrentBid, maxBid);
        else if (randomStrategy == 2) strategy = new CallHalfMore(maxCurrentBid, maxBid);
        else strategy = new CallMore(maxCurrentBid, maxBid);

        context = new BidContext(strategy);
        return context.executeStrategy();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, lastName, address, noParticipation, wonAuctions, email, auctionAndMaxBid);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNoParticipation() {
        return noParticipation;
    }

    public void setNoParticipation(int noParticipation) {
        this.noParticipation = noParticipation;
    }

    public int getWonAuctions() {
        return wonAuctions;
    }

    public void setWonAuctions(int wonAuctions) {
        this.wonAuctions = wonAuctions;
    }

    public Map<Integer, Double> getAuctionAndMaxBid() {
        return auctionAndMaxBid;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", noParticipation=" + noParticipation +
                ", wonAuctions=" + wonAuctions +
                ", email='" + email + '\'' +
                '}';
    }
}
