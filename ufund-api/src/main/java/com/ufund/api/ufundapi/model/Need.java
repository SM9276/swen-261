package com.ufund.api.ufundapi.model;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Need {
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Need [id=%d, name=%s, price=%f, quantity=%d, amount=%d]";
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("amount") private int amount;
    
    /**
     * Create a need with the given name, price and quantity
     * @param name The name of the need
     * @param price The price of the need. Cannot be < 0
     * @param quantity The quantity of the need. Cannot be < 0
     * 
     */
    public Need(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("quantity") int quantity, @JsonProperty("amount") int amount) {
        this.name = name;
        this.id = id;
        if (price < 0) {
            this.price = 0;
        } 
        else {this.price=price;}

        if (quantity < 0) {
            this.quantity = 0;
        } 
        else {this.quantity=quantity;}
        this.amount = amount;
    }

    /**
     * Gets the name of the need
     * @return The name of the need
     */
    public String getName(){return name;}

    /**
     * Sets the name of the need
     * @param name The name of the need
     */
    public void setName(String name){this.name = name;}

    /**
     * Gets the price of the need
     * @return the price of the need
     */
    public double getPrice(){return price;}
    public int getId(){return id;}

    /**
     * Sets the price of the need
     * @param price The price of the need, must be >= 0
     */
    public void setPrice(double price) {
        if (price < 0) {
            this.price = 0;
        } 
        else {this.price=price;}
    }

    /**
     * Gets the quantity of the need
     * @return the quantity of the need
     */
    public int getQuantity(){return quantity;}

    /**
     * Sets the quantity of the need
     * @param quantity The quantity of the need, must be >= 0
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            this.quantity = 0;
        } 
        else {this.quantity=quantity;}
    }
    public int getAmount(){
        return amount;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id, name, price, quantity, amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Need) {
            Need needObj = (Need) obj;
            if ((needObj.getName().equalsIgnoreCase(this.name)) && (Double.compare(needObj.getPrice(), this.price) == 0)) {
                return true;
            } 
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}