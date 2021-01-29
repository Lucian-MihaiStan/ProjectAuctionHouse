package auction.updatedata;

import client.User;
import commander.deleteproduct.DeleteProductDB;
import commander.updateclient.UpdateClientDB;
import loginsql.MySQLConnection;

import java.sql.SQLException;
import java.util.List;

public class UpdateDataDBAfterAuction {
    private final MySQLConnection mySQLConnection = new MySQLConnection();
    public void updateDataDBBeforeAuction(int productId, List<User> clientsParticipating) {
        try {
            this.mySQLConnection.realizeConnectionAsAdmin();

            DeleteProductDB.deleteFromDB(productId, mySQLConnection);


            clientsParticipating.forEach(user -> {
                UpdateClientDB updateClientDB = new UpdateClientDB(user);
                updateClientDB.updateData(mySQLConnection);
            });


        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
