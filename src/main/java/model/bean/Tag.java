package model.bean;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Tag {

    public Tag() {
        this.name = "";
    }

    /**
     *
     * @param name String
     */
    public Tag(@NotNull String name) {
        this.name = name;
    }

    /**
     * @param o Object
     * @return true iff
     */
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

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" + "name='" + name + '\'' + '}';
    }

    private @NotNull String name;
}
