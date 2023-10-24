package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.ufund.api.ufundapi.model.Product;
import com.ufund.api.ufundapi.persistence.InventoryDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Tag;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
* Test for the InventoryController class
*/
@ExtendWith(MockitoExtension.class)
@Tag("Controller")
public class InventoryControllerTest {
    @Mock
    InventoryDAO inventoryDAO;
    @InjectMocks
    InventoryController mockInventoryController;   
    ResponseEntity<Product> testEntityProduct;
    ResponseEntity<Product[]> testEntityProducts;

    /**
    * Testing getProduct given the product exists 
    */

    @Test
    public void testGetProducts() throws IOException{
        Product[] testProducts = {new Product("Water",2,5,1)}; 
        when(inventoryDAO.getProducts()).thenReturn(testProducts);
        testEntityProducts = mockInventoryController.getProduct();
        assertEquals(testEntityProducts.getStatusCodeValue(), 200);
    }

    @Test
    public void testGetProductsThrowsIO() throws IOException{
        when(inventoryDAO.getProducts()).thenThrow(IOException.class);
        testEntityProducts = mockInventoryController.getProduct();
        assertEquals(testEntityProducts.getStatusCodeValue(), 500);
    }

    @Test
    public void testGetProduct() throws IOException{
        Product testProduct = new Product("Water",2,5,1); 
        when(inventoryDAO.getProduct(testProduct.getId())).thenReturn(testProduct);
        testEntityProduct = mockInventoryController.getProduct(testProduct.getId());
        assertEquals(testEntityProduct.getStatusCodeValue(), 200);
    }

    /**
    * Testing getProducts catch block for IOException 
    */
    @Test
    public void testGetProductThrowsIO() throws IOException{
        when(inventoryDAO.getProduct(anyInt())).thenThrow(IOException.class);
        testEntityProduct = mockInventoryController.getProduct(anyInt());
        assertEquals(testEntityProduct.getStatusCodeValue(), 500);
    }

    /**
    * Testing getProduct given the product does not exist 
    */
    @Test
    public void testGetProduct404() throws IOException{
        when(inventoryDAO.getProduct(anyInt())).thenReturn(null);
        ResponseEntity<Product> testEntityProduct = mockInventoryController.getProduct(anyInt());
        assertEquals(testEntityProduct.getStatusCodeValue(), 404);
    }
    /**
    * Testing searchProducts  
    */
    @Test
    public void testSearchProducts() throws IOException{
        Product[] testProducts = new Product[3];
        testProducts[0] = new Product("Food",5.50,6,2);
        when(inventoryDAO.findProducts("ood")).thenReturn(testProducts);
        testEntityProducts = mockInventoryController.searchProduct("ood");
        assertEquals(testEntityProducts.getStatusCodeValue(), 200);
    }

    /**
    * Testing searchProducts catch block for IOException 
    */
    @Test
    public void testSearchProductsThrowsIO() throws IOException{
        when(inventoryDAO.findProducts(anyString())).thenThrow(IOException.class);
        testEntityProducts = mockInventoryController.searchProduct(anyString());
        assertEquals(testEntityProducts.getStatusCodeValue(), 500);
    }
    
    /**
    * Testing createProduct givin the Product does not already exist
    */
    @Test
    public void testCreateProduct() throws IOException{
        Product testProduct = new Product("Food",5.50,6,2);
        when(inventoryDAO.createProduct(testProduct)).thenReturn(testProduct);
        ResponseEntity<Product> testEntity = mockInventoryController.createProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 201);    }
    /**
    * Testing createProduct givin the Product does not already exist
    */
    @Test
    public void testCreateProductConflict() throws IOException{
        Product testProduct = new Product("Food",5.50,6,2);
        when(inventoryDAO.createProduct(testProduct)).thenReturn(null);
        ResponseEntity<Product> testEntity = mockInventoryController.createProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 201);    
    }
    /**
    * Testing createProduct catch block for an IOException 
    */
    @Test
    public void testCreateProductThrowsIO() throws IOException{
        Product testProduct = new Product("Food",5.50,6,2);
        when(inventoryDAO.createProduct(any(Product.class))).thenThrow(IOException.class);
        ResponseEntity<Product> testEntity = mockInventoryController.createProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }
    
    /**
    * Testing updateProduct given the product exists 
    */
    @Test
    public void testUpdateProduct() throws IOException{
        Product testProduct = new Product("Food",5.50,6,2);
        when(inventoryDAO.updateProduct(testProduct)).thenReturn(testProduct);
        ResponseEntity<Product> testEntity = mockInventoryController.updateProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 200);    
    }

    /**
    * Testing createProduct givin the Product does not already exist
    */
    @Test
    public void testUpdateProductConflict() throws IOException{
        Product testProduct = new Product("Food",5.50,6,2);
        when(inventoryDAO.updateProduct(testProduct)).thenReturn(null);
        ResponseEntity<Product> testEntity = mockInventoryController.updateProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 404);    
    }
    /**
    * Testing updateProduct catch block for an IOException 
    */
    @Test
    public void testUpdateProductThrowsIO() throws IOException{
        Product testProduct = new Product("Food",5.50,6,2);
        when(inventoryDAO.updateProduct(any(Product.class))).thenThrow(IOException.class);
        ResponseEntity<Product> testEntity = mockInventoryController.updateProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }
    
    /**
    * Testing Delete given the Product exists
    */
    @Test
    public void testDeleteProduct() throws IOException{
        Product testProduct = new Product("Food",5.50,6,2);
        when(inventoryDAO.deleteProduct(testProduct.getId())).thenReturn(true);
        ResponseEntity<Product> testEntity = mockInventoryController.deleteProduct(testProduct.getId());
        assertEquals(testEntity.getStatusCodeValue(), 200);    
    }

    /**
    * Testing deleteProduct catch block for an IOException 
    */
    @Test
    public void testDeleteProductThrowsIO() throws IOException{
        Product testProduct = new Product("Socks",12,2,3);
        when(inventoryDAO.deleteProduct(3)).thenThrow(IOException.class);
        ResponseEntity<Product> testEntity = mockInventoryController.deleteProduct(testProduct.getId());
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }

    /**
    * Testing deleteProduct givin the Product does not already exist
    */
    @Test
    public void testDeleteProductConflict() throws IOException{
        when(inventoryDAO.deleteProduct(anyInt())).thenReturn(false);
        ResponseEntity<Product> testEntity = mockInventoryController.deleteProduct(anyInt());
        assertEquals(testEntity.getStatusCodeValue(), 404);    
    }
}



