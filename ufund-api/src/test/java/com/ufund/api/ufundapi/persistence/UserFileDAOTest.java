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
import com.ufund.api.ufundapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class UserFileDAOTest {
    UserFileDAO userFileDAO;
    User[] testUsers;
    ObjectMapper mockObjectMapper;
    
    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        testUsers[0] = new User("Boseph", "password1");
        testUsers[1] = new User("Dephyr", "password2");
        testUsers[2] = new User("Zeric", "password3");

        when(mockObjectMapper
            .readValue(new File("ambiguous.txt"), User[].class))
                .thenReturn(testUsers);
        userFileDAO = new UserFileDAO("ambiguous.txt", mockObjectMapper);
    }

    @Test
    public void testGetUsers() {
        User[] users = userFileDAO.getUsers();

        assertEquals(users.length, testUsers.length);
        for (int i = 0; i < testUsers.length; ++i) {
            assertEquals(users[i], testUsers[i]);
        }
    }

    @Test
    public void testFindUsers() {
        // Invoke
        User[] users = userFileDAO.findUsers("ph");

        // Analyze
        assertEquals(users.length,2);
        assertEquals(users[0],testUsers[0]);
        assertEquals(users[1],testUsers[1]);
    }

    @Test
    public void testGetUser() {
        // Invoke
        User user = userFileDAO.getUser("Boseph");

        // Analzye
        assertEquals(user,testUsers[0]);
    }

    @Test
    public void testDeleteUser() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser("Boseph"),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of UserFileDAO is package private
        // we can access it directly
        assertEquals(userFileDAO.users.size(),testUsers.length-1);
    }

    @Test
    public void testCreateUser() {
        // Setup
        User user = new User("Jared", "OPM");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.createUser(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUser(user.getUsername());
        assertEquals(actual.getUsername(),user.getUsername());
    }

    @Test
    public void testUpdateUser() {
        // Setup
        User user = new User("Boseph", "Password2");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUser(user.getUsername());
        assertEquals(actual,user);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(User[].class));

        User user = new User("Mr. Smiley", "ORV");

        assertThrows(IOException.class,
                        () -> userFileDAO.createUser(user),
                        "IOException not thrown");
    }

    @Test
    public void testGetUserNotFound() {
        // Invoke
        User user = userFileDAO.getUser("Deph");

        // Analyze
        assertEquals(user,null);
    }

    @Test
    public void testDeleteUserNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser("Deerman"),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(userFileDAO.users.size(),testUsers.length);
    }

    @Test
    public void testUpdateUserNotFound() {
        // Setup
        User user = new User("Goku", "Gohan's Birthday");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user),
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
        // from the userFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),User[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new UserFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
