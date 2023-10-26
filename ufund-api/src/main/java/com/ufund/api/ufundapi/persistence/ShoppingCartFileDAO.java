package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Product;
import com.ufund.api.ufundapi.model.ShoppingCart;
import com.ufund.api.ufundapi.model.User;
@Component
public class ShoppingCartFileDAO implements ShoppingCartDAO{
    Map<String,ShoppingCart> shoppingCarts;   // Provides a local cache of the inventory objects
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
    private ShoppingCart[] getShoppingCartsArray() {
        return getShoppingCartsArray(null);
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
    private ShoppingCart[] getShoppingCartsArray(String containsText) { // if containsText == null, no filter
        ArrayList<ShoppingCart> shoppingCartArrayList = new ArrayList<>();

        for (ShoppingCart shoppingCart : shoppingCarts.values()) {
            if (containsText == null || shoppingCart.getShoppingCart().contains(containsText)) {
                shoppingCartArrayList.add(shoppingCart);
            }
        }

        ShoppingCart[] shoppingCartArray = new ShoppingCart[shoppingCartArrayList.size()];
        shoppingCartArrayList.toArray(shoppingCartArray);
        return shoppingCartArray;
    }

    public ShoppingCartFileDAO(@Value("${shopping-cart.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the inventories from the file
    }
    @Override
    public ShoppingCart getShoppingCart(String name) throws IOException {
       synchronized(shoppingCarts) {
            if (shoppingCarts.containsKey(name))
                return shoppingCarts.get(name);
            else
                return null;
        }
    }

    @Override
    public ShoppingCart[] findShoppingCart(String containsText) throws IOException {
        synchronized(shoppingCarts) {
            return getShoppingCartsArray(containsText);
        }
    }
    private boolean load() throws IOException {
        shoppingCarts = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of inventories
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        ShoppingCart[] shoppingCartArray = objectMapper.readValue(new File(filename),ShoppingCart[].class);

        // Add each inventory to the tree map and keep track of the greatest id
        for (ShoppingCart shoppingCart : shoppingCartArray) {
            shoppingCarts.put(shoppingCart.getShoppingCart(), shoppingCart);

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
        ShoppingCart[] shoppingCartsArray = getShoppingCartsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),shoppingCartsArray);
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) throws IOException {
        synchronized(shoppingCarts) {
            // We create a new inventory object because the id field is immutable
            // and we need to assign the next unique id
            ShoppingCart newShoppingCart = new ShoppingCart(shoppingCart.getShoppingCart(), shoppingCart.getProducts());
            shoppingCarts.put(newShoppingCart.getShoppingCart(),newShoppingCart);
            save(); // may throw an IOException
            return newShoppingCart;
        }
    }


}
