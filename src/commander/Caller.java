package commander;

import commander.addproduct.ProductBuilderCommander;
import commander.createuser.CreateUserBuilderCommand;
import commander.deleteproduct.DeleteProduct;
import features.Features;
import socketserver.ServerClientThread;

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
                    new CreateUserBuilderCommand()
                            .withUsername(elements.get(1))
                            .withFirstName(elements.get(2))
                            .withLastName(elements.get(3))
                            .withAddress(elements.get(4))
                            .withOtherParameters(elements.subList(5, elements.size()))
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
            case DELETE_PRODUCT -> new DeleteProduct(Integer.parseInt(elements.get(1)));
            case EXIT -> null;
        };
    }
    
    public static void executeCommands() {
        commands.forEach(iCommand -> {
            ServerClientThread.auctionHouse.load();
            iCommand.execute();
        });
        commands = new ArrayList<>();
    }
}
