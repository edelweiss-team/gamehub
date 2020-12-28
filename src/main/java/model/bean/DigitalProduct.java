package model.bean;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
/**
 * This class represents a digital product is a specialization of Product.
 * A digital product has more information than a simple product.
 */

public class DigitalProduct extends Product {

    /**
     * Constructs a new DigitalProduct with empty fields.
     */
    public DigitalProduct() {
        this.platform = "";
        this.publisher = "";
        this.releaseDate = "";
        this.softwareHouse = "";
    }

    /**
     * Constructs a new DigitalProduct starting from the product standard attributes,
     * plus the platform, releaseDate, requiredAge, softwareHouse, publisher attributes.
     * Parameters must be not null.
     *
     * @param id the product's id.
     * @param name the product's name.
     * @param price the product's price.
     * @param description the product's descrption.
     * @param image a String indicating the path of the product's image.
     * @param categories a list indicating the categories to which the product belongs.
     * @param tags a list indicating the tags to which the product belongs.
     * @param quantity the product's stock quantity.
     * @param platform the product's platform.
     * @param releaseDate the product's releaseDate.
     * @param requiredAge the product's required age.
     * @param softwareHouse the product's softwareHouse.
     * @param publisher the product's publisher.
     */
    public DigitalProduct(int id, @NotNull String name, double price, @NotNull String description,
                          @NotNull String image, @NotNull Collection<Category> categories,
                          @NotNull Collection<Tag> tags, int quantity,
                          @NotNull String platform, @NotNull String releaseDate, int requiredAge,
                          @NotNull String softwareHouse, @NotNull String publisher) {

        super(id, name, price, description, image, categories, tags, quantity);
        this.platform = platform;
        this.releaseDate = releaseDate;
        this.requiredAge = requiredAge;
        this.softwareHouse = softwareHouse;
        this.publisher = publisher;
    }

    /**
     * Get the product's platform.
     *
     * @return the platform of the product.
     */
    public @NotNull String getPlatform() {
        return platform;
    }

    /**
     * Set a new platform for the digitalProduct.
     *
     * @param platform the new product's platform, must be not null.
     */
    public void setPlatform(@NotNull String platform) {
        this.platform = platform;
    }

    /**
     * Get the product's release date.
     *
     * @return the release date of the product.
     */
    public @NotNull String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Set a new releaseDate for the digitalProduct.
     *
     * @param releaseDate the new product's releaseDate, must be not null.
     */
    public void setReleaseDate(@NotNull String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Get the product's requiredAge.
     *
     * @return the requiredAge of the product.
     */
    public int getRequiredAge() {
        return requiredAge;
    }

    /**
     * Set a new required age for the product.
     *
     * @param requiredAge the new product's requiredAge.
     */
    public void setRequiredAge(int requiredAge) {
        this.requiredAge = requiredAge;
    }

    /**
     * Get the product's softwareHouse.
     *
     * @return the softwareHouse of the product.
     */
    public @NotNull String getSoftwareHouse() {
        return softwareHouse;
    }

    /**
     * Set a new software house for the product.
     *
     * @param softwareHouse the new product's software house, must be not null.
     */
    public void setSoftwareHouse(@NotNull String softwareHouse) {
        this.softwareHouse = softwareHouse;
    }

    /**
     * Get the product's publisher.
     *
     * @return the publisher of the digital product.
     */
    public @NotNull String getPublisher() {
        return publisher;
    }

    /**
     * Set a new publisher for the product.
     *
     * @param publisher the new digital product's publisher, must be not null.
     */
    public void setPublisher(@NotNull String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return super.toString()
                + " platform='" + platform + '\''
                + ", releaseDate='" + releaseDate + '\''
                + ", requiredAge=" + requiredAge
                + ", softwareHouse='" + softwareHouse + '\''
                + ", publisher='" + publisher + '\''
                + '}';
    }

    @NotNull
    private String platform;
    @NotNull
    private String releaseDate;
    private int requiredAge;
    @NotNull
    private String softwareHouse;
    @NotNull
    private String publisher;
}
