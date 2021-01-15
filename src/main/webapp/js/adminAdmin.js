function getMoreAdminsPaging(startingIndex) {
    $.ajax("get-more-admins?adminsPerRequest=4&startingIndex="+startingIndex,{
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on admin page " + startingIndex/4 + " failed."),
        success: responseObject =>{
            let newAdmins = responseObject.newAdmins, $targetPage = $(".admins-table-body");
            $targetPage.empty();

            //se la pagina è vuota, a meno che non sia la prima, cancelliamola e ritorniamo alla prima pagina
            if(newAdmins.length == 0 && startingIndex != 0){
                $(".paginationAdmins #pageAdmins" + maxPageAdmins).remove();
                maxPageAdmins--;
                $("#pageAdmins1").click();
            }
            for(let admin of newAdmins){
                $targetPage.append("<tr id='" + admin.username + "AdminRow' class='admins-table-body-row'>\n" +
                    "                            <td class=\"can-be-editable editable-name\"> "+ admin.username +" </td>" +
                    "                             <td class=\"can-be-editable editable-isSuperAdmin\">"+ admin.isSuperAdmin + "</td>" +
                    "                            <td class='form-container'>\n" +
                    "                                <form name='changeAdminForm' class='changeAdminForm' method='post' action='manageAdmin-servlet'>\n" +
                    "                                    <input type='hidden' value='"+admin.username+"' name='changeAdmin' class='changeAdminOldName'>\n" +
                    "                                    <input type='submit' value=\"📝\" class='changeAdminAdminButton'>\n" +
                    "                                </form>\n" +
                    "                            </td>\n" +
                    "                            <td class='form-container'>\n" +
                    "                                <form name='removeAdminForm' class='removeAdminForm' method='post' action='manageAdmin-servlet'>\n" +
                    "                                    <input type='hidden' value='"+admin.username+"' name='removeAdmin' class='adminNameForRemove'>\n" +
                    "                                    <input type='submit' value='✗' class='removeAdminAdminButton'>\n" +
                    "                                </form>\n" +
                    "                            </td>\n" +
                    "                        </tr>")
            }
            //async Admin removal
            $(".removeAdminForm").on("submit", ev => ev.preventDefault());
            $(".removeAdminAdminButton").on("click", removeAdminListener);
            $(".changeAdminAdminButton").on("click", changeAdminListener);
        }

    });
}

var changeAdminListener = ev =>{
    //impediamo il submit del form
    ev.preventDefault();

    var $target = $(ev.target);
    var $editableContent = $target.closest(".admins-table-body-row").find(".can-be-editable");
    var $updateContent = $target.closest(".admins-table-body-row").find(".form-container");
    var fd = new FormData();

    //se il valore è change, allora rendiamo editabili i campi e mostriamo il form di inserimento del file
    if($target.val() == "📝"){
        //rendiamo il contenuto della riga editabile
        $editableContent.prop("contenteditable", true);

        //cambiamo il bottone da change a submit
        $target.val("Submit");
    }
    //altrimenti, mandiamo la richiesta asincrona
    else{
        $editableContent.removeAttr("contenteditable"); //rendiamo il contenuto non editabile
        $target.val("📝"); //cambiamo il valore nuovamente a change


        //prendiamo gli altri campi di input ed inseriamoli nel form data
        fd.append("editable-isSuperAdmin", $editableContent.filter(".editable-isSuperAdmin").text());
        fd.append("old-name", $updateContent.find(".changeAdminOldName").val());

        //inviamo la richiesta asincrona
        $.ajax("manage-admin?manage_admin=update_admin", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: ev => (xhr, textStatus, errorThrown) => showPopupMessage("error", xhr.responseText, 8),
            success: responseObject => {
                let msg, type,  $editedRow;

                //leggiamo la risposta json
                $editedRow = $(document.getElementById(responseObject.username + "AdminRow"));
                msg = responseObject.message;
                type = responseObject.type;

                //eventuale codice per visualizzare l'immagine
                $editedRow.find(".editable-isSuperAdmin").text(responseObject.isSuperAdmin);
                $editedRow.prop("id", responseObject.username + "AdminRow");


                //mostriamo il messaggio di popup
                showPopupMessage(type, msg, 8);
            }
        });

    }
}


var paginationAdminsListener = ev =>{
    let $target = $(ev.target), targetIdNum, $currentPage = $(".paginationAdmins span.current"), $pageBtn,
        $targetPageBtn;

    if($target.prop("id") == "ellipseSxAdmins")
        $target = $(".pageNumBtnAdminAdmin.visible").first().prev();
    if($target.prop("id") == "ellipseDxAdmins")
        $target = $(".pageNumBtnAdminAdmin.visible").last().next();
    if($target.prop("id") == "previousPageAdmins"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == 1)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.prev("span.pageNumBtnAdminsAdmin");
    }
    else if($target.prop("id") == "nextPageAdmins"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == maxPageAdmins)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.next("span.pageNumBtnAdminsAdmin");
    }

    targetIdNum = parseInt($target.text());
    $targetPageBtn = $("#pageAdmins"+targetIdNum);
    $currentPage.removeClass("current");
    $targetPageBtn.addClass("current");

    $(".paginationAdmins .pageNumBtnAdminAdmin.visible").removeClass("visible");
    if(targetIdNum == 1 || targetIdNum == 2 || maxPageAdmins <= 4){
        for(let i = 1; i <= 4; i++){
            $pageBtn = $("#pageAdmins"+i);
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else if(targetIdNum == maxPageAdmins || targetIdNum == maxPageAdmins){
        for(let i = 0; i <= 3; i++){
            $pageBtn = $("#pageAdmins"+(maxPageAdmins-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else{
        $targetPageBtn.addClass("visible");
        for(let i = 1; i <= 2; i++){
            $pageBtn = $("#pageAdmins"+(targetIdNum-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
            $pageBtn = $("#pageAdmins"+(targetIdNum+i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }

    if(parseInt($(".pageNumBtnAdminsAdmin.visible").first().text()) != 1)
        $("#ellipseSxAdmins").addClass("visible");
    else{
        $("#ellipseSxAdmins").removeClass("visible");
    }
    if(parseInt($(".pageNumBtnAdminsAdmin.visible").last().text()) != maxPageAdmins)
        $("#ellipseDxAdmins").addClass("visible");
    else
        $("#ellipseDxAdmins").removeClass("visible");

    getMoreAdminsPaging((targetIdNum-1)*4);
};

var removeAdminListener = ev => {
    ev.preventDefault();
    let $targetRow = $(ev.target).closest("tr"), adminName;
    adminName = $targetRow.find(".adminNameForRemove").val();
    $.ajax("manage-admin?manage_admin=remove_admin&removeAdmin=" + adminName, {
        method: "GET",
        dataType: "json",
        error: ev => (xhr, textStatus, errorThrown) => showPopupMessage("error", xhr.responseText, 8),
        success: responseObject => {
            let msg, type;

            //leggiamo la risposta json
            $(document.getElementById(responseObject.username + "AdminRow")).remove();
            msg = responseObject.msg;
            type = responseObject.type;

            //mostriamo il messaggio di popup
            showPopupMessage(type, msg, 8);
        }
    });
};

$(document).ready(function () {

    $(".changeAdminAdminButton").on("click", changeAdminListener);


    //paginazione
    $(".paginationAdmins span").on("click", paginationAdminsListener);
    $(".searchBarAdminAdmin button").on("click", ev =>{
        ev.preventDefault();
        if($(".searchBarAdminAdmin input[type=text]").val().trim().match(new RegExp("^[a-zA-Z0-9]*$"))){
            getMoreAdminsPaging(0, $(".searchBarAdminAdmin input[type=text]").val().trim(), true);
            $("#pageAdmins1").click();
        }
        else
            alert("You must enter a valid name (numbers or letters)");
    });

    //validation
    $("#submitAdminButtonContainerAddAdmin, #contractTime, #curriculum, #userName").on("click input", ev => {
        ev.preventDefault();
        let $errorMessage = $("#errorMessageAddAdmin");
        let $submit =  $("#submitAdminButtonContainerAddAdmin button[type=submit]");
        $submit.prop("disabled", false);
        $errorMessage.removeClass("visible");
        if(!$("#contractTime")[0].checkValidity()) {
            $errorMessage.text("Error: date must be like yyyy-mm-dd");
            $submit.prop("disabled", true);
            $errorMessage.addClass("visible");
        }
        else if(!$("#curriculum")[0].checkValidity()) {
            $errorMessage.text("Error: curriculum has to be between 3 and 10000 characters long!");
            $submit.prop("disabled", true);
            $errorMessage.addClass("visible");
        }
        else if(!$("#userName")[0].checkValidity()) {
            $errorMessage.text("Error: username has to be between " +
                "6 and 20 characters and contain letters and numbers!");
            $submit.prop("disabled", true);
            $errorMessage.addClass("visible");
        }
    });

    //async Admin removal
    $(".removeAdminForm").on("submit", ev => ev.preventDefault());
    $(".removeAdminAdminButton").on("click", removeAdminListener);

    //async Admin add
    $("#addAdminForm").on("submit", ev => ev.preventDefault());

    $("#submitAdminButtonContainerAddAdmin input[type=submit]").on("click", ev => {
        ev.preventDefault();
        let fd = new FormData(document.getElementById("addAdminForm"));
        $.ajax("manage-admin?manage_admin=add_admin", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error:  (xhr, textStatus, errorThrown) => showPopupMessage("error", xhr.responseText, 8),
            success: responseObject => {

                showPopupMessage(responseObject.type, responseObject.msg, 8);
            }
        });
    });
});