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

    <div id='left'>
        <div id='mobileAdminButtonContainer'>
            <button id='mobileAdminButton'>
                <i class='fa fa-arrow-right' id='AdminIcon'></i>
            </button>
        </div>
        <div id='adminPage' class='left-box'>
            <h1 class='admin-header'>Admin Area</h1>
            <ul id='left-box-list'>
                <li class='left-box-item'>
                    <button id='user' class='admin-button current'>
                        <i class='fa fa-user'></i>Manage users
                    </button>
                </li>
                <li class='left-box-item'>
                    <button id='digitalproduct' class='admin-button'>
                        <i class='fa fa-gamepad'></i>Manage Digital Products
                    </button>
                </li>
                <li class='left-box-item'>
                    <button id='physicalproduct' class='admin-button'>
                        <i class='fa fa-gamepad'></i>Manage Physical Products
                    </button>
                </li>
                <li class='left-box-item'>
                    <button id='category' class='admin-button'>
                        <i class='fa fa-list-ul'></i>Manage categories
                    </button>
                </li>
                <li class='left-box-item'>
                    <button id='operator' class='admin-button'>
                        <i class='fa fa-users'></i>Manage operators
                    </button>
                </li>
                <li class='left-box-item'>
                    <button id='moderator' class='admin-button'>
                        <i class='fa fa-users'></i>Manage moderators
                    </button>
                </li>
                <li class='left-box-item'>
                    <button id='admin' class='admin-button'>
                        <i class='fa fa-briefcase'></i>Manage admin
                    </button>
                </li>
            </ul>
        </div>
    </div>

    <div id="user-div">
        <p>User</p>
    </div>
    <div id="digitalproduct-div">
        <p>Prodotto fisico</p>
    </div>
    <div id="physicalproduct-div">
        <p>Prodotto digitale</p>
    </div>
    <div id="category-div">
        <p>Categorie</p>
    </div>
    <div id="operator-div">
        <p>Operatori</p>
    </div>
    <div id="moderator-div">
        <p>Moderatori</p>
    </div>
    <div id="admin-div">
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
<script src="${pageContext.request.contextPath}/js/utility.js"></script>
<script src="${pageContext.request.contextPath}/js/adminArea.js"></script>
</body>
</html>