package load_data_db;

import java.util.List;
import java.util.Map;

public interface IAdapterAdmin {
    IAdapterAdmin connectToDatabaseAsAdmin();
    Map<String, List<?>> extractFromDatabase();
}
