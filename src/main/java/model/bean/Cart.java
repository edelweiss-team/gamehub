package model.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Cart {

    public Cart(User user) {
        totalPrice = 0;
        numberOfItems = 0;
        this.user = user;
        products = new LinkedHashMap<>();
        productsQuantity =  new LinkedHashMap<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }



    public void addProduct(Product product, Integer quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Il prodotto aggiunto al carrello non è valido ");
        }

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("Il prodotto aggiunto al carrello "
                    + "deve avere una quantità maggiore di zero ");
        }

        products.put(product.getId(), product);

        Integer oldQuantity;
        oldQuantity = productsQuantity.get(product.getId());

        if (oldQuantity == null) {
            productsQuantity.put(product.getId(), quantity);
        } else {
            productsQuantity.put(product.getId(), oldQuantity + quantity);
        }

        this.totalPrice += product.getPrice() * quantity;
        this.numberOfItems += quantity;
    }



    public void removeProduct(Product product, Integer quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Un prodotto nullo non "
                    + "può essere rimosso dal carrello ");
        }

        if (!products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Il prodotto che si vuole rimuovere non "
                    + "esiste all'interno del carrello");
        }

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("La quantità che si desidera "
                    + "rimuovere non è valida");
        }

        if (quantity > productsQuantity.get(product.getId())) {
            throw new IllegalArgumentException("Non ci sono abbastanza prodotti da rimuovere");
        }

        Integer oldQuantity;
        oldQuantity = productsQuantity.get(product.getId());
        int newQuantity = oldQuantity - quantity;

        if (newQuantity > 0) {
            productsQuantity.put(product.getId(), newQuantity);
        } else {
            productsQuantity.remove(product.getId());
            products.remove(product.getId());
        }

    }

    public Integer getQuantitySingleProduct(Integer productId) {
        return productsQuantity.get(productId);
    }

    public boolean contains(Integer productId) {
        return products.containsKey(productId);
    }

    public Product getProduct(Integer productId) {
        return products.get(productId);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    private User user;
    private double totalPrice;
    private int numberOfItems;
    private LinkedHashMap<Integer, Product> products;
    private LinkedHashMap<Integer, Integer> productsQuantity;
    

}