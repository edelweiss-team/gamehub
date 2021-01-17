package model.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This abstract class represents a generic product.
 * A product is characterized by a name, a description, a quantity, a price,
 * an image path and a list of tags and categories.
 */
public abstract class Product {

    /**
     * Constructs a generic product, with empty fields.
     * These fields should be initialized to be consistent.
     */
    public Product() {
        id = 0;
        name = "";
        description = "";
        image = "";
        quantity = 0;
        price = 0;
        tags = new HashMap<>();
        categories = new HashMap<>();
    }

    /**
     * Constructs a generic product starting off by its id, name, price, description,
     * image, quantity and {@link Category} and {@link Tag} list.
     *
     * @param id the id of the product
     * @param name the name of the product.
     * @param price the price of the product
     * @param description the description of the product
     * @param image the image path of the product
     * @param categories the category list of the product
     * @param tags the tag list of the product
     * @param quantity the quantity of the product
     */
    public Product(int id, @NotNull String name, double price, @NotNull String description,
                   @NotNull String image, @NotNull Collection<Category> categories,
                   @NotNull Collection<Tag> tags, int quantity) {
        this.description = description;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;

        //inseriamo le categorie
        this.categories = new HashMap<>();
        for (Category c : categories) {
            this.categories.put(c.getName(), c);
        }

        //inseriamo i tag
        this.tags = new HashMap<>();
        for (Tag t : tags) {
            this.tags.put(t.getName(), t);
        }
    }

    /**
     * Gets the product id.
     *
     * @return the id of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the product.
     *
     * @param id the new product id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the product.
     *
     * @return the name of the product.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the new product name.
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets the description of the product.
     *
     * @return the description of the product.
     */
    public @NotNull String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the new product description.
     */
    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    /**
     * Gets the path on the server of the product image.
     *
     * @return the path of the product image on the server.
     */
    public @NotNull String getImage() {
        return image;
    }

    /**
     * Sets a new path for the product image.
     *
     * @param image the new path of the product image.
     */
    public void setImage(@NotNull String image) {
        this.image = image;
    }

    /**
     * Gets the product quantity currently available.
     *
     * @return int quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the new product quantity.
     *
     * @param quantity the new product quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the product.
     *
     * @return the price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the new product price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Establish whatever the product has the passed category or not.
     *
     * @param categoryName the category.
     * @return true iff product.getCategories().contains(category), false otherwise.
     */
    public @Nullable Category hasCategory(@NotNull String categoryName) {
        return this.categories.get(categoryName);
    }

    /**
     * Establish whatever the product has the passed tag or not.
     *
     * @param tagName the tag.
     * @return the tag iff product.getTags().contains(tag), null otherwise.
     */
    public @Nullable Tag hasTag(@NotNull String tagName) {
        return this.tags.get(tagName);
    }

    /**
     * Adds the passed category to the category list of the product, iff it is not already in.
     *
     * @param c the category to add.
     */
    public void addCategory(@NotNull Category c) {
        this.categories.put(c.getName(), c);
    }

    /**
     * Remove the passed category to the category list of the product and returns it, if present.
     *
     * @param c the category to remove.
     * @return the category, if it was removed, null otherwise.
     */
    public @Nullable Category removeCategory(@NotNull Category c) {
        Category toRemove = this.categories.get(c.getName());
        if (toRemove != null) {
            this.categories.remove(c.getName());
        }
        return toRemove;
    }

    /**
     * Adds the passed tag to the tag list of the product, iff it is not already in.
     *
     * @param t the tag to add.
     */
    public void addTag(@NotNull Tag t) {
        this.tags.put(t.getName(), t);
    }

    /**
     * Remove the passed tag to the tag list of the product and returns it, if present.
     *
     * @param t the tag to remove.
     * @return the tag, if it was removed, null otherwise.
     */
    public @Nullable Tag removeTag(@NotNull Tag t) {
        Tag toRemove = this.tags.get(t.getName());
        if (toRemove != null) {
            this.tags.remove(t.getName());
        }
        return toRemove;
    }

    /**
     * Gets the category list of the product.
     *
     * @return the category list of the product.
     */
    public @NotNull Collection<Category> getCategories() {
        return categories.values();
    }

    /**
     * Sets the category list of the product.
     *
     * @param categories the new category list of the product.
     */
    public void setCategories(@NotNull Collection<Category> categories) {
        //inseriamo le categorie
        this.categories = new HashMap<>();
        for (Category c : categories) {
            this.categories.put(c.getName(), c);
        }
    }

    /**
     * Gets the tag list of the product.
     *
     * @return the tag list of the product.
     */
    @NotNull
    public Collection<Tag> getTags() {
        return tags.values();
    }

    /**
     * Sets the tag list of the product.
     *
     * @param tags the new tag list of the product.
     */
    public void setTags(@NotNull Collection<Tag> tags) {
        //inseriamo i tag
        this.tags = new HashMap<>();
        for (Tag t : tags) {
            this.tags.put(t.getName(), t);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", image='" + image + '\''
                + ", quantity=" + quantity
                + ", price=" + price
                + '}';
    }

    @NotNull
    public abstract HashMap<String, String> getAdditionalInformations();

    private int id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String image;
    private int quantity;
    private double price;
    @NotNull
    private HashMap<String, Category> categories;
    @NotNull
    private HashMap<String, Tag> tags;
}
