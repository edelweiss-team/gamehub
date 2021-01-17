package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import controller.userManagement.adminManagement.GetMoreUsersServlet;
import model.bean.User;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetMoreUsersServletTest {
    private GetMoreUsersServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    static User  u;
    static User  u2;
    static UserDAO ud = new UserDAO();

    @BeforeEach
    void setUp() {
        servlet = new GetMoreUsersServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    static void populate(){

        u = new User("NuovoUser", "22popo", "Gerard", "Bresc", "Via acqua", "Solofra", "IT", "1999-05-12", "gerrybres@gmail.it", 'm', "3917542031");
        u2 = new User("NuovoUser45", "22popo", "Gerard", "Bresc", "Via acqua", "Solofra",
                "IT", "1999-05-12", "gerrybresu@gmail.it", 'm', "3917542031");
        ud.doSave(u);
        ud.doSave(u2);
    }

    @Test
    public void GetMoreUsersIndexNotOk() throws ServletException, IOException {
        request.addParameter("startingIndex","1df$7)");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void getMoreUsersNone() throws ServletException, IOException{
        request.addParameter("usersPerRequest","0");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreUsersOne() throws ServletException, IOException{
        request.addParameter("usersPerRequest","1");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreUsersMore() throws ServletException, IOException{
        request.addParameter("usersPerRequest","3");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @AfterAll
    public static void clear(){

        ud.doDeleteFromUsername("NuovoUser");
        ud.doDeleteFromUsername("NuovoUser45");
    }

}