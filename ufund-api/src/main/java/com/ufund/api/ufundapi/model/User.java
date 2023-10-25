package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Inventory entity
 * 
 * @author SWEN Faculty
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "User [ username=%s, password=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    /**
     * Create a inventory with the given id and name
     * @param id The id of the need
     * @param name The name of the need
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the id of the need
     * @return The id of the need
     */

    /**
     * Sets the name of the need - necessary for JSON object to Java object deserialization
     * @param name The name of the need
     */
    public void setUser(String username) {this.username = username;}

    /**
     * Retrieves the name of the need
     * @return The name of the need
     */
    public String getUsername() {return username;}

    /**
     * Sets the name of the need - necessary for JSON object to Java object deserialization
     * @param name The name of the need
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Retrieves the name of the need
     * @return The name of the need
     */
    public String getPassword() {return password;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,username,password);
    }
}
