package commander.addproduct;

import java.util.List;

public class ProductBuilderCommander {
    private final AddProduct productInfo = new AddProduct();

    public ProductBuilderCommander withId(int id) {
        productInfo.setId(id);
        return this;
    }

    public ProductBuilderCommander withProductType(int productType) {
        productInfo.setProductType(productType);
        return this;
    }

    public ProductBuilderCommander withName(String name) {
        productInfo.setName(name);
        return this;
    }

    public ProductBuilderCommander withSellingPrice(double sellingPrice) {
        productInfo.setSellingPrice(sellingPrice);
        return this;
    }

    public ProductBuilderCommander withMinimumPrice(double minimumPrice) {
        productInfo.setMinimumPrice(minimumPrice);
        return this;
    }

    public ProductBuilderCommander withYear(int year) {
        productInfo.setYear(year);
        return this;
    }

    public ProductBuilderCommander withOtherParameters(List<String> parameters) {
        productInfo.setRestParameters(parameters);
        return this;
    }

    public AddProduct build() {
        return productInfo;
    }

}
