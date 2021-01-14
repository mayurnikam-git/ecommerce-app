
/*function removeProductFromCart() {
    var id = $(this).attr('data-product-id');

  var  productId = $(this).closest('tr').find('td:eq(0)').text()
    alert(id +" : "+productId);
    sendRequest("/cart/remove/"+productId,"DELETE",null);
}*/


$(document).ready(function() {
    $(".remove-from-cart").click(function (){
        var productId = $(this).closest('tr').find('td:eq(0)').text();
        var ajaxResponse = sendRequest("/cart/remove/"+productId,"DELETE",null);
        populate(ajaxResponse);
    });
});


function populate(response){
    if(response.responseCode == "000"){
        setResponseMessage("#response-msg", PRODUCT_REMOVED_FROM_CART_SUCCESSFULLY);
    }else {
        setResponseMessage("#response-msg", response.responseDescription);
    }
}
