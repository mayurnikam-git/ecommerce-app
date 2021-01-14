package com.example.springoauthdemo.controller;

import com.example.springoauthdemo.constant.Constant;
import com.example.springoauthdemo.exception.ProductException;
import com.example.springoauthdemo.response.ServiceResponse;

public class BaseController {

    protected ServiceResponse setSuccessResponse(ServiceResponse serviceResponse){
        serviceResponse.setResponseCode(Constant.SUCCESS_CODE);
        serviceResponse.setResponseDescription(Constant.SUCCESS_DESCRIPTION);
        return serviceResponse;
    }

    protected ServiceResponse setExceptionResponse(ServiceResponse serviceResponse, ProductException exception){
        serviceResponse.setResponseCode(exception.getResponseCode());
        serviceResponse.setResponseDescription(exception.getResponseDescription());
        return serviceResponse;
    }

    protected ServiceResponse setExceptionResponse(ServiceResponse serviceResponse, Exception exception){
        serviceResponse.setResponseCode(Constant.ERROR_CODE);
        serviceResponse.setResponseDescription(Constant.ERROR_CODE_DESCRIPTION);
        return serviceResponse;
    }
}
