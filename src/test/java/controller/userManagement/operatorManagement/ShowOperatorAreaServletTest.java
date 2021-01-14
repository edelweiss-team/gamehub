package controller.userManagement.operatorManagement;

import controller.RequestParametersException;
import controller.userManagement.operatorManagement.ShowOperatorAreaServlet;
import model.bean.Operator;
import model.bean.Order;
import model.bean.User;
import model.dao.OperatorDAO;
import model.dao.OrderDAO;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShowOperatorAreaServletTest {
    private ShowOperatorAreaServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static OrderDAO od = new OrderDAO();
    private static UserDAO ud = new UserDAO();
    private static OperatorDAO opdao = new OperatorDAO();
    private static User u2 = new User("cicciopiccio", "Password1","Nomenuovo", "Cognomenuovo",
            "Inidirizzo","Città","Nazione",
            "1999-05-22", "Utente256246@gmail.it", 'm',
            "3281883997");
    private static Order o;

    @BeforeEach
    void setUp() {
        servlet = new ShowOperatorAreaServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        ud.doSave(u2);
        o = new Order(10,u2,null,"2000-10-10");
        od.doSave(o);
        BasicConfigurator.configure();
    }

    @Test
    public void loggedUserIsNull() throws ServletException, IOException {
        Operator operator = new Operator("OtherUsername123", "Password1",
                "Nomenuovo", "Cognomenuovo",
                "Inidirizzo", "Città", "FR",
                "1999-05-22", "Utente99414@gmail.it", 'm',
                "3281883997","2021-05-22","aaa");
        opdao.doSave(operator);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void ordersNotNull() throws ServletException, IOException {
        Operator operator = new Operator("OtherUsername123", "Password1", "Nomenuovo", "Cognomenuovo",
                "Inidirizzo", "Città", "FR",
                "1999-05-22", "Utente99414@gmail.it", 'm',
                "3281883997","2021-05-22","aaa");
        session = new MockHttpSession();
        opdao.doSave(operator);
        session.setAttribute("loggedUser" , operator);
        request.setSession(session);
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/OperatorArea.jsp", response.getForwardedUrl());
    }

    @AfterEach
    public void DeSetup() {
        od.doDelete(o.getId());
        ud.doDeleteFromUsername(u2.getUsername());
        opdao.doDeleteByUsername("OtherUsername123");
    }
}