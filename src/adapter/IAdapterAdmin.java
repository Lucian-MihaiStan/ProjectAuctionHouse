package adapter;

import java.util.List;
import java.util.Map;

public interface IAdapterAdmin {
    IAdapterAdmin connectToDatabaseAsAdmin();
    Map<String, List<Object>> extractFromDatabase();
}
