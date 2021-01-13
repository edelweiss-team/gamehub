package controller;

import model.bean.*;
import model.dao.DigitalProductDAO;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProceedCheckoutServletTest {


    private ProceedCheckoutServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static UserDAO dao = new UserDAO();
    private static DigitalProductDAO daoP = new DigitalProductDAO();
    private static User u2;
    private Cart cart;
    private static DigitalProduct p;

    @BeforeAll
    public static void inizialize(){
        u2 = new User("MyUsername4", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Uten@gmail.it", 'm',
                "3281883997");
        p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        dao.doSave(u2);
        daoP.doSave(p);
    }

    @BeforeEach
    public void setUp() {
        servlet = new ProceedCheckoutServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session =  new MockHttpSession();
        cart = new Cart();
        cart.addProduct(p,1);
        session.setAttribute("cart", cart);
        request.setSession(session);
        BasicConfigurator.configure();
    }

    @Test
    public void NameTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "G");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void nameNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo1");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void surnameTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "B");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void surnameNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia1");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void mailTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Ger@it");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

       @Test
    public void mailNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo.it");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void addressToShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","V");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void addressNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello!!!");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void nationTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "N");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void nationNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "F1");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void nationNotExistNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "AY");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void cityTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","F");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void cityNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano1");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void numberTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "333");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void numberNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "33abc85488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void NameCartTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NO");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void NameCartNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta!");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void NumberCartTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "7623682163");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void NumberCartNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "491655144a956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void expirationTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "2/03");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void expirationNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/aa");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void cvvTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "34");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void cvvNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "34a");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void paymentNotSelectedNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }




    @Test
    public void purchaseOkNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/purchaseConfirmed.jsp", response.getForwardedUrl());

    }


    @Test
    public void payementNotSelectedLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void nameTooShortLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NO");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void formatNotValidLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta!");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void numberTooShortLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "7623682163");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void numberNotValidLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "491655144a956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void expireTooShortLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "2/03");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void expireNotValidLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/aa");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void cvvTooShortLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "34");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void cvvNotValidLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "34a");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void purchaseOkLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/purchaseConfirmed.jsp", response.getForwardedUrl());

    }



    @AfterAll
    public static void clear(){
        dao.doDeleteFromUsername(u2.getUsername());
        daoP.doDelete(p.getId());
    }


}