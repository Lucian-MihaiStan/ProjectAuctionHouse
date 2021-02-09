package products.jewellery;

import products.Product;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Jewellery jewellery = (Jewellery) o;
        return gemstone == jewellery.gemstone &&
                Objects.equals(material, jewellery.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), material, gemstone);
    }

    @Override
    public String toString() {
        return "Jewellery{" +
                "id = " + this.getId() + " " +
                this.getName() + " " +
                this.getMinimumPrice() + " " +
                this.getYear() + " " +
                "material='" + material + '\'' +
                ", gemstone=" + gemstone +
                '}';
    }
}
