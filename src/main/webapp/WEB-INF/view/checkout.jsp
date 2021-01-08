<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <title>Login Page</title>
        <%@include file="header.jsp"%>
        <link href="${pageContext.request.contextPath}/resources/icons/favicon.ico" rel="shortcut icon" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css" type="text/css">
    </head>
    <body class="checkout">
        <c:set var="cartItemList" value="${cart.getAllProducts()}"/>
        <div class="checkoutFormContainer container">
            <div class="py-5 text-center">
                <h2 class="checkoutTitle">Checkout</h2>
            </div>
            <div class="row">
                <div class="col-md-4 order-md-2 mb-4">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Your cart</span>
                        <span class="badge badge-secondary badge-pill">${cart.numberOfItems}</span>
                    </h4>
                    <ul class="cartList list-group mb-3 sticky-top">
                        <c:forEach items="${cartItemList}" var="item">
                            <li class="list-group-item d-flex justify-content-between lh-condensed">
                                <div>
                                    <h6 class="my-0">${item.name}</h6>
                                    <small class="text-muted">Quantity: ${cart.getQuantitySingleProduct(item.id, item.getClass())}</small>
                                </div>
                                <span class="text-muted">$${cart.getQuantitySingleProduct(item.id, item.getClass())*item.price}</span>
                            </li>
                        </c:forEach>
                        <li class="totalCart list-group-item d-flex justify-content-between bg-dark">
                            <div class="text-success">
                                <h6 class="my-0">Total(USD)</h6>
                            </div>
                            <span class="text-success"><strong>$${cart.totalPrice}</strong></span>
                        </li>
                    </ul>
                </div>
                <div class="col-md-8 order-md-1">
                    <c:if test="${empty loggedUser}">
                        <h4 class="mb-3">Billing address</h4>
                    </c:if>
                    <form class="needs-validation" action="proceed-checkout" method="post" novalidate="">
                        <!--Campi se l'utente non è loggato-->
                        <c:if test="${empty loggedUser}">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="firstName">First name</label>
                                    <input maxlength="30" type="text" pattern="^(([A-Z][a-z]*([-'\s\.]))*([A-Z][a-z]*))$" class="form-control" id="firstName" name="firstName" placeholder="Your name..." value="" required="">
                                    <div class="invalid-feedback"> Valid first name is required. </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="lastName">Last name</label>
                                    <input maxlength="30" type="text" pattern="^(([A-Z][a-z]*([-'\s\.]))*([A-Z][a-z]*))$" class="form-control" id="lastName" name="lastName" placeholder="Your surname..." value="" required="">
                                    <div class="invalid-feedback"> Valid last name is required. </div>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="mail">E-mail</label>
                                <div class="input-group">
                                    <input maxlength="40" type="email" class="form-control" id="mail" name="mail" placeholder="you@example.com" required="">
                                    <div class="invalid-feedback" style="width: 100%;">Your email is required.</div>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="address">Address</label>
                                <input maxlength="150" type="text" pattern="^(((Via|Contrada|Piazza|Vicolo|Corso|Viale|Piazzale)\s)?(([A-Z]?[a-z0-9]*([-'\.\s]))*([A-Z]?[a-z0-9]+)))$" class="form-control" id="address" name="address" placeholder="1234 Main St" required="">
                                <div class="invalid-feedback"> Please enter your shipping address. </div>
                            </div>
                            <div class="row">
                                <div class="col-md-5 mb-3">
                                    <label for="country">Country</label>
                                    <c:set var="countryString" value='${countries.get(0)}' scope="page"/>
                                    <c:forEach items="${countries}" var="country">
                                        <c:set var="countryString" value='${countryString.concat("|").concat(country)}'/>
                                    </c:forEach>
                                    <input maxlength="2" placeholder="Your country..." pattern="^(${countryString})$" class="custom-select d-block w-100 bg-dark" list="countries" id="country" name="country" required="">
                                    <datalist id="countries">
                                        <c:forEach items="${countries}" var="country">
                                            <option value="${country}">${country}</option>
                                        </c:forEach>
                                    </datalist><br>
                                    <div class="invalid-feedback"> Please select a valid country. </div>
                                </div>
                                <div class="col-md-5 mb-3">
                                    <label for="city">City</label>
                                    <input pattern="^(([A-Z][a-z]*([-'\.\s]))*([A-Z]?[a-z]+))$" type="text" class="form-control" id="city" name="city" placeholder="Your city..." required="">
                                    <div class="invalid-feedback"> Please enter your city. </div>
                                </div>
                                <div class="col-md-3 mb-3">
                                    <label for="telephone">Telephone</label>
                                    <input name="telephone" pattern="^[(([+]|00)39)?((3[0-9]{2})(\d{7}))]$" type="text" class="form-control" id="telephone" placeholder="Your number..." required="">
                                    <div class="invalid-feedback"> Telephone required, please enter a valid telephone. </div>
                                </div>
                            </div>
                            <!--Fine Campi se l'utente non è loggato-->
                        </c:if>
                        <hr class="mb-4">
                        <hr class="mb-4">
                        <h4 class="mb-3">Payment</h4>
                        <div class="d-block my-3">
                            <div class="custom-control custom-radio">
                                <input id="credit" value="creditCard" name="paymentMethod" type="radio" class="custom-control-input" checked="" required="">
                                <label class="custom-control-label" for="credit">Credit card</label>
                            </div>
                            <div class="custom-control custom-radio">
                                <input id="debit"  value="debitCard" name="paymentMethod" type="radio" class="custom-control-input" required="">
                                <label class="custom-control-label" for="debit">Debit card</label>
                            </div>
                            <div class="custom-control custom-radio">
                                <input id="paypal"  value="paypal" name="paymentMethod" type="radio" class="custom-control-input" required="">
                                <label class="custom-control-label" for="paypal">PayPal</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="cc-name">Name on card</label>
                                <input maxlength="100" pattern="^(([A-Z][a-z]*([-'\s\\.]))*([A-Z][a-z]*))$" type="text" class="form-control" id="cc-name" name="cc-name" placeholder="" required="">
                                <small class="text-muted">Full name as displayed on card</small>
                                <div class="invalid-feedback"> Name on card is required </div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="cc-number">Credit card number</label>
                                <input type="text" pattern="^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$" class="form-control" id="cc-number" name="cc-number" placeholder="" required="">
                                <div class="invalid-feedback"> Valid credit card number is required </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3 mb-3">
                                <label for="cc-expiration">Expiration</label>
                                <input type="text" pattern="^(0[1-9]|1[0-2])\/?([0-9]{4}|[0-9]{2})$" class="form-control" id="cc-expiration" name="cc-expiration" placeholder="" required="">
                                <div class="invalid-feedback"> Valid expiration date required (mm/yy format) </div>
                            </div>
                            <div class="col-md-3 mb-3">
                                <label for="cc-cvv">CVV</label>
                                <input pattern="^[0-9]{3,4}$" type="text" class="form-control" id="cc-cvv" name="cc-cvv" placeholder="" required="">
                                <div class="invalid-feedback"> Valid security code required.  </div>
                            </div>
                        </div>
                        <hr class="mb-4">
                        <button class="btn btn-primary checkoutBtn btn-lg btn-block" type="submit">Continue to checkout</button>
                    </form>
                </div>
            </div>
        </div>
        <div id="spacer"></div>
        <%@include file="footer.jsp"%> <!--footer-->
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
        <script src="${pageContext.request.contextPath}/js/utility.js"></script>
        <script src="${pageContext.request.contextPath}/js/formValidationFunctions.js"></script>
        <script src="${pageContext.request.contextPath}/js/checkout.js"></script>
    </body>
</html>