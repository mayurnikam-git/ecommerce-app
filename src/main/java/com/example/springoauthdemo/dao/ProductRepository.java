package com.example.springoauthdemo.dao;

import com.example.springoauthdemo.constant.Constant;
import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.entity.Product;
import com.example.springoauthdemo.request.EditProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> , CrudRepository<Product, Integer> {

    public default void updateProduct(EditProductRequest productDetailsRequest) throws ProductException {
        Product updatedProductDetails = null;
        try {
            //prepare entity object for updateing product details.
            Optional<Product> exisitngProductDeatils = findById(productDetailsRequest.getProductId());
            if (exisitngProductDeatils.isPresent()) {
                updatedProductDetails = exisitngProductDeatils.get();
                //need to check for not null details before updating.
                updatedProductDetails.setProductName(productDetailsRequest.getProductName());
                updatedProductDetails.setProductPrice(productDetailsRequest.getProductPrice());
            } else {
                throw new ProductException(Constant.EMPTY_RECORDS_CODE, Constant.ERROR_CODE_DESCRIPTION);
            }
            saveAndFlush(updatedProductDetails);
        }catch (ProductException productException){
            throw  productException;
        }catch (Exception exception){
            throw  new ProductException(Constant.ERROR_CODE , "error while updating the product details");
        }
    }

    public List<Product> findByProductIdIn(List<Integer> productIdList);

    public default void removeProduct(int productId) throws ProductException {
        Product updatedProductDetails = null;
        try {
            //prepare entity object for updateing product details.
            Optional<Product> exisitngProductDeatils = findById(productId);
            if (exisitngProductDeatils.isPresent()) {
                updatedProductDetails = exisitngProductDeatils.get();
                //need to check for not null details before updating.
                updatedProductDetails.setActive(Boolean.FALSE);
            } else {
                throw new ProductException(Constant.EMPTY_RECORDS_CODE, Constant.ERROR_CODE_DESCRIPTION);
            }
            saveAndFlush(updatedProductDetails);
        }catch (ProductException productException){
            throw  productException;
        }catch (Exception exception){
            throw  new ProductException(Constant.ERROR_CODE , "error while updating the product details");
        }
    }

}
