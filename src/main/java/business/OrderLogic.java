package business;

import database.query.dbOrder;
import model.Ordert;

import java.util.List;

/**
 * This class implements the logic for the operations on the database.
 */

public class OrderLogic {
    private dbOrder dbo;

    public OrderLogic() {
        dbo = new dbOrder();
    }

    /**
     * This method inserts a given object in the database
     * @param c given object
     * @return id if successful or -1 if unsuccessful
     */
    public int insertOrder(Ordert c) {
        return dbo.insert(c);
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
    public List<Ordert> findAll() {
        return dbo.findAll();
    }

    /**
     * This method returns the object with the given id
     * @param id object's id
     * @return object found at the given id or null
     */
    public Ordert findByID(int id) {
        return dbo.findById(id);
    }

    /**
     * This method implements a query for finding orders by the client's id.
     * @param id client's id
     * @return list of the client's orders
     */
    public List<Ordert> findByClient(int id) {
        return dbo.findByClient(id);
    }
}
