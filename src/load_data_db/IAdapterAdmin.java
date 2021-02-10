package load_data_db;

import java.util.List;
import java.util.Map;

/**
 * load database as admin interface
 */
public interface IAdapterAdmin {
    IAdapterAdmin connectToDatabaseAsAdmin();
    Map<String, List<?>> extractFromDatabase();
}
