function switchSectionOfPage(x) {
    let users = document.getElementById("users-div");
    let products = document.getElementById("products-div");
    let categories = document.getElementById("categories-div");
    let operators = document.getElementById("operators-div");
    let moderators = document.getElementById("moderators-div");
    let admins = document.getElementById("admins-div");

    if(x.id == "user"){
        users.style.display = "block";
        products.style.display = "none";
        categories.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "product"){
        users.style.display = "none";
        products.style.display = "block";
        categories.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "category"){
        users.style.display = "none";
        products.style.display = "none";
        categories.style.display = "block";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "operator"){
        users.style.display = "none";
        products.style.display = "none";
        categories.style.display = "none";
        operators.style.display = "block";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "moderator"){
        users.style.display = "none";
        products.style.display = "none";
        categories.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "block";
        admins.style.display = "none";
    }
    if(x.id == "admin"){
        users.style.display = "none";
        products.style.display = "none";
        categories.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "block";
    }
}

function setSwitchSectionOfPageListener(){
    let boxItemList = document.querySelectorAll(".admin-fieldset button");
    boxItemList.forEach(boxItem =>
        boxItem.addEventListener("click", ev =>switchSectionOfPage(boxItem)));
}

setSwitchSectionOfPageListener();