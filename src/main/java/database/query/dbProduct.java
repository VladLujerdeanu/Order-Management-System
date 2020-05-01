package database.query;

import database.dbConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class extends dbAbstract, implemented with the Product model.
 */

public class dbProduct extends dbAbstract<Product>{

    public dbProduct(){
        super();
    }

    /**
     * This method implements a query for finding a product by name.
     * @param name product's name
     * @return a Product object with the information stored in the database
     */
    public Product findByName(String name) {
        Product found = null;

        Connection dbConn = dbConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        String findByDetailsStatementString = super.createSelectQuery("name");
        try {
            findStatement = dbConn.prepareStatement(findByDetailsStatementString);
            findStatement.setString(1, name);
            rs = findStatement.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");

                found = new Product(id, productName, quantity, price);
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
