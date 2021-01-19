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
                        <p>Scopri ed acquista i giochi più recenti, all'inerno del nostro nuovissimo store. <br> I giochi migliori a prezzi imbattibili! lasciati andare e scegli un gioco. <br>Non esitare, scopri di più!</p>
                        <a href="shop.html?productType=Digital" class="site-btn">I nostri prodotti!</a>
                    </div>
                </div>
            </div>
            <div class="hs-item set-bg" data-setbg="${pageContext.request.contextPath}/img/slider-2.jpg">
                <div class="hs-text">
                    <div class="container">
                        <h2>The Best <span>Community</span> Out There</h2>
                        <p>Confrontati con la miglior Community di sempre! <br> Esprimi le tue opinioni, confrontati con altri giocatori e divertiti nei limiti del rispetto altrui. <br>I moderatori ti aiuteranno a vivere questa esperienza al meglio!</p>
                        <a href="community.html" class="site-btn">Area Community!</a>
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
    <section class="feature-section spad" style="background: #212529;">
        <div class="container">
            <div class="row">
                <c:forEach items="${newProducts}" var="product">
                    <div class="col-lg-3 col-md-6 p-0" style="padding: 2px !important;">
                        <div class="feature-item set-bg" data-setbg="${pageContext.request.contextPath}/img/${product.getImage()}">
                            <span class="cata new">New</span>
                                <div class="fi-content text-white">
                                    <h5 style="word-break: break-word"><a href="showProduct.html?productId=${product.getId()}&productType=${product.getClass().getSimpleName().replaceAll("(Product)","")}">${product.name}</a></h5>
                                    <br>
                                   <p class="fi-content text-white" style="margin: 0">${product.price}€</p>
                                </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <!-- Feature section end -->



    <!-- Tournaments section -->
    <section class="tournaments-section spad">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <a href="shop.html?productType=Physical">
                        <div class="tournament-item mb-4 mb-lg-0">
                            <div class="ti-content">
                                <div class="ti-thumb set-bg" data-setbg="${pageContext.request.contextPath}/img/tournament/1.jpg"></div>
                                <div class="ti-text">
                                    <br><br><br>
                                    <h4 style="text-align: center; vertical-align: center" >Prodotti Fisici</h4>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-6">
                    <a href="shop.html?productType=Digital">
                        <div class="tournament-item">
                            <div class="ti-content">
                                <div class="ti-thumb set-bg" data-setbg="${pageContext.request.contextPath}/img/tournament/2.jpg"></div>
                                <div class="ti-text">
                                    <br><br><br>
                                    <h4 style="text-align: center; vertical-align: center" >Prodotti Digitali</h4>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </section>
    <!-- Tournaments section bg -->


<!-- Footer top section end -->


    <jsp:include page="footer.jsp"/>
</body>
</html>