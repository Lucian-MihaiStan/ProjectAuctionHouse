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
            case createUser ->
                    new CreateUserBuilder()
                            .withId(Integer.parseInt(elements.get(1)))
                            .withFirstName(elements.get(2))
                            .withLastName(elements.get(3))
                            .withNoParticipation(Integer.parseInt(elements.get(4)))
                            .withNoAuctionWon(Integer.parseInt(elements.get(5)))
                            .build();
            case addProduct -> new AddProduct();
        };
    }
    
    public static void executeCommands() {
        commands.forEach(ICommand::execute);
    }
}
