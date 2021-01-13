//sign up form script

//show date as text
var birthDateInput = document.getElementById("birthdate");
birthDateInput.addEventListener("focus", ev => {
    changeTypeDateText(birthDateInput);
});
birthDateInput.addEventListener("blur", ev => {
    changeTypeDateText(birthDateInput);
});

//show password buttons
setShowPasswordBtnListeners("signUpForm");

//form validation

/*
    //INPUT: Un form f con due campi password pf0, pf1
    //OUTPUT: Controlla se il pf0.value = pf1.value. Se sono diversi, allora disabilitiamo il bottone submit,
              e settiamo il messaggio d'errore.
*/
function checkPasswordMatch(){
    let passwordFields = document.querySelectorAll("form input.passwordInput"),
        errorMessage = document.getElementById("errorMessage"),
        msg ="Errore: le password inserite non corrispondono!";
    if(passwordFields[0].value != passwordFields[1].value){
        errorMessage.textContent = msg;
        errorMessage.classList.add("toShow");
        document.getElementById("submitBtn").setAttribute("disabled", "disabled");
        return false;
    }
    return true;
}

function validateSignUpForm() {
    let signUpForm = document.forms["signUpForm"], errorMessage = document.getElementById("errorMessage"),
        flag = checkFormTextFieldsLength("signUpForm"),
        submitBtn = document.getElementById("submitBtn");
    if (flag === 1)
        errorMessage.textContent = "Errore: le info di registrazione non possono essere vuote!";
    else if (flag === 2)
        errorMessage.textContent = "Errore: il testo che hai inserito è troppo lungo! Il massimo è 50 caratteri.";
    if (flag > 0) {
        errorMessage.classList.add("toShow");
        document.getElementById("submitBtn").setAttribute("disabled", "disabled");
        return;
    }
    if (!checkPasswordMatch() || !checkPassword(signUpForm["password"]) ||
        !checkFirstNameOrLastName(signUpForm["name"]) || !checkFirstNameOrLastName(signUpForm["surname"]) ||
        !checkAddress(signUpForm["address"]) ||  !checkCity(signUpForm["city"])
         || !checkTelephone(signUpForm["telephone"]) || !checkCountry(signUpForm["country"]) || !checkSex(signUpForm["sex"])) {
        errorMessage.classList.add("toShow");
        return;
    }
    errorMessage.classList.remove("toShow");
    errorMessage.textContent = "";
    submitBtn.removeAttribute("disabled");
    checkNewUsername(signUpForm["username"]);
    checkEmail(signUpForm["mail"]);
}

let addValidateFormListener;
(addValidateFormListener = function () {
    let signUpForm = document.forms["signUpForm"], elements = signUpForm.elements;
    for(let field of elements){
        field.addEventListener("input", ev => {validateSignUpForm()});
    }
})();

let submitSignUpButtonContainer = document.getElementById("submitSignUpButtonContainer");
submitSignUpButtonContainer.addEventListener("click", ev => validateSignUpForm());
