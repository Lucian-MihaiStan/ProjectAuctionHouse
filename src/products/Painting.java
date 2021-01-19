package products;

public class Painting extends Product{
    private final String nameArtist;
    private final Colors colors;

    public Painting(String nameArtist, Colors colors) {
        this.nameArtist = nameArtist;
        this.colors = colors;
    }

    public enum Colors{
        oil,
        tempera,
        acrylic
    }
}

