package model;

/**
 * This class represents the Order objects extracted from the database.
 */

public class Ordert {
    private int id;
    private int clientID;
    private float total;

    /**
     * This constructor which contains all the available variables
     * @param id order's id
     * @param clientID client's id
     * @param total order's total
     */
    public Ordert(int id, int clientID, float total) {
        this.id = id;
        this.clientID = clientID;
        this.total = total;
    }

    /**
     * The Constructor which contains all the variables minus the id.
     * @param clientID client's id
     * @param total order's total
     */
    public Ordert(int clientID, float total) {
        this.clientID = clientID;
        this.total = total;
    }

    /**
     * Empty constructor
     */
    public Ordert(){
        this.id = 0;
        this.clientID = 0;
        this.total = 0;
    }

    /**
     * This method returns the id of the order
     * @return order's id
     */
    public int getId() {
        return id;
    }

    /**
     * This method set's a new id for the order
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the client's id
     * @return order's id
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * This method set's a new client id
     * @param clientID the new client id
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * This method returns the total of the order
     * @return order's id
     */
    public float getTotal() {
        return total;
    }

    /**
     * This method set's a new total for the order
     * @param total the new total
     */
    public void setTotal(float total) {
        this.total = total;
    }
}
