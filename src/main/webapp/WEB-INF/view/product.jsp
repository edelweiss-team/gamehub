<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.bean.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Product product = (Product) request.getAttribute("product");
   String productType = (String) request.getAttribute("productType");

   HashMap<String, String> additionalInformations;

   if (productType.equalsIgnoreCase("digital")) {
       DigitalProduct dp = (DigitalProduct) product;
       additionalInformations = dp.getAdditionalInformations();
   }
   else {
       PhysicalProduct fp = (PhysicalProduct) product;
       additionalInformations = fp.getAdditionalInformations();
   }

   // formatting price
   String price;

   if ((product.getPrice() - (int) product.getPrice()) == 0) {
       price = "" + (int) product.getPrice() + " €";
   }
   else {
       price = ""  + product.getPrice() + " €";
   }

   // formatting quantity
   String quantity = "Esaurito";
   int maxQuantity = 0;

   if (product.getQuantity() != 0)
       quantity = "" + product.getQuantity();

    ArrayList<Tag> tags = new ArrayList<>(product.getTags());
    ArrayList<Category> categories = new ArrayList<>(product.getCategories());

%>

<!DOCTYPE html>
<html>
<head>
    <title><%=product.getName()%></title>
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


<section class="page-section single-blog-page spad" style="background: #212529">
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="blog-thumb set-bg" data-setbg="img/recent-game/big.jpg">
                    <div class="rgi-extra"></div>
                </div>
            </div>
            <!-- sidebar -->
            <div class="col-lg-4 col-md-7 sidebar pt-5 pt-lg-0">
                <!-- widget -->
                <div class="widget-item">
                    <h3 class="widget-title" style="color: gold; word-break: break-word"><%=product.getName()%></h3>
                    <% for (Tag t : tags) { %>
                        <span class="badge bg-danger"><%=t.getName()%></span>
                    <%}%>
                    <h5 class="widget-title" style="color: #e0a800"><%=price%></h5>
                    <div class="latest-blog">
                        <p style="color: whitesmoke; word-break: break-word"><%=product.getDescription()%></p>
                        <form>
                            <div class="form-group" style="display: inline">
                                <input type="hidden" name="productId" value="${product.id}">
                                <input type="hidden" name="productType" value="${product.getClass().getSimpleName().replaceAll("(Product)","")}">
                                <label for="ProductQuantity">Quantity:</label>
                                <input class="form-control" id="ProductQuantity" max="${product.quantity}" min="1" type="number" name="productQuantity" aria-describedby="quantityHelp" placeholder="1">
                                <small id="quantityHelp" class="form-text text-muted" style="color: darkgray!important">Max: <%=quantity%>.</small>
                            </div>
                            <button class="addToCartBtn site-btn btn-sm">Add to Cart</button>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <table style="width: auto; color: white; border: 1px solid gold" class="table table-striped">
            <thead>
            <tr>
                <% for (String information : additionalInformations.keySet()) { %>
                    <th scope="col" style="border-bottom: 2px solid gold; border-top: 1px solid gold"><%=information%></th>
                <%}%>

            </tr>
            </thead>
            <tbody>
                <tr>
                    <% for (int i=0; i<(additionalInformations.keySet()).size(); i++) { %>
                        <td> <%=additionalInformations.get((new ArrayList<>((additionalInformations.keySet()))).get(i))%> </td>
                    <%}%>
                </tr>
            </tbody>
        </table>
    </div>
</section>

<%@include file="footer.jsp"%> <!--footer-->

<!--====== Javascripts & Jquery ======-->
<script src="${pageContext.request.contextPath}/js/singleProduct.js"></script>
<script src="${pageContext.request.contextPath}/js/searchBarAsync.js"></script>
</body>
</html>
