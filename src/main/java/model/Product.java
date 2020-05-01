package model;

/**
 * This class represents the Products objects extracted from the database.
 */

public class Product {
    private int id = 0;
    private String name;
    private int quantity;
    private float price;

    /**
     * This constructor which contains all the available variables
     * @param id product's id
     * @param name product's name
     * @param quantity product's quantity
     * @param price product's price
     */
    public Product(int id, String name, int quantity, float price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * The Constructor which contains all the variables minus the id.
     * @param name product's name
     * @param quantity product's quantity
     * @param price product's price
     */
    public Product(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Empty constructor
     */
    public Product(){
        this.id = 0;
        this.name = "";
        this.quantity = 0;
        this.price = 0;
    }

    /**
     * This method returns the id of the products
     * @return product's id
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the id of the product
     * @param id product's new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the name of the products
     * @return product's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the name of the product
     * @param name product's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the quantity of the products
     * @return product's quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This method sets the quantity of the product
     * @param quantity product's new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * This method returns the price of the products
     * @return product's price
     */
    public float getPrice() {
        return price;
    }

    /**
     * This method sets the price of the product
     * @param price product's new price
     */
    public void setPrice(float price) {
        this.price = price;
    }
}
