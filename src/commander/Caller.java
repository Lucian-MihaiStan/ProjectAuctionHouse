package commander;

import auction_house.AuctionHouse;
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

    public static synchronized void addCommand(List<String> parameters, AuctionHouse ah) {
        commands.add(getCommand(parameters, ah));
    }

    private static synchronized ICommand getCommand(List<String> elements, AuctionHouse ah) {
        return switch (Features.valueOf(elements.get(0))) {
            case CREATE_USER ->
                    new CreateUserBuilderCommand()
                            .withUsername(elements.get(1))
                            .withPassword(elements.get(2))
                            .withFirstName(elements.get(3))
                            .withLastName(elements.get(4))
                            .withAddress(elements.get(5))
                            .withOtherParameters(elements.subList(6, elements.size()))
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
            case SHOW -> new ShowUser();
            case EXIT -> null;
        };
    }
    
    public static synchronized void executeCommands(ServerClientThread sct, AuctionHouse ah) {
        commands.forEach(iCommand -> {
            if(iCommand != null) {
                ServerClientThread.Helper resultCommands = ServerClientThread.Helper.getInstance();
                resultCommands.setCommandResult(new StringBuilder());
                ah.load(sct.getMySQLConnection());
                iCommand.execute(sct);
            }
        });
        commands = new ArrayList<>();
    }
}
