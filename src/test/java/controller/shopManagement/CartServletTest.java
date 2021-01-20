package controller.shopManagement;

import controller.RequestParametersException;
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

import static org.junit.jupiter.api.Assertions.*;

class CartServletTest {

    private CartServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private User u;
    private UserDAO ud;
    private DigitalProductDAO dpd;
    private PhysicalProductDAO ppd;
    private static DigitalProduct pd1;
    private static PhysicalProduct pp1;

    @BeforeEach
    void setUp() {
        servlet = new CartServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();

        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void populate() {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        UserDAO ud = new UserDAO();
        ud.doSave(u);
        DigitalProductDAO dpd = new DigitalProductDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();
         pd1 = new DigitalProduct(50, "NuovoProdottoTesting1", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
         pp1 = new PhysicalProduct(60, "NuovoProdottoTesting4", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "1x1x1", 20.05);
        pd1 = dpd.doSave(pd1);
        pp1 = ppd.doSave(pp1);

    }


    @Test
    public void addToCartTypeNull() throws ServletException, IOException{
        request.setParameter("addCart", "true");
        request.setParameter("productId", "1");
        request.setParameter("quantity", "1");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }


    @Test
    public void addToCartTypeWrong() throws ServletException, IOException{
        request.setParameter("addCart", "true");
        request.setParameter("productId", "1");
        request.setParameter("quantity", "1");
        request.setParameter("productType", "wrongType");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void removeCartNegativeQuantity() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo",
                "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        request.setParameter("removeCart", "true");
        request.setParameter("productId", Integer.toString(pd1.getId()));
        request.setParameter("productType", "digital");
        request.setParameter("quantity", "-1");
        servlet.doPost(request, response);
        assertFalse(response.getContentAsString().isEmpty());
    }


    @Test
    public void addToCartTypeDigitalNull() throws ServletException, IOException{
        request.setParameter("addCart", "true");
        request.setParameter("productId", "-1");
        request.setParameter("quantity", "1");
        request.setParameter("productType", "digital");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }


    @Test
    public void addToCartTypePhysical() throws ServletException, IOException{
        request.setParameter("addCart", "true");
        request.setParameter("productId", "-1");
        request.setParameter("quantity", "1");
        request.setParameter("productType", "physical");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }


    @Test
    public void addToCartTooMuchQuantity() throws ServletException, IOException {
        request.setParameter("addCart", "true");
        request.setParameter("productId", Integer.toString(pp1.getId()));
        request.setParameter("quantity", "100000");
        request.setParameter("productType", "physical");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void addToCartCorrect() throws ServletException, IOException {
        request.setParameter("addCart", "true");
        request.setParameter("productId", Integer.toString(pd1.getId()));
        request.setParameter("quantity", "1");
        request.setParameter("productType", "digital");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void cartNullLoggedUserNotNull() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void addToCartCorrectAndLoggedUser() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        request.setParameter("addCart", "true");
        request.setParameter("productId", Integer.toString(pd1.getId()));
        request.setParameter("quantity", "1");
        request.setParameter("productType", "digital");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void removeCartNotNullAndTypePhysical() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        request.setParameter("removeCart", "true");
        request.setParameter("productId", Integer.toString(-1));
        request.setParameter("productType", "physical");
        request.setParameter("quantity", "1");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void removeCartNotNullAndTypeDigital() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        request.setParameter("removeCart", "true");
        request.setParameter("productId", Integer.toString(pp1.getId()));
        request.setParameter("productType", "digital");
        request.setParameter("quantity", "1");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }


    @Test
    public void removeCartNotNullAndTypeWrong() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        request.setParameter("removeCart", "true");
        request.setParameter("productId", Integer.toString(pp1.getId()));
        request.setParameter("productType", "wrong");
        request.setParameter("quantity", "1");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void removeCartNotNullAndTypeNull() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1", "Nomenuovo",
                "Cognomenuovo",
                "Inidirizzo", "Città", "Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        request.setParameter("removeCart", "true");
        request.setParameter("productId", "-1");
        request.setParameter("quantity", "1");
        assertThrows(RequestParametersException.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void removeCartNotNullAndProductNull() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        request.setParameter("removeCart", "true");
        request.setParameter("productType", "digital");
        request.setParameter("productId", "100000");
        request.setParameter("quantity", "1");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void removeCartLoggedUserNotNull() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        Cart c = new Cart(u);
        c.addProduct(pd1, 5);
        session.setAttribute("loggedUser", u);
        session.setAttribute("cart", c);
        request.setSession(session);
        request.setParameter("removeCart", "true");
        request.setParameter("productType", "digital");
        request.setParameter("quantity", "1");
        request.setParameter("productId", Integer.toString(pd1.getId()));

        servlet.doPost(request, response);
        assertFalse(response.getContentAsString().isEmpty());
    }

    @Test
    public void showCartNotNull() throws ServletException, IOException {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser", u);
        request.setSession(session);
        request.setParameter("showCart", "true");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/cart.jsp", response.getForwardedUrl());
    }

    @AfterAll
    public static void clear() {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        UserDAO ud = new UserDAO();
        ud.doDeleteFromUsername(u.getUsername());

        DigitalProductDAO dpd = new DigitalProductDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();
        dpd.doDelete(pd1.getId());
        ppd.doDelete(pp1.getId());
    }
}