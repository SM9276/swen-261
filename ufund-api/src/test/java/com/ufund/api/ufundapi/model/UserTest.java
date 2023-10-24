package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Hero class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class UserTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
        String expected_password = "Password";

        // Invoke
        User user = new User(expected_id,expected_name, expected_password);

        // Analyze
        assertEquals(expected_id,user.getId());
        assertEquals(expected_name,user.getUsername());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        String password = "Password";
        User user= new User(id,name, password);

        String expected_name = "Galactic Agent";

        // Invoke
        user.setUser(expected_name);

        // Analyze
        assertEquals(expected_name,user.getUsername());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        String password = "Password";
        String expected_string = String.format(User.STRING_FORMAT,id,name, password);
        User user = new User(id,name,password);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}