package products.painting;


import products.ProductBuilder;

public class PaintingBuilder implements ProductBuilder<Painting, PaintingBuilder> {
    private final Painting painting = new Painting();

    @Override
    public PaintingBuilder withId(int id) {
        painting.setId(id);
        return this;
    }

    @Override
    public PaintingBuilder withName(String name) {
        painting.setName(name);
        return this;
    }

    @Override
    public PaintingBuilder withSellingPrice(double sellingPrice) {
        painting.setSellingPrice(sellingPrice);
        return this;
    }

    @Override
    public PaintingBuilder withMinimPrice(double minimPrice) {
        painting.setMinimumPrice(minimPrice);
        return this;
    }

    @Override
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

    @Override
    public Painting build() {
        return painting;
    }
}
