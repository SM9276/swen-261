package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Inventory entity
 * 
 * @author SWEN Faculty
 */
public class ShoppingCart {
    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "shopping Cart[username=%s, products=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("products") private Product[] products;

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
    public ShoppingCart(@JsonProperty("username") String username, @JsonProperty("products") Product[] products) {
        this.username = username;
        this.products = products;
    }

    /**
     * Retrieves the name of the need
     * @return The name of the need
     */
    public String getShoppingCart() {return username;}

    public Product[] getProducts() {return products;}
    
    public void setProducts(Product[] products) {
        this.products = products;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,username,products);
    }
}
