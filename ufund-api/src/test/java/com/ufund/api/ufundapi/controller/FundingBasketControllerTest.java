package com.ufund.api.ufundapi.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.io.IOException;


import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;




@Tag("Controller-tier")
public class FundingBasketControllerTest {
    private FundingBasketController fundController;
    private FundingBasketDAO mockFundingDAO;


    @BeforeEach
    public void setupHeroController() {
        mockFundingDAO = mock(FundingBasketDAO.class);
        fundController = new FundingBasketController(mockFundingDAO);
    }
     @Test
    public void testGetFundingBasket() throws IOException {  
        // Setup
        Need need = new Need(2,"Apple",5,3,0);
        Need[] needsArray = {need};
        Need[] boughtArray = null;  
        FundingBasket basket = new FundingBasket("Sergio", needsArray , boughtArray);


        when(mockFundingDAO.getFundingBasket(basket.getFundingBasket())).thenReturn(basket);


        // Invoke
        ResponseEntity<FundingBasket> response = fundController.getFundingBasket(basket.getFundingBasket());


        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(basket,response.getBody());
    }
@Test
public void testGetUserNotFound() throws Exception { // createHero may throw IOException
    // Setup
    String userName = "Jay";
    // When the same id is passed in, our mock Hero DAO will return null, simulating
    // no hero found
    when(mockFundingDAO.getFundingBasket(userName)).thenReturn(null);


    // Invoke
    ResponseEntity<FundingBasket> response = fundController.getFundingBasket(userName);


    // Analyze
    assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
}


    @Test
    public void testGetUserHandleException() throws Exception { // createHero may throw IOException
        // Setup
        String userName = "Jay";
        // When getHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockFundingDAO).getFundingBasket(userName);


        // Invoke
        ResponseEntity<FundingBasket> response = fundController.getFundingBasket(userName);


        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }




    @Test
    public void testUpdateFundingBasket() throws IOException {  // createHero may throw IOException
        // Setup
        Need need = new Need(2,"Apple",5,3,0);
        Need[] needsArray =  null;
        Need[] boughtArray = null; 
        FundingBasket basket = new FundingBasket("Alma De Mexico", needsArray , boughtArray);
        FundingBasket basket2 = new FundingBasket("Chon", needsArray , boughtArray);
        when(mockFundingDAO.updateFundingBasket(basket).thenReturn(basket));


        // Invoke
        ResponseEntity<FundingBasket> response = fundController.createFundingBasket(basket);


        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(user,response.getBody());
    }


//     @Test
//     public void testCreateUserFailed() throws IOException {  // createHero may throw IOException
//         // Setup
//         User user = new User("Bolt", "Password");
//         // when createHero is called, return false simulating failed
//         // creation and save
//         when(mockFundingDAO.createUser(user)).thenReturn(null);


//         // Invoke
//         ResponseEntity<User> response = fundController.createUser(user);


//         // Analyze
//         assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
//     }


//     @Test
//     public void testCreateHeroHandleException() throws IOException {  // createHero may throw IOException
//         // Setup
//         User user = new User("Ice Gladiator", "Password");


//         // When createHero is called on the Mock Hero DAO, throw an IOException
//         doThrow(new IOException()).when(mockFundingDAO).createUser(user);


//         // Invoke
//         ResponseEntity<User> response = fundController.createUser(user);


//         // Analyze
//         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
//     }


//     @Test
//     public void testDeleteUser() throws IOException { // deleteHero may throw IOException
//         // Setup
//         String name = "Jay";
//         // when deleteHero is called return true, simulating successful deletion
//         when(mockFundingDAO.deleteUser(name)).thenReturn(true);


//         // Invoke
//         ResponseEntity<User> response = fundController.deleteUser(name);


//         // Analyze
//         assertEquals(HttpStatus.OK,response.getStatusCode());
//     }


//     @Test
//     public void testDeleteUserNotFound() throws IOException { // deleteHero may throw IOException
//         // Setup
//         String name = "Jay";
//         // when deleteHero is called return false, simulating failed deletion
//         when(mockFundingDAO.deleteUser(name)).thenReturn(false);


//         // Invoke
//         ResponseEntity<User> response = fundController.deleteUser(name);


//         // Analyze
//         assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//     }


//     @Test
//     public void testDeleteHeroHandleException() throws IOException { // deleteHero may throw IOException
//         // Setup
//         String name = "Jay";
//         // When deleteHero is called on the Mock Hero DAO, throw an IOException
//         doThrow(new IOException()).when(mockFundingDAO).deleteUser(name);


//         // Invoke
//         ResponseEntity<User> response = fundController.deleteUser(name);


//         // Analyze
//         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
//     }
}



