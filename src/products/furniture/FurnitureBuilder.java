package products.furniture;

import products.ProductBuilder;

public class FurnitureBuilder implements ProductBuilder<Furniture, FurnitureBuilder> {
    private final Furniture furniture = new Furniture();

    @Override
    public FurnitureBuilder withId(int id) {
        furniture.setId(id);
        return this;
    }

    @Override
    public FurnitureBuilder withName(String name) {
        furniture.setName(name);
        return this;
    }

    @Override
    public FurnitureBuilder withSellingPrice(double sellingPrice) {
        furniture.setSellingPrice(sellingPrice);
        return this;
    }

    @Override
    public FurnitureBuilder withMinimPrice(double minimPrice) {
        furniture.setMinimumPrice(minimPrice);
        return this;
    }

    @Override
    public FurnitureBuilder withYear(int year) {
        furniture.setYear(year);
        return this;
    }

    public FurnitureBuilder withType(String type) {
        furniture.setType(type);
        return this;
    }

    public FurnitureBuilder withMaterial(String material) {
        furniture.setMaterial(material);
        return this;
    }

    @Override
    public Furniture build() {
        return furniture;
    }
}
