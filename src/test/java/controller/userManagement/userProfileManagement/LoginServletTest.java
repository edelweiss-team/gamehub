package controller.userManagement.userProfileManagement;

import controller.RequestParametersException;
import controller.userManagement.userProfileManagement.LoginServlet;
import model.bean.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginServletTest {

    private LoginServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static UserDAO dao = new UserDAO();

    @BeforeEach
    public void setUp() {
        servlet = new LoginServlet();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.addHeader("referer", "/WEB-INF/view/Login.jsp");
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    static public void inizializeVariable() {
        User u = new User("MyUsername", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente99@gmail.it", 'm',
                "3281883997");
        dao.doSave(u);
    }

    //TC_2.1_1
    @Test
    public void userTooShort() throws ServletException, IOException {
        request.addParameter("login", "login");
        request.addParameter("username", "My");
        request.addParameter("password", "Password1");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Login.jsp" , response.getForwardedUrl());
    }

    //TC_2.1_1
    @Test
    public void userTooLong() throws ServletException, IOException {
        request.addParameter("login", "login");
        request.addParameter("username", "Myaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        request.addParameter("password", "Password1");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Login.jsp" , response.getForwardedUrl());
    }

    //TC_2.1_2
    @Test
    public void userFormatNotValid() throws ServletException, IOException {
        request.addParameter("login", "login");
        request.addParameter("username", "MyUsername!");
        request.addParameter("password", "Password1");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Login.jsp" , response.getForwardedUrl());
    }

    @Test
    public void passwordNotValid() throws ServletException, IOException {
        request.addParameter("login", "login");
        request.addParameter("username", "MyUsername!");
        request.addParameter("password", "Pass");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Login.jsp" , response.getForwardedUrl());
    }

    //TC_2.1_3
    @Test
    public void passwordNotExists() throws ServletException, IOException {
        request.addParameter("login", "login");
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Login.jsp" , response.getForwardedUrl());
    }


    //TC_2.1_4
    @Test
    public void loginOk() throws ServletException, IOException {
        request.addParameter("login", "login");
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        servlet.doPost(request,response);
        assertEquals("." , response.getForwardedUrl());
    }

    @Test
    public void AlreadyAUserInSession() {
        session.setAttribute("loggedUser", "10");
        request.setSession(session);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void LoginParameterIsNull() {
        session.setAttribute("loggedUser", null);
        request.setSession(session);
        request.setSession(session);
        // 'login' parameter not added to request, it will be null
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @AfterAll
    static public void DeSetup() {
        dao.doDeleteFromUsername("MyUsername");
    }

}
