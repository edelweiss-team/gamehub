package controller.userManagement;

import controller.HomeServlet;
import controller.userManagement.userProfileManagement.UpdateUserServlet;
import model.bean.User;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class HomeServletTest {

    private HomeServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static MockHttpSession session;
    private static UserDAO dao = new UserDAO();
    private static User u2;

    @BeforeEach
    public void setUp() {
        servlet = new HomeServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setSession(session);
        BasicConfigurator.configure();
    }


    @Test
    public void  oneWaySetup() throws ServletException {
        ServletConfig sg = new MockServletConfig();
        servlet.init(sg);
    }



}
