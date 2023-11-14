package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Tag;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
* Test for the InventoryController class
*/
@ExtendWith(MockitoExtension.class)
@Tag("Controller")
public class InventoryControllerTest {
    @Mock
    InventoryDAO inventoryDAO;
    @InjectMocks
    InventoryController mockInventoryController;   
    ResponseEntity<Need> testEntityNeed;
    ResponseEntity<Need[]> testEntityNeeds;

    /**
    * Testing getNeed given the need exists 
    */

    @Test
    public void testGetNeeds() throws IOException{
        Need[] testNeeds = {new Need(2,"Water",5,1)}; 
        when(inventoryDAO.getNeeds()).thenReturn(testNeeds);
        testEntityNeeds = mockInventoryController.getNeed();
        assertEquals(testEntityNeeds.getStatusCodeValue(), 200);
    }

    @Test
    public void testGetNeedsThrowsIO() throws IOException{
        when(inventoryDAO.getNeeds()).thenThrow(IOException.class);
        testEntityNeeds = mockInventoryController.getNeed();
        assertEquals(testEntityNeeds.getStatusCodeValue(), 500);
    }

    @Test
    public void testGetNeed() throws IOException{
        Need testNeed = new Need(2,"Water",5,1); 
        when(inventoryDAO.getNeed(testNeed.getId())).thenReturn(testNeed);
        testEntityNeed = mockInventoryController.getNeed(testNeed.getId());
        assertEquals(testEntityNeed.getStatusCodeValue(), 200);
    }

    /**
    * Testing getNeeds catch block for IOException 
    */
    @Test
    public void testGetNeedThrowsIO() throws IOException{
        when(inventoryDAO.getNeed(anyInt())).thenThrow(IOException.class);
        testEntityNeed = mockInventoryController.getNeed(anyInt());
        assertEquals(testEntityNeed.getStatusCodeValue(), 500);
    }

    /**
    * Testing getNeed given the need does not exist 
    */
    @Test
    public void testGetNeed404() throws IOException{
        when(inventoryDAO.getNeed(anyInt())).thenReturn(null);
        ResponseEntity<Need> testEntityNeed = mockInventoryController.getNeed(anyInt());
        assertEquals(testEntityNeed.getStatusCodeValue(), 404);
    }
    /**
    * Testing searchNeeds  
    */
    @Test
    public void testSearchNeeds() throws IOException{
        Need[] testNeeds = new Need[3];
        testNeeds[0] = new Need(2, "Food",5.50,6);
        when(inventoryDAO.findNeeds("ood")).thenReturn(testNeeds);
        testEntityNeeds = mockInventoryController.searchNeed("ood");
        assertEquals(testEntityNeeds.getStatusCodeValue(), 200);
    }

    /**
    * Testing searchNeeds catch block for IOException 
    */
    @Test
    public void testSearchNeedsThrowsIO() throws IOException{
        when(inventoryDAO.findNeeds(anyString())).thenThrow(IOException.class);
        testEntityNeeds = mockInventoryController.searchNeed(anyString());
        assertEquals(testEntityNeeds.getStatusCodeValue(), 500);
    }
    
    /**
    * Testing createNeed givin the Need does not already exist
    */
    @Test
    public void testCreateNeed() throws IOException{
        Need testNeed = new Need(2,"Food",5.50,6);
        when(inventoryDAO.createNeed(testNeed)).thenReturn(testNeed);
        ResponseEntity<Need> testEntity = mockInventoryController.createNeed(testNeed);
        assertEquals(testEntity.getStatusCodeValue(), 201);    }
    /**
    * Testing createNeed givin the Need does not already exist
    */
    @Test
    public void testCreateNeedConflict() throws IOException{
        Need testNeed = new Need(2,"Food",5.50,6);
        when(inventoryDAO.createNeed(testNeed)).thenReturn(null);
        ResponseEntity<Need> testEntity = mockInventoryController.createNeed(testNeed);
        assertEquals(testEntity.getStatusCodeValue(), 201);    
    }
    /**
    * Testing createNeed catch block for an IOException 
    */
    @Test
    public void testCreateNeedThrowsIO() throws IOException{
        Need testNeed = new Need(2,"Food",5.50,6);
        when(inventoryDAO.createNeed(any(Need.class))).thenThrow(IOException.class);
        ResponseEntity<Need> testEntity = mockInventoryController.createNeed(testNeed);
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }
    
    /**
    * Testing updateNeed given the Need exists 
    */
    @Test
    public void testUpdateNeed() throws IOException{
        Need testNeed = new Need(2,"Food",5.50,6);
        when(inventoryDAO.updateNeed(testNeed)).thenReturn(testNeed);
        ResponseEntity<Need> testEntity = mockInventoryController.updateNeed(testNeed);
        assertEquals(testEntity.getStatusCodeValue(), 200);    
    }

    /**
    * Testing createNeed givin the Need does not already exist
    */
    @Test
    public void testUpdateNeedConflict() throws IOException{
        Need testNeed = new Need(2,"Food",5.50,6);
        when(inventoryDAO.updateNeed(testNeed)).thenReturn(null);
        ResponseEntity<Need> testEntity = mockInventoryController.updateNeed(testNeed);
        assertEquals(testEntity.getStatusCodeValue(), 404);    
    }
    /**
    * Testing updateNeed catch block for an IOException 
    */
    @Test
    public void testUpdateNeedThrowsIO() throws IOException{
        Need testNeed = new Need(2,"Food",5.50,6);
        when(inventoryDAO.updateNeed(any(Need.class))).thenThrow(IOException.class);
        ResponseEntity<Need> testEntity = mockInventoryController.updateNeed(testNeed);
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }
    
    /**
    * Testing Delete given the Need exists
    */
    @Test
    public void testDeleteNeed() throws IOException{
        Need testNeed = new Need(2,"Food",5.50,6);
        when(inventoryDAO.deleteNeed(testNeed.getId())).thenReturn(true);
        ResponseEntity<Need> testEntity = mockInventoryController.deleteNeed(testNeed.getId());
        assertEquals(testEntity.getStatusCodeValue(), 200);    
    }

    /**
    * Testing deleteNeed catch block for an IOException 
    */
    @Test
    public void testDeleteNeedThrowsIO() throws IOException{
        Need testNeed = new Need(3,"Socks",12,2);
        when(inventoryDAO.deleteNeed(3)).thenThrow(IOException.class);
        ResponseEntity<Need> testEntity = mockInventoryController.deleteNeed(testNeed.getId());
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }

    /**
    * Testing deleteNeed givin the Need does not already exist
    */
    @Test
    public void testDeleteNeedConflict() throws IOException{
        when(inventoryDAO.deleteNeed(anyInt())).thenReturn(false);
        ResponseEntity<Need> testEntity = mockInventoryController.deleteNeed(anyInt());
        assertEquals(testEntity.getStatusCodeValue(), 404);    
    }
}



