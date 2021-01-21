package commander;

import commander.addproduct.ProductBuilderCommander;
import commander.createuser.UserBuilderCommander;
import features.Features;

import java.util.ArrayList;
import java.util.List;

public class Caller {
    private static List<ICommand> commands = new ArrayList<>();

    private Caller() {}

    public static void addCommand(List<String> parameters) {
        commands.add(getCommand(parameters));
    }

    private static ICommand getCommand(List<String> elements) {
        return switch (Features.valueOf(elements.get(0))) {
            case CREATE_USER ->
                    new UserBuilderCommander()
                            .withFirstName(elements.get(1))
                            .withLastName(elements.get(2))
                            .withAddress(elements.get(3))
                            .withOtherParameters(elements.subList(4, elements.size()))
                            .build();
            case ADD_PRODUCT ->
                    new ProductBuilderCommander()
                            .withProductType(Integer.parseInt(elements.get(1)))
                            .withName(elements.get(2))
                            .withSellingPrice(Double.parseDouble(elements.get(3)))
                            .withMinimumPrice(Double.parseDouble(elements.get(4)))
                            .withYear(Integer.parseInt(elements.get(5)))
                            .withOtherParameters(elements.subList(6, elements.size()))
                            .build();
            case LIST_USERS -> new ListUsers();
            case LIST_PRODUCTS -> new ListProducts();
        };
    }
    
    public static void executeCommands() {
        commands.forEach(ICommand::execute);
        commands = new ArrayList<>();
    }
}
