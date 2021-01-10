package controller;

import controller.LogoutServlet;
import controller.RequestParametersException;
import model.bean.User;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowAdminAreaServletTest {

    private LogoutServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static UserDAO dao = new UserDAO();
    private User u2;

    @BeforeEach
    public void setUp() {
        servlet = new LogoutServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        u2 = new User("MyUsername", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente@gmail.it", 'm',
                "3281883997");

        request.setSession(session);
        BasicConfigurator.configure();
    }

    //TC_1.1_26
    @Test
    public void logoutNotOk() throws ServletException, IOException {
        session = new MockHttpSession();
        request.setSession(session);
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));
    }

    @Test
    public void logoutOk() throws ServletException, IOException {
        session = new MockHttpSession();
        session.setAttribute("loggedUser" , u2);
        request.setSession(session);
        assertDoesNotThrow(() -> servlet.doPost(request, response));
    }

}
