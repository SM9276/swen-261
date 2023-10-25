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
        testUsers[0] = new User(77, "Jud", "password1");
        testUsers[1] = new User(78, "Zephyr", "password2");
        testUsers[2] = new User(79, "Deric", "password3");

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

}
