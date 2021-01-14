package com.example.springoauthdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.example.springoauthdemo","com.example.springoauthdemo.controller"})
public class SpringoauthdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringoauthdemoApplication.class, args);
    }

}
