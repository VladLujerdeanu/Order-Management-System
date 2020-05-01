package business;

import database.query.dbProduct;
import model.Product;

import java.util.List;

/**
 * This class implements the logic for the operations on the database.
 */

public class ProductLogic {
    private dbProduct dbo;

    public ProductLogic() {
        dbo = new dbProduct();
    }

    /**
     * This method inserts a product into the database if it isn't already in the database If it's in the database it updates the information.
     * @param name product's name
     * @param quantity product's quantity
     * @param price product's price
     * @return products's id if successful and -1 if unsuccessful
     */
    public int insertProduct(String name, int quantity, float price) {
        Product p = dbo.findByName(name);
        if (p != null) {
            return dbo.update(p, new Product(name, p.getQuantity() + quantity, price));
        }
        return dbo.insert(new Product(name, quantity, price));
    }

    /**
     * This method updates the fields of the given product as the first parameter with the information from the product given as the second parameter.
     * @param oldP Product to be updated
     * @param newP new Product
     */
    public void update(Product oldP, Product newP) {
        dbo.update(oldP, newP);
    }

    /**
     * This method deletes the object with the given id from the database
     * @param id object's id
     * @return return true if successful and false if unsuccessful
     */
    public boolean delete(int id) {
        if (dbo.findById(id) != null) {
            return dbo.delete(id);
        }
        return false;
    }

    /**
     * This method returns a list of objects from the table
     * @return list of objects from the database
     */
    public List<Product> findAll() {
        return dbo.findAll();
    }

    /**
     * This method returns the object with the given id
     * @param id object's id
     * @return object found at the given id or null
     */
    public Product findByID(int id) {
        return dbo.findById(id);
    }

    /**
     * This method implements a query for finding a product's by name.
     * @param name product's name
     * @return a Product object with the information stored in the database
     */
    public Product findByName(String name) {
        Product res = dbo.findByName(name);
        if (res == null) {
            return null;
        }
        return res;
    }
}
