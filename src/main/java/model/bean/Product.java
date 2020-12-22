package model.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public abstract class Product {

    public Product(){

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
    public Product(int id, String name, double price, String description, String image,
                   Collection<Category> categories, Collection<Tag> tags, int quantity) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public Category hasCategory(String categoryName) {
        return this.categories.get(categoryName);
    }

    public Tag hasTag(String tagName) {
        return this.tags.get(tagName);
    }

    public void addCategory(Category c) {
        this.categories.put(c.getName(), c);
    }

    public Category removeCategory(Category c) {
        Category toRemove = this.categories.get(c.getName());
        if (toRemove != null) {
            this.categories.remove(c.getName());
        }
        return toRemove;
    }

    public void addTag(Tag t) {
        this.tags.put(t.getName(), t);
    }

    public Tag removeTag(Tag t) {
        Tag toRemove = this.tags.get(t.getName());
        if (toRemove != null) {
            this.tags.remove(t.getName());
        }
        return toRemove;
    }

    public Collection<Category> getCategories() {
        return categories.values();
    }

    public void setCategories(Collection<Category> categories) {
        //inseriamo le categorie
        this.categories = new HashMap<>();
        for (Category c : categories) {
            this.categories.put(c.getName(), c);
        }
    }

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
    private String name;
    private String description;
    private String image;
    private int quantity;
    private double price;
    private HashMap<String, Category> categories;
    private HashMap<String, Tag> tags;
}
