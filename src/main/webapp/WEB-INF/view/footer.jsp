<!-- Footer top section -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <body>
        <section class="footer-top-section">
            <div class="container">
                <div class="footer-top-bg">
                    <img src="${pageContext.request.contextPath}/img/footer-top-bg.png" alt="">
                </div>
                <div class="row">
                    <div class="col-lg-4">
                        <div class="footer-logo text-white">
                            <img src="${pageContext.request.contextPath}/img/logo.png" alt="">
                            <p>Lorem ipsum dolor sit amet, consectetur adipisc ing ipsum dolor sit ame.</p>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="footer-widget mb-5 mb-md-0">
                            <h4 class="fw-title">Latest Threads</h4>
                            <div class="latest-blog">
                                <c:forEach begin="0" end="2" varStatus="loop">
                                <div class="lb-item">
                                    <div class="lb-thumb set-bg" data-setbg="${pageContext.request.contextPath}/img/comingsoon.jpg"></div>
                                    <div class="lb-content">
                                        <div class="lb-date">Cooming Soon!</div>
                                        <!--<p>Cooming Soon! Cooming Soon! Cooming Soon! Cooming Soon! Cooming Soon! Cooming Soon!</p>-->
                                        <a href="#" class="lb-author">By Admin</a>
                                    </div>
                                </div>
                            </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="footer-widget">
                            <h4 class="fw-title">Comments</h4>
                            <div class="top-comment">
                                <c:forEach begin="0" end="3" varStatus="loop">
                                    <div class="tc-item">
                                        <div class="tc-thumb set-bg" data-setbg="${pageContext.request.contextPath}/img/authors/1.jpg"></div>
                                        <div class="tc-content">
                                            <p><a href="#">Cooming Soon!</a>
                                            <div class="tc-date">June 21, 2018</div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Footer section -->
        <footer class="footer-section">
            <div class="container">
                <ul class="footer-menu">
                    <li><a href="Home.jsp">Home</a></li>
                    <li><a href="review.html">Shop</a></li>
                    <li><a href="categories.html">Community</a></li>
                    <li><a href="contact.html">Contact</a></li>
                </ul>
                <p class="copyright">
                    Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with
                </p>
            </div>
        </footer>
        <!-- Footer section end -->


        <!--====== Javascripts & Jquery ======-->
        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.marquee.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
    </body>
</html>