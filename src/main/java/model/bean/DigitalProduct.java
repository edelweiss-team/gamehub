package model.bean;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public class DigitalProduct extends Product {

    public DigitalProduct() {
        this.platform = "";
        this.publisher = "";
        this.releaseDate = "";
        this.softwareHouse = "";
    }

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

    public @NotNull String getPlatform() {
        return platform;
    }

    public void setPlatform(@NotNull String platform) {
        this.platform = platform;
    }

    public @NotNull String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(@NotNull String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRequiredAge() {
        return requiredAge;
    }

    public void setRequiredAge(int requiredAge) {
        this.requiredAge = requiredAge;
    }

    public @NotNull String getSoftwareHouse() {
        return softwareHouse;
    }

    public void setSoftwareHouse(@NotNull String softwareHouse) {
        this.softwareHouse = softwareHouse;
    }

    public @NotNull String getPublisher() {
        return publisher;
    }

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
