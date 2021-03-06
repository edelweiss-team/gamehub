<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Shop</title>
    <%@include file="header.jsp"%>
    <!-- Favicon -->
    <link href="img/favicon.ico" rel="shortcut icon"/>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,500i,700,700i" rel="stylesheet">

    <!-- Stylesheets -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/searchBarTagsAsync.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/voting.css"/>
</head>

<body>
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

<div class="latest-news-section">
    <div class="ln-title">Latest Games</div>
    <div class="news-ticker">
        <div class="news-ticker-contant">
            <c:forEach items="${newProducts}" var="product">
                <div class="nt-item"><span class="new">new</span> ${product.name} </div>
            </c:forEach>
        </div>
    </div>
</div>

<section class="recent-game-section spad set-bg" id="container-shop-list">
    <div class="container">
        <div class="section-title">
            <h2 style="color: whitesmoke">Recommended Products</h2>
            <div class="voting-frame">
                <button class="like">
                    <i class="fa fa-thumbs-o-up" aria-hidden="true"></i>
                </button>
                <button class="dislike">
                    <i class="fa fa-thumbs-o-down" aria-hidden="true"></i>
                </button>
            </div>
        </div>

        <div class="row" id="shop-list">
            <div class="col-lg-8">
                <div class="row" id="shop-container">
                    <c:choose>
                        <c:when test="${products.size()>0}">
                            <c:set var="count" value="0" scope="page"/>
                            <c:forEach items="${products}" var="product">
                                <c:if test="${count<8}">
                                    <div class="col-md-6">
                                        <div class="recent-game-item">
                                            <div class="rgi-thumb set-bg" data-setbg="img/${product.getImage()}"}>
                                                <div class="cata new">
                                                    <form action="add-cart" method="get" class="shop-form">
                                                        <input type="hidden" name="addCart" value="true">
                                                        <input type="hidden" name="productId" value="${product.getId()}">
                                                        <input type="hidden" name="quantity" value="${product.getQuantity()}">
                                                        <input type="hidden" name="productType" value="${product.getClass().getSimpleName().replaceAll("(Product)","")}">
                                                        <button type="submit" class="shop-button addToCartBtn">
                                                            Add To Cart
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                            <div class="rgi-content shop-description">
                                                <form action="showProduct.html" method="get" class="shop-form-singleProduct">
                                                    <button type="submit" class="shop-button-singleProduct">
                                                        <input type="hidden" name="productId" value="${product.getId()}">
                                                        <input type="hidden" name="productType" value="${product.getClass().getSimpleName().replaceAll("(Product)","")}">
                                                        <h5 style="color: whitesmoke; margin: 0;" >${product.getName()}</h5>
                                                    </button>
                                                </form>
                                                <h6 style="color: whitesmoke; margin-top: 16px;">${product.getDescription()}</h6>
                                                <p style="margin: 16px 0 0 0">Remaining: ${product.getQuantity()}</p>
                                                <p style="margin-bottom: 0">${product.getPrice()}$</p>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <c:set var="count" value="${count+1}" scope="page"></c:set>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div style="align-items: center" id="no-product-div">
                                <h6>PRODUCTS NOT AVIABLE</h6>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</section>


<%@include file="footer.jsp"%> <!--footer-->
<script>
    var maxPage = ${(maxPage > 0)?(maxPage):(1)}; //mantiena l'indice dell'ultima pagina
    const SEARCH = '<%=(request.getParameter("search") != null) ? request.getParameter("search") : ""%>';
    const DESCRIPTION = '<%=(request.getParameter("description") != null) ? request.getParameter("description") : ""%>';
    const PRICE = <%=(request.getParameter("price") != null && request.getParameter("price").length()>0) ? request.getParameter("price") : 2000000%>;
    const TAG = '<%=(request.getParameter("tag") != null) ? request.getParameter("tag") : ""%>';
    const TYPE = '<%=(request.getParameter("productType") != null) ? request.getParameter("productType") : "Digital"%>';
    const CTG = '${categoryName}';
    $(document).ready(ev => {
        //selezioniamo il radiobutton Digital or Physical
        if(TYPE.toLowerCase()=="digital")
            $("#radio1").click();
        else
            $("#radio2").click();
    });
</script>
<script src="${pageContext.request.contextPath}/js/shop.js"></script>
<script src="${pageContext.request.contextPath}/js/searchBarAsync.js"></script>
<script src="${pageContext.request.contextPath}/js/voting.js"></script>
</body>
</html>
