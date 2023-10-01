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

import com.ufund.api.ufundapi.model.Inventory;

/**
 * Implements the functionality for JSON file-based peristance for Inventories
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty
 */
@Component
public class InventoryFileDAO implements InventoryDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<Integer,Inventory> inventories;   // Provides a local cache of the inventory objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Inventory
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new inventory
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Inventory File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public InventoryFileDAO(@Value("${ufund.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the inventories from the file
    }

    /**
     * Generates the next id for a new {@linkplain Inventory inventory}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Inventory inventories} from the tree map
     * 
     * @return  The array of {@link Inventory inventories}, may be empty
     */
    private Inventory[] getInventoriesArray() {
        return getInventoriesArray(null);
    }

    /**
     * Generates an array of {@linkplain Inventory inventories} from the tree map for any
     * {@linkplain Inventory inventories} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Inventory inventories}
     * in the tree map
     * 
     * @return  The array of {@link Inventory inventories}, may be empty
     */
    private Inventory[] getInventoriesArray(String containsText) { // if containsText == null, no filter
        ArrayList<Inventory> inventoryArrayList = new ArrayList<>();

        for (Inventory inventory : inventories.values()) {
            if (containsText == null || inventory.getName().contains(containsText)) {
                inventoryArrayList.add(inventory);
            }
        }

        Inventory[] inventoryArray = new Inventory[inventoryArrayList.size()];
        inventoryArrayList.toArray(inventoryArray);
        return inventoryArray;
    }

    /**
     * Saves the {@linkplain Inventory inventories} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Inventory inventories} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Inventory[] inventoryArray = getInventoriesArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),inventoryArray);
        return true;
    }

    /**
     * Loads {@linkplain Inventory inventories} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        inventories = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of inventories
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Inventory[] inventoryArray = objectMapper.readValue(new File(filename),Inventory[].class);

        // Add each inventory to the tree map and keep track of the greatest id
        for (Inventory inventory : inventoryArray) {
            inventories.put(inventory.getId(),inventory);
            if (inventory.getId() > nextId)
                nextId = inventory.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Inventory[] getInventories() {
        synchronized(inventories) {
            return getInventoriesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Inventory[] findInventories(String containsText) {
        synchronized(inventories) {
            return getInventoriesArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Inventory getInventory(int id) {
        synchronized(inventories) {
            if (inventories.containsKey(id))
                return inventories.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Inventory createInventory(Inventory inventory) throws IOException {
        synchronized(inventories) {
            // We create a new inventory object because the id field is immutable
            // and we need to assign the next unique id
            Inventory newInventory = new Inventory(nextId(),inventory.getName());
            inventories.put(newInventory.getId(),newInventory);
            save(); // may throw an IOException
            return newInventory;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Inventory updateInventory(Inventory inventory) throws IOException {
        synchronized(inventories) {
            if (inventories.containsKey(inventory.getId()) == false)
                return null;  // inventory does not exist

            inventories.put(inventory.getId(),inventory);
            save(); // may throw an IOException
            return inventory;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteInventory(int id) throws IOException {
        synchronized(inventories) {
            if (inventories.containsKey(id)) {
                inventories.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
