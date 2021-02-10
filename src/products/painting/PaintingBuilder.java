package products.painting;


import products.ProductBuilder;

/**
 * painting builder class
 */
public class PaintingBuilder implements ProductBuilder<Painting, PaintingBuilder> {
    private final Painting painting = new Painting();

    /**
     * build a painting with id
     * @param id id of painting
     * @return PaintingBuilder instance
     */
    @Override
    public PaintingBuilder withId(int id) {
        painting.setId(id);
        return this;
    }

    /**
     * build a painting with name
     * @param name name of painting
     * @return PaintingBuilder instance
     */
    @Override
    public PaintingBuilder withName(String name) {
        painting.setName(name);
        return this;
    }

    /**
     * build a painting with selling price
     * @param sellingPrice selling price of painting
     * @return PaintingBuilder instance
     */
    @Override
    public PaintingBuilder withSellingPrice(double sellingPrice) {
        painting.setSellingPrice(sellingPrice);
        return this;
    }

    /**
     * build a painting with minimum price
     * @param minimPrice minim pice of painting
     * @return PaintingBuilder instance
     */
    @Override
    public PaintingBuilder withMinimPrice(double minimPrice) {
        painting.setMinimumPrice(minimPrice);
        return this;
    }

    /**
     * build a painting with year
     * @param year yaer of painting
     * @return PaintingBuilder instance
     */
    @Override
    public PaintingBuilder withYear(int year) {
        painting.setYear(year);
        return this;
    }

    /**
     * build a painting with name artist
     * @param nameArtist name of artist of painting
     * @return PaintingBuilder instance
     */
    public PaintingBuilder withNameArtist(String nameArtist) {
        painting.setNameArtist(nameArtist);
        return this;
    }

    /**
     * build a painting with colors
     * @param colors colors of painting
     * @return PaintingBuilder instance
     */
    public PaintingBuilder withColors(Painting.Colors colors) {
        painting.setColors(colors);
        return this;
    }

    /**
     * assembly the components of a painting
     * @return a painting instance
     */
    @Override
    public Painting build() {
        return painting;
    }
}
