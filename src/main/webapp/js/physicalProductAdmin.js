function getMorePhysicalProductsPaging(startingIndex = 0) {
    $.ajax("get-more-products?productType=Physical&productsPerRequest=4&startingIndex=" + startingIndex, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on Physical product page " + startingIndex/4+ " failed."),
        success: responseObject => {
            let newPhysicalProducts = responseObject.newProducts, $targetPage = $(".physicalProducts-table-body");

            $targetPage.empty();


            let newMaxPage = responseObject.newMaxPages;
            for(let i = newMaxPage + 1; i <= maxPagePhysicalProducts; i++){
                $("#pagePhysicalProducts"+i).remove();
            }
            for(let i = maxPagePhysicalProducts+1; i <= newMaxPage; i++){
                if(i == 1)
                    $("#pagePhysicalProducts"+(i-1)).after("<span class='current visible pageNumBtnPhysicalProductAdmin' id='pagePhysicalProducts"+i+"'> "+i+" </span>");
                else if(i <= 4)
                    $("#pagePhysicalProducts"+(i-1)).after("<span class='pageNumBtnPhysicalProductAdmin visible' id='pagePhysicalProducts"+i+"'> "+i+" </span>");
                else
                    $("#pagePhysicalProducts"+(i-1)).after("<span class='pageNumBtnPhysicalProductAdmin' id='pagePhysicalProducts"+i+"'> "+i+" </span>");
                $("#pagePhysicalProducts"+i).on("click", paginationPhysicalProductsListener);
            }
            maxPagePhysicalProducts = newMaxPage;


            //se la pagina è vuota, a meno che non sia la prima, cancelliamola e ritorniamo alla prima pagina
            if(newPhysicalProducts.length == 0 && startingIndex != 0){
                $(".paginationPhysicalProducts #pagePhysicalProducts" + maxPagePhysicalProducts).remove();
                maxPagePhysicalProducts--;
                $("#pagePhysicalProducts1").click();
            }


            //aggiungiamo le categorie alla target page
            for(let physicalProduct of newPhysicalProducts) {

                $targetPage.append("<tr id='" + physicalProduct.id + "PhysicalProductRow' class='physicalProducts-table-body-row'>\n" +
                    "                   <td class='can-be-editable editable-name'>" + physicalProduct.name + "</td>\n" +
                    "                   <td class='can-be-editable editable-categories'" + physicalProduct.categories + "</td>"+
                    "                   <td class='can-be-editable editable-tags'" + physicalProduct.tags + "</td>" +
                    "                   <td class='can-be-editable editable-price'>" + physicalProduct.price +  "</td>\n" +
                    "                   <td class='can-be-editable editable-description'>" + physicalProduct.description +
                    "                   </td>\n" +
                    "                   <td class='can-be-editable editable-imagePath'>" +
                    "                       <input type='file' name='filePhysicalProduct' style=\"display: none\">" +
                    "                       <span>"+physicalProduct.imagePath +"</span>" +
                    "                   </td>\n" +
                    "                   <td class='can-be-editable editable-quantity'>" + physicalProduct.platform +  "</td>\n" +
                    "                   <td class='can-be-editable editable-size'>" + physicalProduct.releaseDate +  "</td>\n" +
                    "                   <td class='can-be-editable editable-weight'>" + physicalProduct.quantity +  "</td>\n" +
                    "                   <td class='form-container'>\n" +
                    "                        <form class=\"changePhysicalProductForm\" name=\"changePhysicalProductForm\" method=\"post\" action=\"manage-product\">\n" +
                    "                            <input type='hidden' value='"+physicalProduct.name+"' name='changePhysicalProduct' class='changePhysicalProductOldName'>\n" +
                    "                            <input type='hidden' name='manage_product' value='update_product'\n" +
                    "                            <input type='hidden' name='product_type' value='physicalProduct'\n" +
                    "                            <input type=\"submit\" value=\"📝\" class=\"changePhysicalProductAdminButton\">\n" +
                    "                            <span class=\"errorPhysicalProductMessage\" style=\"color: #c75450; display: none\"></span>" +
                    "                        </form>\n" +
                    "                   </td>" +
                    "                   <td class='form-container'>\n" +
                    "                       <form class=\"removePhysicalProductForm\" name=\"removePhysicalProductForm\" method=\"post\" action=\"manage-product\">\n" +
                    "                           <input type=\"hidden\" value=\"" + physicalProduct.name +"\" name=\"removePhysicalProduct\" class='removePhysicalProductOldName'>\n" +
                    "                            <input type='hidden' name='manage_product' value='remove_product'\n" +
                    "                            <input type='hidden' name='product_type' value='physicalProduct'\n" +
                    "                           <input type=\"submit\" value=\"✗\" class=\"removePhysicalProductAdminButton\">\n" +
                    "                       </form>\n" +
                    "                   </td>\n" +
                    "              </tr>");
            }
            $(".changePhysicalProductAdminButton").on("click", changePhysicalProductListener);
            //async category removal
            $(".removePhysicalProductForm").on("submit", ev => ev.preventDefault());
            $(".removePhysicalProductAdminButton").on("click", removePhysicalProductListener);
        }
    });
}

var changePhysicalProductListener = ev =>{
    //impediamo il submit del form
    ev.preventDefault();

    var $target = $(ev.target);
    var $editableContent = $target.closest(".physicalProducts-table-body-row").find(".can-be-editable");
    var $updateContent = $target.closest(".physicalProducts-table-body-row").find(".form-container");
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
        $(".physicalProducts-table-body-row .editable-name, .physicalProducts-table-body-row .editable-description, .physicalProducts-table-body-row .editable-price," +
            ".physicalProducts-table-body-row .editable-weight, .physicalProducts-table-body-row .editable-size, .physicalProducts-table-body-row .editable-quantity").on("input",
            validatePhysicalProductTableRowInputsListener);
    }
    //altrimenti, mandiamo la richiesta asincrona
    else{
        $editableContent.removeAttr("contenteditable"); //rendiamo il contenuto non editabile
        $target.val("📝"); //cambiamo il valore nuovamente a change

        //prendiamo il valore di input del campo file, la nuova immagine, se esiste, ed inseriamola nel form data
        let newImageFile = $editableContent.filter(".editable-imagePath").find("input[type=file]")[0].files[0];
        if(newImageFile != null && newImageFile != undefined)
            fd.append("filePhysicalProduct", newImageFile, "filePhysicalProduct.jpg");

        //prendiamo gli altri campi di input ed inseriamoli nel form data
        fd.append("editable-name", $editableContent.filter(".editable-name").text());
        fd.append("editable-price", $editableContent.filter(".editable-price").text());
        fd.append("editable-description", $editableContent.filter(".editable-description").text());
        fd.append("editable-size", $editableContent.filter(".editable-size").text());
        fd.append("editable-weight", $editableContent.filter(".editable-weight").text());
        fd.append("editable-quantity", $editableContent.filter(".editable-quantity").text());
        fd.append("old-name", $updateContent.find(".changePhysicalProductOldName").val());

        //inviamo la richiesta asincrona
        $.ajax("manage-product?manage_product=update_category&product_type=physicalProduct", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: ev => alert("Request failed on Physical product page failed."),
            success: responseObject => {
                let msg, type, updatedPhysicalProduct, oldName, $editedRow;

                //leggiamo la risposta json
                updatedPhysicalProduct = responseObject.updatedPhysicalProduct;
                oldName = responseObject.oldName;
                $editedRow = $(document.getElementById(oldName + "PhysicalProductRow"));
                msg = responseObject.message;
                type = responseObject.type;

                //aggiorniamo gli input type=hidden con il categoryName apposito, e aggiorniamo la riga della tabella
                $editedRow.find(".changePhysicalProductOldName").val(updatedPhysicalProduct.name);
                $editedRow.find(".removePhysicalProductOldName").val(updatedPhysicalProduct.name);
                //eventuale codice per visualizzare l'immagine
                $editedRow.find(".editable-imagePath span").text(updatedPhysicalProduct.imagePath);
                $editedRow.find(".editable-description").text(updatedPhysicalProduct.description);
                $editedRow.find(".editable-name").text(updatedPhysicalProduct.name);
                $editedRow.find(".editable-price").text(updatedPhysicalProduct.price);
                $editedRow.find(".editable-platform").text(updatedPhysicalProduct.platform);
                $editedRow.find(".editable-releaseDate").text(updatedPhysicalProduct.releaseDate);
                $editedRow.find(".editable-requiredAge").text(updatedPhysicalProduct.requiredAge);
                $editedRow.find(".editable-softwareHouse").text(updatedPhysicalProduct.softwareHouse);
                $editedRow.find(".editable-publisher").text(updatedPhysicalProduct.publisher);
                $editedRow.find(".editable-quantity").text(updatedPhysicalProduct.quantity);
                $editedRow.prop("id", updatedPhysicalProduct.id + "PhysicalProductRow");


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

var validatePhysicalProductTableRowInputsListener = ev => {

};

var paginationPhysicalProductsListener = ev => {
    let $target = $(ev.target), targetIdNum, $currentPage = $(".paginationPhysicalProducts span.current"), $pageBtn,
        $targetPageBtn;

    if($target.prop("id") == "ellipseSxPhysicalProducts")
        $target = $(".pageNumBtnPhysicalProductAdmin.visible").first().prev();
    if($target.prop("id") == "ellipseDxPhysicalProducts")
        $target = $(".pageNumBtnPhysicalProductAdmin.visible").last().next();
    if($target.prop("id") == "previousPagePhysicalProducts"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == 1)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.prev("span.pageNumBtnPhysicalProductAdmin");
    }
    else if($target.prop("id") == "nextPagePhysicalProducts"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == maxPagePhysicalProducts)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.next("span.pageNumBtnPhysicalProductAdmin");
    }

    targetIdNum = parseInt($target.text());
    $targetPageBtn = $("#pagePhysicalProducts"+targetIdNum);
    $currentPage.removeClass("current");
    $targetPageBtn.addClass("current");

    $(".paginationPhysicalProducts .pageNumBtnPhysicalProductAdmin.visible").removeClass("visible");
    if(targetIdNum == 1 || targetIdNum == 2 || maxPagePhysicalProducts <= 4){
        for(let i = 1; i <= 4; i++){
            $pageBtn = $("#pagePhysicalProducts" + i);
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else if(targetIdNum == maxPagePhysicalProducts || targetIdNum == maxPagePhysicalProducts-1){
        for(let i = 0; i <= 3; i++){
            $pageBtn = $("#pagePhysicalProducts"+(maxPagePhysicalProducts-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else{
        $targetPageBtn.addClass("visible");
        for(let i = 1; i <= 2; i++){
            $pageBtn = $("#pagePhysicalProducts"+(targetIdNum-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
            $pageBtn = $("#pagePhysicalProducts"+(targetIdNum+i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }

    if(parseInt($(".pageNumBtnPhysicalProductAdmin.visible").first().text()) != 1)
        $("#ellipseSxPhysicalProducts").addClass("visible");
    else{
        $("#ellipseSxPhysicalProducts").removeClass("visible");
    }
    if(parseInt($(".pageNumBtnPhysicalProductAdmin.visible").last().text()) != maxPagePhysicalProducts)
        $("#ellipseDxPhysicalProducts").addClass("visible");
    else
        $("#ellipseDxPhysicalProducts").removeClass("visible");

    getMorePhysicalProductsPaging((targetIdNum-1)*4);
};

var removePhysicalProductListener =  ev => {
    ev.preventDefault();
    let $targetRow = $(ev.target).closest("tr"), PhysicalProductName;
    physicalProductName = $targetRow.find(".removePhysicalProductOldName").val();
    $.ajax("manage-product?product_type=physicalProduct&manage_product=remove_product&removeProduct=" + physicalProductName, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request of Physical product " + physicalProductName + " removal failed."),
        success: responseObject => {
            let removedPhysicalProductName = responseObject.removedPhysicalProduct, type= responseObject.type,
                msg = responseObject.msg;
            if(removedPhysicalProductName != null && removedPhysicalProductName != undefined)
                $(document.getElementById(removedPhysicalProductName + "PhysicalProductRow")).remove();
            //$("#pageCategories" + maxPageCategories).click();
            //triggeriamo la ricerca per cancellare eventuali pagine vuote

            showPopupMessage(type, msg, 8);
        }
    });
};

$(document).ready(function () {
    //modifica categoria esistente
    $(".changePhysicalProductAdminButton").on("click", changePhysicalProductListener);

    //paginazione
    $(".paginationPhysicalProducts span").on("click", paginationPhysicalProductsListener);

    //controlli sull'input
    $(".physicalProducts-table-body-row .editable-name, .physicalProducts-table-body-row .editable-description, .physicalProducts-table-body-row .editable-price," +
        ".physicalProducts-table-body-row .editable-weight, .physicalProducts-table-body-row .editable-size, .physicalProducts-table-body-row .editable-quantity").on("input",
        validatePhysicalProductTableRowInputsListener);

    //async category removal
    $(".removePhysicalProductForm").on("submit", ev => ev.preventDefault());
    $(".removePhysicalProductAdminButton").on("click", removePhysicalProductListener);

    //async category adding
    $("#addPhysicalProductForm").on("submit", ev => ev.preventDefault());

    $("#submitAdminButtonContainerAddPhysicalProduct input[type=submit]").on("click", ev => {
        ev.preventDefault();
        let fd = new FormData(document.getElementById("addPhysicalProductForm"));
        $.ajax("manage-product?&manage_product=add_product&tpye_prodcu=physicalProduct", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: (xhr, textStatus, errorThrown) => showPopupMessage("error", "Error: in physical product", 8),
            success: responseObject => {
                //triggeriamo la ricerca per generare eventuali nuove pages
                showPopupMessage(responseObject.type, responseObject.msg, 8);
            }
        });
    });
});