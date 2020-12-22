package model.bean;

import java.util.Objects;

public class Category {

    public Category(){

    }

    public Category(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
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

    private String name;
    private String description;
    private String image;
}
