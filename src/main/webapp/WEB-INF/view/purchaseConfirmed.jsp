<html>
    <head>
        <title>Purchase completed</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errorPage.css">
        <%@include file="header.jsp"%>
        <style>
            .errorParagraph {
                font-size: 9vw;
            }
            .productsDesc {
                font-family: monospace;
                cursor: pointer;
                font-size: 20px;
                margin-top: 10px;
                display: inline-block;
                text-align: center;
            }
            .productsLink {
                color: #8abeb7;
                font-family: monospace;
                cursor: pointer;
                font-size: 20px;
                text-decoration: underline;
                margin-top: 10px;
                display: inline-block;
                transition: color 0.3s;
            }
            @media screen and (hover: hover) and (any-hover: hover){
                .productsLink:hover{
                    color: #9cc9c3;
                }

                .productsLink:active{
                    color: #add2cd;
                }
            }

            @media screen and (hover: none) and (any-hover: none){
                .productsLink:active{
                    color: #9cc9c3;
                }
            }

            @media screen and (max-width: 880px) {
                .errorParagraph{
                    font-size: 8vw;
                }
            }
        </style>
    </head>
    <body class="errorPageBody">
        <!--header-->
        <div class="main-content">
            <p class="errorParagraph">
                Congratulations! <span>Purchase completed!</span>
            </p>
            <p class="productsDesc">
                Your order, #${order.id} has been received successfully,
                now just wait until an operator approves it!<br>
                Meanwhile, checkout other <a class="productsLink" href="category.html">products</a>!
            </p>
            <div class="home-link">
                <a href="index.html">HOME</a>
            </div>
        </div>
        <%@include file="footer.jsp"%> <!--footer-->
    </body>
</html>
