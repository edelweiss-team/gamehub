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
            <div class="col-lg-8">
                <div class="row">
                    <c:choose>
                        <c:when test="${categories !='null'}">
                            <c:forEach items="${categories}" var="category">
                                <div class="col-md-6">
                                    <div class="recent-game-item">
                                        <div class="rgi-thumb set-bg" data-setbg="img/recent-game/1.jpg">
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
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p style="text-align: center">Categories not available</p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="site-pagination">
                    <span class="active">01.</span>
                    <a href="#">02.</a>
                    <a href="#">03.</a>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Page section end -->

<%@include file="footer.jsp"%> <!--footer-->

</body>
</html>