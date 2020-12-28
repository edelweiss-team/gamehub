package model.bean;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public class PhysicalProduct extends Product {
    public PhysicalProduct(int id, @NotNull String name, double price, @NotNull String description,
                           @NotNull String image, @NotNull Collection<Category> categories,
                           @NotNull Collection<Tag> tags, int quantity, @NotNull String size,
                           double weight) {
        super(id, name, price, description, image, categories, tags, quantity);
        this.size = size;
        this.weight = weight;
    }

    public PhysicalProduct() {
        this.size = "0x0x0";
        this.weight = 0;
    }

    public @NotNull String getSize() {
        return size;
    }

    public void setSize(@NotNull String size) {
        this.size = size;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return super.toString()
                + " size='" + size + '\''
                + ", weight=" + weight
                + '}';
    }

    @NotNull
    private String size;
    private double weight;

}
