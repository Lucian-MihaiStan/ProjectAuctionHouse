package commander;

import commander.addproduct.AddProduct;
import commander.createuser.CreateUserBuilder;
import features.Features;

import java.util.ArrayList;
import java.util.List;

public class Caller {
    private static final List<ICommand> commands = new ArrayList<>();

    private Caller() {}

    public static void addCommand(List<String> parameters) {
        commands.add(getCommand(parameters));
    }

    private static ICommand getCommand(List<String> elements) {
        return switch (Features.valueOf(elements.get(0))) {
            case CREATE_USER ->
                    new CreateUserBuilder()
                            .withId(Integer.parseInt(elements.get(1)))
                            .withFirstName(elements.get(2))
                            .withLastName(elements.get(3))
                            .withAddress(elements.get(4))
                            .withOtherParameters(elements.subList(5, elements.size()))
                            .build();
            case ADD_PRODUCT -> new AddProduct();
            case LIST_USERS -> new ListUsers();
        };
    }
    
    public static void executeCommands() {
        commands.forEach(ICommand::execute);
    }
}
