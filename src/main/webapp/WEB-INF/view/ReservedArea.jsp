<%@ page import="model.bean.*" %>
<%@ page import="java.util.ArrayList" %><%--
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
        <title>Reserved Area</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservedArea.css" type="text/css">
        <%@include file="header.jsp"%>
    </head>
    <body class="reserved-body">
    <div class="reserved-fieldset">
        <form action="logout" method="post">
            <button type="submit" class="btn btn-danger" style="float: right; margin-left: 1%">Logout</button>
        </form>
        <%if(((User)session.getAttribute("loggedUser")).getClass().equals(Admin.class) ||
                ((User)session.getAttribute("loggedUser")).getClass().equals(Operator.class)){%>
            <form action="operatorArea.html" method="post">
                <button type="submit" class="btn btn-success" style="float: right; margin-left: 1%">Operator Area</button>
            </form>
        <%}%>
        <%if(((User)session.getAttribute("loggedUser")).getClass().equals(Admin.class) ||
            ((User)session.getAttribute("loggedUser")).getClass().equals(Moderator.class)){%>
        <form action="community.html" method="post">
            <button type="submit" class="btn btn-success" style="float: right; margin-left: 1%">Moderator Area</button>
        </form>
        <%}%>
        <%if(((User)session.getAttribute("loggedUser")).getClass().equals(Admin.class)) {%>
            <form action="adminArea.html" method="post">
                <button type="submit" class="btn btn-success" style="float: right">Admin Area</button>
            </form>
        <%}%>
        <table class="table table-dark">
            <h1 class="reserved-header">Info</h1>
            <thead>
            <tr>
                <th scope="col">Username</th>
                <th scope="col">Name</th>
                <th scope="col">Surname</th>
                <th scope="col">Date of Birth</th>
                <th scope="col">Telephone</th>
            </tr>
            </thead>
            <tbody class="info-table-body">
            <tr class="info-table-body-row" id="1tr">
                <td class="can-be-editable editable-username">
                    ${loggedUser.username}
                </td>
                <td class="can-be-editable editable-name">
                    ${loggedUser.name}
                </td>
                <td class="can-be-editable editable-surname">
                    ${loggedUser.surname}
                </td>
                <td class="can-be-editable editable-birthDate">
                    ${loggedUser.birthDate}
                </td>
                <td class="can-be-editable editable-telephone">
                    ${loggedUser.telephone}
                </td>
                <td class="form-container">
                    <form class="changeUserForm" name="changeUserForm" method="post" action="changeUser">
                        <input type="hidden" value="${loggedUser.username}" name="changeUser" class="changeUserOldUsername">
                        <input type="submit" value="Modifica" class=" btn btn-outline-warning changeUserReservedAreaButton" id="1">
                        <span class="errorUserMessage" style="color: #c75450; display: none"></span>
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="reserved-fieldset">
        <table class="table table-dark">
            <h1 class="reserved-header">Other info</h1>
            <thead>
            <tr>
                <th scope="col">Password</th>
                <th scope="col">Mail</th>
                <th scope="col">Sex</th>
                <th scope="col">Address</th>
                <th scope="col">City</th>
                <th scope="col">Country</th>
            </tr>
            </thead>
            <tbody class="info-table-body">
            <tr class="info-table-body-row" id="2tr">
                <td class="editable-password">
                    *******
                </td>
                <form class="changeUserForm" name="changeUserForm" id="changeUserForm" method="post" action="changeUser">
                    <input type="hidden" value="${loggedUser.username}" name="changeUser" class="changeUserOldUsername">
                    <input id="passwordBtn" type="submit" value="Modifica" class="btn btn-outline-warning changePasswordReservedArea">
                    <span class="errorPasswordMessage" style="color: #c75450; display: none"></span>
                </form>
                <td class="can-be-editable editable-mail">
                    ${loggedUser.mail}
                </td>
                <td class="can-be-editable editable-sex">
                    ${loggedUser.sex}
                </td>
                <td class="can-be-editable editable-address">
                    ${loggedUser.address}
                </td>
                <td class="can-be-editable editable-city">
                    ${loggedUser.city}
                </td>
                <td class="can-be-editable editable-country">
                    ${loggedUser.country}
                </td>
                <td class="form-container">
                    <form class="changeUserForm" name="changeUserForm" method="post" action="changeUser">
                        <input type="hidden" value="${loggedUser.username}" name="changeUser" class="changeUserOldUsername">
                        <input type="submit" value="Modifica" class="btn btn-outline-warning changeUserReservedAreaButton" id="2">
                        <span class="errorUserMessage" style="color: #c75450; display: none"></span>
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="reserved-fieldset">
        <c:if test = '${orders.size() == 0}'>
            <h1>You haven't any orders</h1>
        </c:if>
        <c:if test= '${orders.size() != 0}' >
            <c:forEach var="i" begin="0" end="${orders.size()-1}">
                <table class="table table-dark">
                    <c:if test="${orders.get(i).operator == null}">
                        <h1 class="reserved-header">Order: #${orders.get(i).id}</h1>
                        <h1 class="reserved-header">Data: ${orders.get(i).data}</h1>
                        <h1 class="reserved-header">Operator: Not signed</h1>
                    </c:if>
                    <c:if test="${orders.get(i).operator != null}">
                        <h1 class="reserved-header">Order: #${orders.get(i).id}</h1>
                        <h1 class="reserved-header">Data: ${orders.get(i).data}</h1>
                        <h1 class="reserved-header">Operator: ${orders.get(i).operator.name}</h1>
                    </c:if>
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                    </tr>
                    </thead>
                    <tbody>
                        <% ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
                            for (int i = 0; i < orders.size(); i++) {
                                out.println(orders.get(i));
                            }
                        %>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>
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
    <script src="${pageContext.request.contextPath}/js/updateReservedArea.js"></script>
</body>
</html>