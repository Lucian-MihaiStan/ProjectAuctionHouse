package auction.updatedata;

import client.User;
import commander.deleteproduct.DeleteProductDB;
import commander.updateclient.UpdateClientDB;
import loginsql.MySQLConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * Update of the database info after auction
 */
public class UpdateDataDBAfterAuction {
    /**
     * connection to database
     */
    private final MySQLConnection mySQLConnection = new MySQLConnection();

    /**
     * Update data of auction
     * @param productId product id
     * @param clientsParticipating clients that participated to auction
     */
    public void updateDataDBAfterAuction(int productId, List<User> clientsParticipating) {
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
