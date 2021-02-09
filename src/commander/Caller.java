package commander;

import commander.addproduct.ProductBuilderCommander;
import commander.auctions.DisplayAuction;
import commander.auctions.EnrollToAuction;
import commander.auctions.ShowAuctions;
import commander.createuser.CreateUserBuilderCommand;
import commander.deleteproduct.DeleteProduct;
import features.Features;
import socketserver.ServerClientThread;

import java.util.ArrayList;
import java.util.List;

public class Caller {
    private Caller() {}

    public static synchronized void addCommand(List<String> parameters, ServerClientThread sct) {
        sct.getCommands().add(getCommand(parameters));
    }

    private static synchronized ICommand getCommand(List<String> elements) {
        return switch (Features.valueOf(elements.get(0))) {
            case CREATE_USER ->
                    new CreateUserBuilderCommand()
                            .withUsername(elements.get(1))
                            .withEmail(elements.get(2))
                            .withPassword(elements.get(3))
                            .withFirstName(elements.get(4))
                            .withLastName(elements.get(5))
                            .withAddress(elements.get(6))
                            .withOtherParameters(elements.subList(7, elements.size()))
                            .build();
            case ADD_PRODUCT ->
                    new ProductBuilderCommander()
                            .withProductType(Integer.parseInt(elements.get(1)))
                            .withName(elements.get(2))
                            .withMinimumPrice(Double.parseDouble(elements.get(3)))
                            .withYear(Integer.parseInt(elements.get(4)))
                            .withOtherParameters(elements.subList(5, elements.size()))
                            .build();
            case LIST_USERS -> new ListUsers();
            case LIST_PRODUCTS -> new ListProducts();
            case DELETE_PRODUCT -> new DeleteProduct(Integer.parseInt(elements.get(1)));
            case SHOW -> new ShowUser();
            case ENROLL_AUCTION -> new EnrollToAuction(Integer.parseInt(elements.get(1)), Double.parseDouble(elements.get(2)));
            case SHOW_AUCTIONS -> new ShowAuctions();
            case LIST_BROKERS -> new ListBrokers();
            case AUCTION -> new DisplayAuction(Integer.parseInt(elements.get(1)));
            case EXIT -> null;
        };
    }
    
    public static synchronized void executeCommands(ServerClientThread sct) {
        ServerClientThread.Helper resultCommands = ServerClientThread.Helper.getInstance();
        resultCommands.setCommandResult(new StringBuilder());
        sct.getCommands().forEach(iCommand -> {
            sleepAfterCommand();
            if(iCommand != null) {
                sct.getAuctionHouse().load(sct.getMySQLConnection());
                iCommand.execute(sct);
            }
        });
        sct.setCommands(new ArrayList<>());
    }

    private static void sleepAfterCommand() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
