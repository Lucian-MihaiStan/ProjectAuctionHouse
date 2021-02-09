package commander.addproduct;

import java.util.List;

/**
 * builder for add product
 */
public class ProductBuilderCommander {
    private final AddProduct productInfo = new AddProduct();

    /**
     * build a add product command with product type
     * @param productType product type of command
     * @return ProductBuilderCommander builder instance
     */
    public ProductBuilderCommander withProductType(int productType) {
        productInfo.setProductType(productType);
        return this;
    }

    /**
     * build a add product command with name
     * @param name name of command
     * @return ProductBuilderCommander builder instance
     */
    public ProductBuilderCommander withName(String name) {
        productInfo.setName(name);
        return this;
    }

    /**
     * build a add product command with minimum price
     * @param minimumPrice minimum price of command
     * @return ProductBuilderCommander builder instance
     */
    public ProductBuilderCommander withMinimumPrice(double minimumPrice) {
        productInfo.setMinimumPrice(minimumPrice);
        return this;
    }

    /**
     * build a add product command with year
     * @param year year of command
     * @return ProductBuilderCommander builder instance
     */
    public ProductBuilderCommander withYear(int year) {
        productInfo.setYear(year);
        return this;
    }

    /**
     * build a add product command with other parameters
     * @param parameters other parameters depending of type of product
     * @return ProductBuilderCommander builder instance
     */
    public ProductBuilderCommander withOtherParameters(List<String> parameters) {
        productInfo.setRestParameters(parameters);
        return this;
    }

    /**
     * assembly the components of an add product command
     * @return AddProduct command instance
     */
    public AddProduct build() {
        return productInfo;
    }

}
