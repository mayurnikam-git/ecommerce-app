package com.example.springoauthdemo.service;

import com.example.springoauthdemo.constant.Constant;
import com.example.springoauthdemo.dao.InventoryRepository;
import com.example.springoauthdemo.domain.BuyOrderDetail;
import com.example.springoauthdemo.domain.CartDetail;
import com.example.springoauthdemo.domain.ProductDetail;
import com.example.springoauthdemo.entity.Inventory;
import com.example.springoauthdemo.entity.Product;
import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.request.InventoryRequest;
import com.example.springoauthdemo.response.InventoryDetailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


/**
 * NOTE :
 *
 * 1.need to stop placing order for the product not presnt in inventory
 * 2. disable the product  ??
 *
 * Inventory api ,
 * update inventory api
 *
 */
@Service
public class InventoryService {

    Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Update the inventory for products
     * @param inventoryRequest
     */
    public void updateInventory(InventoryRequest inventoryRequest){
        try {
            List<Integer> productIdList = inventoryRequest.getProductDetailList().stream().map(productDetail -> new Integer(productDetail.getProductId())).collect(Collectors.toList());
            List<Inventory> existingInventoryList = inventoryRepository.findAllByProductIdIn(productIdList);
            int totalSoldUnits;
            for (ProductDetail productDetail : inventoryRequest.getProductDetailList()) {
                totalSoldUnits = 0;
                for (Inventory inventory : existingInventoryList) {
                    if (inventory.getProductId() == productDetail.getProductId()) {
                        inventory.setProductId(productDetail.getProductId());
                        totalSoldUnits = inventory.getSoldUnits() + productDetail.getQuantity();
                        inventory.setSoldUnits(totalSoldUnits);
                        break;
                    }
                }
            }
        inventoryRepository.saveAll(existingInventoryList);
        }catch (Exception exception){

        }
    }

    /**
     * NOTE : product not present in inventory ,then ??
     * -currently returning default set of values, assuming if the product is present its inventory is yet to be updated.
     *
     * The method is used to retrieve the product details from inventory.
     *
     * @param productId
     */
    public InventoryDetailResponse getInventoryDetailsByProductId(Integer productId) throws ProductException {
        InventoryDetailResponse inventoryDetailResponse = null;
        try {
            Inventory productInventoryDetail = inventoryRepository.findByProductId(productId);
            if(productInventoryDetail != null){
                inventoryDetailResponse = transformInventoryEntityToRespone(productInventoryDetail);
            }else{
                inventoryDetailResponse  = new InventoryDetailResponse();
                inventoryDetailResponse.setQuantity(0);
                inventoryDetailResponse.setSoldUnits(0);
                inventoryDetailResponse.setProductId(productId);
                //throw new ProductException(Constant.EMPTY_RECORDS_CODE, Constant.EMPTY_CODE_DESCRIPTION);
            }
        }catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE, Constant.ERROR_CODE_DESCRIPTION);
        }
        return inventoryDetailResponse;
    }

    /**
     * transforms the inventory entity into inventory detail response.
     *
     * @param productInventoryDetail
     * @return
     */
    private InventoryDetailResponse transformInventoryEntityToRespone(Inventory productInventoryDetail) {
        InventoryDetailResponse inventoryDetailResponse = new InventoryDetailResponse();
        inventoryDetailResponse.setProductId(productInventoryDetail.getProductId());
        inventoryDetailResponse.setFilledOn(productInventoryDetail.getFilledOn());
        inventoryDetailResponse.setSoldUnits(productInventoryDetail.getSoldUnits());
        inventoryDetailResponse.setQuantity(productInventoryDetail.getQuantity());
        return inventoryDetailResponse;
    }

}
