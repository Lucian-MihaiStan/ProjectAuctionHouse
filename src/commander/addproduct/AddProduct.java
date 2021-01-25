package commander.addproduct;

import auction_house.AuctionHouse;
import commander.ICommand;
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
    private double sellingPrice;
    private double minimumPrice;
    private int year;
    private List<String> restParameters;


    @Override
    public void execute(ServerClientThread sct) {
        synchronized (sct.mySQLConnection) {
            AuctionHouse auctionHouse = sct.getAuctionHouse();
            String username = sct.mySQLConnection.getUsername();
            System.out.println(username);
            if (!"admin".equals(username)) {
                ServerClientThread.Helper outCommand = ServerClientThread.Helper.getInstance();
                outCommand.setCommandResult(outCommand.getCommandResult().append("You are not admin to modify the list fo products"));
                return;
            }
            if (productType == 1) auctionHouse.addNewProduct(
                    new PaintingBuilder()
                            .withName(name)
                            .withSellingPrice(sellingPrice)
                            .withMinimumPrice(minimumPrice)
                            .withYear(year)
                            .withNameArtist(HelperAP.setParamPainting(restParameters).getLeft())
                            .withColors(HelperAP.setParamPainting(restParameters).getRight())
                            .build()
            );
            else if (productType == 2) auctionHouse.addNewProduct(
                    new FurnitureBuilder()
                            .withName(name)
                            .withSellingPrice(sellingPrice)
                            .withMinimPrice(minimumPrice)
                            .withYear(year)
                            .withType(HelperAP.setParamFurniture(restParameters).getLeft())
                            .withMaterial(HelperAP.setParamFurniture(restParameters).getRight())
                            .build()
            );
            else auctionHouse.addNewProduct(
                        new JewelleryBuilder()
                                .withName(name)
                                .withSellingPrice(sellingPrice)
                                .withMinimumPrice(minimumPrice)
                                .withMaterial(HelperAP.setParamJewellery(restParameters).getLeft())
                                .withGemstone(HelperAP.setParamJewellery(restParameters).getRight())
                                .build()
                );
            Product lastProduct = auctionHouse.getLastProduct();
            new AddProductSQL(sct.getMySQLConnection()).addProductSQL(lastProduct);
        }
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

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
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
