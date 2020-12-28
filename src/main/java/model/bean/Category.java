package model.bean;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Category {

    public Category(){

    }

    public Category(@NotNull String name, @NotNull String description, @NotNull String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public @Nullable String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public @Nullable String getImage() {
        return image;
    }

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

    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private String image;
}
