package products.jewellery;

import products.Product;

public class Jewellery extends Product {
    private String material;
    private boolean gemstone;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isGemstone() {
        return gemstone;
    }

    public void setGemstone(boolean gemstone) {
        this.gemstone = gemstone;
    }

    @Override
    public String toString() {
        return "Jewellery{" +
                this.getName() + " " +
                this.getSellingPrice() + " " +
                this.getMinimumPrice() + " " +
                this.getYear() + " " +
                "material='" + material + '\'' +
                ", gemstone=" + gemstone +
                '}';
    }
}
