package database.query;

import database.dbConnection;
import model.Client;

import java.sql.*;

/**
 * This class extends dbAbstract, implemented with the Client model.
 */

public class dbClient extends dbAbstract<Client>{

    public dbClient(){
        super();
    }

    /**
     * This method implements a query for finding a client by name.
     * @param firstName client's first name
     * @param lastName client's last name
     * @return a Client object with the information stored in the database
     */
    public Client findByName(String firstName, String lastName) {
        Client found = null;

        Connection dbConn = dbConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        String findByDetailsStatementString = super.createSelectQuery(new String[]{"firstName", "lastName"});
        try {
            findStatement = dbConn.prepareStatement(findByDetailsStatementString);
            findStatement.setString(1, firstName);
            findStatement.setString(2, lastName);
            rs = findStatement.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                String clientFirstName = rs.getString("firstName");
                String clientLastName = rs.getString("lastName");
                String clientAddress = rs.getString("address");

                found = new Client(id, clientFirstName, clientLastName, clientAddress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close(rs);
            dbConnection.close(findStatement);
            dbConnection.close(dbConn);
        }

        return found;
    }
}
