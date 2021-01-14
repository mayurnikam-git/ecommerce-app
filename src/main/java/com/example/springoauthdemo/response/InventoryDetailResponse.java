package com.example.springoauthdemo.response;

import javax.persistence.Column;
import java.sql.Timestamp;

public class InventoryDetailResponse {

    private Integer productId;

    private Timestamp filledOn;

    private Integer quantity;

    private Integer soldUnits;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Timestamp getFilledOn() {
        return filledOn;
    }

    public void setFilledOn(Timestamp filledOn) {
        this.filledOn = filledOn;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSoldUnits() {
        return soldUnits;
    }

    public void setSoldUnits(Integer soldUnits) {
        this.soldUnits = soldUnits;
    }
}
