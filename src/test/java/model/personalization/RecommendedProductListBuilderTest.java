package model.personalization;

import model.bean.DigitalProduct;
import model.bean.Product;
import model.bean.Tag;
import model.bean.Category;
import model.dao.CategoryDAO;
import model.dao.DigitalProductDAO;
import model.dao.TagDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;

class RecommendedProductListBuilderTest {

    private static Tag t0 = new Tag("tagRecc0"), t1 = new Tag("tagRecc1"),
            t2 = new Tag("tagRecc2");
    private static TagDAO td = new TagDAO();
    private static ArrayList<Tag> tags = new ArrayList<>(Arrays.asList(t0, t1, t2));
    private static Category c1 = new Category("cRecc1", "d1", "i1");
    private static CategoryDAO cd = new CategoryDAO();
    private static DigitalProductDAO dpd = new DigitalProductDAO();
    private static DigitalProduct dp0 =
            new DigitalProduct(10000, "prod0", 300, "desc", "path",
                    Arrays.asList(c1), tags, 3, "ps3", "2021-12-12",
                    18, "giggino", "giggino");
    private static DigitalProduct dp1 =
            new DigitalProduct(10001, "prod1", 300, "desc", "path",
                    Arrays.asList(c1), tags, 3, "ps3", "2021-12-12",
                    18, "giggino", "giggino");
    private static DigitalProduct dp2 =
            new DigitalProduct(10002, "prod2", 300, "desc", "path",
                    Arrays.asList(c1), tags, 3, "ps3", "2021-12-12",
                    18, "giggino", "giggino");
    private static DigitalProduct dp3 =
            new DigitalProduct(10002, "prod2", 300, "desc", "path",
                    Arrays.asList(c1), tags, 3, "ps3", "2021-12-12",
                    18, "giggino", "giggino");
    private static RecommendedProductListBuilder rplb = new RecommendedProductListBuilder();
    @BeforeAll
    static void setup() {
        td.doSave(t0);
        td.doSave(t1);
        td.doSave(t2);
        cd.doSave(c1);
        dpd.doSave(dp0);
        dpd.doSave(dp1);
        dpd.doSave(dp2);
        dpd.doSave(dp3);
    }

    @Test
    void tagListEmpty() {
        assertThrows(IllegalArgumentException.class, () -> rplb.buildList(new HashMap<>()));
    }

    @Test
    void tagListOne() {
        HashMap<String, Tag> h = new HashMap<>();
        h.put(t0.getName(), t0);
        List<Product> products = rplb.buildList(h);
        assertFalse(products.isEmpty());
    }


    @Test
    void tagListMore() {
        HashMap<String, Tag> h = new HashMap<>();
        h.put(t0.getName(), t0);
        h.put(t1.getName(), t1);
        List<Product> products = rplb.buildList(h);
        assertFalse(products.isEmpty());
    }

    @Test
    void tagListLessProducts() {
        HashMap<String, Tag> h = new HashMap<>();
        h.put(t0.getName(), t0);
        h.put(t1.getName(), t1);
        h.put(t2.getName(), t2);
        dpd.doDelete(dp0.getId());
        List<Product> products = rplb.buildList(h);
        assertFalse(products.isEmpty());
        dpd.doSave(dp0);
    }

    @AfterAll
    static void clear() {
        td.doDelete(t0.getName());
        td.doDelete(t1.getName());
        td.doDelete(t2.getName());
        cd.doDeleteByName(c1.getName());
        dpd.doDelete(dp0.getId());
        dpd.doDelete(dp1.getId());
        dpd.doDelete(dp2.getId());
        dpd.doDelete(dp3.getId());
    }
}