package controller.userManagement.userProfileManagement;

import controller.userManagement.userProfileManagement.CheckUsernameServlet;
import model.bean.User;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckUsernameServletTest {

    private @NotNull CheckUsernameServlet servlet;
    private @NotNull MockHttpServletRequest request;
    private @NotNull MockHttpServletResponse response;

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

    @Test
    public void usernameIsTook() throws ServletException, IOException {
        UserDAO userDao = new UserDAO();
        User userWithCoolName = new User(
                "coolName",
                "password101",
                "Nomenuovo",
                "Cognomenuovo",
                "Inidirizzo",
                "Città",
                "Nazione",
                "1999-05-22",
                "Utente401@libero.it",
                'm',
                "3281823997");
        userDao.doSave(userWithCoolName);

        request.addParameter("username", userWithCoolName.getUsername());
        servlet.doPost(request, response);
        userDao.doDeleteFromUsername(userWithCoolName.getUsername());
        assertTrue(response.getContentAsString().contains("<notOk/>"));
    }


}
