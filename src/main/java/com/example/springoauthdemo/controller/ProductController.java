package com.example.springoauthdemo.controller;

import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.request.CreateProductRequest;
import com.example.springoauthdemo.request.EditProductRequest;
import com.example.springoauthdemo.response.ProductDetailResponse;
import com.example.springoauthdemo.response.ProductResponse;
import com.example.springoauthdemo.response.ServiceResponse;
import com.example.springoauthdemo.service.ProductDetailServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductDetailServiceImp productDetailServiceImp;

    @GetMapping(" ")
    public String action(){
        logger.debug("START-controller : admin access main page");
        return "product";
    }

    @GetMapping("/add-form")
    public String addProductForm(){
        logger.debug("START-controller : get add product form");
        return "add_product";
    }

    @PostMapping(value = " " )
    @ResponseBody
    public ProductResponse addProduct(@RequestBody CreateProductRequest productRequest){
        logger.info("START-controller : add product");
        ProductResponse response = null;
        response = productDetailServiceImp.addProduct(productRequest);
        setSuccessResponse(response);
        logger.info("END-controller : add product");
        return response;
    }

    @GetMapping("/edit-form")
    public String editProductForm(Model model) throws ProductException {
        logger.debug("START-controller : edit product page requested");
        ProductDetailResponse productDetailResponse = null;
        //try {
            productDetailResponse = productDetailServiceImp.editProductForm();
            setSuccessResponse(productDetailResponse);
        /*} catch (ProductException productException){
            productDetailResponse = (ProductDetailResponse) setExceptionResponse(productDetailResponse, productException);
        } catch (Exception exception){
            setExceptionResponse(productDetailResponse, exception);
        }*/
        model.addAttribute("products",productDetailResponse);
        return "edit_product";
    }

    @PutMapping(" ")
    @ResponseBody
    public ServiceResponse editProduct(@RequestBody EditProductRequest editProductRequest){
        System.out.print("START-controller : edit product");
        ServiceResponse response = null;
        try {
            response = productDetailServiceImp.editProduct(editProductRequest);
            setSuccessResponse(response);
        }catch (ProductException productException){
            response = new ServiceResponse();
            setExceptionResponse(response, productException);
        }catch (Exception exception){
            response = new ServiceResponse();
            setExceptionResponse(response, exception);
        }
        System.out.print("END-controller : edit product");
        return response;
    }

    @GetMapping("/delete-form")
    public String deleteProductForm(Model model){
        logger.debug("START-controller : delete product form");
        ProductDetailResponse productDetailResponse = null;
        try {
            productDetailResponse = productDetailServiceImp.removeProductForm();
        } catch (ProductException productException){
            productDetailResponse = (ProductDetailResponse) setExceptionResponse(productDetailResponse, productException);
        } catch (Exception exception){
            productDetailResponse = (ProductDetailResponse) setExceptionResponse(productDetailResponse, exception);
        }
        model.addAttribute("products",productDetailResponse);
        return "remove_product";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ServiceResponse removeProduct(@PathVariable Integer id){
        logger.debug("START-controller : remove products");
        ServiceResponse response = null;
        try {
            response = productDetailServiceImp.removeProduct(id);
            setSuccessResponse(response);
        }catch (ProductException productException){
            response = setExceptionResponse(response, productException);
        }catch (Exception exception){
            response = setExceptionResponse(response, exception);
        }
        logger.debug("END-controller : remove products");
        return response;
    }
}
