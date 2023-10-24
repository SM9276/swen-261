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
public class ProductTest {

    Product product;
    String name;
    double price;
    int quantity;
    int id;
    Product product2;
    String name2;
    double price2;
    int quantity2;
    int id2;
    Product product3;
    String name3;
    double price3;
    int quantity3;
    int id3;
    Product product4;
    String name4;
    double price4;
    int quantity4;
    int id4;

    @BeforeEach
    public void setUpProductTest(){
        name = "Munchies";
        price = 10.0;
        quantity = 8;
        id = 0;
        product = new Product(name, price, quantity, id);

        name2 = "Munchies";
        price2 = 10.0;
        quantity2 = 10;
        id2 = 1;
        product2 = new Product(name2, price2, quantity, id);
        
        name3 = "Alma de Mexico";
        price3 = 5.0;
        quantity3 = 8;
        id3 = 2;
        product3 = new Product(name3, price3, quantity3, id);

        name4 = "Munchies";
        price4 = 5.0;
        quantity4 = 8;
        id4 = 3;
        product4 = new Product(name4, price4, quantity4, id);
    }

    @Test
    public void testGetName(){
        assertEquals(name, product.getName());
    }

    @Test
    public void testSetName(){
        product.setName("Minecraft");
        assertEquals("Minecraft", product.getName());
    }

    @Test
    public void testGetQuantity(){
        assertEquals(8, product.getQuantity());
    }

    @Test 
    public void testEquals() {
        LinkedList<Product> list = new LinkedList<>();
        list.add(product);
        assertTrue(product.equals(product2));
        assertFalse(product.equals(product3));
        assertFalse(product.equals(product4));
        assertTrue(list.contains(product2));

        Object testObj = new Object();
        assertFalse(product.equals(testObj));
    }
    @Test
    public void testSetQuantity(){
        product.setQuantity(18);
        assertEquals(18, product.getQuantity());

        product.setQuantity(-1);
        assertEquals(0, product.getQuantity());
    }
    @Test
    public void testGetPrice(){
        assertEquals(price, product.getPrice());
    }
    @Test
    public void testSetPrice(){
        product.setPrice(15.00);
        assertEquals(15.00, product.getPrice());

        product.setPrice(-1);
        assertEquals(0, product.getPrice());

        product.setPrice(0);
        assertEquals(0, product.getPrice());
    }
    @Test
    public void testToString(){
        int quantity = 4;
        double price = 10.0;
        String name = "Munchies";
        String expectedString = String.format(Product.STRING_FORMAT, name, price, quantity);
        Product tester = new Product(name, price, quantity, id);
        String actualString = tester.toString();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testHash() {
        assertNotNull(product.hashCode());
    }
    @Test
    public void testConstructor(){
        String name = "Rock";
        double price = 20.7;
        int quantity = 8;
        Product product = new Product(name, price, quantity, id2);
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
    }
    @Test
    public void testConstructorNegative(){
        String name = "Rock";
        double price = -1;
        int quantity = -1;
        Product product = new Product(name, price, quantity, id3);
        assertEquals(name, product.getName());
        assertEquals(0, product.getPrice());
        assertEquals(0, product.getQuantity());
    }

}