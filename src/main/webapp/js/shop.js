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

function getMoreProductsPaging(startingIndex, selectedRadioValue, search="" , description="", tag="", priceValue, categoryName){
    $.ajax("get-more-products?productsPerRequest=8&startingIndex=" + startingIndex +"&productType="+
        selectedRadioValue +"&search=" + search +"&description="+description+"&tag="+tag+"&price="+priceValue+"&category="+categoryName, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on products page " + startingIndex/8+ " failed."),
        success: responseObject => {
            let newProducts = responseObject.newProducts;

            let $targetContainer = $("#shop-container");
            $targetContainer.empty();

            //aggiungiamo le categorie alla target page
            for(let product of newProducts) {
                //let imagePath = '/gamehub-1.0-SNAPSHOT/img/' + category.imagePath; //eventualmente sostituire
                let imagePath = 'img/'+product.imagePath;
                let imagePath2 = '"img/'+product.imagePath+'"';
                $targetContainer.append("<div class=\"col-md-6\">\n" +
                    "                                        <div class=\"recent-game-item\">\n" +
                    "                                            <div class=\"rgi-thumb set-bg\" data-setbg=\""+imagePath+"\" style='background-image: url("+imagePath2+");'>\n" +
                    "                                                <div class=\"cata new\">\n" +
                    "                                                    <form action=\"add-cart\" method=\"get\" class=\"shop-form\">\n" +
                    "                                                        <input type=\"hidden\" name=\"addCart\" value=\"true\">\n" +
                    "                                                        <input type=\"hidden\" name=\"productId\" value=\""+product.id+"\">\n" +
                    "                                                        <input type=\"hidden\" name=\"quantity\" value=\""+product.quantity+"\">\n" +
                    "                                                        <input type=\"hidden\" name=\"productType\" value=\""+product.productType+"\">\n" +
                    "                                                        <button type=\"submit\" class=\"shop-button addToCartBtn\">\n" +
                    "                                                                Add To Cart\n" +
                    "                                                        </button>\n" +
                    "                                                    </form>\n" +
                    "                                                </div>\n" +
                    "                                            </div>\n" +
                    "                                            <div class=\"rgi-content shop-description\">\n" +
                    "                                                <form action=\"showProduct.html\" method=\"get\" class=\"shop-form-singleProduct\">\n" +
                    "                                                    <button type=\"submit\" class=\"shop-button-singleProduct\">\n" +
                    "                                                        <input type=\"hidden\" name=\"productId\" value=\""+product.id+"\">\n" +
                    "                                                        <input type=\"hidden\" name=\"productType\" value=\""+product.productType+"\">\n" +
                    "                                                        <h5 style=\"color: whitesmoke; margin: 0;\" >"+product.name+"</h5>\n" +
                    "                                                    </button>\n" +
                    "                                                </form>\n" +
                    "                                                <h6 style=\"color: whitesmoke; margin-top: 16px;\">"+product.description+"</h6>\n" +
                    "                                                <p style=\"margin: 16px 0px 0px 0px\">Remaining: "+product.quantity+"</p>\n" +
                    "                                                <p style=\"margin-bottom: 0\">"+product.price+"$</p>\n" +
                    "                                            </div>\n" +
                    "                                        </div>\n" +
                    "                                    </div>")
            }

            //aggiunta asincrona al carrello
            setAddToCartButtonListeners();

        }
    });
}

$(document).ready(ev => {

    //aggiunta asincrona al carrello
    setAddToCartButtonListeners();

    //pagination control
    $(".pagination span").on("click", ev => {
        let $target = $(ev.target), targetIdNum, $currentPage = $(".pagination span.current"), $pageBtn, $targetPageBtn,
            $targetPage;

        $('html, body').animate({scrollTop:0}, '300'); //ci spostiamo sulla parte superiore della pagina con animazione

        //se abbiamo cliccato il bottone "avanti" o "indietro" o uno dei "..." calcoliamo la targetPage
        if($target.prop("id") == "ellipseSx")
            $target = $(".pageNumBtn.visible").first().prev();
        if($target.prop("id") == "ellipseDx")
            $target = $(".pageNumBtn.visible").last().next();
        if($target.prop("id") == "previousPage"){
            let currentPageNum;
            currentPageNum = parseInt($currentPage.text());
            if(currentPageNum == 1)
                return;
            $currentPage.removeClass("current");
            $target = $currentPage.prev("span.pageNumBtn");
        }
        else if($target.prop("id") == "nextPage") {
            let currentPageNum;
            currentPageNum = parseInt($currentPage.text());
            if(currentPageNum == maxPage)
                return;
            $currentPage.removeClass("current");
            $target = $currentPage.next("span.pageNumBtn");
        }

        //calcoliamo il target page id number e impostiamo il bottone corrente
        targetIdNum = parseInt($target.text());
        $targetPageBtn = $("#page" + targetIdNum);
        $targetPage = $("#categoryPage" + targetIdNum);
        $(".categoryPage.visible").removeClass("visible");
        $targetPage.addClass("visible");
        $currentPage.removeClass("current");
        $targetPageBtn.addClass("current");

        //ricerca categorie
        let searchString = SEARCH;
        let selectedRadioValue= TYPE;
        let description = DESCRIPTION;
        let tagName = TAG;
        let priceValue = PRICE;
        let categoryName = CTG;
        //carichiamo la pagina dal server, partendo dalla prima categoria della pagina target
        if(!$targetPage.hasClass("loaded"))
            getMoreProductsPaging((targetIdNum - 1)*8, selectedRadioValue, searchString, description, tagName, priceValue, categoryName);

        //rendiamo visibili un range di 5 bottoni, a partire da quello corrente, e nascondiamo gli altri
        $(".pagination .pageNumBtn.visible").removeClass("visible");
        if(targetIdNum == 1 || targetIdNum == 2 || maxPage <= 5) {
            for(let i = 1; i <= 8; i++) {
                $pageBtn = $("#page" + i);
                if($pageBtn.length > 0)
                    $pageBtn.addClass("visible");
            }
        }
        else if(targetIdNum == maxPage || targetIdNum == maxPage - 1){
            for(let i = 0; i <= 7; i++) {
                $pageBtn = $("#page" + (maxPage - i));
                if($pageBtn.length > 0)
                    $pageBtn.addClass("visible");
            }
        }
        else {
            $targetPageBtn.addClass("visible");
            for(let i = 1; i <= 2; i++){
                $pageBtn = $("#page" + (targetIdNum - i));
                if($pageBtn.length > 0)
                    $pageBtn.addClass("visible");
                $pageBtn = $("#page" + (targetIdNum + i));
                if($pageBtn.length > 0)
                    $pageBtn.addClass("visible");
            }
        }

        //se ci sono pagine prima o dopo l'ultima visibile, rendiamo visible "..." in corrispondenza, altrimenti lo rimuoviamo
        if(parseInt($(".pageNumBtn.visible").first().text()) != 1)
            $("#ellipseSx").addClass("visible");
        else
            $("#ellipseSx").removeClass("visible");
        if(parseInt($(".pageNumBtn.visible").last().text()) != maxPage)
            $("#ellipseDx").addClass("visible");
        else
            $("#ellipseDx").removeClass("visible");
    });
});