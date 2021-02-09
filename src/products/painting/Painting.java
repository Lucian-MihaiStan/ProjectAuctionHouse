package products.painting;

import products.Product;

import java.util.Objects;

public class Painting extends Product {
    private String nameArtist;
    private Colors colors;

    public enum Colors{
        oil,
        tempera,
        acrylic
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public Colors getColors() {
        return colors;
    }

    public void setColors(Colors colors) {
        this.colors = colors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Painting painting = (Painting) o;
        return Objects.equals(nameArtist, painting.nameArtist) &&
                colors == painting.colors;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nameArtist, colors);
    }

    @Override
    public String toString() {
        return "Painting{" +
                "id = " + this.getId() + " " +
                this.getName() + " " +
                this.getMinimumPrice() + " " +
                this.getYear() + " " +
                "nameArtist='" + nameArtist + '\'' +
                ", colors=" + colors +
                '}';
    }
}

