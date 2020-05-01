package model;

/**
 * This class represents the Order Items objects extracted from the database.
 */

public class OrderItems {
    private int id;
    private int productID;
    private int quantity;

    /**
     * This constructor which contains all the available variables
     * @param id order's id
     * @param productID product's id
     * @param quantity quantity of the products
     */
    public OrderItems(int id, int productID, int quantity){
        this.id = id;
        this.productID = productID;
        this.quantity = quantity;
    }

    /**
     * Empty constructor
     */
    public OrderItems(){
        this.id = 0;
        this.productID = 0;
        this.quantity = 0;
    }

    /**
     * This method returns the id of the order
     * @return order's id
     */
    public int getId() {
        return id;
    }

    /**
     * This method returns the product's id
     * @return order's id
     */
    public int getProductID() {
        return productID;
    }

    /**
     * This method returns the quantity of the products
     * @return order's id
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This method set's a new id for the order
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method set's a new product id for the order
     * @param productID the new product id
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * This method set's a new quantity for the order
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
