package model.personalization;

import java.util.*;
import model.bean.Product;
import model.bean.Tag;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;
import org.jetbrains.annotations.NotNull;

/**
 * This class has the only purpose to build the actual recommended product list for the
 * {@link RecommendedProductList} class. It should, for this reason, never be used outside of the
 * aforementioned class or one of its specialization.
 */
public class RecommendedProductListBuilder {
    private static final int NUM_PRODS = 3;
    private static final int MAX_LIMIT = 100;

    /**
     * Construct a new {@link RecommendedProductListBuilder}.
     */
    public RecommendedProductListBuilder() {
        this.ppd = new PhysicalProductDAO();
        this.dpd = new DigitalProductDAO();
    }

    /**
     * This method build a recommended product list, maximizing the
     * number of products that match the given tags.
     *
     * @param tags an hash map with keys equal to the tag names, and tag equal to the actual tags
     * @return a recommended product list that maximize the number of products that match the given
     *         tags
     */
    @NotNull
    public List<Product> buildList(@NotNull HashMap<String, Tag> tags) {
        List<Product> products = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>(tags.values());
        HashMap<String, Integer> productsPriorities = new HashMap<>();

        //creiamo una coda a priorità ordinata in base allo score dei prodotti;
        //Lo score di un prodotto p è pari ai tag che ha in comune con la lista tags passata;
        PriorityQueue<Product> pq = new PriorityQueue<>(
                Comparator.comparingInt(
                        p -> -productsPriorities.get(p.getClass().getSimpleName() + p.getId())
                )
        );

        //creiamo la lista di prodotti effettuando il merge delle due liste(fisici e digitali)
        products.addAll(ppd.doRetrieveAllByTags(tagList, 0, MAX_LIMIT));
        products.addAll(dpd.doRetrieveAllByTags(tagList, 0, MAX_LIMIT));

        //calcoliamo lo score di ogni prodotto, sulla base del numero di tag che
        //matchano con la lista passata, e inseriamolo in una coda a priorità (tempo O(nlogn))
        products.forEach(p -> {
            int score = 0;
            //se il prodotto ha il tag, aumentiamo il suo score
            for (Tag t : tagList) {
                if (p.hasTag(t.getName()) != null) {
                    score++;
                }
            }
            productsPriorities.put(p.getClass().getSimpleName() + p.getId(), score);
            pq.add(p); //aggiungiamo alla coda a priorità
        });

        //se ci sono solo NUM_PRODS o meno elementi ritorniamo tutti i prodotti nella coda
        if (pq.size() <= NUM_PRODS) {
            return new ArrayList<>(pq);
        }
        List<Product> bestProducts = new ArrayList<>();
        for (int i = 0; i < NUM_PRODS; i++) {
            bestProducts.add(pq.poll());
        }
        return bestProducts;
    }

    private @NotNull final PhysicalProductDAO ppd;
    private @NotNull final DigitalProductDAO dpd;
}
