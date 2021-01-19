package products.painting;

import products.Product;

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
    public String toString() {
        return "Painting{" +
                this.getId() + " " +
                this.getName() + " " +
                this.getSellingPrice() + " " +
                this.getMinimumPrice() + " " +
                this.getYear() + " " +
                "nameArtist='" + nameArtist + '\'' +
                ", colors=" + colors +
                '}';
    }
}

