package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Inventory;

/**
 * Defines the interface for Hero object persistence
 * 
 * @author SWEN Faculty
 */
public interface InventoryDAO {
    /**
     * Retrieves all {@linkplain Inventory inventories}
     * 
     * @return An array of {@link Inventory inventories} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Inventory[] getInventories() throws IOException;

    /**
     * Finds all {@linkplain Inventory inventories} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Inventory inventories} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Inventory[] findInventories(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Inventory inventory} with the given id
     * 
     * @param id The id of the {@link Inventory inventory} to get
     * 
     * @return a {@link Inventory inventory} object with the matching id
     * <br>
     * null if no {@link Inventory inventory} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Inventory getInventory(int id) throws IOException;

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
    Inventory createInventory(Inventory inventory) throws IOException;

    /**
     * Updates and saves a {@linkplain Inventory inventory}
     * 
     * @param {@link Inventory inventory} object to be updated and saved
     * 
     * @return updated {@link Inventory inventory} if successful, null if
     * {@link Inventory inventory} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Inventory updateInventory(Inventory inventory) throws IOException;

    /**
     * Deletes a {@linkplain Inventory inventory} with the given id
     * 
     * @param id The id of the {@link Inventory inventory}
     * 
     * @return true if the {@link Inventory inventory} was deleted
     * <br>
     * false if inventory with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteInventory(int id) throws IOException;
}

