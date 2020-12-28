package model.bean;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
/**
 * This class represents a Category used to divide products ,
 * a product can have multiple categories.
 */
public class Category {

    /**
     * Constructs a new Admin with empty fields.
     */
    public Category() {
        this.name = this.description = this.image = "";
    }

    /**
     * Constructs a new category strating from its name, descrption and image.
     *
     * @param name the category's name, must be not null.
     * @param description the category's descrption, must be not null.
     * @param image a String indicating the path of the category's image, must be not null.
     */
    public Category(@NotNull String name, @NotNull String description, @NotNull String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    /**
     * Get the name of the category.
     *
     * @return the name of the category.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Set a new name for the category.
     *
     * @param name the new category's name, must be not null.
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     * Get the description of the category
     *
     * @return a String that is a description of the category.
     */
    public @NotNull String getDescription() {
        return description;
    }

    /**
     * Set a new description for the category.
     *
     * @param description the new category's description, must be not null.
     */
    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    /**
     * Get a String indicating the path of the category's image.
     *
     * @return String indicating the path of the category's image.
     */
    public @NotNull String getImage() {
        return image;
    }

    /**
     * Set a new image for the category.
     *
     * @param image a String indicating the path of the category's image, must be not null.
     */
    public void setImage(@NotNull String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Category{"
                + "name='" + name + '\''
                + ", description='" + description + '\''
                + ", image='" + image + '\''
                + '}';
    }

    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String image;
}
