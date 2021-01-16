function getMoreModeratorsPaging(startingIndex) {
    $.ajax("get-more-moderators?moderatorsPerRequest=4&startingIndex="+startingIndex,{
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on moderator page " + startingIndex/4 + " failed."),
        success: responseObject =>{
            let newModerators = responseObject.newModerators, $targetPage = $(".moderators-table-body");
            $targetPage.empty();

            //se la pagina è vuota, a meno che non sia la prima, cancelliamola e ritorniamo alla prima pagina
            if(newModerators.length == 0 && startingIndex != 0){
                $(".paginationModerators #pageModerators" + maxPageModerators).remove();
                maxPageModerators--;
                $("#pageModerators1").click();
            }
            for(let moderator of newModerators){
                $targetPage.append("<tr id='" + moderator.username + "ModeratorRow' class='moderators-table-body-row'>\n" +
                    "                            <td class=\"can-be-editable editable-name\"> "+ moderator.username +" </td>\n" +
                    "                            <td class=\"can-be-editable editable-contractTime\"> "+ moderator.contractTime +"</td>\n" +
                    "                            <td class='form-container'>\n" +
                    "                                <form name='changeModeratorForm' class='changeModeratorForm' method='post' action='manageModerator-servlet'>\n" +
                    "                                    <input type='hidden' value='"+moderator.username+"' name='changeModerator' class='changeModeratorOldName'>\n" +
                    "                                    <input type='submit' value=\"📝\" class='changeModeratorAdminButton'>\n" +
                    "                                </form>\n" +
                    "                            </td>\n" +
                    "                            <td class='form-container'>\n" +
                    "                                <form name='removeModeratorForm' class='removeModeratorForm' method='post' action='manageModerator-servlet'>\n" +
                    "                                    <input type='hidden' value='"+moderator.username+"' name='removeModerator' class='moderatorNameForRemove'>\n" +
                    "                                    <input type='submit' value='✗' class='removeModeratorAdminButton'>\n" +
                    "                                </form>\n" +
                    "                            </td>\n" +
                    "                        </tr>")
            }
            //async Moderator removal
            $(".removeModeratorForm").on("submit", ev => ev.preventDefault());
            $(".removeModeratorAdminButton").on("click", removeModeratorListener);
            $(".changeModeratorAdminButton").on("click", changeModeratorListener);
        }

    });
}

var changeModeratorListener = ev =>{
    //impediamo il submit del form
    ev.preventDefault();

    var $target = $(ev.target);
    var $editableContent = $target.closest(".moderators-table-body-row").find(".can-be-editable");
    var $updateContent = $target.closest(".moderators-table-body-row").find(".form-container");
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
        fd.append("editable-contractTime", $editableContent.filter(".editable-contractTime").text());
        fd.append("old-name", $updateContent.find(".changeModeratorOldName").val());

        //inviamo la richiesta asincrona
        $.ajax("manage-moderator?manage_moderator=update_moderator", {
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
                $editedRow = $(document.getElementById(responseObject.username + "ModeratorRow"));
                msg = responseObject.message;
                type = responseObject.type;

                //eventuale codice per visualizzare l'immagine
                $editedRow.find(".editable-contractTime span").text(responseObject.contractTime);
                $editedRow.prop("id", responseObject.username + "ModeratorRow");


                //mostriamo il messaggio di popup
                showPopupMessage(type, msg, 8);
            }
        });

    }
}


var paginationModeratorsListener = ev =>{
    let $target = $(ev.target), targetIdNum, $currentPage = $(".paginationModerators span.current"), $pageBtn,
        $targetPageBtn;

    if($target.prop("id") == "ellipseSxModerators")
        $target = $(".pageNumBtnModeratorAdmin.visible").first().prev();
    if($target.prop("id") == "ellipseDxModerators")
        $target = $(".pageNumBtnModeratorAdmin.visible").last().next();
    if($target.prop("id") == "previousPageModerators"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == 1)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.prev("span.pageNumBtnModeratorAdmin");
    }
    else if($target.prop("id") == "nextPageModerators"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == maxPageModerators)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.next("span.pageNumBtnModeratorAdmin");
    }

    targetIdNum = parseInt($target.text());
    $targetPageBtn = $("#pageModerators"+targetIdNum);
    $currentPage.removeClass("current");
    $targetPageBtn.addClass("current");

    $(".paginationModerators .pageNumBtnModeratorAdmin.visible").removeClass("visible");
    if(targetIdNum == 1 || targetIdNum == 2 || maxPageModerators <= 4){
        for(let i = 1; i <= 4; i++){
            $pageBtn = $("#pageModerators"+i);
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else if(targetIdNum == maxPageModerators || targetIdNum == maxPageModerators){
        for(let i = 0; i <= 3; i++){
            $pageBtn = $("#pageModerators"+(maxPageModerators-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else{
        $targetPageBtn.addClass("visible");
        for(let i = 1; i <= 2; i++){
            $pageBtn = $("#pageModerators"+(targetIdNum-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
            $pageBtn = $("#pageModerators"+(targetIdNum+i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }

    if(parseInt($(".pageNumBtnModeratorAdmin.visible").first().text()) != 1)
        $("#ellipseSxModerators").addClass("visible");
    else{
        $("#ellipseSxModerators").removeClass("visible");
    }
    if(parseInt($(".pageNumBtnModeratorAdmin.visible").last().text()) != maxPageModerators)
        $("#ellipseDxModerators").addClass("visible");
    else
        $("#ellipseDxModerators").removeClass("visible");

    getMoreModeratorsPaging((targetIdNum-1)*4);
};

var removeModeratorListener = ev => {
    ev.preventDefault();
    let $targetRow = $(ev.target).closest("tr"), moderatorName;
    moderatorName = $targetRow.find(".moderatorNameForRemove").val();
    $.ajax("manage-moderator?manage_moderator=remove_moderator&removeModerator=" + moderatorName, {
        method: "GET",
        dataType: "json",
        error: ev => (xhr, textStatus, errorThrown) => showPopupMessage("error", xhr.responseText, 8),
        success: responseObject => {
            let msg, type;

            //leggiamo la risposta json
            $(document.getElementById(responseObject.username + "ModeratorRow")).remove();
            msg = responseObject.msg;
            type = responseObject.type;

            //mostriamo il messaggio di popup
            showPopupMessage(type, msg, 8);
        }
    });
};

$(document).ready(function () {

    $(".changeModeratorAdminButton").on("click", changeModeratorListener);


    //paginazione
    $(".paginationModerators span").on("click", paginationModeratorsListener);
    $(".searchBarModeratorAdmin button").on("click", ev =>{
        ev.preventDefault();
        if($(".searchBarModeratorAdmin input[type=text]").val().trim().match(new RegExp("^[a-zA-Z0-9]*$"))){
            getMoreModeratorsPaging(0, $(".searchBarModeratorAdmin input[type=text]").val().trim(), true);
            $("#pageModerators1").click();
        }
        else
            alert("You must enter a valid name (numbers or letters)");
    });

    //validation
    $("#submitAdminButtonContainerAddModerator, #contractTimeModerator, #moderatorName").on("click input", ev => {
        ev.preventDefault();
        let $errorMessage = $("#errorMessageAddModerator");
        let $submit =  $("#submitAdminButtonContainerAddModerator input[type=submit]");
        $submit.prop("disabled", false);
        $errorMessage.removeClass("visible");
        if(!$("#contractTimeModerator")[0].checkValidity()) {
            $errorMessage.text("Error: date must be like yyyy-mm-dd");
            $submit.prop("disabled", true);
            $errorMessage.addClass("visible");
        }
        else if(!$("#moderatorName")[0].checkValidity()) {
            $errorMessage.text("Error: username has to be between " +
                "6 and 20 characters and contain letters and numbers!");
            $submit.prop("disabled", true);
            $errorMessage.addClass("visible");
        }
    });

    //async Moderator removal
    $(".removeModeratorForm").on("submit", ev => ev.preventDefault());
    $(".removeModeratorAdminButton").on("click", removeModeratorListener);

    //async Moderator add
    $("#addModeratorForm").on("submit", ev => ev.preventDefault());

    $("#submitAdminButtonContainerAddModerator input[type=submit]").on("click", ev => {
        ev.preventDefault();
        let fd = new FormData(document.getElementById("addModeratorForm"));
        $.ajax("manage-moderator?manage_moderator=add_moderator", {
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