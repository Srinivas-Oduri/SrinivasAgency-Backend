package com.srinivasagency.platocartai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "carts")
public class Cart {

    @Id
    private String id;
    private String userId;
    private List<String> productIds;
    private List<Double> prices;  // Added prices list to store price for each product
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

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public boolean isBulkProduct() {
        return isBulkProduct;
    }

    public void setBulkProduct(boolean bulkProduct) {
        isBulkProduct = bulkProduct;
    }
}
