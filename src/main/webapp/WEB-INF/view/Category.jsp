<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="zxx">
<head>
    <title>Category Page</title>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css"/>



</head>

<body class="category">

<!-- Latest news section -->
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
<!-- Latest news section end -->

<!-- Page info section -->
<section class="page-info-section set-bg" data-setbg="img/page-top-bg/1.jpg">
    <div class="pi-content">
        <div class="container">
            <div class="row">
                <div class="col-xl-5 col-lg-6 text-white">
                    <h2>Our Categories</h2>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec malesuada lorem maximus mauris scelerisque, at rutrum nulla dictum.</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Page info section -->


<!-- Page section -->
<section class="page-section recent-game-page spad" id="container-category-list">
    <div class="container">
        <div class="row" id="category-list">
            <div class="row mb-5">
                <div class="col-lg-8 mx-auto">
                    <div class="bg-transparent p-5 rounded">
                        <form id="search-form" action="show-categories">
                            <div class="p-1 bg-transparent rounded rounded-pill shadow-sm mb-4">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <button id="button-addon2" type="submit" class="btn btn-link text-warning">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                    <input type="search" placeholder="What're you searching for?" name="search" aria-describedby="button-addon2" class="category-search form-control border-0 bg-light">
                                </div>
                            </div>
                        </form>
                        <!-- End -->
                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="row" id="category-container">
                    <c:choose>
                        <c:when test="${categories.size() > 0}">
                            <c:set var="count" value="0" scope="page"/>
                            <c:forEach items="${categories}" var="category">
                                <c:if test="${count < 8}">
                                    <div class="col-md-6">
                                        <div class="recent-game-item">
                                            <div class="rgi-thumb set-bg" data-setbg="img/${category.getImage()}"}>
                                                <div class="cata new">
                                                    <form action="shop.html" method="get" id="category-form">
                                                        <button type="submit" id="category-button" name="categoryName" value="${category.getName()}">
                                                                ${category.getName()}
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                            <div class="rgi-content" id="category-description">
                                                <h6 style="color: whitesmoke" >${category.getDescription()}</h6>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <c:set var="count" value="${count+1}" scope="page"></c:set>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div style="align-items: center">
                                <h6>CATEGORIES NOT AVIABLE</h6>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="pagination site-pagination">
                    <span id="previousPage" class="visible">&laquo;</span>
                    <span id="ellipseSx">...</span>
                    <c:set var="maxPage" value="${Math.ceil(categories.size()/8)}"/>
                    <c:forEach var="i" begin="1" end="${maxPage}">
                        <c:if test="${i == 1}">
                            <span class="current visible pageNumBtn" id="page${i}">${i}</span>
                        </c:if>
                        <c:if test="${i != 1}">
                            <c:if test="${i <= 8}">
                                <span class="pageNumBtn visible" id="page${i}">${i}</span>
                            </c:if>
                            <c:if test="${i > 8}">
                                <span class="pageNumBtn" id="page${i}">${i}</span>
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <c:if test="${maxPage > 8}">
                        <span id="ellipseDx" class="visible">...</span>
                    </c:if>
                    <c:if test="${maxPage <= 8}">
                        <span id="ellipseDx">...</span>
                    </c:if>
                    <span id="nextPage" class="visible">&raquo;</span>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Page section end -->

<%@include file="footer.jsp"%> <!--footer-->
<script>var maxPage = ${(maxPage > 0)?(maxPage):(1)}; //mantiena l'indice dell'ultima pagina</script>
<script src="${pageContext.request.contextPath}/js/categories-page.js"></script>

</body>
</html>