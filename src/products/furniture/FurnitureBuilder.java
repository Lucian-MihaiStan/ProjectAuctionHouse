package products.furniture;

import products.ProductBuilder;

/**
 * furniture builder class
 */
public class FurnitureBuilder implements ProductBuilder<Furniture, FurnitureBuilder> {
    private final Furniture furniture = new Furniture();

    /**
     * build a furniture with id
     * @param id id of furniture
     * @return FurnitureBuilder instance
     */
    @Override
    public FurnitureBuilder withId(int id) {
        furniture.setId(id);
        return this;
    }

    /**
     * build a furniture with name
     * @param name id of furniture
     * @return FurnitureBuilder instance
     */
    @Override
    public FurnitureBuilder withName(String name) {
        furniture.setName(name);
        return this;
    }

    /**
     * build a furniture with selling price
     * @param sellingPrice selling price of furniture
     * @return FurnitureBuilder instance
     */
    @Override
    public FurnitureBuilder withSellingPrice(double sellingPrice) {
        furniture.setSellingPrice(sellingPrice);
        return this;
    }

    /**
     * build a furniture with minim price
     * @param minimPrice minimi price of furniture
     * @return FurnitureBuilder instance
     */
    @Override
    public FurnitureBuilder withMinimPrice(double minimPrice) {
        furniture.setMinimumPrice(minimPrice);
        return this;
    }

    /**
     * build a furniture with year
     * @param year year of furniture
     * @return FurnitureBuilder instance
     */
    @Override
    public FurnitureBuilder withYear(int year) {
        furniture.setYear(year);
        return this;
    }

    /**
     * build a furniture with type
     * @param type type of furniture
     * @return FurnitureBuilder instance
     */
    public FurnitureBuilder withType(String type) {
        furniture.setType(type);
        return this;
    }

    /**
     * build a furniture with material
     * @param material material of furniture
     * @return FurnitureBuilder instance
     */
    public FurnitureBuilder withMaterial(String material) {
        furniture.setMaterial(material);
        return this;
    }

    /**
     * assembly the components of a furniture
     * @return a furniture instance
     */
    @Override
    public Furniture build() {
        return furniture;
    }
}
