<%@ page import="model.bean.User" %>
<%@ page import="model.bean.Admin" %>
<%@ page import="model.bean.Operator" %>
<%@ page import="model.bean.Moderator" %>
<%@ page import="model.bean.DigitalProduct" %><%--
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
    <title>Operator Area</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/operatorArea.css" type="text/css">
    <%@include file="header.jsp"%>
</head>
<body class="operator-body">
<div class="operator-fieldset">
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
</div>
<div class="operator-fieldset">
    <c:if test = '${orders.size() == 0}'>
        <h1>You haven't any orders</h1>
    </c:if>
    <c:if test= '${orders.size() != 0}' >
        <c:forEach var="i" begin="0" end="${orders.size()-1}">
            <table class="table table-dark">
                <h1 class="operator-header">Order: #${orders.get(i).id}</h1>
                <h1 class="operator-header">Data: ${orders.get(i).data}</h1>
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                    <c:if test ='${orders.get(i).operator != null}'>
                        <th scope="col">Approved</th>
                    </c:if>
                    <c:if test ='${orders.get(i).operator == null}'>
                        <th scope="col">
                            <!-- Button trigger modal -->
                            <a href="#myModal${orders.get(i).id}" role="button" class="btn btn-success" data-toggle="modal">Approve</a>

                            <!-- Modal -->
                            <div id="myModal${orders.get(i).id}" class="modal fade" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Confirmation</h5>
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        </div>
                                        <div class="modal-body">
                                            <form name="approveOrderForm" class="approveOrderForm" method="post" action="approveOrder-servlet">
                                                <div class="modal-body">
                                                    <c:forEach var="product" items="${orders.get(i).getAllProducts()}">
                                                        <c:if test="${product.getClass().getSimpleName() == 'DigitalProduct'}">
                                                            <input type="text" placeholder="Inserisci qui il codice di attivazione">
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                    <input type='hidden' value='${orders.get(i).id}' name='approveOrder' class='approveOrder'>
                                                    <button type="submit" class="btn btn-primary">Approva</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${orders.get(i).getAllProducts()}">
                    <tr>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td>${orders.get(i).getQuantitySingleProduct(product.id, product.getClass())}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:forEach>
    </c:if>
</div>
<div class="operator-fieldset">

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
<script>
    $('#myModal').on('shown.bs.modal', function () {
        $('#myInput').trigger('focus')
    })
</script>
</body>
</html>