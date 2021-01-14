function addProduct() {
    var requestObj = {};
    var productName = $("input[name='productName']").val();
    var productPrice = $("input[name='productPrice']").val();

    requestObj.productName = productName;
    requestObj.productPrice=productPrice;
    var requestJson = createJson(requestObj);
    var ajaxResponse = sendRequest("/products","POST",requestJson);
    populate(ajaxResponse);
}

function populate(response){
    if(response.responseCode == "000"){
        setResponseMessage("#response-msg", PRODUCT_ADDED_SUCCESSFULLY);
    }else {
        setResponseMessage("#response-msg", response.responseDescription);
    }
}