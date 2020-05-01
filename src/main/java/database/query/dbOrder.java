package database.query;

import database.dbConnection;
import model.Ordert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class extends dbAbstract, implemented with the Order model.
 */

public class dbOrder extends dbAbstract<Ordert>{

    public dbOrder(){
        super();
    }

    /**
     * This method implements a query for finding orders by the client's id.
     * @param id client's id
     * @return list of the client's orders
     */
    public List<Ordert> findByClient(int id) {
        List<Ordert> found = null;

        Connection dbConn = dbConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        String findByDetailsStatementString = super.createSelectQuery("clientID");
        try {
            findStatement = dbConn.prepareStatement(findByDetailsStatementString);
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();

            found = createObjects(rs);
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
