package com.example.springoauthdemo.exception;

import com.example.springoauthdemo.constant.Constant;
import com.example.springoauthdemo.response.ServiceResponse;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ServiceResponse> exception(DataIntegrityViolationException exception){
        Throwable rootException = NestedExceptionUtils.getRootCause(exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse(Constant.ERROR_DUPLICATE_RESOURCE,rootException.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.OK);
    }

   /* @org.springframework.web.bind.annotation.ExceptionHandler(ProductException.class)
    protected ModelAndView handleUserException(ProductException exception){
        ModelAndView modelAndView = new ModelAndView();
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getResponseCode(),exception.getResponseDescription());
        modelAndView.setViewName("error");
        Map<String, Object> model = modelAndView.getModel();
        model.put("exception", exceptionResponse);
        return modelAndView;
    }*/
}
