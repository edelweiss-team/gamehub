package controller.userManagement.userProfileManagement;

import controller.RequestParametersException;
import model.bean.User;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShowLoginPageServletTest {
    private ShowLoginPageServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        servlet = new ShowLoginPageServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @Test
    public void loggedUserIsNull() throws ServletException, IOException {
        session = new MockHttpSession();
        request.setSession(session);
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Login.jsp", response.getForwardedUrl());
    }

    @Test
    public void loggedUserNotNull() throws ServletException, IOException {
        session = new MockHttpSession();
        User u2 = new User("cicciopiccio", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente256246@gmail.it", 'm',
                "3281883997");
        session.setAttribute("loggedUser" , u2);
        request.setSession(session);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }
}