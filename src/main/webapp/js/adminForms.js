function hasEmptyTextAdmin(element){
    let text = element.value;
    return text.length === 0 || text.length == 0 || text === "" || text == "" || text == null;
}

function shortTextIsTooLongAdmin(element) {
    let text = element.value;
    return text.length > 50;
}

function checkFormLengthAdmin(formName) {
    var form = document.forms[formName];
    var elements = form.elements;
    for(let i = 0; i < elements.length; i++){
        if(elements[i].getAttribute("type") != null &&
            elements[i].getAttribute("type") != undefined &&
            elements[i].getAttribute("type") == "text"){
            if(hasEmptyTextAdmin(elements[i]))
                return 1;
            else if(shortTextIsTooLongAdmin(elements[i]))
                return 2;
        }
    }
    return 0;
}

function checkCategoryName(categoryNameField){
    let categoryName = categoryNameField.value;
    let pattern = new RegExp("^(([A-Za-z][a-z0-9]*([-'\\s\\.]))*([A-Za-z0-9][a-z0-9]*))$");
    if(pattern.test(categoryName) && categoryName.length >= 3 && categoryName.length <= 50)
        return true;
    else{
        $(".submitBtn").prop("disabled", true);
        return false;
    }
}

function checkImage(imageField) {
    return true;
}

function checkDescription(descriptionField){
    let description = descriptionField.value;
    if(description.length >= 10 && description.length <= 1000)
        return true;
    else{
        $(".submitBtn").prop("disabled", true);
        return false;
    }
}

function addEmptyCheckToTextFieldsAdmin(formName){
    let form = document.forms[formName], elements = form.elements;
    for(let i=0; i < elements.length; i++){
        if(elements[i].getAttribute("type") != null &&
            elements[i].getAttribute("type") !== undefined &&
            (elements[i].getAttribute("type") == "text" ||
                elements[i].getAttribute("type") == "file")){
            elements[i].addEventListener("input", checkFieldChangeListenerAdmin);
        }
    }
}


var checkFieldChangeListenerAdmin = ev => {
    let form = document.getElementById(ev.target.form.id);
    if(checkFormLengthAdmin(form.name) > 0) { //l'evento è sempre generato da un input del form
        $(".submitBtn").prop("disabled", true);
    }
    else {
        //$(".submitBtn").removeProp("disabled");
        $(".submitBtn").removeAttr("disabled");
    }
};

var setErrorListenerAdminAddCategory = ev =>{
    let flag = checkFormLengthAdmin("addCategoryForm");
    let errorMessage = document.getElementById("errorMessageAddCategory");
    if (flag === 1)
        errorMessage.textContent = "Errore: i campi non possono essere vuoti";
    if(flag === 2)
        errorMessage.textContent = "Errore: il testo che hai inserito è troppo lungo! Max:30 caratteri";
    if(flag > 0)
        errorMessage.classList.add("toShow");
};


let addCategoryForm = document.getElementById("addCategoryForm"),
    submitAdminButtonContainerAddCategory = document.getElementById("submitAdminButtonContainerAddCategory");

addEmptyCheckToTextFieldsAdmin("addCategoryForm");
submitAdminButtonContainerAddCategory.addEventListener("click", setErrorListenerAdminAddCategory);
$(addCategoryForm).on("input", ev => {
    let nameField = document.getElementById("categoryName");
    let imageField = document.getElementById("image_path");
    let descriptionField = document.getElementById("description_category");
    let errorMessage = document.getElementById("errorMessageAddCategory");
    let flag = true;

    if(!checkCategoryName(nameField)){
        errorMessage.textContent = "Errore: nome categoria";
        errorMessage.classList.add("toShow");
        flag = false;
    }
    if(!checkImage(imageField)){
        errorMessage.textContent = "Errore: immagine";
        errorMessage.classList.add("toShow");
        flag = false;
    }
    if(!checkDescription(descriptionField)){
        errorMessage.textContent = "Errore: descrizione";
        errorMessage.classList.add("toShow");
        flag = false;
    }
    if(flag) {
        errorMessage.classList.remove("toShow");
        $(".submitBtn").removeAttr("disabled");
    }
});
addCategoryForm.onsubmit = ev =>{
    let nameField = document.getElementById("categoryName");
    let imageField = document.getElementById("image_path");
    let descriptionField = document.getElementById("description_category");
    let errorMessage = document.getElementById("errorMessageAddCategory");
    let flag = true;

    if(!checkCategoryName(nameField)){
        errorMessage.textContent = "Errore: nome categoria";
        errorMessage.classList.add("toShow");
        flag = false;
    }
    else if(!checkImage(imageField)){
        errorMessage.textContent = "Errore: immagine";
        errorMessage.classList.add("toShow");
        flag = false;
    }
    else if(!checkDescription(descriptionField)){
        errorMessage.textContent = "Errore: descrizione";
        errorMessage.classList.add("toShow");
        flag = false;
    }
    if(flag)
        errorMessage.classList.remove("toShow");
    return flag;
};