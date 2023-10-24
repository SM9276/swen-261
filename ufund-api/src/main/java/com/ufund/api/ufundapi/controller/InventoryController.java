package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.persistence.InventoryDAO;
import com.ufund.api.ufundapi.model.Inventory;

/**
 * Handles the REST API requests for the Inventory resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("inventory")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDAO inventoryDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param inventoryDao The {@link InventoryDAO Inventory Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public InventoryController(InventoryDAO inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Inventory inventory} for the given id
     * 
     * @param id The id used to locate the {@link Inventory inventory}
     * 
     * @return ResponseEntity with {@link Inventory inventory} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventory(@PathVariable int id) {
        LOG.info("GET /needs/" + id);
        try {
            Inventory inventory = inventoryDao.getInventory(id);
            if (inventory != null)
                return new ResponseEntity<Inventory>(inventory,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Inventory inventory}
     * 
     * @return ResponseEntity with array of {@link Inventory inventory} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Inventory[]> getInventory() {
        LOG.info("GET /needs");

        try {
            Inventory[] inventory = inventoryDao.getInventories();
            if (inventory != null)
                return new ResponseEntity<Inventory[]>(inventory,HttpStatus.OK);
            else
                return new ResponseEntity<Inventory[]>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Inventory inventory} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Inventory inventory}
     * 
     * @return ResponseEntity with array of {@link Inventory inventory} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all inventories that contain the text "ma"
     * GET http://localhost:8080/ufund/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Inventory[]> searchInventory(@RequestParam String name) {
        LOG.info("GET /needs/?name="+name);

        try {
            Inventory[] inventory = inventoryDao.findInventories(name);
            if (inventory != null)
                return new ResponseEntity<Inventory[]>(inventory,HttpStatus.OK);
            else
                return new ResponseEntity<Inventory[]>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Inventory inventory} with the provided inventory object
     * 
     * @param inventory - The {@link Inventory inventory} to create
     * 
     * @return ResponseEntity with created {@link Inventory inventory} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Inventory inventory} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        LOG.info("POST /needs " + inventory);

        try {
            if (inventory != null){
                inventoryDao.createInventory(inventory);
                return new ResponseEntity<Inventory>(inventory,HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<Inventory>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Inventory inventory} with the provided {@linkplain Inventory inventory} object, if it exists
     * 
     * @param inventory The {@link Inventory inventory} to update
     * 
     * @return ResponseEntity with updated {@link Inventory inventory} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory inventory) {
        LOG.info("PUT /needs " + inventory);

        try {
            Inventory current_inventory = inventoryDao.updateInventory(inventory);
            if (current_inventory != null)
                return new ResponseEntity<Inventory>(inventory, HttpStatus.OK);
            else

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Inventory inventory} with the given id
     * 
     * @param id The id of the {@link Inventory inventory} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Inventory> deleteInventory(@PathVariable int id) {
        LOG.info("DELETE /needs/" + id);
        try {
            //Inventory inventory = inventoryDao.getInventory(id);
            if (inventoryDao.deleteInventory(id))
                return new ResponseEntity<Inventory>(HttpStatus.OK);
                
            else
                return new ResponseEntity<Inventory>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
