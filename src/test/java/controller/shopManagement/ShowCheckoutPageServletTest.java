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

class ShowCheckoutPageServletTest {

    private ShowCheckoutPageServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static DigitalProductDAO daoP = new DigitalProductDAO();
    private static PhysicalProductDAO daoPP = new PhysicalProductDAO();
    private Cart cart;
    private static DigitalProduct p;
    private static PhysicalProduct pp;
    private static int idp;
    private static int idpp;

    @BeforeAll
    public static void inizialize(){
        p = new DigitalProduct(7, "NuovoProdottoTesting1234", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        pp = new PhysicalProduct(8, "NuovoProdottoTesting2321", 23.56, "testing", "imagetesting",
                new ArrayList<Category>(), new ArrayList<Tag>(), 330, "3x3x3", 3);
        p=daoP.doSave(p);
        pp=daoPP.doSave(pp);
        idp=p.getId();
        idpp=pp.getId();
    }

    @BeforeEach
    void setUp() {
        servlet = new ShowCheckoutPageServlet();
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
    public void cartIsNull() throws ServletException, IOException {
        session.setAttribute("cart", null);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void cartIsEmpty() throws ServletException, IOException{
        cart.removeProduct(p,1);
        cart.removeProduct(pp,2);
        session.setAttribute("cart", cart);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    public void forwardOk() throws ServletException, IOException{
        session.setAttribute("cart", cart);
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/checkout.jsp", response.getForwardedUrl());
    }

    @AfterAll
    public static void clear(){
        daoP.doDelete(p.getId());
        daoPP.doDelete(pp.getId());
    }

}