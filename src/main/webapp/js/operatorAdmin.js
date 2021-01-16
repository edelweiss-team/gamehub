function getMoreOperatorsPaging(startingIndex) {
    $.ajax("get-more-operators?operatorsPerRequest=4&startingIndex="+startingIndex,{
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on operator page " + startingIndex/4 + " failed."),
        success: responseObject =>{
            let newOperators = responseObject.newOperators, $targetPage = $(".operators-table-body");
            $targetPage.empty();

            //se la pagina è vuota, a meno che non sia la prima, cancelliamola e ritorniamo alla prima pagina
            if(newOperators.length == 0 && startingIndex != 0){
                $(".paginationOperators #pageOperators" + maxPageOperators).remove();
                maxPageOperators--;
                $("#pageOperators1").click();
            }
            for(let operator of newOperators){
                $targetPage.append("<tr id='" + operator.username + "OperatorRow' class='operators-table-body-row'>\n" +
                    "                            <td class=\"can-be-editable editable-name\"> "+ operator.username +" </td>\n" +
                    "                            <td class=\"can-be-editable editable-cv\"> "+ operator.cv +"</td>\n" +
                    "                            <td class=\"can-be-editable editable-contractTime\"> "+ operator.contractTime +"</td>\n" +
                    "                            <td class='form-container'>\n" +
                    "                                <form name='changeOperatorForm' class='changeOperatorForm' method='post' action='manageOperator-servlet'>\n" +
                    "                                    <input type='hidden' value='"+operator.username+"' name='changeOperator' class='changeOperatorOldName'>\n" +
                    "                                    <input type='submit' value=\"📝\" class='changeOperatorAdminButton'>\n" +
                    "                                </form>\n" +
                    "                            </td>\n" +
                    "                            <td class='form-container'>\n" +
                    "                                <form name='removeOperatorForm' class='removeOperatorForm' method='post' action='manageOperator-servlet'>\n" +
                    "                                    <input type='hidden' value='"+operator.username+"' name='removeOperator' class='operatorNameForRemove'>\n" +
                    "                                    <input type='submit' value='✗' class='removeOperatorAdminButton'>\n" +
                    "                                </form>\n" +
                    "                            </td>\n" +
                    "                        </tr>")
            }
            //async Operator removal
            $(".removeOperatorForm").on("submit", ev => ev.preventDefault());
            $(".removeOperatorAdminButton").on("click", removeOperatorListener);
            $(".changeOperatorAdminButton").on("click", changeOperatorListener);
        }

    });
}

var changeOperatorListener = ev =>{
    //impediamo il submit del form
    ev.preventDefault();

    var $target = $(ev.target);
    var $editableContent = $target.closest(".operators-table-body-row").find(".can-be-editable");
    var $updateContent = $target.closest(".operators-table-body-row").find(".form-container");
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
        fd.append("editable-cv", $editableContent.filter(".editable-cv").text());
        fd.append("editable-contractTime", $editableContent.filter(".editable-contractTime").text());
        fd.append("old-name", $updateContent.find(".changeOperatorOldName").val());

        //inviamo la richiesta asincrona
        $.ajax("manage-operator?manage_operator=update_operator", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: (xhr, textStatus, errorThrown) =>
                showPopupMessage("error", "Error in cv or contractTime", 8),
            success: responseObject => {
                let msg, type,  $editedRow;

                //leggiamo la risposta json
                $editedRow = $(document.getElementById(responseObject.username + "OperatorRow"));
                msg = responseObject.message;
                type = responseObject.type;

                //eventuale codice per visualizzare l'immagine
                $editedRow.find(".editable-contractTime span").text(responseObject.contractTime);
                $editedRow.find(".editable-cv").text(responseObject.cv);
                $editedRow.prop("id", responseObject.username + "OperatorRow");


                //mostriamo il messaggio di popup
                showPopupMessage(type, msg, 8);
            }
        });

    }
}


var paginationOperatorsListener = ev =>{
    let $target = $(ev.target), targetIdNum, $currentPage = $(".paginationOperators span.current"), $pageBtn,
        $targetPageBtn;

    if($target.prop("id") == "ellipseSxOperators")
        $target = $(".pageNumBtnOperatorAdmin.visible").first().prev();
    if($target.prop("id") == "ellipseDxOperators")
        $target = $(".pageNumBtnOperatorAdmin.visible").last().next();
    if($target.prop("id") == "previousPageOperators"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == 1)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.prev("span.pageNumBtnOperatorAdmin");
    }
    else if($target.prop("id") == "nextPageOperators"){
        let currentPageNum;
        currentPageNum = parseInt($currentPage.text());
        if(currentPageNum == maxPageOperators)
            return;
        $currentPage.removeClass("current");
        $target = $currentPage.next("span.pageNumBtnOperatorAdmin");
    }

    targetIdNum = parseInt($target.text());
    $targetPageBtn = $("#pageOperators"+targetIdNum);
    $currentPage.removeClass("current");
    $targetPageBtn.addClass("current");

    $(".paginationOperators .pageNumBtnOperatorAdmin.visible").removeClass("visible");
    if(targetIdNum == 1 || targetIdNum == 2 || maxPageOperators <= 4){
        for(let i = 1; i <= 4; i++){
            $pageBtn = $("#pageOperators"+i);
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else if(targetIdNum == maxPageOperators || targetIdNum == maxPageOperators){
        for(let i = 0; i <= 3; i++){
            $pageBtn = $("#pageOperators"+(maxPageOperators-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }
    else{
        $targetPageBtn.addClass("visible");
        for(let i = 1; i <= 2; i++){
            $pageBtn = $("#pageOperators"+(targetIdNum-i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
            $pageBtn = $("#pageOperators"+(targetIdNum+i));
            if($pageBtn.length > 0)
                $pageBtn.addClass("visible");
        }
    }

    if(parseInt($(".pageNumBtnOperatorAdmin.visible").first().text()) != 1)
        $("#ellipseSxOperators").addClass("visible");
    else{
        $("#ellipseSxOperators").removeClass("visible");
    }
    if(parseInt($(".pageNumBtnOperatorAdmin.visible").last().text()) != maxPageOperators)
        $("#ellipseDxOperators").addClass("visible");
    else
        $("#ellipseDxOperators").removeClass("visible");

    getMoreOperatorsPaging((targetIdNum-1)*4);
};

var removeOperatorListener = ev => {
    ev.preventDefault();
    let $targetRow = $(ev.target).closest("tr"), operatorName;
    operatorName = $targetRow.find(".operatorNameForRemove").val();
    $.ajax("manage-operator?manage_operator=remove_operator&removeOperator=" + operatorName, {
        method: "GET",
        dataType: "json",
        error: ev => (xhr, textStatus, errorThrown) => showPopupMessage("error", xhr.responseText, 8),
        success: responseObject => {
            let msg, type;

            //leggiamo la risposta json
            $(document.getElementById(responseObject.username + "OperatorRow")).remove();
            msg = responseObject.msg;
            type = responseObject.type;

            //mostriamo il messaggio di popup
            showPopupMessage(type, msg, 8);
        }
    });
};

$(document).ready(function () {

    $(".changeOperatorAdminButton").on("click", changeOperatorListener);


    //paginazione
    $(".paginationOperators span").on("click", paginationOperatorsListener);
    $(".searchBarOperatorAdmin button").on("click", ev =>{
        ev.preventDefault();
        if($(".searchBarOperatorAdmin input[type=text]").val().trim().match(new RegExp("^[a-zA-Z0-9]*$"))){
            getMoreOperatorsPaging(0, $(".searchBarOperatorAdmin input[type=text]").val().trim(), true);
            $("#pageOperators1").click();
        }
        else
            alert("You must enter a valid name (numbers or letters)");
    });

    //validation
    $("#submitAdminButtonContainerAddOperator, #contractTime, #curriculum, #userName").on("click input", ev => {
        ev.preventDefault();
        let $errorMessage = $("#errorMessageAddOperator");
        let $submit =  $("#submitAdminButtonContainerAddOperator input[type=submit]");
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

    //async Operator removal
    $(".removeOperatorForm").on("submit", ev => ev.preventDefault());
    $(".removeOperatorAdminButton").on("click", removeOperatorListener);

    //async Operator add
    $("#addOperatorForm").on("submit", ev => ev.preventDefault());

    $("#submitAdminButtonContainerAddOperator input[type=submit]").on("click", ev => {
        ev.preventDefault();
        let fd = new FormData(document.getElementById("addOperatorForm"));
        $.ajax("manage-operator?manage_operator=add_operator", {
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