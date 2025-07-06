package com.srinivasagency.platocartai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String bulkImageUrl;
    private String size;
    private String from;
    private String material;
    private double bulkProductPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBulkImageUrl() {
        return bulkImageUrl;
    }

    public void setBulkImageUrl(String bulkImageUrl) {
        this.bulkImageUrl = bulkImageUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getBulkProductPrice() {
        return bulkProductPrice;
    }

    public void setBulkProductPrice(double bulkProductPrice) {
        this.bulkProductPrice = bulkProductPrice;
    }
}
