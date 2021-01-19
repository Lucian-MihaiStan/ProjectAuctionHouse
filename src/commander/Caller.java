package commander;

import commander.addproduct.ProductBuilderCommander;
import commander.createuser.UserBuilderCommander;
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
                    new UserBuilderCommander()
                            .withId(Integer.parseInt(elements.get(1)))
                            .withFirstName(elements.get(2))
                            .withLastName(elements.get(3))
                            .withAddress(elements.get(4))
                            .withOtherParameters(elements.subList(5, elements.size()))
                            .build();
            case ADD_PRODUCT ->
                    new ProductBuilderCommander()
                            .withId(Integer.parseInt(elements.get(1)))
                            .withProductType(Integer.parseInt(elements.get(2)))
                            .withName(elements.get(3))
                            .withSellingPrice(Double.parseDouble(elements.get(4)))
                            .withMinimumPrice(Double.parseDouble(elements.get(5)))
                            .withYear(Integer.parseInt(elements.get(6)))
                            .withOtherParameters(elements.subList(7, elements.size()))
                            .build();
            case LIST_USERS -> new ListUsers();
            case LIST_PRODUCTS -> new ListProducts();
        };
    }
    
    public static void executeCommands() {
        commands.forEach(ICommand::execute);
    }
}
