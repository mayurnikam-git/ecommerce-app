package com.example.springoauthdemo.service;

import com.example.springoauthdemo.constant.Constant;
import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.dao.ProductRepository;
import com.example.springoauthdemo.domain.ProductDetail;
import com.example.springoauthdemo.entity.Product;
import com.example.springoauthdemo.request.CreateProductRequest;
import com.example.springoauthdemo.request.EditProductRequest;
import com.example.springoauthdemo.response.InventoryDetailResponse;
import com.example.springoauthdemo.response.ProductDetailResponse;
import com.example.springoauthdemo.response.ProductResponse;
import com.example.springoauthdemo.response.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImp {

    public static final Logger logger = LoggerFactory.getLogger(ProductDetailServiceImp.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryService inventoryService;

    /**
     * This method is used to add a product
     *
     * @param productDetailsRequest
     */
    public ProductResponse addProduct(CreateProductRequest productDetailsRequest) {
        logger.info("START-service : add product");
        ProductResponse response = null;

        //prepare entity object to add a new product.
        Product productRequest = new Product();
        productRequest.setProductName(productDetailsRequest.getProductName());
        productRequest.setProductPrice(productDetailsRequest.getProductPrice());
        productRequest.setActive(Boolean.TRUE);

        //doa call to save the new product.
        Product productResponse = productRepository.save(productRequest);

        //prepare the response object
        if(productResponse != null){
            response = new ProductResponse();
            response.setProductId(productResponse.getProductId());
            response.setProductName(productResponse.getProductName());
            response.setProductPrice(productResponse.getProductPrice());
        }

        logger.info("END-service : add product");
        return response;
    }


    /**
     * This method is used to edit a product
     *
     * @param productDetailsRequest
     */
    public ServiceResponse editProduct(EditProductRequest productDetailsRequest) throws ProductException {
        System.out.print("START-service : edit product");
        ServiceResponse response = null;
        Product updatedProductDetails = null;
        try {
            //dao call to update product details.
            productRepository.updateProduct(productDetailsRequest);
            response = new ServiceResponse();
        }catch (ProductException productException){
            throw  productException;
        }catch (Exception exception){
            throw  new ProductException(Constant.ERROR_CODE , "error while updating the product details");
        }
        System.out.print("END-service : edit product");
        return response;
    }


    /**
     * This method is used to get all the product .
     *
     * @return ProductDetailResponse
     */
    public ProductDetailResponse getAllProducts() throws ProductException {
        logger.info("START-service : getAllProducts");
        ProductDetailResponse productDetailResponse= null;
        //try {
            //dao call to retrieve all product list
            List<Product> productList = productRepository.findAll();
            if (!(productList == null || productList.isEmpty())) {
                productDetailResponse = new ProductDetailResponse();
                //transform database response object to response object
                List<ProductDetail> productDetailList = new ArrayList<>();
                productDetailList = productList.stream().filter(product -> product.getActive()).map(product -> transformProductToProductDetail(product)).collect(Collectors.toList());

                productDetailResponse.setProductDetailList(productDetailList);
            } else {
                throw new ProductException(Constant.EMPTY_RECORDS_CODE, "no products found");
            }
        /*} catch (ProductException productException){
            throw productException;
        } catch (Exception exception){
            throw new Exception("Exception in getAllProducts service.");
        }*/
        logger.info("END-service : getAllProducts");
        return productDetailResponse;
    }

    public ProductDetail transformProductToProductDetail(Product product){
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductId(product.getProductId());
        productDetail.setProductName(product.getProductName());
        productDetail.setProductPrice(product.getProductPrice());
        return productDetail;
    }

    public ProductResponse getProductDetails(Integer productId) throws Exception{
        ProductResponse productDetailResponse = null;
        InventoryDetailResponse inventoryDetailResponse = null;
        int inStockQuantity = 0;
        try {
            Optional<Product> productDbObject = productRepository.findById(productId);
            if (productDbObject.isPresent()) {
                Product product = productDbObject.get();
                productDetailResponse = transformProductEntityToResponse(product);
                inventoryDetailResponse = inventoryService.getInventoryDetailsByProductId(productId);
                inStockQuantity = inventoryDetailResponse.getQuantity() - inventoryDetailResponse.getSoldUnits();
                productDetailResponse.setInStockQuantity(inStockQuantity);
            } else {
                throw new ProductException(Constant.EMPTY_RECORDS_CODE, Constant.EMPTY_CODE_DESCRIPTION);
            }
        }catch (ProductException productException){
            throw productException;
        }catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE,Constant.ERROR_CODE_DESCRIPTION);
        }
        return productDetailResponse;
    }

    private ProductResponse transformProductEntityToResponse(Product product){
        ProductResponse productDetailResponse = new ProductResponse();
        productDetailResponse.setProductId(product.getProductId());
        productDetailResponse.setProductName(product.getProductName());
        productDetailResponse.setProductPrice(product.getProductPrice());
        return productDetailResponse;
    }

    /**
     * This method is used to remove the product.
     */
    public ProductDetailResponse removeProductForm() throws Exception {
        System.out.print("START-service : remove product ");
        ProductDetailResponse productDetailResponse= null;
        try {
            productDetailResponse = getAllProducts();
        } catch (ProductException productException){
            throw productException;
        } catch (Exception exception){
            throw new Exception("Exception in getAllProducts service.");
        }
        System.out.print("END-service : remove product");
        return productDetailResponse;

    }

    /**
     * This method is used to remove the product.
     * @param productId
     */
    public ServiceResponse removeProduct(Integer productId) throws ProductException {
        System.out.print("START-service : remove product ");
        ServiceResponse response = null;
        try {
            //productRepository.deleteById(productId);
            productRepository.removeProduct(productId);
            response = new ServiceResponse();
        } catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE,"error while deleting product");
        }
        System.out.print("END-service : remove product");
        return response;
    }

    //------------------------------WORKING


    public ProductDetailResponse editProductForm() throws ProductException {
        System.out.print("START-service : edit product form ");
        ProductDetailResponse productDetailResponse= null;
        //try {
            productDetailResponse = getAllProducts();
        /*} catch (ProductException productException){
            throw productException;
        } catch (Exception exception){
            throw new ProductException(Constant.ERROR_CODE,"Exception in edit product form service.");
        }*/
        System.out.print("END-service : edit product form");
        return productDetailResponse;

    }

    public List<Product> getProductByProductIds(List<Integer> productIdList){
        List<Product> productList = productRepository.findByProductIdIn(productIdList);
        return productList;
    }
}
