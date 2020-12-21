package model.bean;

import java.util.Collection;

public class DigitalProduct extends Product{

    public DigitalProduct(int id, String name, double price, String description, String image,
                          Collection<Category> categories, Collection<Tag> tags, int quantity,
                          String platform, String releaseDate, int requiredAge, String softwareHouse, String publisher){

        super(id, name, price, description, image, categories, tags, quantity);
        this.platform = platform;
        this.releaseDate = releaseDate;
        this.requiredAge = requiredAge;
        this.softwareHouse = softwareHouse;
        this.publisher = publisher;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRequiredAge() {
        return requiredAge;
    }

    public void setRequiredAge(int requiredAge) {
        this.requiredAge = requiredAge;
    }

    public String getSoftwareHouse() {
        return softwareHouse;
    }

    public void setSoftwareHouse(String softwareHouse) {
        this.softwareHouse = softwareHouse;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    private String platform;
    private String releaseDate;
    private int requiredAge;
    private String softwareHouse;
    private String publisher;
}
