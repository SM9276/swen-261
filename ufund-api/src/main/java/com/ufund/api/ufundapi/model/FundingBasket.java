package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Inventory entity
 * 
 * @author SWEN Faculty
 */
public class FundingBasket {
    private static final Logger LOG = Logger.getLogger(FundingBasket.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "funding Basket[username=%s, needs=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("needs") private Need[] needs;
    @JsonProperty("bought") private Need[] bought;

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
    public FundingBasket(@JsonProperty("username") String username, @JsonProperty("needs") Need[] needs, @JsonProperty ("bought") Need[] bought) {
        this.username = username;
        this.needs = needs;
        this.bought = bought;
    }

    /**
     * Retrieves the name of the need
     * @return The name of the need
     */
    public String getFundingBasket() {return username;}

    public Need[] getNeeds() {return needs;}
    
    public void setNeeds(Need[] needs) {
        this.needs = needs;
    }
    public Need[] getBought() {return bought;}
    public void setBought(Need[] bought){
        this.bought = bought;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,username,needs);
    }
}
