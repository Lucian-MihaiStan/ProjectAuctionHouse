package commander.createuser;

import java.util.List;

public class CreateUserBuilderCommand {

    private final CreateUser createUser = new CreateUser();

    public CreateUserBuilderCommand withFirstName(String firstName) {
        createUser.setFirstName(firstName);
        return this;
    }

    public CreateUserBuilderCommand withLastName(String lastName) {
        createUser.setLastName(lastName);
        return this;
    }

    public CreateUserBuilderCommand withAddress(String address) {
        createUser.setAddress(address);
        return this;
    }

    public CreateUserBuilderCommand withOtherParameters(List<String> otherParameters) {
        createUser.setRestParameters(otherParameters);
        return this;
    }

    public CreateUser build() {
        return createUser;
    }
}
