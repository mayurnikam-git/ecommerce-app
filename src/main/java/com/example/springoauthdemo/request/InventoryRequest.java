package com.example.springoauthdemo.request;

import com.example.springoauthdemo.domain.ProductDetail;

import java.util.List;

public class InventoryRequest {

    private List<ProductDetail> productDetailList;

    public List<ProductDetail> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<ProductDetail> productDetailList) {
        this.productDetailList = productDetailList;
    }
}
