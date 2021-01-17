package model.bean;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void getTagsNoTag(){
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = new ArrayList<>();
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        Collection<Tag> toCompare = p.getTags();
        assertIterableEquals(tags, p.getTags());
    }

    @Test
    void getTagsSingleTag(){
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Collections.singletonList(
                new Tag("tag0")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };
        assertIterableEquals(tags, p.getTags());
    }

    @Test
    void getTagsMultiTag(){
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertIterableEquals(tags, p.getTags());
    }

    @Test
    void getCategoriesNoCategory(){
        List<Category> categories = new ArrayList<>();
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertIterableEquals(categories, p.getCategories());
    }

    @Test
    void getCategoriesSingleCategory(){
        List<Category> categories = Collections.singletonList(
                new Category("cat0", "desc0", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertIterableEquals(categories, p.getCategories());
    }

    @Test
    void getCategoriesMultiCategory(){
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertIterableEquals(categories, p.getCategories());
    }

    @Test
    void hasCategory() {
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertEquals(p.hasCategory("cat0"), categories.get(0));
    }

    @Test
    void hasNotCategory(){
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertNull(p.hasCategory("cat2"));
    }

    @Test
    void hasTag() {
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertEquals(p.hasTag("tag0"), tags.get(0));
    }

    @Test
    void hasNotTag() {
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertNull(p.hasTag("tag3"));
    }

    @Test
    void addTag() {
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        p.addTag(new Tag("tag3"));
        assertNotNull(p.hasTag("tag3"));
    }

    @Test
    void addCategory() {
        List<Category> categories = Collections.singletonList(
                new Category("cat0", "desc0", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        p.addCategory(new Category("cat1", "desc1", "path0"));
        assertNotNull(p.hasCategory("cat1"));
    }

    @Test
    void removeCategoryExists() {
        Category cat = new Category("cat0", "desc0", "path0");

        List<Category> categories = Collections.singletonList(cat);
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };
        assertEquals(p.removeCategory(cat), cat);
    }

    @Test
    void removeCategoryNotExists() {
        Category cat = new Category("cat0", "desc0", "path0");

        List<Category> categories = Collections.singletonList(cat);
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        assertNull(p.removeCategory(new Category("cat1", "desc1", "path1")));
    }

    @Test
    void removeTagExists() {
        Tag t = new Tag("tag2");

        List<Category> categories = Collections.singletonList(
                new Category("cat0", "desc0", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1"),
                t
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };
        assertEquals(p.removeTag(t), t);
    }

    @Test
    void removeTagNotExists() {
        Tag t = new Tag("tag2");

        List<Category> categories = Collections.singletonList(
                new Category("cat0", "desc0", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };
        assertNull(p.removeTag(t));
    }

    @Test
    void setTagsNoTag(){
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = new ArrayList<>();
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                Arrays.asList(new Tag("tag0"), new Tag("tag1")), 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };
        p.setTags(tags);
        assertIterableEquals(tags, p.getTags());
    }

    @Test
    void setTagsSingleTag(){
        List<Category> categories = Collections.singletonList(
                new Category("cat0", "desc0", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                new ArrayList<>(), 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        p.setTags(tags);
        assertIterableEquals(tags, p.getTags());
    }

    @Test
    void setTagsMultiTag(){
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", categories,
                new ArrayList<>(), 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        p.setTags(tags);
        assertIterableEquals(tags, p.getTags());
    }

    @Test
    void setCategoriesNoCategory(){
        List<Category> categories = Collections.singletonList(
                new Category("cat0", "desc0", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", new ArrayList<>(),
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        p.setCategories(categories);
        assertIterableEquals(categories, p.getCategories());
    }

    @Test
    void setCategoriesSingleCategory(){
        List<Category> categories = Collections.singletonList(
                new Category("cat0", "desc0", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", new ArrayList<>(),
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        p.setCategories(categories);
        assertIterableEquals(categories, p.getCategories());
    }

    @Test
    void setCategoriesMultiCategory(){
        List<Category> categories = Arrays.asList(
                new Category("cat0", "desc0", "path0"),
                new Category("cat1", "desc1", "path0")
        );
        List<Tag> tags = Arrays.asList(
                new Tag("tag0"),
                new Tag("tag1")
        );
        Product p;
        p = new Product(
                1, "Prod0", 22.2, "desc", "path", new ArrayList<>(),
                tags, 2
        ) {
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        };

        p.setCategories(categories);
        assertIterableEquals(categories, p.getCategories());
    }

    @Test
    void testEqualsOk() {
        class ProductImpl0 extends Product{
            public ProductImpl0(int id, String name, double price, String description, String image,
                                Collection<Category> categories, Collection<Tag> tags, int quantity){
                super(id, name, price, description, image, categories, tags, quantity);
            }
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        }
        Product p0 = new ProductImpl0(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1
        );

        Product p1 = new ProductImpl0(
                1, "p1", 11, "desc1", "path1", new ArrayList<>(),
                new ArrayList<>(), 2
        );
        assertTrue(p0.equals(p1));
    }

    @Test
    void testEqualsNotOkSameClass() {
        class ProductImpl0 extends Product{
            public ProductImpl0(int id, String name, double price, String description, String image,
                                Collection<Category> categories, Collection<Tag> tags, int quantity){
                super(id, name, price, description, image, categories, tags, quantity);
            }
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        }
        Product p0 = new ProductImpl0(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1
        );

        Product p1 = new ProductImpl0(
                2, "p1", 11, "desc1", "path1", new ArrayList<>(),
                new ArrayList<>(), 2
        );
        assertFalse(p0.equals(p1));
    }

    @Test
    void testEqualsNotOkOtherClass() {
        class ProductImpl0 extends Product{
            public ProductImpl0(int id, String name, double price, String description, String image,
                                Collection<Category> categories, Collection<Tag> tags, int quantity){
                super(id, name, price, description, image, categories, tags, quantity);
            }
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        }
        class ProductImpl1 extends Product{
            public ProductImpl1(int id, String name, double price, String description, String image,
                                Collection<Category> categories, Collection<Tag> tags, int quantity){
                super(id, name, price, description, image, categories, tags, quantity);
            }
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        }

        Product p0 = new ProductImpl0(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1
        );

        Product p1 = new ProductImpl1(
                1, "p1", 11, "desc1", "path1", new ArrayList<>(),
                new ArrayList<>(), 2
        );
        assertFalse(p0.equals(p1));
    }

    @Test
    void testEqualsNotOkNull() {
        class ProductImpl0 extends Product{
            public ProductImpl0(int id, String name, double price, String description, String image,
                                Collection<Category> categories, Collection<Tag> tags, int quantity){
                super(id, name, price, description, image, categories, tags, quantity);
            }

            @Override
            public @NotNull HashMap<String, String> getAdditionalInformations() {
                return new HashMap<>();
            }
        }

        Product p0 = new ProductImpl0(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1
        );

        assertFalse(p0.equals(null));
    }
}