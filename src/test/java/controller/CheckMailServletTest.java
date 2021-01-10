package controller;

import controller.CheckMailServlet;
import controller.UpdateUserServlet;
import model.bean.User;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CheckMailServletTest {

    private @NotNull CheckMailServlet servlet;
    private @NotNull MockHttpServletRequest request;
    private @NotNull MockHttpServletResponse response;
    private @Nullable MockHttpSession session;
    private static @NotNull final UserDAO dao = new UserDAO();
    private static @Nullable User u2;

    @BeforeAll
    static public void inizializeVariable() {
        User u = new User("OtherUsername", "Password1", "Nomenuovo", "Cognomenuovo",
                "Inidirizzo", "Città", "FR",
                "1999-05-22", "Utente99@gmail.it", 'm',
                "3281883997");
        dao.doSave(u);
    }

    @AfterAll
    static public void DeSetup() {
        dao.doDeleteFromUsername("OtherUsername");
    }

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

    @Test
    public void checkOkNull() throws ServletException, IOException {
        request.addParameter("mail", "Utente99@gmail.it");
        servlet.doPost(request, response);
        assertTrue(response.getContentAsString().contains("<notOk/>"));
    }
}
