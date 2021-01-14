package com.example.springoauthdemo.service;

import com.example.springoauthdemo.dao.PurchaseRepository;
import com.example.springoauthdemo.domain.BuyOrderDetail;
import com.example.springoauthdemo.entity.Purchase;
import com.example.springoauthdemo.response.BuyOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public boolean placeBuyOrder(BuyOrderResponse buyOrderResponse){

        List<Purchase> purchaseList = null;

        purchaseList =  buyOrderResponse.getBuyOrderDetailList().stream().map(buyOrderDetail -> preparePurchaseOrder(buyOrderDetail)).collect(Collectors.toList());
        purchaseRepository.saveAll(purchaseList);
        return true;
    }

    private Purchase preparePurchaseOrder(BuyOrderDetail buyOrderDetail) {
        Purchase purchaseOrder = new Purchase();
        purchaseOrder.setProductId(buyOrderDetail.getProductId());
        purchaseOrder.setPrice(buyOrderDetail.getPrice());
        purchaseOrder.setQuantity(buyOrderDetail.getQuantity());
        purchaseOrder.setStatus(1);
        purchaseOrder.setUserId(1); //hardcoded
        purchaseOrder.setProductName(buyOrderDetail.getProductName());
        //purchaseOrder.setOrderDate();
        //purchaseOrder.setDispatchDate();
        return purchaseOrder;
    }


}
