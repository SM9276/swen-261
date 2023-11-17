package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Hero Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class FundingBasketControllerTest {
    private FundingBasketController fundingBasketController;
    private FundingBasketDAO mockFundingBasketDAO;

    /**
     * Before each test, create a new FundingBasketController object and inject
     * a mock funding basket DAO
     */
    @BeforeEach
    public void setupHeroController() {
        mockFundingBasketDAO = mock(FundingBasketDAO.class);
        fundingBasketController = new FundingBasketController(mockFundingBasketDAO);

    }

    @Test
    public void testGetFundingBasket() throws IOException {  // getHero may throw IOException
        // Setup Needs List
        String khoi = "khoi";
        Need[] needList = new Need[5];

        //setup Funding Basket
        FundingBasket fundingBasket = new FundingBasket(khoi, needList, needList);
        // When the same id is passed in, our mock funding basket DAO will return the funding basket object
        when(mockFundingBasketDAO.getFundingBasket(khoi)).thenReturn(fundingBasket);

        // Invoke
        ResponseEntity<FundingBasket> response = fundingBasketController.getFundingBasket(khoi);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(fundingBasket,response.getBody());
    }

    @Test
    public void testGetFundingBasketNotFound() throws Exception { // createFundingBasket may throw IOException
        // Setup
        String khoi = "khoi";
        // When the same funding Basket is passed in, our mock Funding Basket DAO will return null, simulating
        // no Funding Basket found
        when(mockFundingBasketDAO.getFundingBasket(khoi)).thenReturn(null);

        // Invoke
        ResponseEntity<FundingBasket> response = fundingBasketController.getFundingBasket(khoi);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetFundingBasketHandleException() throws Exception { // createFundingBasket may throw IOException
        // Setup
        String khoi = "khoi";
        // When getFundingBasket is called on the Mock Funding Basket DAO, throw an IOException
        doThrow(new IOException()).when(mockFundingBasketDAO).getFundingBasket(khoi);

        // Invoke
        ResponseEntity<FundingBasket> response = fundingBasketController.getFundingBasket(khoi);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // /*****************************************************************
    //  * The following tests will fail until all HeroController methods
    //  * are implemented.
    //  ****************************************************************/

    @Test
    public void testCreateFundingBasket() throws IOException {  // createFundingBasket may throw IOException
        // Setup
        String khoi = "khoi";
        Need[] needList = new Need[5];
        FundingBasket fundingBasket = new FundingBasket(khoi,needList, needList);
        // when createFundingBasket is called, return true simulating successful
        // creation and save
        when(mockFundingBasketDAO.createFundingBasket(fundingBasket)).thenReturn(fundingBasket);

        // Invoke
        ResponseEntity<FundingBasket> response = fundingBasketController.createFundingBasket(fundingBasket);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(fundingBasket,response.getBody());
    }

    @Test
    public void testCreateFundingBasketFailed() throws IOException {  // createFundingBasket may throw IOException
        // Setup
        String khoi = "khoi";
        Need[] needList = new Need[5];
        FundingBasket fundingBasket = new FundingBasket(khoi, needList, needList);
        // when createFundingBasket is called, return false simulating failed
        // creation and save
        when(mockFundingBasketDAO.createFundingBasket(fundingBasket)).thenReturn(null);

        // Invoke
        ResponseEntity<FundingBasket> response = fundingBasketController.createFundingBasket(fundingBasket);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateFundingBasketHandleException() throws IOException {  // createFundingBasket may throw IOException
        // Setup
        String khoi = "khoi";
        Need[] needList = new Need[5];
        FundingBasket fundingBasket = new FundingBasket(khoi, needList, needList);

        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockFundingBasketDAO).createFundingBasket(fundingBasket);

        // Invoke
        ResponseEntity<FundingBasket> response = fundingBasketController.createFundingBasket(fundingBasket);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateFundingBasket() throws IOException { // updateHero may throw IOException
        //Setup Need List
        String khoi = "khoi";
        Need[] needList = new Need[5];
        Need[] listNeed = new Need[4];

        // Setup funding basket
        FundingBasket fundingBasket = new FundingBasket(khoi,needList,needList);
        // when updateFundingBasket is called, return true simulating successful
        // update and save
        when(mockFundingBasketDAO.updateFundingBasket(fundingBasket)).thenReturn(fundingBasket);
        ResponseEntity<FundingBasket> response = fundingBasketController.updateFundingBasket(fundingBasket);
        fundingBasket.setNeeds(listNeed);;

        // Invoke
        response = fundingBasketController.updateFundingBasket(fundingBasket);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(fundingBasket,response.getBody());
    }

    @Test
    public void testUpdateFundingBasketFailed() throws IOException { // updateFundingBasket may throw IOException
        // Setup
        String khoi = "khoi";
        Need[] needList = new Need[5];
        FundingBasket fundingBasket = new FundingBasket(khoi, needList,needList);
        // when updateHero is called, return true simulating successful
        // update and save
        when(mockFundingBasketDAO.updateFundingBasket(fundingBasket)).thenReturn(null);

        // Invoke
        ResponseEntity<FundingBasket> response = fundingBasketController.updateFundingBasket(fundingBasket);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateFundingBasketHandleException() throws IOException { // updateFundingBasket may throw IOException
        // Setup
        String khoi = "khoi";
        Need[] needList = new Need[5];
        FundingBasket fundingBasket = new FundingBasket(khoi,needList,needList);
        // When updateFundingBasket is called on the Mock Funding Basket DAO, throw an IOException
        doThrow(new IOException()).when(mockFundingBasketDAO).updateFundingBasket(fundingBasket);

        // Invoke
        ResponseEntity<FundingBasket> response = fundingBasketController.updateFundingBasket(fundingBasket);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


}