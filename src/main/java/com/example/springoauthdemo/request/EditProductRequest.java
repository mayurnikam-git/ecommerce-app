package com.example.springoauthdemo.request;

import java.math.BigDecimal;

public class EditProductRequest {

    private Integer productId;

    private String productName;

    private BigDecimal productPrice;

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
        if(productName != null) {
            this.productName = productName;
        }
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        if(productPrice != null) {
            this.productPrice = productPrice;
        }
    }
}
