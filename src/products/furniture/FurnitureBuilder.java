package products.furniture;

public class FurnitureBuilder {
    private final Furniture furniture = new Furniture();

    public FurnitureBuilder withId(int id) {
        furniture.setId(id);
        return this;
    }

    public FurnitureBuilder withName(String name) {
        furniture.setName(name);
        return this;
    }

    public FurnitureBuilder withSellingPrice(double sellingPrice) {
        furniture.setSellingPrice(sellingPrice);
        return this;
    }

    public FurnitureBuilder withMinimPrice(double minimPrice) {
        furniture.setMinimumPrice(minimPrice);
        return this;
    }

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

    public Furniture build() {
        return furniture;
    }
}
