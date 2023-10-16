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
    static final String STRING_FORMAT = "Product [id=%d, name=%s]";

    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("id") private int id;

    public Product(){

    }
    public Product(String name, double price, int quantity, int id) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
    }

    /**
     * Retrieves the id of the product
     * @return The id of the product
     */
    public int getQuantity(){
        return quantity;
    }
    public double getPrice(){
        return price;
    }

    /**
     * Retrieves the id of the product
     * @return The id of the product
     */
    public int getId() {return id;}


    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {this.name = name;}
    public void setQuantity(int quant){this.quantity = quant;}
    public void setPrice(double pri){this.price = pri;}
    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id, name);
    }
}
