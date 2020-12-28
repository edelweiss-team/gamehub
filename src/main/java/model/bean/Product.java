package model.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Product {

    /**
     *
     */
    public Product() {
        name = "";
        description = "";
        image = "";
        quantity = 0;
        price = 0;
        tags = new HashMap<>();
        categories = new HashMap<>();
    }

    /**
     *
     * @param name String
     * @param price double
     * @param description String
     * @param image String
     * @param categories Collection<Category></Category>
     * @param tags Collection<Tag></Tag>
     * @param quantity int
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
     *
     * @return id int
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return String this.name
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     *
     * @param name String
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     *
     * @return String this.description
     */
    public @NotNull String getDescription() {
        return description;
    }

    /**
     *
     * @param description String
     */
    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    /**
     *
     * @return String image
     */
    public @NotNull String getImage() {
        return image;
    }

    /**
     *
     * @param image String, path of the image
     */
    public void setImage(@NotNull String image) {
        this.image = image;
    }

    /**
     *
     * @return int quantity
     */
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public @Nullable Category hasCategory(@NotNull String categoryName) {
        return this.categories.get(categoryName);
    }

    public @Nullable Tag hasTag(@NotNull String tagName) {
        return this.tags.get(tagName);
    }

    public void addCategory(@NotNull Category c) {
        this.categories.put(c.getName(), c);
    }

    public @Nullable Category removeCategory(@NotNull Category c) {
        Category toRemove = this.categories.get(c.getName());
        if (toRemove != null) {
            this.categories.remove(c.getName());
        }
        return toRemove;
    }

    public void addTag(@NotNull Tag t) {
        this.tags.put(t.getName(), t);
    }

    public @Nullable Tag removeTag(@NotNull Tag t) {
        Tag toRemove = this.tags.get(t.getName());
        if (toRemove != null) {
            this.tags.remove(t.getName());
        }
        return toRemove;
    }

    public @NotNull Collection<Category> getCategories() {
        return categories.values();
    }

    public void setCategories(Collection<Category> categories) {
        //inseriamo le categorie
        this.categories = new HashMap<>();
        for (Category c : categories) {
            this.categories.put(c.getName(), c);
        }
    }

    @NotNull
    public Collection<Tag> getTags() {
        return tags.values();
    }

    public void setTags(Collection<Tag> tags) {
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
