package com.example.springoauthdemo.service;

import com.example.springoauthdemo.UserPrincipal;
import com.example.springoauthdemo.constant.Constant;
import com.example.springoauthdemo.dao.CartRepository;
import com.example.springoauthdemo.domain.BuyOrderDetail;
import com.example.springoauthdemo.domain.CartDetail;
import com.example.springoauthdemo.domain.ProductDetail;
import com.example.springoauthdemo.entity.Cart;
import com.example.springoauthdemo.entity.Inventory;
import com.example.springoauthdemo.entity.Product;
import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.request.AddProductRequest;
import com.example.springoauthdemo.request.InventoryRequest;
import com.example.springoauthdemo.response.BuyOrderResponse;
import com.example.springoauthdemo.response.CartDetailResponse;
import com.example.springoauthdemo.response.ProductDetailResponse;
import com.example.springoauthdemo.response.ServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    ProductDetailServiceImp productDetailServiceImp;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private InventoryService inventoryService;


    public ServiceResponse addToCart(UserPrincipal principal, AddProductRequest addProductRequest) {
        logger.info("Start : addToCart service");
        ServiceResponse response = null;
        Cart savedCartDetails = null;
        //try {
        //Entity
            Cart existingProductInCart = cartRepository.findByUserIdAndProductId(principal.getUserId(),addProductRequest.getProductId());

            if(existingProductInCart != null && existingProductInCart.getId() != null){
                Integer quantity = existingProductInCart.getQuantity() + addProductRequest.getQuantity();
                existingProductInCart.setQuantity(quantity);
                savedCartDetails = cartRepository.save(existingProductInCart);
            }else {

                Cart newProductToBeAddedInCart = new Cart();
                newProductToBeAddedInCart.setUserId(principal.getUserId());
                newProductToBeAddedInCart.setProductId(addProductRequest.getProductId());
                newProductToBeAddedInCart.setQuantity(addProductRequest.getQuantity());
                newProductToBeAddedInCart.setProductName(addProductRequest.getProductName());
                savedCartDetails = cartRepository.save(newProductToBeAddedInCart);
            }
            if(savedCartDetails != null && savedCartDetails.getId() !=null){
                response = new ServiceResponse();
            }
        /*} catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE, "failed to add prodcut to cart");
        }*/
        logger.info("End : addToCart service");
        return response;
    }

    public Integer removeFromCart(UserPrincipal userPrincipal, Integer productId) throws ProductException {
        logger.info("Start : removeFromCart service");
        int productRemovedFromCart = 0 ;
        try {
            if(productId != null) {
                productRemovedFromCart = cartRepository.deleteByProductId(productId);
            }
            if(productRemovedFromCart == 0){
                throw new ProductException(Constant.EMPTY_RECORDS_CODE,Constant.EMPTY_CODE_DESCRIPTION);
            }
        } catch (ProductException productException){
            throw productException;
        } catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE, Constant.ERROR_CODE_DESCRIPTION);
        }
        logger.info("End : removeFromCart service");
        return productRemovedFromCart;
    }

    public CartDetailResponse viewCart(Integer userId) throws ProductException {
        logger.info("Start : viewCart service");
        CartDetailResponse cartDetailResponse = null;
        List<CartDetail> cartDetailList = null;
        try {
            cartDetailList = cartRepository.getCartDeatilsForUser(userId);
            if (cartDetailList != null) {
                cartDetailResponse = new CartDetailResponse();
                cartDetailResponse.setCartDetails(cartDetailList);
            }
        }catch (ProductException productException){
            throw productException;
        }catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE,Constant.ERROR_CODE_DESCRIPTION);
        }
        logger.info("End : viewCart service");
        return cartDetailResponse;
    }

    /**
     * NOTE : TRANSACTION needs to be performed for different alter operation
     *
     * @param userPrincipal
     * @return
     */
    public BuyOrderResponse buyProduct(UserPrincipal userPrincipal){
        BuyOrderResponse buyOrderResponse = new BuyOrderResponse();
        BuyOrderDetail buyOrderDetail = null;
        List<BuyOrderDetail> buyOrderDetailList = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        try{
            List<CartDetail> userCartDetails = cartRepository.getCartDeatilsForUser(userPrincipal.getUserId());
            List<Integer> productIdList = userCartDetails.stream().map(userCartDetail->new Integer(userCartDetail.getProductId())).collect(Collectors.toList());

            List<Product> productDetailList = productDetailServiceImp.getProductByProductIds(productIdList);

            for(CartDetail cartDetail : userCartDetails){
                for(Product product : productDetailList){
                    if(cartDetail.getProductId() == product.getProductId()){
                        buyOrderDetail = new BuyOrderDetail();
                        buyOrderDetail.setProductId(product.getProductId());
                        buyOrderDetail.setProductName(product.getProductName());
                        buyOrderDetail.setPrice(product.getProductPrice());
                        buyOrderDetail.setQuantity(cartDetail.getQuantity());
                        buyOrderDetail.setSum(product.getProductPrice().multiply(new BigDecimal(cartDetail.getQuantity())));
                        total = total.add(buyOrderDetail.getSum());
                        buyOrderDetailList.add(buyOrderDetail);
                        break;
                    }
                }
            }
            buyOrderResponse.setTotal(total);
            buyOrderResponse.setBuyOrderDetailList(buyOrderDetailList);


            //** TRANSACTION : START
            //add to placed order table
            boolean orderConfirmed = purchaseService.placeBuyOrder(buyOrderResponse);

            InventoryRequest updateInventoryRequest = new InventoryRequest();

            List<ProductDetail> updateInvertoryProductDetail = buyOrderResponse.getBuyOrderDetailList().stream().map(buyOrderDetail1 -> prepareUpdateInventoryRequest(buyOrderDetail1)).collect(Collectors.toList());
            updateInventoryRequest.setProductDetailList(updateInvertoryProductDetail);
            //update the inventory
            inventoryService.updateInventory(updateInventoryRequest);
            //remove from cart
            cartRepository.deleteByUserId(userPrincipal.getUserId());
// TRANSACTION : END


//            cartRepository.buyProduct(userPrincipal.getUserId());
        }catch (Exception exception){
            logger.error(String.valueOf(exception));
        }
        return buyOrderResponse;
    }

    //change var name "buyOrderDetail1"
    private ProductDetail prepareUpdateInventoryRequest(BuyOrderDetail buyOrderDetail1) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductId(buyOrderDetail1.getProductId());
        productDetail.setQuantity(buyOrderDetail1.getQuantity());
        return productDetail;
    }

}
