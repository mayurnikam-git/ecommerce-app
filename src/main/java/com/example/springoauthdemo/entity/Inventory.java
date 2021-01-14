package com.example.springoauthdemo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Inventory {

    /**
     * create table inventory(
     * id int primary key,
     *  int unique not null,
     * filled_on timestamp not null,
     * quantity int not null,
     * sold_units int not null default 0,
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "filled_on")
    private Timestamp filledOn;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sold_units")
    private Integer soldUnits;

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

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
