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
import com.ufund.api.ufundapi.persistence.ShoppingCartDAO;
import com.ufund.api.ufundapi.model.Product;
import com.ufund.api.ufundapi.model.ShoppingCart;
import com.ufund.api.ufundapi.model.User;

/**
 * Handles the REST API requests for the Inventory resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("/shopping-cart")
@CrossOrigin(origins = "http://localhost:4200")

public class ShoppingCartController {
    private static final Logger LOG = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartDAO shoppingCartDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param shoppingCartDao The {@link InventoryDAO Inventory Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public ShoppingCartController(ShoppingCartDAO shoppingCartDao) {
        this.shoppingCartDao = shoppingCartDao;
    }

    /**
     * Responds to the GET request for a {@linkplain ShoppingCart shoppingCart} for the given id
     * 
     * @param id The id used to locate the {@link ShoppingCart shoppingCart}
     * 
     * @return ResponseEntity with {@link ShoppingCart shoppingCart} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{name}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable String name) {
        LOG.info("GET /shopping-cart/" + name);
        try {
            ShoppingCart shoppingCart = shoppingCartDao.getShoppingCart(name);
            if (shoppingCart != null)
                return new ResponseEntity<ShoppingCart>(shoppingCart,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain ShoppingCart shoppingCart} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link ShoppingCart shoppingCart}
     * 
     * @return ResponseEntity with array of {@link ShoppingCart shoppingCart} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all inventories that contain the text "ma"
     * GET http://localhost:8080/ufund/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<ShoppingCart[]> searchUserShoppingCart(@RequestParam String name) {
        LOG.info("GET /shopping-cart/?user="+name);

        try {
            ShoppingCart[] shoppingCart = shoppingCartDao.findShoppingCart(name);
            if (shoppingCart != null)
                return new ResponseEntity<ShoppingCart[]>(shoppingCart,HttpStatus.OK);
            else
                return new ResponseEntity<ShoppingCart[]>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        LOG.info("POST /shopping-carts " + shoppingCart);

        try {
            if (shoppingCart != null){
                shoppingCartDao.createShoppingCart(shoppingCart);
                return new ResponseEntity<ShoppingCart>(shoppingCart,HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<ShoppingCart>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
