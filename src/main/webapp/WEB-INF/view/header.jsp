<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>${param.pageTitle}</title>
    <meta charset="UTF-8">
    <meta name="description" content="Game Hub">
    <meta name="keywords" content="warrior, game, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Favicon -->
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="shortcut icon"/>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,500i,700,700i" rel="stylesheet">

    <!-- Stylesheets -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>


    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<!-- Header section -->
<header class="header-section">
    <div class="container">
        <!-- logo -->
        <a class="site-logo" href="index.html">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="">
        </a>
        <!-- responsive -->
        <div class="nav-switch">
            <i class="fa fa-bars"></i>
        </div>
        <!-- site menu -->
        <nav class="main-menu">
            <ul>
                <li><a href="index.html">Home</a></li>
                <li><a href="shop.html">Shop</a></li>
                <li><a href="community.html">Community</a></li>
                <li><a href="contact.html">Contact</a></li>
            </ul>
        </nav>
        <div id="cartContainer">
            <form action="show-cart">
                <button id="cart" class="cart" type="submit" name="showCart" value="showCart">
                    <i class="fa fa-shopping-cart"></i>
                </button>
            </form>
        </div>
        <div class="user-panel">
            <a href="login.html">Login</a>  /  <a href="signup.html">Sign up</a>
        </div>
    </div>
</header>
<!-- Header section end -->