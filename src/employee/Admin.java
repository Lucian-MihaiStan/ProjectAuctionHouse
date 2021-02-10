package employee;

import auction_house.AuctionHouse;
import products.Product;

/**
 * admin instance
 */
public class Admin implements IEmployee{
    private static Admin instance;

    private static final String ADMIN_CREDENTIALS = "admin";

    /**
     * get instance of administrator
     * @return instance of administrator
     */
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

    /**
     * add product in products list
     * @param product product that must be added
     */
    public void addProduct(Product product) {
        synchronized (AuctionHouse.getInstance().getProductsList()) {
            AuctionHouse.getInstance().getProductsList().add(product);
        }
    }

    /**
     * delete product from auction house by id
     * @param id id of product
     */
    @Override
    public void deleteProduct(int id) {
        synchronized (AuctionHouse.getInstance().getProductsList()) {
            AuctionHouse.getInstance().getProductsList().removeIf(product -> product.getId() == id);
        }
    }
}
