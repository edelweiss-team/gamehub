package model.controller;

import controller.CheckMailServlet;
import controller.CheckUsernameServlet;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckUsernameServletTest {

    private CheckUsernameServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    public void setUp() {
        servlet = new CheckUsernameServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    //TC_1.1_26
    @Test
    public void checkOk() throws ServletException, IOException {
        request.addParameter("username", "nonesiste");
        servlet.doPost(request, response);
        assertTrue(response.getContentAsString().contains("<ok/>"));
    }

    @Test
    public void checkNotOk() throws ServletException, IOException {
        request.addParameter("username", "nonesiste!!!");
        servlet.doPost(request, response);
        assertTrue(response.getContentAsString().contains("<notOk/>"));
    }


}
