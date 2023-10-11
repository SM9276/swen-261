package com.ufund.api.ufundapi.model;

import java.util.LinkedList;
import java.util.List;
public class ShoppingCart {
    private List <Product> products;
    private double total;
    private int itemCount;
    public ShoppingCart(){
        this.products = new LinkedList<Product>();
        this.total = 0;
        this.itemCount = 0;
    }
    public List<Product> getProducts(){
        return this.products;
    }
    public int getItemCount(){
        return this.itemCount;
    }
    public double getTotal(){
        return this.total;
    }

    
}
