package auction_house;

import adapter.AdapterAdminAC;
import adapter.IAdapterAdmin;
import auction.Auction;
import client.Client;
import products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    public void addNewProduct(Product product) {
        productsList.add(product);
    }

    public Product getLastProduct() {
        return productsList.get(productsList.size() - 1);
    }

    public void loadAsAdmin() {
        IAdapterAdmin adapter = new AdapterAdminAC();
        Map<String, List<Object>> auctionHouseData = adapter.connectToDatabaseAsAdmin().extractFromDatabase();
        try {
            auctionHouseData.get("IP").forEach(client -> clientsList.add((Client) client));
        } catch (ClassCastException e){
            //
        }
        try {
            auctionHouseData.get("LP").forEach(client -> clientsList.add((Client) client));
        } catch (ClassCastException e){
            //
        }
    }
}
