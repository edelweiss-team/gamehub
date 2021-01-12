//switch section
function switchSectionOfPage(x) {
    let user = document.getElementById("user-div");
    let digitalproduct = document.getElementById("digitalproduct-div");
    let physicalproduct = document.getElementById("physicalproduct-div");
    let category = document.getElementById("category-div");
    let operator = document.getElementById("operator-div");
    let moderator = document.getElementById("moderator-div");
    let admin = document.getElementById("admin-div");

    let prevoiusCurrent = document.querySelector(".left-box-item button.current");
    prevoiusCurrent.classList.remove("current");
    x.classList.add("current");
    if (x.id == "user") {
        user.style.display = "block";
        digitalproduct.style.display = "none";
        physicalproduct.style.display = "none";
        category.style.display = "none";
        operator.style.display = "none";
        moderator.style.display = "none";
        admin.style.display = "none";
    }
    if (x.id == "digitalproduct") {
        user.style.display = "none";
        digitalproduct.style.display = "block";
        physicalproduct.style.display = "none";
        user.style.display = "none";
        category.style.display = "none";
        operator.style.display = "none";
        moderator.style.display = "none";
        admin.style.display = "none";
    }
    if (x.id == "physicalproduct") {
        user.style.display = "none";
        digitalproduct.style.display = "none";
        physicalproduct.style.display = "block";
        user.style.display = "none";
        category.style.display = "none";
        operator.style.display = "none";
        moderator.style.display = "none";
        admin.style.display = "none";
    }
    if (x.id == "category") {
        user.style.display = "none";
        digitalproduct.style.display = "none";
        physicalproduct.style.display = "none";
        user.style.display = "none";
        category.style.display = "block";
        operator.style.display = "none";
        moderator.style.display = "none";
        admin.style.display = "none";
    }
    if (x.id == "operator") {
        user.style.display = "none";
        digitalproduct.style.display = "none";
        physicalproduct.style.display = "none";
        user.style.display = "none";
        category.style.display = "none";
        operator.style.display = "block";
        moderator.style.display = "none";
        admin.style.display = "none";
    }
    if (x.id == "moderator") {
        user.style.display = "none";
        digitalproduct.style.display = "none";
        physicalproduct.style.display = "none";
        user.style.display = "none";
        category.style.display = "none";
        operator.style.display = "none";
        moderator.style.display = "block";
        admin.style.display = "none";
    }
    if (x.id == "admin") {
        user.style.display = "none";
        digitalproduct.style.display = "none";
        physicalproduct.style.display = "none";
        user.style.display = "none";
        category.style.display = "none";
        operator.style.display = "none";
        moderator.style.display = "none";
        admin.style.display = "block";
    }
}

function setSwitchSectionOfPageListeners(){
    let leftBoxItemList = document.querySelectorAll(".left-box-item button");
    leftBoxItemList.forEach(leftBoxItem =>
        leftBoxItem.addEventListener("click", ev => switchSectionOfPage(leftBoxItem)));
}

setSwitchSectionOfPageListeners();

//show dateInput as date/text
function setDateTypeChangeListeners(){
    let dateInputList = document.querySelectorAll(".dateInput");
    for(let dateInput of dateInputList) {
        dateInput.addEventListener("focus", ev => changeTypeDateText(dateInput));
        dateInput.addEventListener("blur", ev => changeTypeDateText(dateInput));
    }
}

setDateTypeChangeListeners();
