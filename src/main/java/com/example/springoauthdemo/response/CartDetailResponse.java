package com.example.springoauthdemo.response;

import com.example.springoauthdemo.domain.CartDetail;

import java.util.List;

public class CartDetailResponse extends ServiceResponse {

    private List<CartDetail> cartDetails;

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
