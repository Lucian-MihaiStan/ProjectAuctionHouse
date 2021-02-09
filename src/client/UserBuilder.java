package client;

/**
 * User generic builder
 * @param <E> class that extended by user
 * @param <T> generic builder extended
 */
public interface UserBuilder <E extends User, T extends UserBuilder<E, T>>{
    /**
     * build user with username
     * @param username username of the user
     * @return generic builder instance
     */
    T withUsername(String username);

    /**
     * build user with first name
     * @param firstName first name of the user
     * @return generic builder instance
     */
    T withFirstName(String firstName);

    /**
     * build user with last name
     * @param lastName username of the user
     * @return generic builder instance
     */
    T withLastName(String lastName);

    /**
     * build user with email
     * @param email username of the user
     * @return generic builder instance
     */
    T withEmail(String email);

    /**
     * build user with address
     * @param address username of the user
     * @return generic builder instance
     */
    T withAddress(String address);

    /**
     * build user with number of participation
     * @param noParticipation number of participation of the user
     * @return generic builder instance
     */
    T withNoParticipation(int noParticipation);

    /**
     * build user with number of won auctions
     * @param wonAuction username of the user
     * @return generic builder instance
     */
    T withWonAuction(int wonAuction);

    /**
     * assembly the components of a user
     * @return an user instance
     */
    E build();
}
