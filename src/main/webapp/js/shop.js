//funzione per l'aggiunta dei listener per la rimozione asincrona dal carrello
function setAddToCartButtonListeners(){
    //aggiunta asincrona al carrello
    $(".addToCartBtn").on("click", ev => {
        let productToAddId;
        let productToAddType;
        ev.preventDefault();
        //rimuoviamo dall'id del bottone tutti i caratteri non numeri
        productToAddId = $(ev.target).closest("form").find("input[name=productId]").val();
        productToAddType=  $(ev.target).closest("form").find("input[name=productType]").val();
        //gestiamo la risposta con AJAX
        $.ajax("add-cart?quantity=1&addCart=true&productId=" + productToAddId + "&productType=" + productToAddType, {
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