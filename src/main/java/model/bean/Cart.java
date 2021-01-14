package model.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class represents a cart used to store the items in the session and,
 * as for logged users, in the database too.
 */
public class Cart {

    /**
     * Constructs a new {@link Cart} starting from a user.
     * This cart is by default empty.
     * TotalPrice and numberOfItems are 0.
     *
     * @param user the {@link User} who the cart belongs.
     */
    public Cart(@Nullable User user) {
        totalPrice = 0;
        numberOfItems = 0;
        this.user = user;
        products = new LinkedHashMap<>();
        productsQuantity =  new LinkedHashMap<>();
    }

    /**
     * Constructs a new {@link Cart} without starting from a user.
     * This cart is by default empty.
     * TotalPrice and numberOfItems are 0.
     *
     */
    public Cart() {
        totalPrice = 0;
        numberOfItems = 0;
        this.user = null;
        products = new LinkedHashMap<>();
        productsQuantity =  new LinkedHashMap<>();
    }

    /**
     *Determines the {@link User} who the cart belongs.
     *
     * @return the {@link User} owner of the cart if it exist, null otherwise.
     */
    @Nullable
    public User getUser() {
        return user;
    }

    /**
     * Set the {@link User} who owns the cart .
     *
     * @param user who owns the cart.
     */
    public void setUser(@Nullable User user) {
        this.user = user;
    }

    /**
     * Determines the number of {@link Product}s in the cart.
     *
     * @return an int indicating the number of {@link Product}s in the cart.
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Set the number of {@link Product}s in the cart.
     *
     * @param numberOfItems the new number of {@link Product}s in the cart.
     */
    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    /**
     * Determines the total price of the cart,
     * obtained from the sum of the prices of the single {@link Product}s.
     *
     * @return  a double indicating the total price of the cart.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Set the total price of the cart.
     *
     * @param totalPrice the new total price of the cart.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Allows to add a new {@link Product} to the cart,
     * starting from the {@link Product} to be added and its quantity.
     * If the {@link Product} is already in the cart, its quantity will simply be updated
     * The new total quantity will be added to the old one.
     * The old price will be updated.
     *
     * @param product the {@link Product} that should be added to the cart.
     * @param quantity the quantity of the {@link Product}
     *                 that should be added to the cart, must be greater than 1.
     */
    public void addProduct(@Nullable Product product, @Nullable Integer quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Il prodotto aggiunto al carrello non è valido ");
        }

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("Il prodotto aggiunto al carrello "
                    + "deve avere una quantità maggiore di zero ");
        }

        products.put(product.getClass().getSimpleName() + product.getId(), product);

        Integer oldQuantity;
        oldQuantity = productsQuantity.get(product.getClass().getSimpleName() + product.getId());

        if (oldQuantity == null) {
            productsQuantity.put(product.getClass().getSimpleName() + product.getId(), quantity);
        } else {
            productsQuantity.put(product.getClass().getSimpleName()
                             + product.getId(), oldQuantity + quantity);
        }

        this.totalPrice += product.getPrice() * quantity;
        this.numberOfItems += quantity;
    }


    /**
     * Allows to remove a {@link Product} from the cart,
     * starting from the {@link Product} to be removed and its quantity.
     * The new quantity will be subtracted from the old one.
     * The old price will be updated.
     *
     * @param product the {@link Product} that should be added to the cart
     * @param quantity the quantity of the {@link Product} that should be added to the cart,
     *                 must be greater than 1 and less than or equal to the quantity
     *                 already contained in the cart for that {@link Product}.
     */
    public void removeProduct(@Nullable Product product, @Nullable Integer quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Un prodotto nullo non "
                    + "può essere rimosso dal carrello ");
        }

        if (!products.containsKey(product.getClass().getSimpleName() + product.getId())) {
            throw new IllegalArgumentException("Il prodotto che si vuole rimuovere non "
                    + "esiste all'interno del carrello");
        }

        if (quantity == null || quantity < 1) {
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

        this.totalPrice -= product.getPrice() * quantity;
        this.numberOfItems -= quantity;

    }

    /**
     * Method to return the quantity of a {@link Product} in the cart.
     *
     * @param productId an Integer representing the {@link Product} id to get the quantity,
     *                 must be not null.
     * @param prodClass the {@link Class} of the {@link Product}.
     * @return The quantity of the {@link Product} if it is in the cart, 0 otherwise.
     */
    @NotNull
    public Integer getQuantitySingleProduct(
            @NotNull Integer productId, @NotNull Class<? extends Product> prodClass) {
        return (productsQuantity.get(prodClass.getSimpleName() + productId) != null)
                ? productsQuantity.get(prodClass.getSimpleName() + productId) : 0;
    }

    /**
     * Determines if a {@link Product} is contained in the cart.
     *
     * @param productId the id of the searched {@link Product}, must be not null.
     * @param prodClass the class of the {@link Product}.
     * @return true if the searched {@link Product} is contained in the cart, false otherwise.
     */
    public boolean contains(
            @NotNull Integer productId, @NotNull Class<? extends Product> prodClass) {
        return products.containsKey(prodClass.getSimpleName() + productId);
    }

    /**
     * Get a {@link Product} from the cart starting given its id and its class.
     *
     * @param productId the id of the desired {@link Product}, must be not null.
     * @param prodClass the class of the {@link Product}.
     * @return the {@link Product} if the searched {@link Product} is contained in the cart,
     *         null otherwise.
     */
    @Nullable
    public Product getProduct(
            @NotNull Integer productId, @NotNull Class<? extends Product> prodClass) {
        return products.get(prodClass.getSimpleName() + productId);
    }

    /**
     * Get all the products all the {@link Product}s contained in the cart.
     *
     * @return a collection of all {@link Product}s contained in the cart.
     */
    @NotNull
    public Collection<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Nullable
    private User user;
    private double totalPrice;
    private int numberOfItems;
    @NotNull
    private final LinkedHashMap<String, Product> products;
    @NotNull
    private final LinkedHashMap<String, Integer> productsQuantity;
}
