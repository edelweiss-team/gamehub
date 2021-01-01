<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Roberto Esposito
  Date: 1/1/2021
  Time: 3:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <title>Reserved Area</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservedArea.css" type="text/css">
</head>
<body class="reserved-body">
    <div class="reserved-fieldset">
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
            <tbody>
            <tr>
                <td>${loggedUser.username}</td>
                <td>${loggedUser.name}</td>
                <td>${loggedUser.surname}</td>
                <td>${loggedUser.birthDate}</td>
                <td>${loggedUser.telephone}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="reserved-fieldset">
        <table class="table table-dark">
            <h1 class="reserved-header">Residence</h1>
            <thead>
            <tr>
                <th scope="col">Address</th>
                <th scope="col">City</th>
                <th scope="col">Country</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${loggedUser.username}</td>
                <td>${loggedUser.name}</td>
                <td>${loggedUser.surname}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="reserved-fieldset">
        <c:if test = '${orders.size() == 0}'>
            <h1>You haven't any orders</h1>
        </c:if>
        <c:if test= '${orders.size() != 0}' >
            <c:forEach var="i" begin="0" end="${orders.length()}">
                <table class="table table-dark">
                    <h1 class="reserved-header">${orders.get(i).date} - ${orders.get(i).operator}</h1>
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="j" begin="0" end="${orders.get(i).products}">

                        </c:forEach>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>
    </div>
</body>
</html>
