package model.bean;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a Tag used to add information to a product ,
 * a product can have multiple tags.
 */
public class Tag {

    /**
     * Constructs a new Tag with empty fields.
     */
    public Tag() {
        this.name = "";
    }

    /**
     *Constructs a new Tag starting from his name.
     *
     * @param name the category's name, must be not null.
     */
    public Tag(@NotNull String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Get Tag's name.
     *
     * @return a String indicating the name of the tag
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Set the new tag name.
     *
     * @param name the new name of the Tag, must be not null.
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" + "name='" + name + '\'' + '}';
    }

    private @NotNull String name;
}
