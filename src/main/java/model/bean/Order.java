package model.bean;

import java.util.Collection;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class Order {

    public Order(int id, @NotNull User user, @Nullable Operator operator, @NotNull String data) {
        this.id = id;
        this.data = data;
        this.user = user;
        this.operator = operator;
        numberOfItems = 0;
        totPrice = 0;
        products = new HashMap<>();
        productsQuantity = new HashMap<>();
    }

    public Order() {
        this.id = 0;
        this.data = "";
        this.numberOfItems = 0;
        this.user = new User();
        this.products = new HashMap<>();
        this.productsQuantity = new HashMap<>();
        this.totPrice = 0;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public int getId() {
        return id;
    }

    @NotNull
    public String getData() {
        return data;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setData(@NotNull String data) {
        this.data = data;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }

    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    @Nullable
    public Operator getOperator() {
        return operator;
    }

    public void setOperator(@Nullable Operator operator) {
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

    public void removeProduct(@NotNull Product product, @NotNull Integer quantity) {

        if (!products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Il prodotto che si vuole rimuovere non "
                    + "esiste all'interno del carrello");
        }

        if (quantity < 1) {
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

    @NotNull
    public Integer getQuantitySingleProduct(@NotNull Integer productId) {
        return productsQuantity.get(productId) != null ? productsQuantity.get(productId) : 0;
    }

    public boolean contains(@NotNull Integer productId) {
        return products.containsKey(productId);
    }

    @NotNull
    public Product getProduct(@NotNull Integer productId) {
        return products.get(productId);
    }

    @NotNull
    public Collection<Product> getAllProducts() {
        return products.values();
    }


    private int id;
    @NotNull
    private String data;
    private double totPrice;
    @NotNull
    private User user;
    @Nullable
    private Operator operator;
    private int numberOfItems;
    @NotNull
    private final HashMap<Integer, Product> products;
    @NotNull
    private final HashMap<Integer, Integer> productsQuantity;
}
