package com.example.springoauthdemo.dao;

import com.example.springoauthdemo.constant.Constant;
import com.example.springoauthdemo.domain.BuyOrderDetail;
import com.example.springoauthdemo.domain.CartDetail;
import com.example.springoauthdemo.domain.ProductDetail;
import com.example.springoauthdemo.entity.Product;
import com.example.springoauthdemo.exception.ExceptionHandler;
import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.entity.Cart;
import com.example.springoauthdemo.response.BuyOrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Override
    public <S extends Cart> S save(S s);

    @Transactional
    public boolean deleteByUserId(Integer integer);

    public default List<CartDetail> getCartDeatilsForUser(Integer userId) throws ProductException {
        List<CartDetail> cartDetailList = null;
        try {
            List<Cart> cartDetails = findByUserId(userId);
            System.out.print("queryyyyyyyyyyy ********************* : " + cartDetails);
            if (cartDetails != null && !cartDetails.isEmpty()) {
                //transform database response object to response object
                cartDetailList   = new ArrayList<>();
                cartDetailList = cartDetails.stream().map(cart -> transformCartToCartDetail(cart)).collect(Collectors.toList());
            } else {
                throw new ProductException(Constant.EMPTY_RECORDS_CODE, Constant.EMPTY_CODE_DESCRIPTION);
            }
        }catch (ProductException productException){
            throw productException;
        }catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE, Constant.ERROR_CODE_DESCRIPTION);
        }
        return cartDetailList;
    }

    public default CartDetail transformCartToCartDetail(Cart cart){
        CartDetail cartDetail = new CartDetail();
        cartDetail.setProductId(cart.getProductId());
        cartDetail.setProductName(cart.getProductName());
        cartDetail.setQuantity(cart.getQuantity());
        return cartDetail;
    }

    @Query("select c from Cart c where c.userId=:userId")
    public List<Cart> findByUserId(@Param("userId") Integer userId);

    public Cart findByUserIdAndProductId(Integer userId, Integer productId);

    @Transactional(rollbackFor= Exception.class)
    public Integer deleteByProductId(Integer productId);

    /*public default BuyOrderResponse buyProduct(Integer userId) throws ProductException {
        BuyOrderResponse buyOrderResponse = null;
        BuyOrderDetail buyOrderDetail = null;
        List<BuyOrderDetail> buyOrderDetailList = new ArrayList<>();
        BigDecimal total = null;
        try {
        }catch (ProductException productException){
            throw productException;
        }catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE, Constant.ERROR_CODE_DESCRIPTION);
        }
        return buyOrderResponse;
    }*/
}
