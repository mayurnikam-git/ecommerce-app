package com.example.springoauthdemo.controller;

import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.UserPrincipal;
import com.example.springoauthdemo.request.AddProductRequest;
import com.example.springoauthdemo.response.CartDetailResponse;
import com.example.springoauthdemo.response.ServiceResponse;
import com.example.springoauthdemo.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{

    public static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    /*
    * This method is used to add a product to the cart before placing the final buy order.
    */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse addToCart(Principal principal, @RequestBody AddProductRequest addProductRequest){
        logger.info("Start : addToCart api");
        ServiceResponse response = null;
        //try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)principal;
            UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
            userPrincipal.getUserId();
            System.out.print("users************************** : " + userPrincipal.getUserId()+ " ****");
            response = cartService.addToCart(userPrincipal, addProductRequest);
            setSuccessResponse(response);
        /*} catch (ProductException productException){
            setExceptionResponse(response, productException);
        } catch (Exception exception){
            setExceptionResponse(response, exception);
        }*/
        logger.info("End : addToCart api");
        return response;
    }

    /*
     * This method is used to remove a product from the cart before placing the final buy order.
     */
    @DeleteMapping(value = "/remove/{productId}")
    @ResponseBody
    public ServiceResponse removeFromCart(Principal principal, @PathVariable Integer productId){
        logger.info("Start : removeFromCart api");
        ServiceResponse response = null;
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)principal;
            UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
            userPrincipal.getUserId();
            System.out.print("users************************** : " + userPrincipal.getUserId()+ " ****");

            cartService.removeFromCart(userPrincipal, productId);
            response=new ServiceResponse();
            setSuccessResponse(response);
        } catch (ProductException productException){
            response = new ServiceResponse();
            setExceptionResponse(response,productException);
        }  catch (Exception exception){
            response = new ServiceResponse();
            setExceptionResponse(response,exception);
        }
        logger.info("End : removeFromCart api");
        return response;
    }

    /*
     * This method is used to view cart.
     * -displays all the product which have been selected to be bought.
     *
     * as a final review before placing the buy and/or alter the order list.
     */
    @RequestMapping(" ")
    public String viewCart(Principal principal, Model model){
        logger.info("Start : viewCart api");
        CartDetailResponse cartDetailResponse = null;
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)principal;
            UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
            userPrincipal.getUserId();
            System.out.print("users************************** : " + userPrincipal.getUserId()+ " ****");

            cartDetailResponse = cartService.viewCart(userPrincipal.getUserId());
            setSuccessResponse(cartDetailResponse);
        } catch (ProductException productException){
            cartDetailResponse = new CartDetailResponse();
            setExceptionResponse(cartDetailResponse, productException);
        } catch (Exception exception){
            cartDetailResponse = new CartDetailResponse();
            setExceptionResponse(cartDetailResponse, exception);
        }
        model.addAttribute("cartDetails", cartDetailResponse);
        logger.info("End : viewCart api");
        return "cart_details";
    }
}
