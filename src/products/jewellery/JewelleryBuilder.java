package products.jewellery;


import products.ProductBuilder;

public class JewelleryBuilder implements ProductBuilder<Jewellery, JewelleryBuilder> {
    private final Jewellery jewellery = new Jewellery();

    @Override
    public JewelleryBuilder withId(int id) {
        jewellery.setId(id);
        return this;
    }

    @Override
    public JewelleryBuilder withName(String name) {
        jewellery.setName(name);
        return this;
    }

    @Override
    public JewelleryBuilder withSellingPrice(double sellingPrice) {
        jewellery.setSellingPrice(sellingPrice);
        return this;
    }

    @Override
    public JewelleryBuilder withMinimPrice(double minimumPrice) {
        jewellery.setMinimumPrice(minimumPrice);
        return this;
    }

    @Override
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

    @Override
    public Jewellery build() {
        return jewellery;
    }
}
