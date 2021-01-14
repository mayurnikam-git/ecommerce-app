package com.example.springoauthdemo.response;

import com.example.springoauthdemo.domain.ProductDetail;

import java.util.List;

public class ProductDetailResponse extends ServiceResponse {

    private List<ProductDetail> productDetailList;

    public List<ProductDetail> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<ProductDetail> productDetailList) {
        this.productDetailList = productDetailList;
    }

    @Override
    public String toString() {
        return "ProductDetailResponse{" +
                "productDetailList=" + productDetailList +
                '}';
    }
}
