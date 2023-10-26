package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Product;
// import com.ufund.api.ufundapi.model.Product;
import com.ufund.api.ufundapi.model.ShoppingCart;
// import com.ufund.api.ufundapi.model.ShoppingCart;

public interface ShoppingCartDAO {
    /**
     * Retrieves a {@linkplain User user} with the given id
     * 
     * @param string The username of the {@link ShoppingCart shoppingCart} to get
     * 
     * @return a {@link ShoppingCart shoppingCart} object with the matching id
     * <br>
     * null if no {@link ShoppingCart shoppingCart} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart getShoppingCart(String name) throws IOException;

    /**
     * Finds all {@linkplain ShoppingCart shoppingCart} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link ShoppingCart shoppingCart} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart[] findShoppingCart(String containsText) throws IOException;
        /**
     * Creates and saves a {@linkplain Inventory inventory}
     * 
     * @param inventory {@linkplain Inventory inventory} object to be created and saved
     * <br>
     * The id of the inventory object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Inventory inventory} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart createShoppingCart(ShoppingCart shoppingCart) throws IOException;
}
