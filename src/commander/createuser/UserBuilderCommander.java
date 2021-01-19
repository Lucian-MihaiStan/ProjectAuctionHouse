package commander.createuser;

import java.util.List;

public class UserBuilderCommander {

    private final CreateUser createUser = new CreateUser();

    public UserBuilderCommander withId(int id) {
        createUser.setId(id);
        return this;
    }

    public UserBuilderCommander withFirstName(String firstName) {
        createUser.setFirstName(firstName);
        return this;
    }

    public UserBuilderCommander withLastName(String lastName) {
        createUser.setLastName(lastName);
        return this;
    }

    public UserBuilderCommander withAddress(String address) {
        createUser.setAddress(address);
        return this;
    }

    public UserBuilderCommander withOtherParameters(List<String> otherParameters) {
        createUser.setRestParameters(otherParameters);
        return this;
    }

    public CreateUser build() {
        return createUser;
    }
}
