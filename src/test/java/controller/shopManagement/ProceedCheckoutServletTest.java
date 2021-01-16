package controller.shopManagement;

import controller.RequestParametersException;
import controller.shopManagement.ProceedCheckoutServlet;
import model.bean.*;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;
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
    private static PhysicalProductDAO daoPP = new PhysicalProductDAO();
    private static User u2;
    private Cart cart;
    private static DigitalProduct p;
    private static PhysicalProduct pp;

    @BeforeAll
    public static void inizialize(){
        u2 = new User("MyUsername4", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Uten@gmail.it", 'm',
                "3281883997");
        p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        pp = new PhysicalProduct(8, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting",
                new ArrayList<Category>(), new ArrayList<Tag>(), 330, "3x3x3", 3);
        dao.doSave(u2);
        daoP.doSave(p);
        daoPP.doSave(pp);
    }

    @BeforeEach
    public void setUp() {
        servlet = new ProceedCheckoutServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session =  new MockHttpSession();
        cart = new Cart();
        cart.addProduct(p,1);
        cart.addProduct(pp, 2);
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
    public void countryNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
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
    public void nameNullNotLogged() throws ServletException, IOException {

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
    public void surnnameNullNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
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
    public void telephoneNullNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void mailNullNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
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
    public void cityNullNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo1");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
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
    public void addressNullNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
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
    public void firstnameTooShortNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.com");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "345");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }


    @Test
    public void mailTooLongNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Gerardo@gmail.commmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        request.addParameter("address","Via Castello");
        request.addParameter("city","Fisciano");
        request.addParameter("telephone", "3328985488");
        request.addParameter("country", "FR");
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "NomeCarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "346");
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
    public void purchaseOkNotLoggedAlreadyRegistered() throws ServletException, IOException {

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
    public void purchaseOkEmailNotValidNotLogged() throws ServletException, IOException {

        request.addParameter("firstName", "Gerardo");
        request.addParameter("lastName", "Brescia");
        request.addParameter("mail","Uten@gmail.it");
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
        assertEquals("/WEB-INF/view/Login.jsp", response.getForwardedUrl());
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

    @Test
    public void expirationNullOkLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void ccnameNullOkLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void ccnameTooLongOkLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecartadnjsdjjdnvjnijnviufnivin" +
                "fuvninfinvininidfnindzinjndjnisnvoidhvhdihhdhjjdhgiagydyuhkhzdhkahskgfhakhsdk" +
                "jsakhkhfgfkakjshfhaskhonjndvjdsonoovoosjsjoisjojsvjijdoidsjhbvishuuuoo" +
                "jojoijijaojoaijjaojosovojfoiojfzojooefeusnnijidbdsjdjnvonnojdnjddjno");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void numberNullOkLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void cvvNullOkLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void paymentNullOkLogged() throws ServletException, IOException {
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
    public void paymentNotOkOkLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        cart.addProduct(p,1);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditc");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void cartEmptyLogged() throws ServletException, IOException {
        session.setAttribute("loggedUser", u2);
        cart = new Cart(u2);
        session.setAttribute("cart", cart);
        request.setSession(session);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }

    @Test
    public void cartNullLogged() throws ServletException, IOException {
        MockHttpSession session2 = new MockHttpSession();
        session2.setAttribute("loggedUser", u2);
        request.setSession(session2);
        request.addParameter("cc-expiration", "12/31");
        request.addParameter("cc-name", "Nomecarta");
        request.addParameter("cc-number", "4916551444956962");
        request.addParameter("cc-cvv", "347");
        request.addParameter("paymentMethod", "creditCard");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));

    }



    @AfterAll
    public static void clear(){
        dao.doDeleteFromUsername(u2.getUsername());
        dao.doDeleteFromUsername("Gerardo@gmail.com");
        daoP.doDelete(p.getId());
        daoPP.doDelete(pp.getId());
    }


}