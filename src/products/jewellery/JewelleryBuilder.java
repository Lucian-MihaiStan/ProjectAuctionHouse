package products.jewellery;


import products.ProductBuilder;

/**
 * jewellery builder class
 */
public class JewelleryBuilder implements ProductBuilder<Jewellery, JewelleryBuilder> {
    private final Jewellery jewellery = new Jewellery();

    /**
     * build a jewellery with id
     * @param id id of jewellery
     * @return JewelleryBuilder instance
     */
    @Override
    public JewelleryBuilder withId(int id) {
        jewellery.setId(id);
        return this;
    }

    /**
     * build a jewellery with name
     * @param name name of jewellery
     * @return JewelleryBuilder instance
     */
    @Override
    public JewelleryBuilder withName(String name) {
        jewellery.setName(name);
        return this;
    }

    /**
     * build a jewellery with selling price
     * @param sellingPrice selling price of jewellery
     * @return JewelleryBuilder instance
     */
    @Override
    public JewelleryBuilder withSellingPrice(double sellingPrice) {
        jewellery.setSellingPrice(sellingPrice);
        return this;
    }

    /**
     * build a jewellery with minimum price
     * @param minimumPrice minim price of jewellery
     * @return JewelleryBuilder instance
     */
    @Override
    public JewelleryBuilder withMinimPrice(double minimumPrice) {
        jewellery.setMinimumPrice(minimumPrice);
        return this;
    }

    /**
     * build a jewellery with year
     * @param year year of jewellery
     * @return JewelleryBuilder instance
     */
    @Override
    public JewelleryBuilder withYear(int year) {
        jewellery.setYear(year);
        return this;
    }

    /**
     * build a jewellery with material
     * @param material id of jewellery
     * @return JewelleryBuilder instance
     */
    public JewelleryBuilder withMaterial(String material) {
        jewellery.setMaterial(material);
        return this;
    }

    /**
     * build a jewellery with gemstone
     * @param gemstone gemstone of jewellery
     * @return JewelleryBuilder instance
     */
    public JewelleryBuilder withGemstone(boolean gemstone) {
        jewellery.setGemstone(gemstone);
        return this;
    }

    /**
     * assembly the components of a jewellery
     * @return a jewellery instance
     */
    @Override
    public Jewellery build() {
        return jewellery;
    }
}
