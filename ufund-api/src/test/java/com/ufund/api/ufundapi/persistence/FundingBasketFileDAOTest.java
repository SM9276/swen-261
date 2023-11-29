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
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class FundingBasketFileDAOTest {
    FundingBasketFileDAO fundingBasketFileDAO;
    FundingBasket[] testBaskets;
    Need[] needsList = new Need[3];
    Need[] boughtList = new Need[3];
    Need   need;
    Need   bought;

    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupFundingBasketFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testBaskets = new FundingBasket[3];
        
        needsList[0] = need;
        boughtList[0] = bought;

        testBaskets[0] = new FundingBasket("Khoi", needsList, boughtList);
        testBaskets[1] = new FundingBasket("The", needsList, boughtList);
        testBaskets[2] = new FundingBasket("Boy", needsList, boughtList);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),FundingBasket[].class))
                .thenReturn(testBaskets);
        fundingBasketFileDAO = new FundingBasketFileDAO("doesnt_matter.txt", mockObjectMapper);
    }


    @Test
    public void testGetFundingBasket() throws IOException{
        // Invoke
        FundingBasket fundingBasket = fundingBasketFileDAO.getFundingBasket("Khoi");

        // Analyze
        assertEquals(fundingBasket,testBaskets[0]);
    }


    @Test
    public void testCreateFundingBasket() throws IOException {
        // Setup
        FundingBasket fundingBasket = new FundingBasket("Choy", needsList, boughtList);

        // Invoke
        FundingBasket result = assertDoesNotThrow(() -> fundingBasketFileDAO.createFundingBasket(fundingBasket),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        FundingBasket actual = fundingBasketFileDAO.getFundingBasket("Choy");
        assertEquals(actual.getFundingBasket(),fundingBasket.getFundingBasket());
        assertEquals(actual.getNeeds(),fundingBasket.getNeeds());
        assertEquals(actual.getBought(),fundingBasket.getBought());

    }

    @Test
    public void testUpdateFundingBasket() throws IOException {
        // Setup
        Need[] newNeedsList = new Need[3];
        Need[] newBoughtList = new Need[3];
        Need mock = new Need(5, "milk", 1, 2, 3);
        Need mock2 = new Need(6, "lemons", 2, 3, 1);;
        newNeedsList[0] = mock;
        newNeedsList[1] = mock2;
        newBoughtList[0] = mock2;
        newBoughtList[1] = mock;

        FundingBasket fundingBasket = new FundingBasket("Khoi", newNeedsList, newBoughtList);

        // Invoke
        FundingBasket result = assertDoesNotThrow(() -> fundingBasketFileDAO.updateFundingBasket(fundingBasket),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        FundingBasket actual = fundingBasketFileDAO.getFundingBasket("Khoi");
        assertEquals(actual,fundingBasket);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(FundingBasket[].class));

        FundingBasket fundingBasket = new FundingBasket("Choy", needsList, boughtList);

        assertThrows(IOException.class,
                        () -> fundingBasketFileDAO.createFundingBasket(fundingBasket),
                        "IOException not thrown");
    }

    @Test
    public void testGetFundingBasketNotFound() throws IOException {
        // Invoke
        FundingBasket fundingBasket = fundingBasketFileDAO.getFundingBasket("Bro");

        // Analyze
        assertEquals(fundingBasket,null);
    }

    @Test
    public void testUpdateFundingBasketNotFound() {
        // Setup
        FundingBasket fundingBasket = new FundingBasket("Choy", needsList, boughtList);

        // Invoke
        FundingBasket result = assertDoesNotThrow(() -> fundingBasketFileDAO.updateFundingBasket(fundingBasket),
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
        // from the FundingBasketFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),FundingBasket[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new FundingBasketFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
