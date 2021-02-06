package client;

public interface UserBuilder <E extends User, T extends UserBuilder<E, T>>{
    T withUsername(String username);
    T withFirstName(String firstName);
    T withLastName(String lastName);
    T withEmail(String email);
    T withAddress(String address);
    T withNoParticipation(int noParticipation);
    T withWonAuction(int wonAuction);
    E build();
}
