package model.bean;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

/**
 * This specialization of Product represents a physical product.
 * A physical product has more information than a simple product such as the weight and the size.
 */
public class PhysicalProduct extends Product {

    /**
     * Constructs a new DigitalProduct starting from the product standard attributes,
     * plus the size, weight.
     *
     * @param id the product's id.
     * @param name the product's name.
     * @param price the product's price.
     * @param description the product's description.
     * @param image a String indicating the path of the product's image.
     * @param categories a list indicating the categories to which the product belongs.
     * @param tags a list indicating the tags to which the product belongs.
     * @param quantity the product's stock quantity.
     * @param size the product's size
     * @param weight the product's weight in kg
     */
    public PhysicalProduct(int id, @NotNull String name, double price, @NotNull String description,
                           @NotNull String image, @NotNull Collection<Category> categories,
                           @NotNull Collection<Tag> tags, int quantity, @NotNull String size,
                           double weight) {
        super(id, name, price, description, image, categories, tags, quantity);
        this.size = size;
        this.weight = weight;
    }

    /**
     * Constructs a new DigitalProduct with empty fields.
     */
    public PhysicalProduct() {
        this.size = "0x0x0";
        this.weight = 0;
    }

    /**
     * Gets the product's size.
     *
     * @return a String indicating the product's size.
     */
    public @NotNull String getSize() {
        return size;
    }

    /**
     * Sets the new size of the product.
     *
     * @param size a String indicating the new size of the product, must be not null.
     */
    public void setSize(@NotNull String size) {
        this.size = size;
    }

    /**
     * Gets the product's weight.
     *
     * @return the product's weight.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the new weight of the product.
     *
     * @param weight the new weight of the product
     */
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
