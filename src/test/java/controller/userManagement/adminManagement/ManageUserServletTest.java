package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import controller.shopManagement.CartServlet;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.User;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ManageUserServletTest {
    private ManageUserServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static User u;
    private static UserDAO ud;

    @BeforeEach
    void setUp() {
        servlet = new ManageUserServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();

        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void populate(){
        u = new User("MyUser1234", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente401@gmail.it", 'm',
                "3281883997");
        ud = new UserDAO();
        ud.doSave(u);
    }

    @Test
    public void removeUserNull() throws ServletException, IOException {
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void removeUserMinLengthNotOk() throws ServletException, IOException {
        u.setUsername("Use9e");
        request.addParameter("removeUser",u.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void removeUserMaxLengthNotOk() throws ServletException, IOException {
        u.setUsername("Userasdasdasdasdasdasd");
        request.addParameter("removeUser",u.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void removeUserMaxLengthOk() throws ServletException, IOException {
        request.addParameter("removeUser",u.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void removeUserMaxLengthOkUserNull() throws ServletException, IOException {
        ud.doDeleteFromUsername(u.getUsername());
        request.addParameter("removeUser",u.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

}