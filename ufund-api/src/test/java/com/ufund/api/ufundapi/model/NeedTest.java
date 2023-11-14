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

    Need Need;
    String name;
    double price;
    int quantity;
    int id;
    Need Need2;
    String name2;
    double price2;
    int quantity2;
    int id2;
    Need Need3;
    String name3;
    double price3;
    int quantity3;
    int id3;
    Need Need4;
    String name4;
    double price4;
    int quantity4;
    int id4;

    @BeforeEach
    public void setUpNeedTest(){
        name = "Munchies";
        price = 10.0;
        quantity = 8;
        id = 0;
        Need = new Need(id, name, price, quantity);

        name2 = "Munchies";
        price2 = 10.0;
        quantity2 = 10;
        id2 = 1;
        Need2 = new Need(id2, name2, price2, quantity2);
        
        name3 = "Alma de Mexico";
        price3 = 5.0;
        quantity3 = 8;
        id3 = 2;
        Need3 = new Need(id3, name3, price3, quantity3);

        name4 = "Munchies";
        price4 = 5.0;
        quantity4 = 8;
        id4 = 3;
        Need4 = new Need(id4, name4, price4, quantity4);
    }

    @Test
    public void testGetName(){
        assertEquals(name, Need.getName());
    }

    @Test
    public void testSetName(){
        Need.setName("Minecraft");
        assertEquals("Minecraft", Need.getName());
    }

    @Test
    public void testGetQuantity(){
        assertEquals(8, Need.getQuantity());
    }

    @Test 
    public void testEquals() {
        LinkedList<Need> list = new LinkedList<>();
        list.add(Need);
        assertTrue(Need.equals(Need2));
        assertFalse(Need.equals(Need3));
        assertFalse(Need.equals(Need4));
        assertTrue(list.contains(Need2));

        Object testObj = new Object();
        assertFalse(Need.equals(testObj));
    }
    @Test
    public void testSetQuantity(){
        Need.setQuantity(18);
        assertEquals(18, Need.getQuantity());

        Need.setQuantity(-1);
        assertEquals(0, Need.getQuantity());
    }
    @Test
    public void testGetPrice(){
        assertEquals(price, Need.getPrice());
    }
    @Test
    public void testSetPrice(){
        Need.setPrice(15.00);
        assertEquals(15.00, Need.getPrice());

        Need.setPrice(-1);
        assertEquals(0, Need.getPrice());

        Need.setPrice(0);
        assertEquals(0, Need.getPrice());
    }
    @Test
    public void testToString(){
        int quantity = 4;
        double price = 10.0;
        String name = "Munchies";
        String expectedString = String.format(com.ufund.api.ufundapi.model.Need.STRING_FORMAT, id, name, price, quantity);
        Need tester = new Need(id, name, price, quantity);
        String actualString = tester.toString();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testHash() {
        assertNotNull(Need.hashCode());
    }
    @Test
    public void testConstructor(){
        String name = "Rock";
        double price = 20.7;
        int quantity = 8;
        Need Need = new Need(id2, name, price, quantity);
        assertEquals(name, Need.getName());
        assertEquals(price, Need.getPrice());
        assertEquals(quantity, Need.getQuantity());
    }
    @Test
    public void testConstructorNegative(){
        String name = "Rock";
        double price = -1;
        int quantity = -1;
        Need Need = new Need(id3, name, price, quantity);
        assertEquals(name, Need.getName());
        assertEquals(0, Need.getPrice());
        assertEquals(0, Need.getQuantity());
    }

}