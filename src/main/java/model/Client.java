package model;

import java.util.Objects;

/**
 * This class represents the Client objects extracted from the database.
 */

public class Client {
    private int id = 0;
    private String firstName = "";
    private String lastName = "";
    private String address = "";

    /**
     * The Constructor which contains all the available variables.
     * @param id client's id
     * @param firstName client's first name
     * @param lastName client's last name
     * @param address client's address
     */

    public Client(int id, String firstName, String lastName, String address){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    /**
     * The Constructor which contains all the variables minus the id.
     * @param firstName client's first name
     * @param lastName client's last name
     * @param address client's address
     */
    public Client(String firstName, String lastName, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    /**
     * Empty constructor.
     */

    public Client(){
        this.firstName = "";
        this.lastName = "";
        this.address = "";
    }

    /**
     * This method returns the id of the client.
     * @return client's id
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the id of the client.
     * @param id client's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the first name of the client.
     * @return client's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method sets the first name of the client.
     * @param firstName client's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method returns the last name of the client.
     * @return client's first name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method sets the last name of the client.
     * @param lastName client's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This method returns the address of the client.
     * @return client's first name
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method sets the address of the client.
     * @param address client's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method overrides the equals method.
     * @param o object to be compared with
     * @return true if equal or false if not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(firstName, client.firstName) &&
                Objects.equals(lastName, client.lastName) &&
                Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address);
    }
}
