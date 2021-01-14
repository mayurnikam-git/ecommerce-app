
function sendRequest(url, type, request) {
   /* var csrfTokenHeader = $("#csrf-token").attr("name");
    var csrfTokenValue = $("#csrf-token").val();
    console.log("token", csrfTokenHeader,csrfTokenValue);
*/
    var ajaxResponse = null;
    $.ajax(
        {
            url: url,
            type: type,
            /*beforeSend: function(request) {
            request.setRequestHeader(csrfTokenHeader, csrfTokenValue);
            },*/
           /* headers:{
                "X-CSRFToken":csrfTokenValue
            },*/
            async : false,
            data: request,
            contentType: "application/json",
            success : function (response){
                console.log("success : " ,response);
                ajaxResponse=response;
               // populate(response);
            },
            error : function (response){
                console.log("error : " , response);
            },
            complete: function (response){
                console.log("complete : ", response);
            }
        }
    )

    return ajaxResponse;
}

function createJson(requestArray){
    return JSON.stringify(requestArray);
}

function getSelectValue(elementId){
   return $("#"+elementId+" option:selected");
}

function unhideElement(elementId) {
    var x = $(elementId);
    if (x.attr("hidden") == "hidden"){
        x.removeAttr("hidden");
    }
}

function setResponseMessage(elementId, message){
        $(elementId).text(message);
}