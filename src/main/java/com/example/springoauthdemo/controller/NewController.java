package com.example.springoauthdemo.controller;

import com.example.springoauthdemo.request.CreateProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NewController extends BaseController{

    public static final Logger logger = LoggerFactory.getLogger(NewController.class);

    @RequestMapping("/test")
    public String home(){
        logger.info("Home page requested");
        return "main.html";
    }

    @PostMapping("/test2")
    public void test2(@RequestBody CreateProductRequest request){
        logger.info("request : " + request);
    }

}
