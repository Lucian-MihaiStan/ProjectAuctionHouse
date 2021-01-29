package commander.updateclient;

import client.User;
import loginsql.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateClientDB {
    private final User user;

    public UpdateClientDB(User user){
        this.user = user;
    }

    public void updateData(MySQLConnection mySQLConnection) {
        String updateQuery = "UPDATE auctionhouse.client SET noParticipation=" + user.getNoParticipation()
                + ", noAuctionsWon=" + user.getWonAuctions() + " WHERE username='" + user.getUsername() + "'";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
