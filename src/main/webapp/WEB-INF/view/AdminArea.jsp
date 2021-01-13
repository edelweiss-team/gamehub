<%@ page import="model.bean.User" %>
<%@ page import="model.bean.Admin" %>
<%@ page import="model.bean.Operator" %>
<%@ page import="model.bean.Moderator" %><%--
  Created by IntelliJ IDEA.
  User: Roberto Esposito
  Date: 1/1/2021
  Time: 3:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <title>Admin Area</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminArea.css" type="text/css">
    <%@include file="header.jsp"%>
</head>
<body class="admin-body">

    <div class="admin-fieldset">
        <button id="admin" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Admins</button>
        <button id="moderator" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Moderators</button>
        <button id="operator" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Operators</button>
        <button id="category" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Categories</button>
        <button id="product" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Products</button>
        <button id="user" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Users</button>
    </div>

    <div id="users-div" style="display: block">
        <h1 class='manage-header-user'>Manage users</h1>
        <div class='admin-fieldset'>
            <h3>Remove user</h3>
            <div class="table-div users-table-div">
                <table border='1' id='users-table' class='content-table'>
                    <thead>
                    <tr class='users-table-header'>
                        <th>Username</th>
                        <th>Mail</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Date of Birth</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody class='users-table-body'>
                    <c:forEach items='${firstUsers}' var='user'>
                        <tr id="${user.username}UserRow" class='users-table-body-row'>
                            <td>  ${user.username}  </td>
                            <td>  ${user.mail}  </td>
                            <td>  ${user.name}  </td>
                            <td>  ${user.surname}  </td>
                            <td>  ${user.birthDate}  </td>
                            <td class='form-container'>
                                <form name='removeUserForm' class='removeUserForm' method='post' action='removeUser-servlet'>
                                    <input type='hidden' value='${user.username}' name='removeUser' class="usernameForRemove">
                                    <input type='submit' value='✗' class='removeUserAdminButton'>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class='paginationUsers'>
                    <span id='previousPageUsers' class='visible'>&laquo;</span>
                    <span id='ellipseSxUsers'>...</span>
                    <c:set var='maxPageUsers' value='${Math.ceil(usersLength/4)}'/>
                    <c:forEach var='i' begin='1' end='${maxPageUsers}'>
                        <c:if test='${i == 1}'>
                            <span class='current visible pageNumBtnUserAdmin' id='pageUsers${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i != 1}'>
                            <c:if test='${i <= 4}'>
                                <span class='pageNumBtnUserAdmin visible' id='pageUsers${i}'>${i}</span>
                            </c:if>
                            <c:if test='${i > 4}'>
                                <span class='pageNumBtnUserAdmin' id='pageUsers${i}'>${i}</span>
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <c:if test='${maxPageUsers > 4}'>
                        <span id='ellipseDxUsers' class='visible'>...</span>
                    </c:if>
                    <c:if test='${maxPageUsers <= 4}'>
                        <span id='ellipseDxUsers'>...</span>
                    </c:if>
                    <span id='nextPageUsers' class='visible'>&raquo;</span>
                </div>
            </div>
        </div>

    </div>

    <div id="products-div" style="display: none">
        <p>Products</p>
    </div>

    <div id="categories-div" style="display: none">
        <p>Categories</p>
    </div>

    <div id="operators-div" style="display: none">
        <p>Operators</p>
    </div>

    <div id="moderators-div" style="display: none">
        <p>Moderators</p>
    </div>

    <div id="admins-div" style="display: none">
        <p>Admins</p>
    </div>

    <%@include file="footer.jsp"%> <!--footer-->
    <script>
        function resizeFooter(){ //per evitare strani ridimensionamenti su iPadPro
            if(window.matchMedia("screen and (min-width: 629px) and (min-height: 1010px)").matches)
                $(".footer").css("position", "absolute").css("bottom", "0");
            else
                $(".footer").css("position", "").css("bottom", "");
        }
        resizeFooter();
        window.onresize = ev => resizeFooter();
    </script>
    <script>var maxPageUsers = ${(maxPageUsers > 0)?(maxPageUsers):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script src="${pageContext.request.contextPath}/js/adminArea.js"></script>
    <script src="${pageContext.request.contextPath}/js/utility.js"></script>
    <script src="${pageContext.request.contextPath}/js/usersAdmin.js"></script>
</body>
</html>