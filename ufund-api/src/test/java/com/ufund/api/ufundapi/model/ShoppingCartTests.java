package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

@Tag("Model-tier")
public class ShoppingCartTests {
    private Product mockProduct1;
    private Product mockProduct2;
    private Product mockProduct3;
    private List<Product> productList;

    private ShoppingCart shoppingCart1;
    private ShoppingCart shoppingCart2;
    private ShoppingCart shoppingCartEmpty;

  
    @BeforeEach
    public void setupShoppingCart() {
        productList = new LinkedList<Product>();

        mockProduct1 = new Product("1", 1, 1);
        new Product("1", 1, 2);
        mockProduct2 = new Product("2", 2, 2);
        mockProduct3 = new Product("3", 3, 3);

        productList.add(mockProduct1);
        productList.add(mockProduct2);
        productList.add(mockProduct3);
        
        shoppingCart1 = new ShoppingCart();
        shoppingCart2 = new ShoppingCart();
        shoppingCartEmpty = new ShoppingCart();

        for (Product product : productList) {
            shoppingCart1.addToCart(product);
            shoppingCart2.addToCart(product);
        }
    }

    @Test
    public void testConstructorNotNull() {
        assertNotNull(shoppingCart1);
        assertNotNull(shoppingCart2);
        assertNotNull(shoppingCartEmpty);
    }

    @Test
    public void testgetTotal() {
        assertEquals(14, shoppingCart1.getTotal());
        assertEquals(14, shoppingCart2.getTotal());
        assertEquals(0, shoppingCartEmpty.getTotal());
    }

    @Test
    public void testGetProducts() {
        assertNotNull(shoppingCart1.getProducts());
        assertNotNull(shoppingCart2.getProducts());
        assertNotNull(shoppingCartEmpty.getProducts());

        assertTrue(shoppingCart1.getProducts().contains(mockProduct1));
        assertTrue(shoppingCart1.getProducts().contains(mockProduct2));
        assertTrue(shoppingCart1.getProducts().contains(mockProduct2));
        assertNotSame(productList, shoppingCartEmpty.getProducts());

    }

    @Test
    public void testGetItemCount() {
        assertEquals(6, shoppingCart1.getItemCount());
        assertEquals(6, shoppingCart2.getItemCount());
        assertEquals(0, shoppingCartEmpty.getItemCount());
    }

    @Test
    public void testAddToCart() {
        shoppingCartEmpty.addToCart(mockProduct1);

        assertEquals(1, shoppingCartEmpty.getItemCount());
        assertEquals(1, shoppingCartEmpty.getTotal());

        shoppingCartEmpty.addToCart(mockProduct1);
        assertEquals(2, shoppingCartEmpty.getItemCount());
        assertEquals(2, shoppingCartEmpty.getTotal());
    }

    @Test
    public void testAddToCartQuantity() {
        shoppingCartEmpty.addToCart(mockProduct3, 2);

        assertEquals(2, shoppingCartEmpty.getItemCount());
        assertEquals(6, shoppingCartEmpty.getTotal());
        
        shoppingCartEmpty.addToCart(mockProduct3, 2);
        assertEquals(4, shoppingCartEmpty.getItemCount());
        assertEquals(12, shoppingCartEmpty.getTotal());
    }

    @Test
    public void testRemoveFromCart() {
        shoppingCart1.removeFromCart(mockProduct1);
        shoppingCart2.removeFromCart(mockProduct1);
        
        shoppingCart1.removeFromCart(mockProduct2);
        shoppingCart2.removeFromCart(mockProduct2);

        assertEquals(3, shoppingCart1.getItemCount());
        assertEquals(3, shoppingCart2.getItemCount());

        assertEquals(9, shoppingCart1.getTotal());
        assertEquals(9, shoppingCart2.getTotal());
        shoppingCart1.removeFromCart(mockProduct1);
    }

    @Test
    public void testRemoveFromCartQuantity() throws IllegalArgumentException {
        shoppingCart1.removeFromCart(mockProduct1, 1);
        shoppingCart1.removeFromCart(mockProduct2, 2);
        shoppingCart1.removeFromCart(mockProduct3, 2);

        assertEquals(1, shoppingCart1.getItemCount());
        assertEquals(3, shoppingCart1.getTotal());

        shoppingCart1.removeFromCart(mockProduct1, 1);
    }

    @Test
    public void testRemoveFromCartQuantityZero() throws IllegalArgumentException {
        shoppingCart1.removeFromCart(mockProduct1, 1);
        shoppingCart1.removeFromCart(mockProduct2, 2);
        shoppingCart1.removeFromCart(mockProduct3, 3);

        assertEquals(new LinkedList<>(), shoppingCart1.getProducts());
        assertEquals(0, shoppingCart1.getItemCount());
        assertEquals(0, shoppingCart1.getTotal());
    }
}