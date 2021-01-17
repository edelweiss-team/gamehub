$(".dislike").click(function(e) {
    e.preventDefault();
    $(".like").css("background-color", "rgba(38,166,154 ,0.3)");
    $(".dislike").css("background-color", "navajowhite");
    $.ajax("vote-personalization?vote=false", {
        method: "GET",
        error: ev => alert("Request failed."),
        success: responseText => {
            let responseObject = JSON.parse(responseText), msg, type;
            msg = responseObject.msg;
            type = responseObject.type;

            if(type != "error") {
                $(".like").css("background-color", "rgba(38,166,154 ,0.3)");
                $(".dislike").css("background-color", "navajowhite");
            }

            //mostriamo il messaggio popup che viene nascosto dopo 5 secondi
            showPopupMessage(type, msg, 5);
        }
    });
});

$(".like").click(function(e) {
    e.preventDefault();
    $(".like").css("background-color", "navajowhite");
    $(".dislike").css("background-color", "rgba(255, 138, 128, 0.3");
    $.ajax("vote-personalization?vote=true", {
        method: "GET",
        error: ev => alert("Request failed."),
        success: responseText => {
            let responseObject = JSON.parse(responseText), msg, type;
            msg = responseObject.msg;
            type = responseObject.type;

            if(type != "error") {
                $(".like").css("background-color", "#ffb320");
                $(".dislike").css("background-color", "rgba(255, 138, 128, 0.3");
            }

            //mostriamo il messaggio popup che viene nascosto dopo 5 secondi
            showPopupMessage(type, msg, 5);
        }
    });
});