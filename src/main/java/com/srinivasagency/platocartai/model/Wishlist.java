package com.srinivasagency.platocartai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String id;
    private String userId;
    private String productId;
    private Double price;  // Added price field
    private boolean isBulkProduct; // Add this field

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isBulkProduct() {
        return isBulkProduct;
    }

    public void setBulkProduct(boolean bulkProduct) {
        isBulkProduct = bulkProduct;
    }
}
