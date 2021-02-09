package commander.createuser;

import loginsql.clientconnection.usersql.AddUserSQL;

import java.util.List;

/**
 * create user command builder
 */
public class CreateUserBuilderCommand {

    private final CreateUser createUser = new CreateUser();

    /**
     * build a create user command with username
     * @param username username of command
     * @return CreateUserBuilderCommand instance
     */
    public CreateUserBuilderCommand withUsername(String username) {
        createUser.setUsername(username);
        return this;
    }

    /**
     * build a create user command with email
     * @param email email of command
     * @return CreateUserBuilderCommand instance
     */
    public CreateUserBuilderCommand withEmail(String email) {
        createUser.setEmail(email);
        return this;
    }

    /**
     * build a create user command with password
     * @param password password of command
     * @return CreateUserBuilderCommand instance
     */
    public CreateUserBuilderCommand withPassword(String password) {
        AddUserSQL.password = password;
        return this;
    }

    /**
     * build a create user command with first name
     * @param firstName first name of user
     * @return CreateUserBuilderCommand instance
     */
    public CreateUserBuilderCommand withFirstName(String firstName) {
        createUser.setFirstName(firstName);
        return this;
    }

    /**
     * build a create user command with last name
     * @param lastName last name of user
     * @return CreateUserBuilderCommand instance
     */
    public CreateUserBuilderCommand withLastName(String lastName) {
        createUser.setLastName(lastName);
        return this;
    }

    /**
     * build a create user command with address
     * @param address address of command
     * @return CreateUserBuilderCommand instance
     */
    public CreateUserBuilderCommand withAddress(String address) {
        createUser.setAddress(address);
        return this;
    }

    /**
     * build a create user command with other parameters depending on user type
     * @param otherParameters other parameters of command
     * @return CreateUserBuilderCommand instance
     */
    public CreateUserBuilderCommand withOtherParameters(List<String> otherParameters) {
        createUser.setRestParameters(otherParameters);
        return this;
    }

    /**
     * assembly the components of a create user command
     * @return CreateUserBuilderCommand instance
     */
    public CreateUser build() {
        return createUser;
    }
}
