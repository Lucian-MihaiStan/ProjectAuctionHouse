package auctionHouse;

import auction.Auction;
import client.Client;
import client.IndividualPerson;
import products.Product;

import java.util.ArrayList;
import java.util.List;

public class AuctionHouse {
    private static AuctionHouse instance;

    private List<Product> productsList;
    private List<Client> clientsList;
    private List<Auction> auctionsActive;

    public static AuctionHouse getInstance() {
        if(instance == null) {
           instance = new AuctionHouse();
        }
        return instance;
    }

    private AuctionHouse() {
        productsList = new ArrayList<>();
        clientsList = new ArrayList<>();
        auctionsActive = new ArrayList<>();
    }

    public static void setInstance(AuctionHouse instance) {
        AuctionHouse.instance = instance;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public List<Client> getClientsList() {
        return clientsList;
    }

    public void setClientsList(List<Client> clientsList) {
        this.clientsList = clientsList;
    }

    public List<Auction> getAuctionsActive() {
        return auctionsActive;
    }

    public void setAuctionsActive(List<Auction> auctionsActive) {
        this.auctionsActive = auctionsActive;
    }

    public void addNewClient(Client client) {
        clientsList.add(client);
    }

    public Client getLastClient() {
        return clientsList.get(clientsList.size() - 1);
    }
}
