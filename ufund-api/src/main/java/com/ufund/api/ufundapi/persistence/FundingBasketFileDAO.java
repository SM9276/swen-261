package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.User;
@Component
public class FundingBasketFileDAO implements FundingBasketDAO{
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<String,FundingBasket> fundingBaskets;   // Provides a local cache of the inventory objects
    // so that we don't need to read from the file
    // each time

    private ObjectMapper objectMapper;  // Provides conversion between Inventory
            // objects and JSON text format written
            // to the file

    private String filename;    // Filename to read from and write to
    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    public FundingBasketFileDAO(@Value("${funding-basket.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the inventories from the file
    }
    private FundingBasket[] getFundingBasketsArray() {
        return getFundingBasketsArray(null);
    }
    /**
     * Generates an array of {@linkplain User users} from the tree map for any
     * {@linkplain User users} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User user}
     * in the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private FundingBasket[] getFundingBasketsArray(String containsText) { // if containsText == null, no filter
        ArrayList<FundingBasket> fundingBasketArrayList = new ArrayList<>();

        for (FundingBasket fundingBasket : fundingBaskets.values()) {
            if (containsText == null || fundingBasket.getFundingBasket().contains(containsText)) {
                fundingBasketArrayList.add(fundingBasket);
            }
        }

        FundingBasket[] fundingBasketArray = new FundingBasket[fundingBasketArrayList.size()];
        fundingBasketArrayList.toArray(fundingBasketArray);
        return fundingBasketArray;
    }


    @Override
    public FundingBasket getFundingBasket(String name) throws IOException {
       synchronized(fundingBaskets) {
            if (fundingBaskets.containsKey(name))
                return fundingBaskets.get(name);
            else
                return null;
        }
    }


    private boolean load() throws IOException {
        fundingBaskets = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of inventories
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        FundingBasket[] fundingBasketArray = objectMapper.readValue(new File(filename),FundingBasket[].class);

        // Add each inventory to the tree map and keep track of the greatest id
        for (FundingBasket fundingBasket : fundingBasketArray) {
            fundingBaskets.put(fundingBasket.getFundingBasket(), fundingBasket);
            System.out.println("yo");
        }
        return true;
    }
        /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        FundingBasket[] fundingBasketsArray = getFundingBasketsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),fundingBasketsArray);
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public FundingBasket createFundingBasket(FundingBasket fundingBasket) throws IOException {
        synchronized(fundingBaskets) {
            // We create a new inventory object because the id field is immutable
            // and we need to assign the next unique id
            FundingBasket newFundingBasket = new FundingBasket(fundingBasket.getFundingBasket(), fundingBasket.getNeeds());
            fundingBaskets.put(newFundingBasket.getFundingBasket(),newFundingBasket);
            save(); // may throw an IOException
            return newFundingBasket;
        }
    }
    @Override
    public FundingBasket updateFundingBasket(FundingBasket fundingBasket) throws IOException {
        synchronized(fundingBaskets) {
            if (fundingBaskets.containsKey(fundingBasket.getFundingBasket()) == false)
                return null;  // inventory does not exist

            fundingBaskets.put(fundingBasket.getFundingBasket(),fundingBasket);
            save(); // may throw an IOException
            return fundingBasket;
        }
    }



}
