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
    public void testGetUser() throws IOException {  // getHero may throw IOException
        // Setup
        User user = new User(99,"Galactic Agent", "Admin");
        // When the same id is passed in, our mock Hero DAO will return the Hero object
        when(mockUserDAO.getUser(user.getId())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws Exception { // createHero may throw IOException
        // Setup
        int userId = 99;
        // When the same id is passed in, our mock Hero DAO will return null, simulating
        // no hero found
        when(mockUserDAO.getUser(userId)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception { // createHero may throw IOException
        // Setup
        int userId = 99;
        // When getHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUser(userId);

        // Invoke
        ResponseEntity<User> response = userController.getUser(userId);

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
        User user = new User(99,"Wi-Fire", "Password");
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
        User user = new User(99,"Bolt", "Password");
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
        User user = new User(99,"Ice Gladiator", "Password");

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
        int userId = 99;
        // when deleteHero is called return true, simulating successful deletion
        when(mockUserDAO.deleteUser(userId)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException { // deleteHero may throw IOException
        // Setup
        int userId = 99;
        // when deleteHero is called return false, simulating failed deletion
        when(mockUserDAO.deleteUser(userId)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteHeroHandleException() throws IOException { // deleteHero may throw IOException
        // Setup
        int userId = 99;
        // When deleteHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).deleteUser(userId);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
