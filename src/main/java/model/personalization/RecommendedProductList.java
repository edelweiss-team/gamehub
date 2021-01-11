package model.personalization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.bean.Product;
import model.bean.Tag;
import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class models the recommended {@link Product} list, created based on the user interest,
 * following the indications of the machine learning model.
 * Note that this class uses the Proxy design pattern, so that when an instance of this class is
 * constructed, the actual {@link Product} list is empty, and then, when it's requested, the list
 * is filled based on the machine learning model recommendations.
 */
public class RecommendedProductList {
    //variabile static e final in quanto non più di un utente alla volta deve accedere al bridge
    @NotNull
    private static final PersonalizationBridge BRIDGE = PersonalizationBridge.getInstance();

    /**
     * Construct a new, empty {@link RecommendedProductList} for the given {@link User}.
     *
     * @param user the user we want to construct the {@link RecommendedProductList} for
     */
    public RecommendedProductList(@NotNull User user) {
        this.products = new ArrayList<>();
        this.builder = new RecommendedProductListBuilder();
        this.user = user;
        this.vote = null;
    }

    /**
     * This method gets the vote value of the {@link RecommendedProductList}.
     * Default vote value is null.
     *
     * @return true or false based on the user vote, null if the user hasn't vote yet
     */
    @Nullable
    public Boolean getVote() {
        return vote;
    }

    /**
     * This method sets the vote value of the {@link RecommendedProductList}.
     * Note that each time this method is called, the user's vote is saved on a file, for future
     * machine learning model retraining, based on it's performance.
     * Note that this method should be called iff the actual {@link Product} {@link List} has
     * already been initialized, for semantics reasons; for this reason, a getList() invocation
     * prior to this method invocation is mandatory.
     *
     * @param vote the user vote value for the list, must be either true or false
     * @throws ListNotInitializedException if the method is called while the {@link Product}
     * {@link List} it's not initialized yet (meaning the getList() method was not called yet).
     */
    public void setVote(@NotNull Boolean vote) {
        if (this.products.isEmpty()) {
            throw new ListNotInitializedException("Error: setVote() method may not be called if"
                    + " the product list is not initialized yet!");
        }
        this.vote = vote;
        //chiamiamo il bridge per registrare il voto
        BRIDGE.registerVote(user, vote);
    }

    /**
     * This method gets the recommended {@link Product}s as a {@link List}, based on the
     * user interests. Note that the first time this method is called, the actual list is
     * constructed querying (indirectly) the machine learning model; the next times this method is
     * invoked, the previously retrieved {@link List} it's returned.
     *
     * @return the {@link List} of {@link Product}s constructed basing on the user's interests
     */
    @NotNull
    public List<Product> getList() {
        if (this.products.isEmpty()) {
            List<Tag> tags;
            HashMap<String, Tag> tagMap = new HashMap<>();

            //synchronized su BRIDGE per via della non-concorrenza dei suoi metodi
            //prendiamoci la lista dei tag per quest'utente, interrogando indirettamente il modello
            tags = BRIDGE.getTagList(user);
            tags.forEach(t -> tagMap.put(t.getName(), t));

            //chiamiamo il builder della lista che costruirà, sulla base dei tag ricevuti, la lista
            //di prodotti consigliata, dal database
            this.products.addAll(builder.buildList(tagMap));
        }
        return new ArrayList<>(this.products);
    }

    @Override
    @NotNull
    public String toString() {
        return "RecommendedProductList{"
                + "products=" + products
                + ", user=" + user
                + ", vote=" + vote
                + '}';
    }

    @NotNull
    private final List<Product> products;
    @NotNull
    private final User user;
    @Nullable
    private Boolean vote;
    @NotNull
    private final RecommendedProductListBuilder builder;
}
