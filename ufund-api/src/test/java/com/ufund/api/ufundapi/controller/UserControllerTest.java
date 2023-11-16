package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.model.User;

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
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new HeroController object and inject
     * a mock User DAO
     */
    @BeforeEach
    public void setupHeroController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testGetUser() throws IOException {  // getUser may throw IOException
        // Setup
        User user = new User("khoi", "pham");
        // When the same id is passed in, our mock User DAO will return the Hero object
        when(mockUserDAO.getUser(user.getUsername())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getUsername());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws Exception { // createUser may throw IOException
        // Setup
        String khoi = "khoi";
        // When the same id is passed in, our mock User DAO will return null, simulating
        // no user found
        when(mockUserDAO.getUser(khoi)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(khoi);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception { // createUser may throw IOException
        // Setup
        String khoi ="khoi";
        // When getUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUser(khoi);

        // Invoke
        ResponseEntity<User> response = userController.getUser(khoi);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all HeroController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateHero() throws IOException {  // createHero may throw IOException
        // Setup
        User user = new User("Wi-Fire", "Password");
        // when createHero is called, return true simulating successful
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException {  // createHero may throw IOException
        // Setup
        User user = new User("Bolt", "Password");
        // when createHero is called, return false simulating failed
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateHeroHandleException() throws IOException {  // createHero may throw IOException
        // Setup
        User user = new User("Ice Gladiator", "Password");

        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).createUser(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException { // deleteHero may throw IOException
        // Setup
        String name = "Jay";
        // when deleteHero is called return true, simulating successful deletion
        when(mockUserDAO.deleteUser(name)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException { // deleteHero may throw IOException
        // Setup
        String name = "Jay";
        // when deleteHero is called return false, simulating failed deletion
        when(mockUserDAO.deleteUser(name)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteHeroHandleException() throws IOException { // deleteHero may throw IOException
        // Setup
        String name = "Jay";
        // When deleteHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).deleteUser(name);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(name);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
