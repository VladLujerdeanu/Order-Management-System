package business;

import database.query.dbClient;
import model.Client;

import java.util.List;

/**
 * This class implements the logic for the operations on the database.
 */

public class ClientLogic {
    private dbClient dbc;

    public ClientLogic() {
        dbc = new dbClient();
    }

    /**
     * This method inserts a client into the database if there isn't already a client into the database.
     * @param firstName client's first name
     * @param lastName client's last name
     * @param address client's address
     * @return client's id if successful and -1 if unsuccessful
     */
    public int insertClient(String firstName, String lastName, String address) {
        Client check = findByName(firstName, lastName);
        if(check != null) {
            if (!check.getFirstName().equalsIgnoreCase(firstName) && !check.getLastName().equalsIgnoreCase(lastName) && !check.getAddress().equalsIgnoreCase(address)) {
                return dbc.insert(new Client(firstName, lastName, address));
            } else {
                return -1;
            }
        } else {
            return dbc.insert(new Client(firstName, lastName, address));
        }
    }

    /**
     * This method deletes the object with the given id from the database
     * @param id object's id
     * @return return true if successful and false if unsuccessful
     */
    public boolean delete(int id) {
        return dbc.delete(id);
    }

    /**
     * This method returns a list of objects from the table
     * @return list of objects from the database
     */
    public List<Client> findAll() {
        return dbc.findAll();
    }

    /**
     * This method returns the object with the given id
     * @param id object's id
     * @return object found at the given id or null
     */
    public Client findByID(int id) {
        return dbc.findById(id);
    }

    /**
     * This method implements a query for finding a client by name.
     * @param firstName client's first name
     * @param lastName client's last name
     * @return a Client object with the information stored in the database
     */
    public Client findByName(String firstName, String lastName) {
        return dbc.findByName(firstName, lastName);
    }
}
