function getMoreOperatorsPaging(startingIndex) {
    $.ajax("get-more-operators?operatorsPerRequest=4&startingIndex="+startingIndex,{
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on operator page " + startingIndex/4 + " failed."),
        success: responseObject =>{
            let newOperators = responseObject.newOperators, $targetPage = $(".operators-table-body");
            $targetPage.empty();
            if(isFirstSearch){
                let newMaxPage = responseObject.newMaxPages;
                for(let i = newMaxPage+1; i <= maxPageOperators; i++){
                    $("#pageOperators"+i).remove();
                }
                for(let i = maxPageOperators+1; i <= newMaxPage; i++){
                    if(i == 1)
                        $("#pageOperators"+(i-1)).after("<span class='current visible pageNumBtnOperatorAdmin' id='pageOperators"+i+"'> "+i+" </span>");
                    else if(i <= 4)
                        $("#pageOperators"+(i-1)).after("<span class='pageNumBtnOperatorAdmin visible' id='pageOperators"+i+"'> "+i+" </span>");
                    else
                        $("#pageOperators"+(i-1)).after("<span class='pageNumBtnOperatorAdmin' id='pageOperators"+i+"'> "+i+" </span>");
                    $("#pageOperators"+i).on("click", paginationOperatorsListener);
                }
                maxPageOperators = newMaxPage;
            }
            else{
                //se la pagina è vuota, a meno che non sia la prima, cancelliamola e ritorniamo alla prima pagina
                if(newOperators.length == 0 && startingIndex != 0){
                    $(".paginationOperators #pageOperators" + maxPageOperators).remove();
                    maxPageOperators--;
                    $("#pageOperators1").click();
                }
                for(let operator of newOperators){
                    $targetPage.append("<tr id='" + operator.username + "OperatorRow' class='operators-table-body-row'>\n" +
                        "                            <td> "+ operator.username +" </td>\n" +
                        "                            <td> "+ operator.curriculum +"</td>\n" +
                        "                            <td class='form-container'>\n" +
                        "                                <form name='removeOperatorForm' class='removeOperatorForm' method='post' action='removeOperator-servlet'>\n" +
                        "                                    <input type='hidden' value='"+operator.username+"' name='removeOperator' class='operatorNameForRemove'>\n" +
                        "                                    <input type='submit' value='✗' class='removeOperatorAdminButton'>\n" +
                        "                                </form>\n" +
                        "                            </td>\n" +
                        "                        </tr>")
                }
                //async Operator removal
                $(".removeOperatorForm").on("submit", ev => ev.preventDefault());
                $(".removeOperatorAdminButton").on("click", removeOperatorListener);
            }
        }
    });
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
    $.ajax("remove-operator?removeOperator=" + operatorName, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request of operator " + operatorName + " removal failed."),
        success: responseObject => {
            let removedOperatorName = responseObject.removedOperatorName, type= responseObject.type,
                msg = responseObject.msg;
            if(removedOperatorName != null && removedOperatorName != undefined)
                $(document.getElementById(removedOperatorName + "OperatorRow")).remove();
            //spostiamoci sull'ultima pagina per eliminare eventualemente delle pagine vuotex
            //$("#pageOperators" + maxPageOperators).click();
            //triggeriamo la ricerca per cancellare eventuali pagine vuote eventuali nuove pages
            $("#searchBarContainerOperatorAdmin button[type=submit]").click();
            showPopupMessage(type, msg, 8);
        }
    });
};

$(document).ready(function () {

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

    //async Operator removal
    $(".removeOperatorForm").on("submit", ev => ev.preventDefault());
    $(".removeOperatorAdminButton").on("click", removeOperatorListener);

    //async Operator add
    $("#addOperatorForm").on("submit", ev => ev.preventDefault());

    $("#submitAdminButtonContainerAddOperator input[type=submit]").on("click", ev => {
        ev.preventDefault();
        let fd = new FormData(document.getElementById("addOperatorForm"));
        $.ajax("add-operator", {
            method: "POST",
            dataType: "json",
            enctype : 'multipart/form-data',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: ev => alert("Request on operator adding failed."),
            success: responseObject => {
                //triggeriamo la ricerca per generare eventuali nuove pages
                $("#searchBarContainerOperatorAdmin button[type=submit]").click();
                showPopupMessage(responseObject.type, responseObject.msg, 8);
            }
        });
    });
})