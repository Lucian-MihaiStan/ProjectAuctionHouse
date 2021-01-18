package commander.addproduct;

import commander.ICommand;

import java.util.List;
import java.util.Map;

public class AddProduct implements ICommand {
    public AddProduct() {

    }


    @Override
    public Map<String, Object> extractParameters(List<String> listParameters) {
        return null;
    }

    @Override
    public void execute() {

    }
}
