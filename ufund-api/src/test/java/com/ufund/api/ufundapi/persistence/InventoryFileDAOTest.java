package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryDAO inventoryFileDAO;
    Need[] testNeeds;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupInventoryFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testNeeds = new Need[3];
        testNeeds[0] = new Need(1, "khoi", 1, 1, 1);
        testNeeds[1] = new Need(2, "the", 2, 2, 2);
        testNeeds[2] = new Need(3, "boi", 3, 3, 3);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Need[].class))
                .thenReturn(testNeeds);
        inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetNeeds() throws IOException {
        // Invoke
        Need[] needs = inventoryFileDAO.getNeeds();

        // Analyze
        assertEquals(needs.length,testNeeds.length);
        for (int i = 0; i < testNeeds.length;++i)
            assertEquals(needs[i],testNeeds[i]);
    }

    @Test
    public void testFindNeeds() throws IOException {
        // Invoke
        Need[] needs = inventoryFileDAO.findNeeds("oi");

        // Analyze
        assertEquals(needs.length,2);
        assertEquals(needs[0],testNeeds[0]);
        assertEquals(needs[1],testNeeds[2]);
    }

    @Test
    public void testGetNeed() throws IOException {
        // Invoke
        Need need = inventoryFileDAO.getNeed(1);

        // Analyze
        assertEquals(need,testNeeds[0]);
    }

    @Test
    public void testDeleteNeed() throws IOException {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteNeed(1),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test inventory array - 1 (because of the delete)
        // Because needs attribute of InventoryFileDAO is package private
        // we can access it directly
        assertEquals(inventoryFileDAO.getNeeds().length, testNeeds.length-1);
    }

    @Test
    public void testCreateNeed() throws IOException {
        // Setup
        Need need = new Need(4, "Wonder", 4, 4, 4);

        // Invoke
        Need result = assertDoesNotThrow(() -> inventoryFileDAO.createNeed(need),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Need actual = inventoryFileDAO.getNeed(4);
        assertEquals(actual.getId(),need.getId());
        assertEquals(actual.getName(),need.getName());
        assertEquals(actual.getAmount(),need.getAmount());
        assertEquals(actual.getPrice(),need.getPrice());
        assertEquals(actual.getQuantity(),need.getQuantity());

    }

    @Test
    public void testUpdateNeed() throws IOException {
        // Setup
        Need need = new Need(1, "khoi", 4, 4, 4);

        // Invoke
        Need result = assertDoesNotThrow(() -> inventoryFileDAO.updateNeed(need),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Need actual = inventoryFileDAO.getNeed(need.getId());
        assertEquals(actual,need);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Need[].class));

        Need need = new Need(4, "wonder", 4, 4, 4);

        assertThrows(IOException.class,
                        () -> inventoryFileDAO.createNeed(need),
                        "IOException not thrown");
    }

    @Test
    public void testGetNeedNotFound() throws IOException {
        // Invoke
        Need need= inventoryFileDAO.getNeed(10);

        // Analyze
        assertEquals(need,null);
    }

    @Test
    public void testDeleteNeedNotFound() throws IOException {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteNeed(5),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(inventoryFileDAO.getNeeds().length,testNeeds.length);
    }

    @Test
    public void testUpdateNeedNotFound() {
        // Setup
        Need need = new Need(4, "wonder", 4, 4, 4);

        // Invoke
        Need result = assertDoesNotThrow(() -> inventoryFileDAO.updateNeed(need),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the HeroFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Need[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new InventoryFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
