package controller;

import model.bean.Operator;
import model.bean.Order;
import model.bean.User;
import model.dao.OperatorDAO;
import model.dao.OrderDAO;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShowReservedAreaServletTest {
    private ShowReservedAreaServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static OrderDAO od = new OrderDAO();
    private static UserDAO ud = new UserDAO();
    private static User u2 = new User("cicciopiccio", "Password1","Nomenuovo", "Cognomenuovo",
            "Inidirizzo","Città","Nazione",
            "1999-05-22", "Utente256246@gmail.it", 'm',
            "3281883997");
    private static Order o;

    @BeforeEach
    void setUp() {
        servlet = new ShowReservedAreaServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void setUpBeforeAll(){
        ud.doSave(u2);
    }

    @Test
    public void loggedUserIsNull() throws ServletException, IOException {
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void userNotNull() throws ServletException, IOException {
        session = new MockHttpSession();
        session.setAttribute("loggedUser" , u2);
        o = new Order(10,u2,null,"2000-10-10");
        od.doSave(o);
        request.setSession(session);
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/ReservedArea.jsp", response.getForwardedUrl());
    }

    @Test
    public void userOrdersNull() throws ServletException, IOException {
        session = new MockHttpSession();
        session.setAttribute("loggedUser" , u2);
        request.setSession(session);
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/ReservedArea.jsp", response.getForwardedUrl());
    }

    @AfterAll
    public static void DeSetup() {
        od.doDelete(o.getId());
        ud.doDeleteFromUsername(u2.getUsername());
    }
}