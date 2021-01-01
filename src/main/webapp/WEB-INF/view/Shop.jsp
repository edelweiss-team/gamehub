<%--
  Created by IntelliJ IDEA.
  User: Roberto Esposito
  Date: 12/30/2020
  Time: 6:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Shop</title>
    <%@include file="header.jsp"%>
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

<section class="recent-game-section spad set-bg" data-setbg="${pageContext.request.contextPath}/img/recent-game-bg.png">
    <div class="container">
        <div class="section-title">
            <h2>${categoryName}</h2>
        </div>
        <div class="row">
            <c:forEach begin="0" end="3" varStatus="loop">
                <div class="col-lg-3 col-md-6">
                    <div class="review-item">
                        <div class="review-cover set-bg" data-setbg="${pageContext.request.contextPath}/img/review/1.jpg">
                            <div class="score yellow">9.3€</div>
                        </div>
                        <div class="review-text">
                            <h5>Assasin’’s Creed</h5>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisc ing ipsum dolor sit ame.</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<%@include file="footer.jsp"%> <!--footer-->
</body>
</html>
