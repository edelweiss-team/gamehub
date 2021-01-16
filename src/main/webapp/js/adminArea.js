function switchSectionOfPage(x) {
    let users = document.getElementById("users-div");
    let productsDigital = document.getElementById("productsDigital-div");
    let productsPhysical = document.getElementById("productsPhysical-div");
    let categories = document.getElementById("categories-div");
    let tags = document.getElementById("tags-div");
    let operators = document.getElementById("operators-div");
    let moderators = document.getElementById("moderators-div");
    let admins = document.getElementById("admins-div");

    if(x.id == "user"){
        users.style.display = "block";
        productsDigital.style.display = "none";
        productsPhysical.style.display = "none";
        categories.style.display = "none";
        tags.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "productDigital"){
        users.style.display = "none";
        productsDigital.style.display = "block";
        productsPhysical.style.display = "none";
        categories.style.display = "none";
        tags.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if (x.id == "productPhysical") {
        users.style.display = "none";
        productsDigital.style.display = "none";
        productsPhysical.style.display = "block";
        categories.style.display = "none";
        tags.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "category"){
        users.style.display = "none";
        productsDigital.style.display = "none";
        productsPhysical.style.display = "none";
        categories.style.display = "block";
        tags.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "tag"){
        users.style.display = "none";
        productsDigital.style.display = "none";
        productsPhysical.style.display = "none";
        categories.style.display = "none";
        tags.style.display = "block";
        operators.style.display = "none";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "operator"){
        users.style.display = "none";
        productsDigital.style.display = "none";
        productsPhysical.style.display = "none";
        categories.style.display = "none";
        tags.style.display = "none";
        operators.style.display = "block";
        moderators.style.display = "none";
        admins.style.display = "none";
    }
    if(x.id == "moderator"){
        users.style.display = "none";
        productsDigital.style.display = "none";
        productsPhysical.style.display = "none";
        categories.style.display = "none";
        tags.style.display = "none";
        operators.style.display = "none";
        moderators.style.display = "block";
        admins.style.display = "none";
    }
    if(x.id == "admin"){
        users.style.display = "none";
        productsDigital.style.display = "none";
        productsPhysical.style.display = "none";
        categories.style.display = "none";
        tags.style.display = "none";
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

