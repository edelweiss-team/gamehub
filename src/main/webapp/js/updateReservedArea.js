var changeUserListener = ev =>{
    ev.preventDefault();

    var $target = $(ev.target);
    var $editableContent = $target.closest(".info-table-body-row").find(".can-be-editable");
    var $updateContent = $target.closest(".info-table-body-row").find(".form-container");
    var fd = new FormData();

    if($target.val() == "Edit") {
        $editableContent.prop("contenteditable", true);
        $target.val("Submit");
    } else {
        $editableContent.removeAttr("contenteditable");
        $target.val("Edit");

        fd.append("editable-username", $(".editable-username").text());
        fd.append("editable-name", $(".editable-name").text());
        fd.append("editable-surname", $(".editable-surname").text());
        fd.append("editable-birthDate", $(".editable-birthDate").text());
        fd.append("editable-telephone", $(".editable-telephone").text());
        fd.append("old-username", $updateContent.find(".changeUserOldUsername").val());
        fd.append("editable-mail", $(".editable-mail").text());
        fd.append("editable-sex", $(".editable-sex").text());
        fd.append("editable-address", $(".editable-address").text());
        fd.append("editable-city", $(".editable-city").text());
        fd.append("editable-country", $(".editable-country").text());
        fd.append("table-triggered", ev.target.id);

        $.ajax("changeUser", {
            method: "POST",
            dataType: "json",
            enctype: "multipart/form-data",
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: ev => alert("Request failed on Reserved Area page."),
            success: responseObject =>{
                let msg, type, updatedUser, oldUsername, $editedRow, targetRow;

                updatedUser = responseObject.updatedUser;
                oldUsername = responseObject.oldUsername;
                targetRow = responseObject.targetRow;
                msg = responseObject.message;
                type = responseObject.type;
                if(targetRow == "1") {
                    $editedRow = $(document.getElementById("1tr"));
                    $editedRow.find(".changeUserOldName").val(oldUsername);
                    $editedRow.find(".editable-username").text(updatedUser.username);
                    $editedRow.find(".editable-name").text(updatedUser.name);
                    $editedRow.find(".editable-surname").text(updatedUser.surname);
                    $editedRow.find(".editable-birthDate").text(updatedUser.birthDate);
                    $editedRow.find(".editable-telephone").text(updatedUser.telephone);
                }
                if(targetRow == "2") {
                    $editedRow = $(document.getElementById("2tr"));
                    $editedRow.find(".changeUserOldName").val(oldUsername);
                    $editedRow.find(".editable-mail").text(updatedUser.mail);
                    $editedRow.find(".editable-sex").text(updatedUser.sex);
                    $editedRow.find(".editable-address").text(updatedUser.address);
                    $editedRow.find(".editable-city").text(updatedUser.city);
                    $editedRow.find(".editable-country").text(updatedUser.country);
                }

                showPopupMessage(type, msg, 8);
            }

        });
    }
};

var validateUserTableRowInputsListener = ev =>{

};

var validatePasswordListener = ev =>{
    ev.preventDefault();

    var $target = $(ev.target);
    var $editableContent = $(".editable-password");
    var $updateContent = $target.closest(".info-table-body-row").find(".changeUserForm");
    var fd = new FormData();

    if($target.val() == "Edit") {
        $editableContent.prop("contenteditable", true);
        $target.val("Submit");
    } else {
        $editableContent.removeAttr("contenteditable");
        $target.val("Edit");

        fd.append("editable-password", $(".editable-password").text());
        fd.append("editable-username", $(".editable-username").text());
        fd.append("editable-name", $(".editable-name").text());
        fd.append("editable-surname", $(".editable-surname").text());
        fd.append("editable-birthDate", $(".editable-birthDate").text());
        fd.append("editable-telephone", $(".editable-telephone").text());
        fd.append("old-username", $(".editable-username").text());
        fd.append("editable-mail", $(".editable-mail").text());
        fd.append("editable-sex", $(".editable-sex").text());
        fd.append("editable-address", $(".editable-address").text());
        fd.append("editable-city", $(".editable-city").text());
        fd.append("editable-country", $(".editable-country").text());
        fd.append("table-triggered", ev.target.id);

        $.ajax("changeUser", {
            method: "POST",
            dataType: "json",
            enctype: "multipart/form-data",
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            error: ev => alert("Request failed on Reserved Area page."),
            success: responseObject =>{
                let msg, type;
                msg = responseObject.message;
                type = responseObject.type;
                showPopupMessage(type, msg, 8);
            }
        });
    }

}

$(document).ready(function () {
    $(".changeUserReservedAreaButton").on("click", changeUserListener);

    $(".changePasswordReservedArea").on("click", validatePasswordListener);
    $(".info-table-body-row .editable-username, .info-table-body-row .editable-name, .info-table-body-row .editable-surname, .info-table-body-row .editable-birthDate, .info-table-body-row .editable-telephone").on("input", validateUserTableRowInputsListener);
});