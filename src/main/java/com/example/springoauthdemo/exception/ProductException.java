package com.example.springoauthdemo.exception;

public class ProductException extends Exception {

    private String responseCode;

    private String responseDescription;

    public ProductException(String responseCode, String message){
        this.responseCode = responseCode;
        responseDescription = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    @Override
    public String toString() {
        return "ProductException{" +
                "responseCode='" + responseCode + '\'' +
                ", responseDescription='" + responseDescription + '\'' +
                '}';
    }
}
