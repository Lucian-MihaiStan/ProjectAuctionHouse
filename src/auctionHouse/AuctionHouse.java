package auctionHouse;

import auction.Auction;
import client.Client;
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
}
