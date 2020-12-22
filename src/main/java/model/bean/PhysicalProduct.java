package model.bean;

import java.util.Collection;

public class PhysicalProduct extends Product {
    public PhysicalProduct(int id, String name, double price, String description, String image,
                           Collection<Category> categories, Collection<Tag> tags, int quantity,
                           String size, double weight) {
        super(id, name, price, description, image, categories, tags, quantity);
        this.size = size;
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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

    private String size;
    private double weight;

}
