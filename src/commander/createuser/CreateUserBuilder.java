package commander.createuser;

public class CreateUserBuilder {

    private final CreateUser createUser = new CreateUser();

    public CreateUserBuilder withId(int id) {
        createUser.setId(id);
        return this;
    }

    public CreateUserBuilder withFirstName(String firstName) {
        createUser.setFirstName(firstName);
        return this;
    }

    public CreateUserBuilder withLastName(String lastName) {
        createUser.setLastName(lastName);
        return this;
    }

    public CreateUserBuilder withNoParticipation(int noParticipation) {
        createUser.setNoParticipation(noParticipation);
        return this;
    }

    public CreateUserBuilder withNoAuctionWon(int noAuctionWon) {
        createUser.setNoAuctionsWon(noAuctionWon);
        return this;
    }

    public CreateUser build() {
        return createUser;
    }
}
