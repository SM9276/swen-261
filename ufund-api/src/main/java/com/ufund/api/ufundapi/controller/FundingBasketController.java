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
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import com.ufund.api.ufundapi.model.Product;
import com.ufund.api.ufundapi.model.FundingBasket;
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
@RequestMapping("/funding-basket")
@CrossOrigin(origins = "http://localhost:4200")

public class FundingBasketController {
    private static final Logger LOG = Logger.getLogger(FundingBasketController.class.getName());
    private FundingBasketDAO fundingBasketDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param fundingBasketDao The {@link InventoryDAO Inventory Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public FundingBasketController(FundingBasketDAO fundingBasketDao) {
        this.fundingBasketDao = fundingBasketDao;
    }

    /**
     * Responds to the GET request for a {@linkplain FundingBasket fundingBasket} for the given id
     * 
     * @param id The id used to locate the {@link FundingBasket fundingBasket}
     * 
     * @return ResponseEntity with {@link FundingBasket fundingBasket} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{name}")
    public ResponseEntity<FundingBasket> getFundingBasket(@PathVariable String name) {
        LOG.info("GET /funding-basket/" + name);
        try {
            FundingBasket fundingBasket = fundingBasketDao.getFundingBasket(name);
            if (fundingBasket != null)
                return new ResponseEntity<FundingBasket>(fundingBasket,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain FundingBasket fundingBasket} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link FundingBasket fundingBasket}
     * 
     * @return ResponseEntity with array of {@link FundingBasket fundingBasket} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all inventories that contain the text "ma"
     * GET http://localhost:8080/ufund/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<FundingBasket[]> searchUserFundingBasket(@RequestParam String name) {
        LOG.info("GET /funding-basket/?user="+name);

        try {
            FundingBasket[] fundingBasket = fundingBasketDao.findFundingBasket(name);
            if (fundingBasket != null)
                return new ResponseEntity<FundingBasket[]>(fundingBasket,HttpStatus.OK);
            else
                return new ResponseEntity<FundingBasket[]>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<FundingBasket> createFundingBasket(@RequestBody FundingBasket fundingBasket) {
        LOG.info("POST /funding-basket " + fundingBasket);

        try {
            if (fundingBasket != null){
                fundingBasketDao.createFundingBasket(fundingBasket);
                return new ResponseEntity<FundingBasket>(fundingBasket,HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<FundingBasket>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
