package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.InventoryDAO;
import com.ufund.api.ufundapi.model.Need;

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
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDAO mockInventoryDAO;

    /**
     * Before each test, create a new InventoryController object and inject
     * a mock Inventory DAO
     */
    @BeforeEach
    public void setupHeroController() {
        mockInventoryDAO = mock(InventoryDAO.class);
        inventoryController = new InventoryController(mockInventoryDAO);
    }

    @Test
    public void testGetNeed() throws IOException {  // getNeed may throw IOException
        // Setup
        Need need = new Need(1, "khoi", 1, 1, 1);
        // When the same id is passed in, our mock Inventory DAO will return the Need object
        when(mockInventoryDAO.getNeed(need.getId())).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = inventoryController.getNeed(need.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testGetNeedNotFound() throws Exception { // createHero may throw IOException
        // Setup
        int needId = 99;
        // When the same id is passed in, our mock Hero DAO will return null, simulating
        // no hero found
        when(mockInventoryDAO.getNeed(needId)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = inventoryController.getNeed(needId);
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetNeedHandleException() throws Exception { // createNeed may throw IOException
        // Setup
        int needId = 99;
        // When getNeed is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).getNeed(needId);

        // Invoke
        ResponseEntity<Need> response = inventoryController.getNeed(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // /*****************************************************************
    //  * The following tests will fail until all HeroController methods
    //  * are implemented.
    //  ****************************************************************/

    @Test
    public void testCreateNeed() throws IOException {  // createNeed may throw IOException
        // Setup
        Need need = new Need(1, "khoi", 1, 1, 1);
        // when createNeed is called, return true simulating successful
        // creation and save
        when(mockInventoryDAO.createNeed(need)).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = inventoryController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    // @Test 
    // public void testCreateNeedFailed() throws IOException {  // createNeed may throw IOException
    //     // Setup
    //     Need need = new Need(1, "khoi", 1, 1, 1);
    //     // when createNeed is called, return false simulating failed
    //     // creation and save
    //     when(mockInventoryDAO.createNeed(need)).thenReturn(null);

    //     // Invoke
    //     ResponseEntity<Need> response = inventoryController.createNeed(need);

    //     // Analyze
    //     assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    // }

    @Test
    public void testCreateNeedHandleException() throws IOException {  // createNeed may throw IOException
        // Setup
        Need need = new Need(1, "khoi", 1, 1, 1);

        // When createNeed is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).createNeed(need);

        // Invoke
        ResponseEntity<Need> response = inventoryController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateNeed() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(1, "khoi", 1, 1, 1);
        // when updateHero is called, return true simulating successful
        // update and save
        when(mockInventoryDAO.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = inventoryController.updateNeed(need);
        need.setName("boy");
        need.setPrice(3);
        need.setQuantity(-5);

        // Invoke
        response = inventoryController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testUpdateNeedFailed() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(1, "khoi", 1, 1, 1);
        // when updateNeed is called, return true simulating successful
        // update and save
        when(mockInventoryDAO.updateNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need>response = inventoryController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateNeedHandleException() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(1, "khoi", 1, 2, 3);
        // When updateHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).updateNeed(need);

        // Invoke
        ResponseEntity<Need> response = inventoryController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetNeeds() throws IOException { // getNeeds may throw IOException
        // Setup
        Need[] needs = new Need[2];
        needs[0] = new Need(1, "khoi", 1, 2, 3);
        needs[1] = new Need(2, "boy", 3, 2, 1);
        // When getHeroes is called return the heroes created above
        when(mockInventoryDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = inventoryController.getNeed();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetNeedsHandleException() throws IOException { // getNeeds may throw IOException
        // Setup
        // When getNeeds is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).getNeeds();

        // Invoke
        ResponseEntity<Need[]> response = inventoryController.getNeed();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchNeeds() throws IOException { // findNeeds may throw IOException
        // Setup
        String searchString = "oi";
        Need[] need = new Need[2];
        need[0] = new Need(1, "khoi", 1, 2, 3);
        need[1] = new Need(2, "boi", 3, 2, 1);
        // When findHeroes is called with the search string, return the two
        /// heroes above
        when(mockInventoryDAO.findNeeds(searchString)).thenReturn(need);

        // Invoke
        ResponseEntity<Need[]> response = inventoryController.searchNeed(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testSearchNeedsHandleException() throws IOException { // findNeeds may throw IOException
        // Setup
        String searchString = "an";
        // When createNeed is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).findNeeds(searchString);

        // Invoke
        ResponseEntity<Need[]> response = inventoryController.searchNeed(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteNeed() throws IOException { // deleteNeed may throw IOException
        // Setup
        int needId = 99;
        // when deleteHero is called return true, simulating successful deletion
        when(mockInventoryDAO.deleteNeed(needId)).thenReturn(true);

        // Invoke
        ResponseEntity<Need> response = inventoryController.deleteNeed(needId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedNotFound() throws IOException { // deleteNeed may throw IOException
        // Setup
        int needId = 99;
        // when deleteNeed is called return false, simulating failed deletion
        when(mockInventoryDAO.deleteNeed(needId)).thenReturn(false);

        // Invoke
        ResponseEntity<Need> response = inventoryController.deleteNeed(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedHandleException() throws IOException { // deleteNeed may throw IOException
        // Setup
        int needId = 99;
        // When deleteHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).deleteNeed(needId);

        // Invoke
        ResponseEntity<Need> response = inventoryController.deleteNeed(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}




