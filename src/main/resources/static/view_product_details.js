function addProductToCart() {
    var requestObj = {};
    var productId = $("td[name='productId']").text();
    var productName = $("td[name='productName']").text();
    /*var productName = $(this).text();
*/
    requestObj.productId = productId;
    requestObj.quantity = 1;
    requestObj.productName = productName;
    var requestJson = createJson(requestObj);
    var ajaxResponse = sendRequest("/cart/add","POST",requestJson);
    populate(ajaxResponse);
}

function populate(response){
    if(response.responseCode == "000"){
        setResponseMessage("#response-msg", PRODUCT_ADDED_TO_CART_SUCCESSFULLY);
    }else {
        setResponseMessage("#response-msg", response.responseDescription);
    }
}
