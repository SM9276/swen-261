package com.ufund.api.ufundapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;



@Tag("Model-tier")
public class NeedTest {

    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
        int expected_price = 99;
        int expected_quantity = 99;
        int expected_amount = 99;

        // Invoke
        Need need = new Need(expected_id, expected_name, expected_price, expected_quantity, expected_amount);

        // Analyze
        assertEquals(expected_id,need.getId());
        assertEquals(expected_name,need.getName());
        assertEquals(expected_price, need.getPrice());
        assertEquals(expected_quantity, need.getQuantity());
        assertEquals(expected_amount, need.getAmount());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        double price    = 99;
        int quantity = 99;
        int amount   = 99;
        Need need = new Need(id, name, price, quantity, amount);

        String expected_name = "Galactic Agent";

        // Invoke
        need.setName(expected_name);

        // Analyze
        assertEquals(expected_name,need.getName());
    }

    @Test
    public void testPrice() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        double price    = 99;
        int quantity = 99;
        int amount   = 99;
        Need need = new Need(id, name, price, quantity, amount);

        double expected_price = 1;

        // Invoke
        need.setPrice(expected_price);;

        // Analyze
        assertEquals(expected_price,need.getPrice());
    }

    @Test
    public void testQuantity() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        double price    = 99;
        int quantity = 99;
        int amount   = 99;
        Need need = new Need(id, name, price, quantity, amount);

        int expected_quantity = 1;

        // Invoke
        need.setQuantity(expected_quantity);;

        // Analyze
        assertEquals(expected_quantity,need.getQuantity());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        float price = 99;
        int quantity = 99;
        int amount = 99;

        String expected_string = String.format(Need.STRING_FORMAT, id, name, price, quantity, amount);
        Need need = new Need(id, name, price, quantity, amount);

        // Invoke
        String actual_string = need.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

}
