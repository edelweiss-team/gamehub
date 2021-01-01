package model.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class represents an order created by a user in the store.
 * It holds information about order date, product list with quantities and the
 * operator who approved the order, if it was.
 */
public class Order {

    /**
     * Constructs a new order starting by the user who created the order,
     * the operator who approved the order (may be null), the id, and the order date.
     *
     * @param id the order id.
     * @param user the user who created the order.
     * @param operator the operator who approved the order (may be null).
     * @param data the order date.
     */
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

    /**
     * Construct a new order with empty fields.
     */
    public Order() {
        this.id = 0;
        this.data = "";
        this.numberOfItems = 0;
        this.user = new User();
        this.products = new HashMap<>();
        this.productsQuantity = new HashMap<>();
        this.totPrice = 0;
    }

    /**
     * Gets the total size of the order, in terms of items.
     *
     * @return the total number of items of the order.
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Sets the new number of items of the order.
     *
     * @param numberOfItems the new number of items of the order.
     */
    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    /**
     * Gets the order id.
     *
     * @return the order id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the order date.
     *
     * @return the order date.
     */
    @NotNull
    public String getData() {
        return data;
    }

    /**
     * Sets the new product id.
     *
     * @param id the new order id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the new date of the order.
     *
     * @param data the new date of the the order.
     */
    public void setData(@NotNull String data) {
        this.data = data;
    }

    /**
     * Gets the total price of the order.
     *
     * @return the total price of the order.
     */
    public double getTotPrice() {
        return totPrice;
    }

    /**
     * Sets the new total price of the order.
     *
     * @param totPrice the new total price of the order.
     */
    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }

    /**
     * Determines the user who the cart belongs.
     *
     * @return the owner of the cart if it exist, null otherwise.
     */
    @NotNull
    public User getUser() {
        return user;
    }

    /**
     * Set the user who owns the cart .
     *
     * @param user who owns the cart.
     */
    public void setUser(@NotNull User user) {
        this.user = user;
    }

    /**
     * Gets the operator who approved the order, if it was approved, null otherwise.
     *
     * @return the operator who approved the order, if it was approved, null otherwise.
     */
    @Nullable
    public Operator getOperator() {
        return operator;
    }

    /**
     * Sets the new operator who approved the order.
     *
     * @param operator the operator who approved the order.
     */
    public void setOperator(@Nullable Operator operator) {
        this.operator = operator;
    }


    /**
     * Allows to add a new product to the order,
     * starting from the product to be added and its quantity.
     * If the product is already in the cart, its quantity will simply be updated
     * The new total quantity will be added to the old one.
     * The old price will be updated.
     *
     * @param product the product that should be added to the order.
     * @param quantity the quantity of the product
     *                 that should be added to the order, must be greater than 1.
     */
    public void addProduct(@NotNull Product product, @NotNull Integer quantity) {

        if (quantity < 1) {
            throw new IllegalArgumentException("Il prodotto aggiunto al carrello "
                    + "deve avere una quantità maggiore di zero ");
        }

        products.put(product.getClass().getSimpleName() + product.getId(), product);

        Integer oldQuantity;
        oldQuantity = productsQuantity.get(product.getClass().getSimpleName() + product.getId());

        if (oldQuantity == null) {
            productsQuantity.put(product.getClass().getSimpleName() + product.getId(), quantity);
        } else {
            productsQuantity.put(product.getClass().getSimpleName() + product.getId(), oldQuantity + quantity);
        }

        this.totPrice += product.getPrice() * quantity;
        this.numberOfItems += quantity;
    }

    /**
     * Allows to remove a product from the order,
     * starting from the product to be removed and its quantity.
     * The new quantity will be subtracted from the old one.
     * The old price will be updated.
     *
     * @param product the product that should be added to the order
     * @param quantity the quantity of the product that should be added to the order,
     *                 must be greater than 1 and less than or equal to the quantity
     *                 already contained in the cart for that product.
     */
    public void removeProduct(@NotNull Product product, @NotNull Integer quantity) {

        if (!products.containsKey(product.getClass().getSimpleName() + product.getId())) {
            throw new IllegalArgumentException("Il prodotto che si vuole rimuovere non "
                    + "esiste all'interno del carrello");
        }

        if (quantity < 1) {
            throw new IllegalArgumentException("La quantità che si desidera "
                    + "rimuovere non è valida");
        }

        if (quantity > productsQuantity.get(product.getClass().getSimpleName() + product.getId())) {
            throw new IllegalArgumentException("Non ci sono abbastanza prodotti da rimuovere");
        }

        Integer oldQuantity;
        oldQuantity = productsQuantity.get(product.getClass().getSimpleName() + product.getId());
        int newQuantity = oldQuantity - quantity;

        if (newQuantity > 0) {
            productsQuantity.put(product.getClass().getSimpleName() + product.getId(), newQuantity);
        } else {
            productsQuantity.remove(product.getClass().getSimpleName() + product.getId());
            products.remove(product.getClass().getSimpleName() + product.getId());
        }
    }

    /**
     * Method to return the quantity of a product in the order.
     *
     * @param productId an Integer representing the product id to get the quantity,
     *                 must be not null.
     * @param prodClass the class of the product.
     * @return The quantity of the product if it is in the cart, 0 otherwise.
     */
    @NotNull
    public Integer getQuantitySingleProduct(
            @NotNull Integer productId, @NotNull Class<? extends Product> prodClass) {
        return (productsQuantity.get(prodClass.getSimpleName() + productId) != null)
                ? productsQuantity.get(prodClass.getSimpleName() + productId) : 0;
    }

    /**
     * Determines if a product is contained in the order.
     *
     * @param prodClass the class of the product.
     * @return true if the searched product is contained in the cart, false otherwise.
     */
    public boolean contains(
            @NotNull Integer productId, @NotNull Class<? extends Product> prodClass) {
        return products.containsKey(prodClass.getSimpleName() + productId);
    }


    /**
     * Get a product from the order starting from its id.
     *
     * @param productId the id of the desired product, must be not null.
     * @param prodClass the class of the product.
     * @return the product if the searched product is contained in the cart, null otherwise.
     */
    @Nullable
    public Product getProduct(
            @NotNull Integer productId, @NotNull Class<? extends Product> prodClass) {
        return products.get(prodClass.getSimpleName() + productId);
    }

    /**
     * Get all the products all the products contained in the order.
     *
     * @return a collection of all products contained in the order.
     */
    @NotNull
    public Collection<Product> getAllProducts() {
        return products.values();
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", data='" + data + '\''
                + ", totPrice=" + totPrice
                + ", operator=" + this.getOperator()
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && data.equals(order.data) && user.equals(order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, user);
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
    private final HashMap<String, Product> products;
    @NotNull
    private final HashMap<String, Integer> productsQuantity;
}
