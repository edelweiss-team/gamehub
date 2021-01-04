<%@ page import="java.util.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="model.bean.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Cart</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css" type="text/css">
        <meta name="viewport" content="initial-scale=1, width=device-width">
        <%@include file="header.jsp"%> <!--header-->
    </head>
    <body class="cart">
        <br>
        <h1 style="visibility: hidden">
            hidden content
        </h1>
        <div id="content">
            <div class="shopping-cart">
                <div class="title">
                    <div class="titleText">Cart</div>
                </div>
                <%
                    if (session.getAttribute("cart") == null
                            || ((Cart) session.getAttribute("cart")).getNumberOfItems() == 0){
                %>
                        <h5 class="substitutionText">Sorry, your cart is empty :(</h5>
                <%
                    }
                    else {
                        Cart cart = (Cart)session.getAttribute("cart");
                        ArrayList<Product> products = new ArrayList<>(cart.getAllProducts());
                        pageContext.setAttribute("cartItems", products);
                        for(Product p : products){
                            String productType = "";
                            if (p instanceof PhysicalProduct) {
                                productType = "Physical";
                            } else if (p instanceof DigitalProduct) {
                                productType = "Digital";
                            }
                %>
                            <div class="cartItem" id="cartItem<%=productType + p.getId()%>">
                                <div class="buttonsContainer">
                                    <form action="remove-cart" class="delete-btn-form">
                                        <input type="hidden" class="productType" name="productType" value="<%=productType%>">
                                        <input type="hidden" name="removeCart" value="removeCart">
                                        <input type="hidden" class="productId" name="productId" value="<%=p.getId()%>">
                                        <button class="delete-btn" type="submit">&#10007;</button>
                                    </form>
                                </div>
                                <div class="imageContainer">
                                    <a href="showProduct.html?productId=<%=p.getId()%>&productType=<%=productType%>" target="_blank">
                                        <img alt="No image, sorry :(" src="${pageContext.request.contextPath}/img/<%=p.getImage()%>">
                                    </a>
                                </div>
                                <div class="genericInfo">
                                    <span class="name">
                                        <a href="showProduct.html?productId=<%=p.getId()%>&productType=<%=productType%>" target="_blank">
                                            <%=p.getName()%>
                                        </a>,
                                    </span>
                                    <span class="quantity">Quantity: <%=cart.getQuantitySingleProduct(p.getId(), p.getClass())%></span>
                                    <span class="availableQuantity">Available: <%=p.getQuantity()%></span>
                                </div>
                                <div class="price">
                                    <span style="color: lightgrey">
                                        <%=(new DecimalFormat("#.##")).format(
                                                p.getPrice()*cart.getQuantitySingleProduct(p.getId(), p.getClass())
                                            ).replaceAll(",", ".")
                                        %>$
                                    </span>
                                </div>
                            </div>
                <%
                        }
                %>
                        <div id="purchaseFormContainer">
                            <div class="total-price">
                                Total:
                                <span class="number">
                                    <%=(new DecimalFormat("#.##")).format(cart.getTotalPrice()).replaceAll(",", ".")%>$
                                </span>
                            </div>
                            <form action="purchase-items" method="post">
                                <button type="submit" class="purchaseButton">
                                    <i class="fa fa-credit-card"></i> Purchase
                                </button>
                            </form>
                        </div>
                <%
                    }
                %>
            </div>
        </div>
        <%@include file="footer.jsp"%> <!--footer-->
        <script src="${pageContext.request.contextPath}/js/utility.js"></script>
        <script src="${pageContext.request.contextPath}/js/cart.js"></script>
        <%
            if(request.getAttribute("errorUserNotLogged") != null){
        %>
        <script>
            $(document).ready(function () {
                showPopupMessage("error", "${errorUserNotLogged}", 8);
            });
        </script>
        <%
            }
            else if(request.getAttribute("purchasedJsonObject") != null){
        %>
        <script>
            $(document).ready(function () {
                let message = "purchase completed! You just bought the courses: \n", purchasedJsonObject;
                purchasedJsonObject = JSON.parse('${purchasedJsonObject}');
                for(let courseName of purchasedJsonObject.itemsNameList){
                    message += courseName + ", ";
                }
                message += "and you just spent " + purchasedJsonObject.purchasedTotalPrice + "$.";
                showPopupMessage("success", message, 8);
            });
        </script>
        <%
            }
        %>
    </body>
</html>
