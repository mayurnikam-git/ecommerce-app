package com.example.springoauthdemo.response;

import java.math.BigDecimal;

public class ProductResponse extends ServiceResponse {

    private Integer productId;

    private String productName;

    private BigDecimal productPrice;

    private boolean inStock;

    private Integer inStockQuantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Integer getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(Integer inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }
}
