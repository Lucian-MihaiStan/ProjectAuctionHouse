package auction_house;

import client.User;
import load_data_db.LoadDBDataAdmin;
import load_data_db.IAdapterAdmin;
import auction.Auction;
import products.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.System.*;

public class AuctionHouse {
    private static AuctionHouse instance;

    private List<Product> productsList;
    private List<User> userList;
    private List<Auction> auctionsActive;

    public static synchronized AuctionHouse getInstance() {
        if(instance == null) {
            instance = new AuctionHouse();
        }
        return instance;
    }

    private AuctionHouse() {
        productsList = new ArrayList<>();
        userList = new ArrayList<>();
        auctionsActive = new ArrayList<>();
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Auction> getAuctionsActive() {
        return auctionsActive;
    }

    public void setAuctionsActive(List<Auction> auctionsActive) {
        this.auctionsActive = auctionsActive;
    }

    public void addNewClient(User user) {
        userList.add(user);
    }

    public User getLastClient() {
        return userList.get(userList.size() - 1);
    }

    public void addNewProduct(Product product) {
        productsList.add(product);
    }

    public Product getLastProduct() {
        return productsList.get(productsList.size() - 1);
    }

    public AuctionHouse load() {
        IAdapterAdmin adapter = new LoadDBDataAdmin();
        Map<String, List<?>> auctionHouseData = adapter.connectToDatabaseAsAdmin().extractFromDatabase();
        try{
            auctionHouseData.get("users").forEach(
                    user -> userList.add((User) user)
            );
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        try{
            auctionHouseData.get("products").forEach(
                    product -> productsList.add((Product) product)
            );
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        /*try {
            auctionHouseData.get("auctions").forEach(auction -> auctionsActive.add(auction));
        } catch (ClassCastException e){
            //
        }*/

        return this;
    }

    @Override
    public String toString() {
        return "AuctionHouse{" +
                "productsList=" + productsList +
                ", userList=" + userList +
                ", auctionsActive=" + auctionsActive +
                '}';
    }
}
