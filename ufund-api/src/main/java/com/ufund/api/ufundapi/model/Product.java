package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Product entity
 * 
 * @author SWEN Faculty
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Proudct [id=%d, name=%s]";

    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private double quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Retrieves the id of the hero
     * @return The id of the hero
     */
    public int getQuantity(){
        return 0;
    }
    public double getPrice(){
        return this.price;
    }

    /**
     * Sets the name of the hero - necessary for JSON object to Java object deserialization
     * @param name The name of the hero
     */
    public void setName(String name) {this.name = name;}
    public void setQuantity(int quant){this.quantity = quant;}
    public void setPrice(double pri){this.price = pri;}
    /**
     * Retrieves the name of the hero
     * @return The name of the hero
     */
    public String getName() {return name;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,name);
    }
}
