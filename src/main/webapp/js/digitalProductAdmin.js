function getMoreDigitalProductsPaging(startingIndex = 0) {
    $.ajax("get-more-products?productType=Digital&productsPerRequest=4&startingIndex=" + startingIndex, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on digital product page " + startingIndex/4+ " failed."),
        success: responseObject => {
            let newDigitalProducts = responseObject.newProducts, $targetPage = $(".digitalProducts-table-body");

            $targetPage.empty();


            let newMaxPage = responseObject.newMaxPages;
            for(let i = newMaxPage + 1; i <= maxPageDigitalProducts; i++){
                $("#pageDigitalProducts"+i).remove();
            }
            for(let i = maxPageDigitalProducts+1; i <= newMaxPage; i++){
                if(i == 1)
                    $("#pageDigitalProducts"+(i-1)).after("<span class='current visible pageNumBtnDigitalProductAdmin' id='pageDigitalProducts"+i+"'> "+i+" </span>");
                else if(i <= 4)
                    $("#pageDigitalProducts"+(i-1)).after("<span class='pageNumBtnDigitalProductAdmin visible' id='pageDigitalProducts"+i+"'> "+i+" </span>");
                else
                    $("#pageDigitalProducts"+(i-1)).after("<span class='pageNumBtnDigitalProductAdmin' id='pageDigitalProducts"+i+"'> "+i+" </span>");
                $("#pageDigitalProducts"+i).on("click", paginationDigitalProductsListener);
            }
            maxPageDigitalProducts = newMaxPage;


            //se la pagina è vuota, a meno che non sia la prima, cancelliamola e ritorniamo alla prima pagina
            if(newDigitalProducts.length == 0 && startingIndex != 0){
                $(".paginationDigitalProducts #pageDigitalProducts" + maxPageDigitalProducts).remove();
                maxPageDigitalProducts--;
                $("#pageDigitalProducts1").click();
            }


            //aggiungiamo le categorie alla target page
            for(let digitalProduct of newDigitalProducts) {

                $targetPage.append("<tr id='" + digitalProduct.id + "DigitalProductRow' class='digitalProducts-table-body-row'>\n" +
                    "                   <td class='can-be-editable editable-name'>" + digitalProduct.name + "</td>\n" +
                    "                   <td class='can-be-editable editable-categories'" + digitalProduct.categories + "</td>"+
                    "                   <td class='can-be-editable editable-tags'" + digitalProduct.tags + "</td>" +
                    "                   <td class='can-be-editable editable-price'>" + digitalProduct.price +  "</td>\n" +
                    "                   <td class='can-be-editable editable-description'>" + digitalProduct.description +
                    "                   </td>\n" +
                    "                   <td class='can-be-editable editable-imagePath'>" +
                    "                       <input type='file' name='fileDigitalProduct' style=\"display: none\">" +
                    "                       <span>"+digitalProduct.imagePath +"</span>" +
                    "                   </td>\n" +
                    "                   <td class='can-be-editable editable-platform'>" + digitalProduct.platform +  "</td>\n" +
                    "                   <td class='can-be-editable editable-releaseDate'>" + digitalProduct.releaseDate +  "</td>\n" +
                    "                   <td class='can-be-editable editable-requiredAge'>" + digitalProduct.requiredAge +   "</td>\n" +
                    "                   <td class='can-be-editable editable-softwareHouse'>" + digitalProduct.softwareHouse +  "</td>\n" +
                    "                   <td class='can-be-editable editable-publisher'>" + digitalProduct.publisher +  "</td>\n" +
                    "                   <td class='can-be-editable editable-quantity'>" + digitalProduct.quantity +  "</td>\n" +
                    "                   <td class='form-container'>\n" +
                    "                        <form class=\"changeDigitalProductForm\" name=\"changeDigitalProductForm\" method=\"post\" action=\"manage-product\">\n" +
                    "                            <input type='hidden' value='"+digitalProduct.name+"' name='changeDigitalProduct' class='changeDigitalProductOldName'>\n" +
                    "                            <input type='hidden' name='manage_product' value='update_product'\n" +
                    "                            <input type='hidden' name='product_type' value='digitalProduct'\n" +
                    "                            <input type=\"submit\" value=\"📝\" class=\"changeDigitalProductAdminButton\">\n" +
                    "                            <span class=\"errorDigitalProductMessage\" style=\"color: #c75450; display: none\"></span>" +
                    "                        </form>\n" +
                    "                   </td>" +
                    "                   <td class='form-container'>\n" +
                    "                       <form class=\"removeDigitalProductForm\" name=\"removeDigitalProductForm\" method=\"post\" action=\"manage-product\">\n" +
                    "                           <input type=\"hidden\" value=\"" + digitalProduct.name +"\" name=\"removeDigitalProduct\" class='removeDigitalProductOldName'>\n" +
                    "                            <input type='hidden' name='manage_product' value='remove_product'\n" +
                    "                            <input type='hidden' name='product_type' value='digitalProduct'\n" +
                    "                           <input type=\"submit\" value=\"✗\" class=\"removeDigitalProductAdminButton\">\n" +
                    "                       </form>\n" +
                    "                   </td>\n" +
                    "              </tr>");
            }
            $(".changeDigitalProductAdminButton").on("click", changeDigitalProductListener);
            //async category removal
            $(".removeDigitalProductForm").on("submit", ev => ev.preventDefault());
            $(".removeDigitalProductAdminButton").on("click", removeDigitalProductListener);
        }
    });
}

var changeDigitalProductListener = ev =>{
    //impediamo il submit del form
    ev.preventDefault();

    var $target = $(ev.target);
    var $editableContent = $target.closest(".digitalProducts-table-body-row").find(".can-be-editable");
    var $updateContent = $target.closest(".digitalProducts-table-body-row").find(".form-container");
    var fd = new FormData();

    //se il valore è change, allora rendiamo editabili i campi e mostriamo il form di inserimento del file
    if($target.val() == "📝"){
        //rendiamo il contenuto della riga editabile
        $editableContent.prop("contenteditable", true);
        //nascondiamo il nome del file immagine e mostriamo l'input[type=file]
        $editableContent.filter(".editable-imagePath").find(".imageContainer").css("display", "none");
        $editableContent.filter(".editable-imagePath").find("input[type=file]").css("display", "unset");
        $editableContent.filter(".editable-imagePath").find("span").css("display", "none");
        //cambiamo il bottone da change a submit
        $target.val("Submit");
        //aggiungiamo il controllo dell'input
        $(".digitalProducts-table-body-row .editable-name, .digitalProducts-table-body-row .editable-description, .digitalProducts-table-body-row .editable-price," +
            ".digitalProducts-table-body-row .editable-platform, .digitalProducts-table-body-row .editable-releaseDate, .digitalProducts-table-body-row .editable-requiredAge" +
            ".digitalProducts-table-body-row .editable-softwareHouse, .digitalProducts-table-body-row .editable-publisher, .digitalProducts-table-body-row .editable-quantity," +
            ".digitalProducts-table-body-row .editable-categories, .digitalProducts-table-body-row .editable-tags").on("input",
            validateDigitalProductTableRowInputsListener);
    }
    //altrimenti, mandiamo la richiesta asincrona
    else{
        $editableContent.removeAttr("contenteditable"); //rendiamo il contenuto non editabile
        $target.val("📝"); //cambiamo il valore nuovamente a change

        //prendiamo il valore di input del campo file, la nuova immagine, se esiste, ed inseriamola nel form data
        let newImageFile = $editableContent.filter(".editable-imagePath").find("input[type=file]")[0].files[0];
        if(newImageFile != null && newImageFile != undefined)
            fd.append("fileDigitalProduct", newImageFile, "fileDigitalProduct.jpg");

        //prendiamo gli altri campi di input ed inseriamoli nel form data
        fd.append("editable-name", $editableContent.filter(".editable-name").text());
        fd.append("editable-price", $editableContent.filter(".editable-price").text());
        fd.append("editable-description", $editableContent.filter(".editable-description").text());
        fd.append("editable-platform", $editableContent.filter(".editable-platform").text());
        fd.append("editable-releaseDate", $editableContent.filter(".editable-releaseDate").text());
        fd.append("editable-requiredAge", $editableContent.filter(".editable-requiredAge").text());
        fd.append("editable-softwareHouse", $editableContent.filter(".editable-softwareHouse").text());
        fd.append("editable-publisher", $editableContent.filter(".editable-publisher").text());
        fd.append("editable-quantity", $editableContent.filter(".editable-quantity").text());
        fd.append("editable-categories", $editableContent.filter(".editable-categories").text().replaceAll("&nbsp", ""));
        fd.append("editable-tags", $editableContent.filter(".editable-tags").text().replaceAll("&nbsp", ""));
        fd.append("old-name", $updateContent.find(".changeDigitalProductOldName").val());

        //inviamo la richiesta asincrona
        $.ajax("manage-product?manage_product=update_product&product_type=digitalProduct", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: ev => alert("Request failed on digital product page failed."),
            success: responseObject => {
                let msg, type, updatedDigitalProduct, oldName, $editedRow;

                //leggiamo la risposta json
                updatedDigitalProduct = responseObject.updatedDigitalProduct;
                oldName = responseObject.oldName;
                $editedRow = $(document.getElementById(oldName + "DigitalProductRow"));
                msg = responseObject.message;
                type = responseObject.type;

                //aggiorniamo gli input type=hidden con il categoryName apposito, e aggiorniamo la riga della tabella
                $editedRow.find(".changeDigitalProductOldName").val(updatedDigitalProduct.name);
                $editedRow.find(".removeDigitalProductOldName").val(updatedDigitalProduct.name);
                //eventuale codice per visualizzare l'immagine
                $editedRow.find(".editable-imagePath span").text(updatedDigitalProduct.imagePath);
                $editedRow.find(".editable-description").text(updatedDigitalProduct.description);
                $editedRow.find(".editable-name").text(updatedDigitalProduct.name);
                $editedRow.find(".editable-price").text(updatedDigitalProduct.price);
                $editedRow.find(".editable-platform").text(updatedDigitalProduct.platform);
                $editedRow.find(".editable-releaseDate").text(updatedDigitalProduct.releaseDate);
                $editedRow.find(".editable-requiredAge").text(updatedDigitalProduct.requiredAge);
                $editedRow.find(".editable-softwareHouse").text(updatedDigitalProduct.softwareHouse);
                $editedRow.find(".editable-publisher").text(updatedDigitalProduct.publisher);
                $editedRow.find(".editable-categories").text(updatedDigitalProduct.categories);
                $editedRow.find(".editable-tags").text(updatedDigitalProduct.tags);
                $editedRow.find(".editable-quantity").text(updatedDigitalProduct.quantity);
                $editedRow.prop("id", updatedDigitalProduct.id + "DigitalProductRow");


                //mostriamo il messaggio di popup
                showPopupMessage(type, msg, 8);
            }
        });

        //facciamo ritornare la riga della tabella allo stato precedente non modificabile
        $editableContent.filter(".editable-imagePath").find("span").css("display", "unset");
        $editableContent.filter(".editable-imagePath").find(".imageContainer").css("display", "unset");
        $editableContent.filter(".editable-imagePath").find("input[type=file]").css("display", "none");
    }
}

var validateDigitalProductTableRowInputsListener = ev => {

};

var paginationDigitalProductsListener = ev => {
    let $target = $(ev.target), targetIdNum, $currentPage = $(".paginationDigitalProducts span.current"), $pageBtn,
        $targetPageBtn;

    if($target.prop("id") == "ellipseSxDigitalProducts")
        $target = $(".pageNumBtnDigitalProductAdmin.visible").first().prev();
    if($target.prop("id") == "ellipseDxDigitalProducts")
        $target = $(".pageNumBtnDigitalProductAdmin.visible").last().next();
    if($target.prop("id") == "previousPageDigitalProducts"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == 1)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.prev("span.pageNumBtnDigitalProductAdmin");
    }
    else if($target.prop("id") == "nextPageDigitalProducts"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == maxPageDigitalProducts)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.next("span.pageNumBtnDigitalProductAdmin");
    }

    targetIdNum = parseInt($target.text());
    $targetPageBtn = $("#pageDigitalProducts"+targetIdNum);
    $currentPage.removeClass("current");
    $targetPageBtn.addClass("current");

    $(".paginationDigitalProducts .pageNumBtnDigitalProductAdmin.visible").removeClass("visible");
    if(targetIdNum == 1 || targetIdNum == 2 || maxPageDigitalProducts <= 4){
        for(let i = 1; i <= 4; i++){
            $pageBtn = $("#pageDigitalProducts" + i);
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else if(targetIdNum == maxPageDigitalProducts || targetIdNum == maxPageDigitalProducts-1){
        for(let i = 0; i <= 3; i++){
            $pageBtn = $("#pageDigitalProducts"+(maxPageDigitalProducts-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else{
        $targetPageBtn.addClass("visible");
        for(let i = 1; i <= 2; i++){
            $pageBtn = $("#pageDigitalProducts"+(targetIdNum-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
            $pageBtn = $("#pageDigitalProducts"+(targetIdNum+i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }

    if(parseInt($(".pageNumBtnDigitalProductAdmin.visible").first().text()) != 1)
        $("#ellipseSxDigitalProducts").addClass("visible");
    else{
        $("#ellipseSxDigitalProducts").removeClass("visible");
    }
    if(parseInt($(".pageNumBtnDigitalProductAdmin.visible").last().text()) != maxPageDigitalProducts)
        $("#ellipseDxDigitalProducts").addClass("visible");
    else
        $("#ellipseDxDigitalProducts").removeClass("visible");

    getMoreDigitalProductsPaging((targetIdNum-1)*4);
};

var removeDigitalProductListener =  ev => {
    ev.preventDefault();
    let $targetRow = $(ev.target).closest("tr"), digitalProductName;
    digitalProductName = $targetRow.find(".removeDigitalProductOldName").val();
    $.ajax("manage-product?product_type=digitalProduct&manage_product=remove_product&removeProduct=" + digitalProductName, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request of digital product " + digitalProductName + " removal failed."),
        success: responseObject => {
            let removedDigitalProductName = responseObject.removedDigitalProduct, type= responseObject.type,
                msg = responseObject.msg;
            if(removedDigitalProductName != null && removedDigitalProductName != undefined)
                $(document.getElementById(removedDigitalProductName + "DigitalProductRow")).remove();
            //$("#pageCategories" + maxPageCategories).click();
            //triggeriamo la ricerca per cancellare eventuali pagine vuote

            showPopupMessage(type, msg, 8);
        }
    });
};

$(document).ready(function () {
    //modifica categoria esistente
    $(".changeDigitalProductAdminButton").on("click", changeDigitalProductListener);

    //paginazione
    $(".paginationDigitalProducts span").on("click", paginationDigitalProductsListener);

    //controlli sull'input
    $(".digitalProducts-table-body-row .editable-name, .digitalProducts-table-body-row .editable-description, .digitalProducts-table-body-row .editable-price," +
        ".digitalProducts-table-body-row .editable-platform, .digitalProducts-table-body-row .editable-releaseDate, .digitalProducts-table-body-row .editable-requiredAge" +
        ".digitalProducts-table-body-row .editable-softwareHouse, .digitalProducts-table-body-row .editable-publisher, .digitalProducts-table-body-row .editable-quantity," +
        ".digitalProducts-table-body-row .editable-categories, .digitalProducts-table-body-row .editable-tags").on("input",
        validateDigitalProductTableRowInputsListener);

    //async category removal
    $(".removeDigitalProductForm").on("submit", ev => ev.preventDefault());
    $(".removeDigitalProductAdminButton").on("click", removeDigitalProductListener);

    //async category adding
    $("#addDigitalProductForm").on("submit", ev => ev.preventDefault());

    $("#submitAdminButtonContainerAddDigitalProduct input[type=submit]").on("click", ev => {
        ev.preventDefault();
        let fd = new FormData(document.getElementById("addDigitalProductForm"));
        $.ajax("manage-product?&manage_product=add_product&tpye_prodcu=digitalProduct", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: (xhr, textStatus, errorThrown) => showPopupMessage("error", "Error: in digital product", 8),
            success: responseObject => {
                //triggeriamo la ricerca per generare eventuali nuove pages
                showPopupMessage(responseObject.type, responseObject.msg, 8);
            }
        });
    });
});