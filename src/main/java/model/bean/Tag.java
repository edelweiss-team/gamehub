package model.bean;

import java.util.Objects;

/**
 *
 */
public class Tag {

    public Tag(){

    }

    /**
     * @param name String
     */
    public Tag(String name){
        this.name = name;
    }

    /**
     *
     * @param o
     * @return true iff
     */
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if (!(o instanceof Tag))
            return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" + "name='" + name + '\'' + '}';
    }

    private String name;
}
