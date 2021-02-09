package products.furniture;

import products.Product;

import java.util.Objects;

public class Furniture extends Product {
    private String type;
    private String material;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Furniture{" +
                "id = " + this.getId() + " " +
                this.getName() + " " +
                this.getMinimumPrice() + " " +
                this.getYear() + " " +
                "type='" + type + '\'' +
                ", material='" + material + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Furniture furniture = (Furniture) o;
        return Objects.equals(type, furniture.type) &&
                Objects.equals(material, furniture.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, material);
    }
}
