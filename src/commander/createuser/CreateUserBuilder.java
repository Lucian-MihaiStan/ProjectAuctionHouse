package commander.createuser;

import java.util.List;

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

    public CreateUserBuilder withAddress(String address) {
        createUser.setAddress(address);
        return this;
    }

    public CreateUserBuilder withOtherParameters(List<String> otherParameters) {
        createUser.setRestParameters(otherParameters);
        return this;
    }

    public CreateUser build() {
        return createUser;
    }
}
