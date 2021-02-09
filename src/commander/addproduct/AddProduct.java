package commander.addproduct;

import auction_house.AuctionHouse;
import commander.ICommand;
import employee.Admin;
import loginsql.product_connection.productsql.AddProductSQL;
import products.furniture.FurnitureBuilder;
import products.jewellery.JewelleryBuilder;
import products.painting.PaintingBuilder;
import products.Product;
import socketserver.ServerClientThread;

import java.util.List;

public class AddProduct implements ICommand {
    private int productType;
    private String name;
    private double minimumPrice;
    private int year;
    private List<String> restParameters;

    @Override
    public synchronized void execute(ServerClientThread sct) {
        AuctionHouse auctionHouse = sct.getAuctionHouse();
        String username = sct.getMySQLConnection().getUsername();
        if (!"admin".equals(username)) {
            ServerClientThread.Helper outCommand = ServerClientThread.Helper.getInstance();
            outCommand.setCommandResult(outCommand.getCommandResult().append("You are not admin to modify the list fo products"));
            return;
        }
        Admin admin = Admin.getInstance();

        Product product = initProduct();

        admin.addProduct(product);
        Product lastProduct = auctionHouse.getLastProduct();
        new AddProductSQL(sct.getMySQLConnection()).addProductSQL(lastProduct);
    }

    private Product initProduct() {
        if (productType == 1) return initPainting();
        else if (productType == 2) return initFurniture();
        else return initJewellery();
    }

    private Product initPainting() {
        return new PaintingBuilder()
                .withName(name)
                .withSellingPrice(-1)
                .withMinimPrice(minimumPrice)
                .withYear(year)
                .withNameArtist(HelperAP.setParamPainting(restParameters).getLeft())
                .withColors(HelperAP.setParamPainting(restParameters).getRight())
                .build();
    }

    private Product initFurniture() {
        return new FurnitureBuilder()
                .withName(name)
                .withSellingPrice(-1)
                .withMinimPrice(minimumPrice)
                .withYear(year)
                .withType(HelperAP.setParamFurniture(restParameters).getLeft())
                .withMaterial(HelperAP.setParamFurniture(restParameters).getRight())
                .build();
    }

    private Product initJewellery() {
        return new JewelleryBuilder()
                .withName(name)
                .withSellingPrice(-1)
                .withMinimPrice(minimumPrice)
                .withMaterial(HelperAP.setParamJewellery(restParameters).getLeft())
                .withGemstone(HelperAP.setParamJewellery(restParameters).getRight())
                .build();
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getRestParameters() {
        return restParameters;
    }

    public void setRestParameters(List<String> restParameters) {
        this.restParameters = restParameters;
    }
}
