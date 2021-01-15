function getMoreUsersPaging(startingIndex) {
    $.ajax("get-more-users?usersPerRequest=4&startingIndex="+startingIndex,{
        method: "GET",
        dataType: "json",
        error: ev => alert("Request failed on user page " + startingIndex/4 + " failed."),
        success: responseObject => {
            let newUsers = responseObject.newUsers, $targetPage = $(".users-table-body");
            $targetPage.empty();
            //se la pagina è vuota, a meno che non sia la prima, cancelliamola e ritorniamo alla prima pagina
            if(newUsers.length == 0 && startingIndex != 0){
                $(".paginationUsers #pageUsers" + maxPageUsers).remove();
                maxPageUsers--;
                $("#pageUsers1").click();
            }
            for(let user of newUsers){
                $targetPage.append("<tr id='"+user.username+"UserRow' class='users-table-body-row'>\n" +
                    "                            <td>"+  user.username +" </td>\n" +
                    "                            <td>" + user.mail +" </td>\n" +
                    "                            <td> "+ user.name +" </td>\n" +
                    "                            <td> "+ user.surname +" </td>\n" +
                    "                            <td> "+ user.birthDate +" </td>\n" +
                    "                            <td class='form-container'>\n" +
                    "                                <form name='removeUserForm' class='removeUserForm' method='post' action='removeUser-servlet'>\n" +
                    "                                    <input type='hidden' value='"+user.username+"' name='removeUser' class='usernameForRemove'>\n" +
                    "                                    <input type='submit' value='✗' class='removeUserAdminButton'>\n" +
                    "                                </form>\n" +
                    "                            </td>\n" +
                    "                        </tr>"
                )
            }
            //async user removal
            $(".removeUserForm").on("submit", ev => ev.preventDefault());
            $(".removeUserAdminButton").on("click", removeUserListener);
        }
    });
}

var paginationUsersListener = ev =>{
  let $target = $(ev.target), targetIdNum, $currentPage = $(".paginationUsers span.current"), $pageBtn,
  $targetPageBtn;

  if($target.prop("id") == "ellipseSxUsers")
      $target = $(".pageNumBtnUserAdmin.visible").first().prev();
  if($target.prop("id") == "ellipseDxUsers")
      $target = $(".pageNumBtnUserAdmin.visible").last().next();
  if($target.prop("id") == "previousPageUsers") {
      let currentPageNum;
      currentPageNum = parseInt($currentPage.text());
      if(currentPageNum == 1)
          return;
      $currentPage.removeClass("current");
      $target = $currentPage.prev("span.pageNumBtnUserAdmin");
  }
  else if($target.prop("id") == "nextPageUsers"){
      let currentPageNum;
      currentPageNum = parseInt($currentPage.text());
      if(currentPageNum == maxPageUsers)
          return;
      $currentPage.removeClass("current");
      $target = $currentPage.next("span.pageNumBtnUserAdmin");
  }

  targetIdNum = parseInt($target.text());
  $targetPageBtn = $("#pageUsers"+targetIdNum);
  $currentPage.removeClass("current");
  $targetPageBtn.addClass("current");

  $(".paginationUsers .pageNumBtnUserAdmin.visible").removeClass("visible");
  if(targetIdNum == 1 || targetIdNum == 2 || maxPageUsers <= 4) {
      for(let i = 1; i <= 4; i++){
          $pageBtn = $("#pageUsers"+i);
          if($pageBtn.length > 0)
              $pageBtn.addClass("visible");
      }
  }
  else if(targetIdNum == maxPageUsers || targetIdNum == maxPageUsers-1){
      for(let i = 0;i <= 3; i++){
          $pageBtn = $("#pageUsers"+(maxPageUsers-i));
          if($pageBtn.length > 0)
              $pageBtn.addClass("visible");
      }
  }
  else {
      $targetPageBtn.addClass("visible");
      for(let i = 1; i <= 2; i++){
          $pageBtn = $("#pageUsers"+(targetIdNum-i));
          if($pageBtn.length > 0)
              $pageBtn.addClass("visible");
          $pageBtn = $("#pageUsers"+(targetIdNum+i));
          if($pageBtn.length > 0)
              $pageBtn.addClass("visible");
      }
  }

    if(parseInt($(".pageNumBtnUserAdmin.visible").first().text()) != 1)
        $("#ellipseSxUsers").addClass("visible");
    else{
        $("#ellipseSxUsers").removeClass("visible");
    }
    if(parseInt($(".pageNumBtnUserAdmin.visible").last().text()) != maxPageUsers)
        $("#ellipseDxUsers").addClass("visible");
    else
        $("#ellipseDxUsers").removeClass("visible");

    getMoreUsersPaging((targetIdNum-1)*4);

};

var removeUserListener = ev => {
  ev.preventDefault();
  let $targetRow = $(ev.target).closest("tr"), username;
  username = $targetRow.find(".usernameForRemove").val();
  $.ajax("remove-user?removeUser="+username, {
      method: "GET",
      dataType: "json",
      error: ev => alert("Request of user " + username + " remove failed."),
      success: responseObject =>{
          let removedUsername = responseObject.removedUsername, type = responseObject.type,
              msg=responseObject.msg;
          if(removedUsername != null && removedUsername != undefined)
              $(document.getElementById(removedUsername+"UserRow")).remove();
          showPopupMessage(type, msg, 8);
      }
  });
};

$(document).ready(function () {

    //pagination
    $(".paginationUsers span").on("click", paginationUsersListener);

    //async user remove
    $(".removeUserForm").on("submit", ev=> ev.preventDefault());
    $(".removeUserAdminButton").on("click", removeUserListener);
})