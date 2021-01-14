package com.example.springoauthdemo.response;

import com.example.springoauthdemo.domain.BuyOrderDetail;

import java.math.BigDecimal;
import java.util.List;

public class BuyOrderResponse extends ServiceResponse{

    private BigDecimal total;

    private List<BuyOrderDetail> buyOrderDetailList;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<BuyOrderDetail> getBuyOrderDetailList() {
        return buyOrderDetailList;
    }

    public void setBuyOrderDetailList(List<BuyOrderDetail> buyOrderDetailList) {
        this.buyOrderDetailList = buyOrderDetailList;
    }
}

