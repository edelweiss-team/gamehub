function setAddToCartButtonListeners(){
    //aggiunta asincrona al carrello
    $(".addToCartBtn").on("click", ev => {
        let productToAddId;
        let productToAddType;
        let quantity;
        ev.preventDefault();
        //rimuoviamo dall'id del bottone tutti i caratteri non numeri
        productToAddId = $(ev.target).closest("form").find("input[name=productId]").val();
        productToAddType =  $(ev.target).closest("form").find("input[name=productType]").val();
        quantity = $(ev.target).closest("form").find("input[name=productQuantity]").val();
        if (quantity == "")
            quantity = 1;
        //gestiamo la risposta con AJAX
        $.ajax("add-cart?quantity="+ quantity + "&addCart=true&productId=" + productToAddId + "&productType=" + productToAddType, {
            method: "GET",
            error: ev => alert("Request failed."),
            success: responseText => {
                let responseObject = JSON.parse(responseText), msg, type;
                msg = responseObject.message;
                type = responseObject.type;
                showPopupMessage(type, msg, 8);
            }
        });
    });
}

$(document).ready(ev => {

    //aggiunta asincrona al carrello
    setAddToCartButtonListeners();
});