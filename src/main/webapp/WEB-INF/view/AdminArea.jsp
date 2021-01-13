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
    <form action="logout" method="post">
        <button type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Users</button>
    </form>
    <form action="operatorArea.html" method="post">
        <button type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Products</button>
    </form>
    <form action="community.html" method="post">
        <button type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Categories</button>
    </form>
    <form action="community.html" method="post">
        <button type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Operators & Moderators</button>
    </form>
    <form action="adminArea.html" method="post">
        <button type="submit" class="btn btn-warning" style="float: right">Admins</button>
    </form>
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
</body>
</html>