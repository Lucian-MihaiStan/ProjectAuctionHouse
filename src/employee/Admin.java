package employee;

import auction.Auction;
import auction_house.AuctionHouse;
import products.Product;

public class Admin implements IEmployee{
    private static Admin instance;

    private static final String ADMIN_CREDENTIALS = "admin";

    public static Admin getInstance() {
        if(instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    private Admin() {
        // private constructor to hide the default one
    }

    public String getAdminCredentials() {
        return ADMIN_CREDENTIALS;
    }

    public void addProduct(Product product) {
        synchronized (AuctionHouse.getInstance().getProductsList()) {
            AuctionHouse.getInstance().getProductsList().add(product);
        }
    }

    @Override
    public void deleteProduct(int id) {
        synchronized (AuctionHouse.getInstance().getProductsList()) {
            AuctionHouse.getInstance().getProductsList().removeIf(product -> product.getId() == id);
        }
    }
}
