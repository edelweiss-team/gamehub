function getMoreCategoriesPaging(startingIndex, search=""){
    $.ajax("get-more-categories?categoriesPerRequest=8&startingIndex=" + startingIndex + "&search=" + search, {
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on category page " + startingIndex/8+ " failed."),
        success: responseObject => {
            let newCategories = responseObject.newCategories, $targetPage, targetPageNumber;

            //calcoliamo la pagina target
            targetPageNumber = parseInt($(".pagination .current").text())
            $targetPage = $("#categoryPage" + targetPageNumber);
            $targetPage.empty();
            let $targetContainer = $("#category-container");
            $targetContainer.empty();
            //impostiamola come "loaded", in quanto le sue categorie sono state caricate
            $targetPage.addClass("loaded");

            //aggiungiamo le categorie alla target page
            for(let category of newCategories) {
                //let imagePath = '/gamehub-1.0-SNAPSHOT/img/' + category.imagePath; //eventualmente sostituire
                let imagePath = 'img/'+category.imagePath;
                let imagePath2 = '"img/'+category.imagePath+'"';
                $targetContainer.append("    <div class=\"col-md-6\">\n" +
                    "                                    <div class=\"recent-game-item\">\n" +
                    "                                        <div class=\"rgi-thumb set-bg\" data-setbg=\""+imagePath+"\" style='background-image: url("+imagePath2+");'>\n" +
                    "                                            <div class=\"cata new\">\n" +
                    "                                                <form action=\"shop.html\" method=\"get\" id=\"category-form\">\n" +
                    "                                                    <button type=\"submit\" id=\"category-button\" name=\"categoryName\" value=\""+category.name+"\">\n" +
                    "                                                            "+category.name+"\n" +
                    "                                                    </button>\n" +
                    "                                                </form>\n" +
                    "                                            </div>\n" +
                    "                                        </div>\n" +
                    "                                        <div class=\"rgi-content\" id=\"category-description\">\n" +
                    "                                            <h6 style=\"color: whitesmoke\" >"+category.description+"</h6>\n" +
                    "                                        </div>\n" +
                    "                                    </div>\n" +
                    "                                </div>");
            }

            //click sulle immagini fa sì che si clicchi sul bottone
            $("#categoryPage" + targetPageNumber + " .buttonform").on("click", ev => {
                $(ev.target).children("button[type=submit]").trigger("click");
            });
        }
    });
}

$(document).ready(ev => {



    //click sulle immagini fa sì che si clicchi sul bottone
    $(".buttonform").on("click", ev => {
        $(ev.target).children("button[type=submit]").trigger("click");
    });

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
        let searchString = $("#search-form .category-search").val();
        //carichiamo la pagina dal server, partendo dalla prima categoria della pagina target
        if(!$targetPage.hasClass("loaded"))
            getMoreCategoriesPaging((targetIdNum - 1)*8, searchString);

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