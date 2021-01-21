package controller.userManagement.operatorManagement;

import controller.RequestParametersException;
import model.bean.Order;
import model.bean.User;
import model.dao.OrderDAO;
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

import static org.junit.jupiter.api.Assertions.*;

class DeclineOrderServletTest {

    private DeclineOrderServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static OrderDAO od;
    private static Order o;

    @BeforeEach
    void setUp() {
        servlet = new DeclineOrderServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();

        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void populate() {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Via Inidirizzo","Città","IT",
                "1999-05-22", "test6@ananana.it", 'm',
                "3281883997");
        UserDAO ud = new UserDAO();
        ud.doSave(u);
        OrderDAO od = new OrderDAO();
        o = new Order(30, u, null, "2020-11-11");
        od.doSave(o);
    }

    @Test
    public void declineOrderIdWrong() {
        request.setParameter("declineOrder", "prova");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void declineOrderIdRightOrderNull() {
        request.setParameter("declineOrder", "50");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void declineOrderOk() throws ServletException, IOException {
        request.setParameter("declineOrder", "30");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/OperatorArea.jsp", response.getForwardedUrl());
    }

    @AfterAll
    public static void clear() {
        User u = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Via Inidirizzo","Città","IT",
                "1999-05-22", "test6@ananana.it", 'm',
                "3281883997");
        UserDAO ud = new UserDAO();
        ud.doDeleteFromUsername(u.getUsername());
        OrderDAO od = new OrderDAO();
        o = new Order(30, u, null, "2020-11-11");
        od.doDelete(o.getId());
    }

}