function getMoreTagsPaging(startingIndex=0){
    $.ajax("get-more-tags?tagsPerRequest=4&search=&startingIndex=" + startingIndex, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on tag page " + startingIndex/4+ " failed."),
        success: responseObject => {
            let newTags = responseObject.newTags, $targetPage = $(".tags-table-body");

            $targetPage.empty();


            let newMaxPage = responseObject.newMaxPages;
            for(let i = newMaxPage + 1; i <= maxPageTags; i++){
                $("#pageTags"+i).remove();
            }
            for(let i = maxPageTags+1; i <= newMaxPage; i++){
                if(i == 1)
                    $("#pageTags"+(i-1)).after("<span class='current visible pageNumBtnTagAdmin' id='pageTags"+i+"'> "+i+" </span>");
                else if(i <= 4)
                    $("#pageTags"+(i-1)).after("<span class='pageNumBtnTagAdmin visible' id='pageTags"+i+"'> "+i+" </span>");
                else
                    $("#pageTags"+(i-1)).after("<span class='pageNumBtnTagAdmin' id='pageTags"+i+"'> "+i+" </span>");
                $("#pageTags"+i).on("click", paginationTagsListener);
            }
            maxPageTags = newMaxPage;


            //se la pagina è vuota, a meno che non sia la prima, cancelliamola e ritorniamo alla prima pagina
            if(newTags.length == 0 && startingIndex != 0){
                $(".paginationTags #pageTags" + maxPageTags).remove();
                maxPageTags--;
                $("#pageTags1").click();
            }


            //aggiungiamo le tags alla target page
            for(let tag of newTags) {
                $targetPage.append("<tr id='" + tag.name + "TagRow' class='tags-table-body-row'>\n" +
                    "                   <td class='can-be-editable editable-name'>" + tag.name + "</td>\n" +
                    "                   <td class='form-container'>\n" +
                    "                        <form class=\"changeTagForm\" name=\"changeTagForm\" method=\"post\" action=\"changeTag-servlet\">\n" +
                    "                            <input type='hidden' value='"+tag.name+"' name='changeTag' class='changeTagOldName'>\n" +
                    "                            <input type=\"submit\" value=\"📝\" class=\"changeTagAdminButton\">\n" +
                    "                            <span class=\"errorTagMessage\" style=\"color: #c75450; display: none\"></span>" +
                    "                        </form>\n" +
                    "                   </td>" +
                    "                   <td class='form-container'>\n" +
                    "                       <form class=\"removeTagForm\" name=\"removeTagForm\" method=\"post\" action=\"removeTag\">\n" +
                    "                           <input type=\"hidden\" value=\"" + tag.name + "\" name=\"removeTag\" class='removeTagOldName'>\n" +
                    "                           <input type=\"submit\" value=\"✗\" class=\"removeTagAdminButton\">\n" +
                    "                       </form>\n" +
                    "                   </td>\n" +
                    "              </tr>");
            }
            $(".changeTagAdminButton").on("click", changeTagListener);
            //async tag removal
            $(".removeTagForm").on("submit", ev => ev.preventDefault());
            $(".removeTagAdminButton").on("click", removeTagListener);
        }
    });
}

var changeTagListener = ev =>{
    //impediamo il submit del form
    ev.preventDefault();

    var $target = $(ev.target);
    var $editableContent = $target.closest(".tags-table-body-row").find(".can-be-editable");
    var $updateContent = $target.closest(".tags-table-body-row").find(".form-container");
    var fd = new FormData();

    //se il valore è change, allora rendiamo editabili i campi e mostriamo il form di inserimento del file
    if($target.val() == "📝"){
        //rendiamo il contenuto della riga editabile
        $editableContent.prop("contenteditable", true);
        //cambiamo il bottone da change a submit
        $target.val("Submit");
        //aggiungiamo il controllo dell'input
        $(".tags-table-body-row .editable-name").on("input",
            validateTagTableRowInputsListener);
    }
    //altrimenti, mandiamo la richiesta asincrona
    else{
        $editableContent.removeAttr("contenteditable"); //rendiamo il contenuto non editabile
        $target.val("📝"); //cambiamo il valore nuovamente a change


        //prendiamo gli altri campi di input ed inseriamoli nel form data
        fd.append("editable-name", $editableContent.filter(".editable-name").text());
        fd.append("old-name", $updateContent.find(".changeTagOldName").val());

        //inviamo la richiesta asincrona
        $.ajax("manage-tag?manage_tag=update_tag", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: ev => alert("Request failed on Tag page failed."),
            success: responseObject => {
                let msg, type, updatedTag, oldName, $editedRow;

                //leggiamo la risposta json
                updatedTag = responseObject.name;
                oldName = responseObject.oldName;
                $editedRow = $(document.getElementById(oldName + "TagRow"));
                msg = responseObject.message;
                type = responseObject.type;
                if(type == "error")
                    return;

                //aggiorniamo gli input type=hidden con il TagName apposito, e aggiorniamo la riga della tabella
                $editedRow.find(".changeTagOldName").val(updatedTag.name);
                $editedRow.find(".removeTagOldName").val(updatedTag.name);
                //eventuale codice per visualizzare l'immagine
                $editedRow.find(".editable-name").text(updatedTag.name);
                $editedRow.prop("id", updatedTag.name + "TagRow");


                //mostriamo il messaggio di popup
                showPopupMessage(type, msg, 8);
            }
        });
    }
}

var validateTagTableRowInputsListener = ev => {
    let $targetRow, nameText, $errorMessage;
    $targetRow = $(ev.target).closest("tr");
    nameText = $targetRow.find(".editable-name").text().trim();
    $errorMessage = $targetRow.find(".errorTagMessage");

    if(nameText.length > 45 || nameText.length < 1){
        $targetRow.find(".changeTagAdminButton").hide();
        $errorMessage.css("display", "unset");
        $errorMessage.text("Error: tag name must contain from 1 to 45 characters!");
    }
    else{
        $targetRow.find(".changeTagAdminButton").show();
        $errorMessage.css("display", "none");
        $errorMessage.text("");
    }
};

var paginationTagsListener = ev => {
    let $target = $(ev.target), targetIdNum, $currentPage = $(".paginationTags span.current"), $pageBtn,
        $targetPageBtn;

    if($target.prop("id") == "ellipseSxTags")
        $target = $(".pageNumBtnTagAdmin.visible").first().prev();
    if($target.prop("id") == "ellipseDxTags")
        $target = $(".pageNumBtnTagAdmin.visible").last().next();
    if($target.prop("id") == "previousPageTags"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == 1)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.prev("span.pageNumBtnTagAdmin");
    }
    else if($target.prop("id") == "nextPageTags"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == maxPageTags)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.next("span.pageNumBtnTagAdmin");
    }

    targetIdNum = parseInt($target.text());
    $targetPageBtn = $("#pageTags"+targetIdNum);
    $currentPage.removeClass("current");
    $targetPageBtn.addClass("current");

    $(".paginationTags .pageNumBtnTagAdmin.visible").removeClass("visible");
    if(targetIdNum == 1 || targetIdNum == 2 || maxPageTags <= 4){
        for(let i = 1; i <= 4; i++){
            $pageBtn = $("#pageTags"+i);
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else if(targetIdNum == maxPageTags || targetIdNum == maxPageTags-1){
        for(let i = 0; i <= 3; i++){
            $pageBtn = $("#pageTags"+(maxPageTags-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else{
        $targetPageBtn.addClass("visible");
        for(let i = 1; i <= 2; i++){
            $pageBtn = $("#pageTags"+(targetIdNum-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
            $pageBtn = $("#pageTags"+(targetIdNum+i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }

    if(parseInt($(".pageNumBtnTagAdmin.visible").first().text()) != 1)
        $("#ellipseSxTags").addClass("visible");
    else{
        $("#ellipseSxTags").removeClass("visible");
    }
    if(parseInt($(".pageNumBtnTagAdmin.visible").last().text()) != maxPageTags)
        $("#ellipseDxTags").addClass("visible");
    else
        $("#ellipseDxTags").removeClass("visible");

    getMoreTagsPaging((targetIdNum-1)*4);
};

var removeTagListener =  ev => {
    ev.preventDefault();
    let $targetRow = $(ev.target).closest("tr"), tagName;
    tagName = $targetRow.find(".removeTagOldName").val();
    $.ajax("manage-tag?manage_tag=remove_tag&removeTag=" + tagName, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request of tag " + tagName + " removal failed."),
        success: responseObject => {
            let removedTagName = responseObject.removedTag, type= responseObject.type,
                msg = responseObject.msg;
            if(removedTagName != null && removedTagName != undefined)
                $(document.getElementById(removedTagName + "TagRow")).remove();
            //$("#pageTags" + maxPageTags).click();
            //triggeriamo la ricerca per cancellare eventuali pagine vuote

            showPopupMessage(type, msg, 8);
        }
    });
};

$(document).ready(function () {
    //modifica tag esistente
    $(".changeTagAdminButton").on("click", changeTagListener);

    //paginazione
    $(".paginationTags span").on("click", paginationTagsListener);

    //controlli sull'input
    $(".tags-table-body-row .editable-name, .tags-table-body-row .editable-description").on("input",
        validateTagTableRowInputsListener);

    //async tag removal
    $(".removeTagForm").on("submit", ev => ev.preventDefault());
    $(".removeTagAdminButton").on("click", removeTagListener);

    //async tag adding
    $("#addTagForm").on("submit", ev => ev.preventDefault());

    $("#submitAdminButtonContainerAddTag input[type=submit]").on("click", ev => {
        ev.preventDefault();

        let $errorMessage = $("#errorMessageAddTag")
        $errorMessage.removeClass("visible");
        $errorMessage.text("");

        if(!new RegExp("^.{1,45}$").test($("#tagName").val())){
            $errorMessage.addClass("visible");
            $errorMessage.text("Error: tag name must be from 1 to 45 characters long!");
            return;
        }

        let fd = new FormData(document.getElementById("addTagForm"));
        $.ajax("manage-tag?&manage_tag=add_tag", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: ev => alert("Request on tag adding failed."),
            success: responseObject => {
                //triggeriamo la ricerca per generare eventuali nuove pages
                showPopupMessage(responseObject.type, responseObject.msg, 8);
            }
        });
    });
});