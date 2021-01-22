package products.jewellery;

import products.furniture.FurnitureBuilder;

public class JewelleryBuilder {
    private Jewellery jewellery = new Jewellery();

    public JewelleryBuilder withName(String name) {
        jewellery.setName(name);
        return this;
    }

    public JewelleryBuilder withSellingPrice(double sellingPrice) {
        jewellery.setSellingPrice(sellingPrice);
        return this;
    }

    public JewelleryBuilder withMinimPrice(double minimumPrice) {
        jewellery.setMinimumPrice(minimumPrice);
        return this;
    }

    public JewelleryBuilder withMinimumPrice(double minimumPrice) {
        jewellery.setMinimumPrice(minimumPrice);
        return this;
    }

    public JewelleryBuilder withYear(int year) {
        jewellery.setYear(year);
        return this;
    }

    public JewelleryBuilder withMaterial(String material) {
        jewellery.setMaterial(material);
        return this;
    }

    public JewelleryBuilder withGemstone(boolean gemstone) {
        jewellery.setGemstone(gemstone);
        return this;
    }

    public Jewellery build() {
        return jewellery;
    }
}
