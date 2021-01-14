package com.example.springoauthdemo.controller;

import com.example.springoauthdemo.constant.Constant;
import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.UserPrincipal;
import com.example.springoauthdemo.response.BuyOrderResponse;
import com.example.springoauthdemo.response.ProductDetailResponse;
import com.example.springoauthdemo.response.ProductResponse;
import com.example.springoauthdemo.service.CartService;
import com.example.springoauthdemo.service.ProductDetailServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class HomeController extends BaseController{

    public static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    ProductDetailServiceImp productDetailServiceImp;

    @Autowired
    CartService cartServiceImpl;

    /**
     * The api is used to display all the product.
     *
     * @param model
     * @return view
     */
    @GetMapping("/")
    public String viewProducts(Model model){
        logger.debug("START-controller : view products");
        ProductDetailResponse productDetailResponse = null;
        try {
            //System.out.print("START-controller : get products");
            productDetailResponse = productDetailServiceImp.getAllProducts();
            setSuccessResponse(productDetailResponse);
            //System.out.print("END-controller : get products");
        } catch (ProductException productException){
            productDetailResponse = new ProductDetailResponse();
            setExceptionResponse(productDetailResponse, productException);
        } catch (Exception exception){
            productDetailResponse = new ProductDetailResponse();
            productDetailResponse.setResponseCode(Constant.ERROR_CODE);
            productDetailResponse.setResponseDescription(exception.getMessage());
        }
        model.addAttribute("products",productDetailResponse);
        logger.debug("END-controller : view products");
        return "view_product";
    }

    /**
     * The api is used to display the details of a specific product.
     *
     * @param productId
     * @param model
     * @return view
     */
    @RequestMapping("/{productId}")
    public String viewProductDetail(@PathVariable Integer productId, Model model){
        logger.info("Start : viewProductDetail api");
        ProductResponse response = null;
        try{
            response = productDetailServiceImp.getProductDetails(productId);
            setSuccessResponse(response);
        } catch (ProductException productException){
            response = new ProductResponse();
            setExceptionResponse(response, productException);
        } catch (Exception exception){
            response = new ProductResponse();
            setExceptionResponse(response, exception);
        }
        logger.info("End : viewProductDetail api");
        model.addAttribute("product", response);
        return "view_product_details";
    }

    /**
     * The api returns the product details.
     *
     * @param productId
     * @param model
     * @return response
     */
    @RequestMapping("/detail/{productId}")
    @ResponseBody
    public ProductResponse viewProductDetailResponse(@PathVariable Integer productId, Model model){
        logger.info("Start : viewProductDetail api");
        ProductResponse response = null;
        try{
            response = productDetailServiceImp.getProductDetails(productId);
            setSuccessResponse(response);
        } catch (ProductException productException){
            response = new ProductResponse();
            setExceptionResponse(response, productException);
        } catch (Exception exception){
            response = new ProductResponse();
            setExceptionResponse(response, exception);
        }
        logger.info("End : viewProductDetail api");
        return response;
    }

    /**
     * The api is used to buy the product added to cart.
     *
     * @param principal
     * @param model
     * @return
     */
    @RequestMapping("/buy")
    public String buyProduct(Principal principal, Model model){
        logger.info("Start : buyProduct api");
        BuyOrderResponse buyOrderResponse = null;
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)principal;
            UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
            userPrincipal.getUserId();
            System.out.print("users************************** : " + userPrincipal.getUserId()+ " ****");

            buyOrderResponse = cartServiceImpl.buyProduct(userPrincipal);
            setSuccessResponse(buyOrderResponse);
        } catch (Exception exception){
            buyOrderResponse = new BuyOrderResponse();
            setExceptionResponse(buyOrderResponse, exception);
        }
        logger.info("End : buyProduct api");
        model.addAttribute("buyOrderResponse", buyOrderResponse);
        return "order_details";
    }
}
