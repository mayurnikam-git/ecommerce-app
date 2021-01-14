package com.example.springoauthdemo.util;

import com.example.springoauthdemo.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class Utility {

    public UserPrincipal getUserDetails(Principal principal){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)principal;
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        return userPrincipal;
    }

    public String convertObjectToJsonString(Object object){
        String jsonString = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonString = mapper. writeValueAsString(object);
        }catch (Exception exception){

        }
        return jsonString;
    }

   /* public static <T> Class convertObjectToJsonString(String json, Class<T> clazz){
        Class<T> clazzObject = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            clazzObject = (Class) mapper.convertValue(json, clazz);
        }catch (Exception exception){

        }
        return clazzObject;
    }*/
}
