<%@ page import="model.bean.DigitalProduct" %>
<%@ page import="model.bean.Product" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="model.bean.PhysicalProduct" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% Product product = (Product) request.getAttribute("product");

   String productType = (String) request.getAttribute("productType");
   HashMap<String, String> additionalInformations = new HashMap<>();

   if (productType.equalsIgnoreCase("digital")) {
       DigitalProduct dp = (DigitalProduct) product;

       additionalInformations.put("Piattaforma", dp.getPlatform());
       additionalInformations.put("Data di rilascio", dp.getReleaseDate());
       additionalInformations.put("Publisher", dp.getPublisher());
       additionalInformations.put("Software house", dp.getSoftwareHouse());
       additionalInformations.put("PEGI", "" + dp.getRequiredAge());
   }
   else {
       PhysicalProduct fp = (PhysicalProduct) product;

       additionalInformations.put("Dimensioni", fp.getSize());
       additionalInformations.put("Peso", "" + fp.getWeight());
   }

   String quantity = "Esaurito";

   if (product.getQuantity() != 0)
       quantity = "" + product.getQuantity();
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

<-- inizio codice vecchio ///////////////////////////////////////////////////////////////////////////////////////// --!>
<div id="img">
    <img src="${prodotto.image}">
</div>

<div>
    <h1><%=product.getName()%></h1>
    <br>
    <h4><%=product.getDescription()%></h4>

    <b>Disponibilità:</b> <%=quantity%> <br>
    <b>Prezzo:</b> <%=product.getPrice()%>€<br>
</div>

<form action="AddProductToCart" method="post" name="myForm" onsubmit="return disable(<%=product.getQuantity()%>)">
    <b>Quantita:</b>
    <select name="quantita">
        <%for (int i=1 ; i<product.getQuantity()+1; i++) {%>
        <option value="<%=i%>"><%=i%></option>
        <%}%>
    </select><br>
    <input type="text" hidden="hidden" name="idProdotto" value="<%=product.getId()%>">
    <input type="submit" name="aggCar" id="aggCar" value="Aggiungi al carrello">
</form>
<-- fine codice vecchio /////////////////////////////////////////////////////////////////////////////////////////// --!>


<-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! --!>

<%
    Set<String> additionalInformations_ = additionalInformations.keySet();

    for (String information : additionalInformations_) { %>
        <p><%=information%>:</p> <p><%=additionalInformations.get(information)%></p>
    <%}%>

<%@include file="footer.jsp"%> <!--footer-->

<!--====== Javascripts & Jquery ======-->
<script src="${pageContext.request.contextPath}/js/shop.js"></script>
<script src="${pageContext.request.contextPath}/js/searchBarAsync.js"></script>
</body>
</html>
