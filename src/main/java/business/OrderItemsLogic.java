package business;

import database.dbConnection;
import database.query.dbAbstract;
import database.query.dbOrderItems;
import model.OrderItems;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * This class implements the logic for the operations on the database.
 */

public class OrderItemsLogic {
    private dbOrderItems dbo;

    public OrderItemsLogic() {
        dbo = new dbOrderItems();
    }

    /**
     * This method inserts a order item into the database.
     * @param id order's id
     * @param productID product's id
     * @param quantity quantity of products
     * @return the order id
     */
    public int insertItems(int id, int productID, int quantity) {
        Connection dbConn = dbConnection.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            dbOrderItems dba = new dbOrderItems();
            insertStatement = dbConn.prepareStatement("INSERT INTO OrderItems (id, productID, quantity) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, id);
            insertStatement.setInt(2, productID);
            insertStatement.setInt(3, quantity);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print("Order placed successfully!\n> ");
        return id;
    }

    /**
     * This method deletes the object with the given id from the database
     * @param id object's id
     * @return return true if successful and false if unsuccessful
     */
    public boolean delete(int id) {
        return dbo.delete(id);
    }

    /**
     * This method returns a list of objects from the table
     * @return list of objects from the database
     */
    public List<OrderItems> findAll() {
        return dbo.findAll();
    }

    /**
     * This method returns the object with the given id
     * @param id object's id
     * @return object found at the given id or null
     */
    public OrderItems findByID(int id) {
        return dbo.findById(id);
    }
}
