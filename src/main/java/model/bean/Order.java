package model.bean;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;


public class Order {



    public Order(int id, User user, Operator operator, String data) {
        this.id = id;
        this.data = data;
        this.user = user;
        this.operator = operator;
        numberOfItems = 0;
        totPrice = 0;
        products = new HashMap<>();
        productsQuantity = new HashMap<>();
    }

    public Order() {}

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }


    public void addProduct(@NotNull Product product, @NotNull Integer quantity) {

        if (quantity < 1) {
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

        this.totPrice += product.getPrice() * quantity;
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


    private int id;
    private String data;
    private double totPrice;
    private User user;
    private Operator operator;
    private int numberOfItems;
    private HashMap<Integer, Product> products;
    private HashMap<Integer, Integer> productsQuantity;
}
