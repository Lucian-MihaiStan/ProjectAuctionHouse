package products;

public interface ProductBuilder <E extends Product, T extends ProductBuilder<E, T>> {
    T withId(int id);
    T withName(String name);
    T withSellingPrice(double sellingPrice);
    T withMinimPrice(double minimPrice);
    T withYear(int year);
    E build();
}
