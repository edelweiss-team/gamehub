<html>

<head>
    <title>Home GameHub</title>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Game Hub"/>
    </jsp:include>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

</head>
<body>
    <!-- Hero section -->
    <section class="hero-section">
        <div class="hero-slider owl-carousel">
            <div class="hs-item set-bg" data-setbg="${pageContext.request.contextPath}/img/slider-1.jpg">
                <div class="hs-text">
                    <div class="container">
                        <h2>The Best <span>Games</span> Out There</h2>
                        <p>Scopri ed acquista i giochi più recenti, all'inerno del nostro nuovissimo store <br> I Giochi Migliori a Prezzi Imbattibili, lasciati andare e scegli un gioco. <br>Non esitare scopri di più.</p>
                        <a href="#" class="site-btn">Know More</a>
                    </div>
                </div>
            </div>
            <div class="hs-item set-bg" data-setbg="${pageContext.request.contextPath}/img/slider-2.jpg">
                <div class="hs-text">
                    <div class="container">
                        <h2>The Best <span>Games</span> Out There</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec malesuada <br> lorem maximus mauris scelerisque, at rutrum nulla dictum. Ut ac ligula sapien. <br>Suspendisse cursus faucibus finibus.</p>
                        <a href="#" class="site-btn">Read More</a>
                    </div>
                </div>
            </div>
        </div>
    </section>
<!-- Hero section end -->


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


<!-- Feature section -->
    <section class="feature-section spad">
        <div class="container">
            <div class="row">
                <c:forEach items="${newProducts}" var="product">
                    <div class="col-lg-3 col-md-6 p-0">
                        <div class="feature-item set-bg" data-setbg="${pageContext.request.contextPath}/img/features/1.jpg">
                            <span class="cata new">New</span>
                                <div class="fi-content text-white">
                                    <h5><a href="#">${product.name}</a></h5>
                                    <p>${product.description}</p>
                                    <br>
                                   <a href="#" class="fi-content text-white">${product.price}</a>
                                </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <!-- Feature section end -->


    <!-- Recent game section  -->
    <section class="recent-game-section spad set-bg" data-setbg="${pageContext.request.contextPath}/img/recent-game-bg.png">
        <div class="container">
            <div class="section-title">
                <div class="cata new">new</div>
                <h2>Recent Games</h2>
            </div>
            <div class="row">
                <c:forEach items="${secondProducts}" var="product">
                    <div class="col-lg-3 col-md-6">
                        <div class="review-item">
                            <div class="review-cover set-bg" data-setbg="${pageContext.request.contextPath}/img/review/1.jpg">
                                <div class="score yellow">${product.price}€</div>
                            </div>
                            <div class="review-text">
                                <h5>${product.name}</h5>
                                <p>${product.description}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <!-- Recent game section end -->


    <!-- Tournaments section -->
    <section class="tournaments-section spad">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <div class="tournament-item mb-4 mb-lg-0">
                        <div class="ti-content">
                            <div class="ti-thumb set-bg" data-setbg="${pageContext.request.contextPath}/img/tournament/1.jpg"></div>
                            <div class="ti-text">
                                <br><br><br>
                                <h4 style="text-align: center; vertical-align: center" >Prodotti Fisici</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="tournament-item">
                        <div class="ti-content">
                            <div class="ti-thumb set-bg" data-setbg="${pageContext.request.contextPath}/img/tournament/2.jpg"></div>
                            <div class="ti-text">
                                <br><br><br>
                                <h4 style="text-align: center; vertical-align: center" >Prodotti Digitali</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Tournaments section bg -->


    <!-- Review section -->
    <section class="review-section spad set-bg" data-setbg="${pageContext.request.contextPath}/img/review-bg.png">
        <div class="container">
            <div class="section-title">
                <div class="cata new">new</div>
                <h2>Recommended Products</h2>
            </div>
            <div class="row">
                <c:forEach begin="0" end="3" varStatus="loop">
                    <div class="col-lg-3 col-md-6">
                        <div class="review-item">
                            <div class="review-cover set-bg" data-setbg="${pageContext.request.contextPath}/img/review/1.jpg">
                                <div class="score yellow">9.3</div>
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
        </div>
    </section>
    <!-- Review section end -->



<!-- Footer top section end -->


    <jsp:include page="footer.jsp"/>
</body>
</html>