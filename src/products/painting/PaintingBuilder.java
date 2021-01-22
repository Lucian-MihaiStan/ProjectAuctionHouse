package products.painting;

import products.jewellery.JewelleryBuilder;

public class PaintingBuilder {
    private final Painting painting = new Painting();

    public PaintingBuilder withName(String name) {
        painting.setName(name);
        return this;
    }

    public PaintingBuilder withSellingPrice(double sellingPrice) {
        painting.setSellingPrice(sellingPrice);
        return this;
    }

    public PaintingBuilder withMinimumPrice(double minimumPrice) {
        painting.setMinimumPrice(minimumPrice);
        return this;
    }

    public PaintingBuilder withYear(int year) {
        painting.setYear(year);
        return this;
    }

    public PaintingBuilder withNameArtist(String nameArtist) {
        painting.setNameArtist(nameArtist);
        return this;
    }

    public PaintingBuilder withColors(Painting.Colors colors) {
        painting.setColors(colors);
        return this;
    }

    public Painting build() {
        return painting;
    }
}
