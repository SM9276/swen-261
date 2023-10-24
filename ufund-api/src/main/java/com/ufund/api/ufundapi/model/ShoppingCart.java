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

    public void addToCart(Product item) {
        if (products.contains(item)) {
            int index = products.indexOf(item);
            products.get(index).setQuantity(products.get(index).getQuantity() + item.getQuantity());
        } else {
            products.add(new Product(item.getName(), item.getPrice(), item.getQuantity(),item.getId()));
        }
        total += item.getPrice() * item.getQuantity();
        itemCount += item.getQuantity();
    }

    public void addToCart(Product item, int quantity) {
        if (products.contains(item)) {
            int index = products.indexOf(item);
            products.get(index).setQuantity(products.get(index).getQuantity() + quantity);
        } else {
            products.add(new Product(item.getName(), item.getPrice(), quantity,item.getId()));
        }
        total += item.getPrice() * quantity;
        itemCount += quantity;
    }

    public void removeFromCart(Product item) {
        if (products.contains(item)) {
            total -= item.getPrice() * item.getQuantity();
            itemCount -= item.getQuantity();
            if (itemCount <= 0) itemCount = 0;
            if (total <= 0) total = 0;
            products.remove(item);
        }
    }

    public void removeFromCart(Product item, int quantity) {
        if (products.contains(item)) {
            total -= item.getPrice() * quantity;
            itemCount -= quantity;

            if (itemCount <= 0) itemCount = 0;
            if (total <= 0) total = 0;
            
            int index = products.indexOf(item);
            products.get(index).setQuantity(products.get(index).getQuantity() - quantity);

            if (products.get(products.indexOf(item)).getQuantity() <= 0 ) {
                products.remove(item);
            }
        }   
    }
    public List<Product> getProducts() {
        if (products.isEmpty()) {
            this.itemCount = 0;
            this.total = 0;
        }
        return products;
    }
    public int getItemCount(){
        return this.itemCount;
    }
    public double getTotal(){
        return this.total;
    }
}
