package products;

/**
 * generic product builder class
 * @param <E> class that extended by user
 * @param <T> generic builder extended
 */
public interface ProductBuilder <E extends Product, T extends ProductBuilder<E, T>> {
    /**
     * build a product with id
     * @param id id of product
     * @return generic builder instance
     */
    T withId(int id);

    /**
     * build a product with name
     * @param name name of product
     * @return generic builder instance
     */
    T withName(String name);

    /**
     * build a product with selling price
     * @param sellingPrice selling price of product
     * @return generic builder instance
     */
    T withSellingPrice(double sellingPrice);

    /**
     * build a product with minimum price
     * @param minimPrice minim price of product
     * @return generic builder instance
     */
    T withMinimPrice(double minimPrice);

    /**
     * build a product with year
     * @param year year of product
     * @return generic builder instance
     */
    T withYear(int year);

    /**
     * assembly the components of a product
     * @return a product instance
     */
    E build();
}
