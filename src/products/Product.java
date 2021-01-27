package products;

import products.furniture.Furniture;

public abstract class Product {
    private int id;
    private String name;
    private double sellingPrice;
    private double minimumPrice;
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", minimumPrice=" + minimumPrice +
                ", year=" + year +
                '}';
    }

    public abstract class ProductBuilder {
        protected Product product;

        protected ProductBuilder() {}

        public ProductBuilder withId(int id) {
            product.id = id;
            return this;
        }

        public ProductBuilder withName(String name) {
            product.name = name;
            return this;
        }

        public ProductBuilder withMinimumPrice(int minimumPrice) {
            product.minimumPrice = minimumPrice;
            return this;
        }

        public Furniture.FurnitureBuilderInner asFurnitureBuilder() {
            return (Furniture.FurnitureBuilderInner) this;
        }

        public Product build() {
            return product;
        }
    }
}
