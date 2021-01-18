package commander;

import java.util.List;
import java.util.Map;

public interface ICommand {
    Map<String, Object> extractParameters(List<String> listParameters);
    void execute();
}
