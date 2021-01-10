package model.personalization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.bean.Product;
import model.bean.Tag;
import model.bean.User;
import model.dao.UserDAO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RecommendedProductList {
    //variabile static e final in quanto non più di un utente alla volta deve accedere al bridge
    @NotNull
    private static final PersonalizationBridge BRIDGE = new PersonalizationBridge();

    public RecommendedProductList(@NotNull User user) {
        this.products = new ArrayList<>();
        this.builder = new RecommendedProductListBuilder();
        this.user = user;
        this.vote = null;
    }

    @Nullable
    public Boolean getVote() {
        return vote;
    }

    public void setVote(@NotNull Boolean vote) {
        this.vote = vote;
        //chiamiamo il bridge per registrare il voto
        //synchronized (BRIDGE) {
            BRIDGE.registerVote(user, vote);
        //}
    }

    @NotNull
    public List<Product> getList() {
        if (this.products.isEmpty()) {
            List<Tag> tags;
            HashMap<String, Tag> tagMap = new HashMap<>();

            //synchronized su BRIDGE per via della non-concorrenza dei suoi metodi
            //prendiamoci la lista dei tag per quest'utente, interrogando indirettamente il modello
            synchronized (BRIDGE) {
                tags = BRIDGE.getTagList(user);
            }
            tags.forEach(t -> tagMap.put(t.getName(), t));

            //chiamiamo il builder della lista che costruirà, sulla base dei tag ricevuti, la lista
            //di prodotti consigliata, dal database
            this.products.addAll(builder.buildList(tagMap));
        }
        return new ArrayList<>(this.products);
    }

    @Override
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
