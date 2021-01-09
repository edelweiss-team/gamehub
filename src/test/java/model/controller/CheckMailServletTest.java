package model.controller;

import controller.CheckMailServlet;
import controller.UpdateUserServlet;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CheckMailServletTest {

    private CheckMailServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static UserDAO dao = new UserDAO();
    private static User u2;

    @BeforeEach
    public void setUp() {
        servlet = new CheckMailServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setSession(session);
        BasicConfigurator.configure();
    }

    //TC_1.1_26
    @Test
    public void checkOk() throws ServletException, IOException {
        request.addParameter("mail", "nonesistente@gmail.com");
        servlet.doPost(request, response);
        assertTrue(response.getContentAsString().contains("<ok/>"));
    }

    @Test
    public void checkNotOk() throws ServletException, IOException {
        request.addParameter("mail", "nonesistentegmail.com");
        servlet.doPost(request, response);
        assertTrue(response.getContentAsString().contains("<notOk/>"));
    }


}
