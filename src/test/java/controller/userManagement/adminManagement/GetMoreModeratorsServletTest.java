package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import controller.userManagement.adminManagement.GetMoreModeratorsServlet;
import model.bean.Moderator;
import model.bean.Operator;
import model.bean.User;
import model.dao.ModeratorDAO;
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


class GetMoreModeratorsServletTest {
    private GetMoreModeratorsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static User u;
    private static User u2;
    private static Moderator op;
    private static Moderator op2;
    private static ModeratorDAO od = new ModeratorDAO();
    private static UserDAO ud = new UserDAO();

    @BeforeEach
    void setUp() {
        servlet = new GetMoreModeratorsServlet();
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

        op = new Moderator(u, "2020-03-03");
        op2 = new Moderator(u2, "2020-03-03");

        od.doSave(op);
        od.doSave(op2);

    }

    @Test
    public void GetMoreModeratorsIndexNotOk() throws ServletException, IOException {
        request.addParameter("startingIndex","1df$7)");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void getMoreModeratorsRequestNull() throws ServletException, IOException{
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreModeratorsMore() throws ServletException, IOException{
        request.addParameter("moderatorsPerRequest","3");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreModeratorsOne() throws ServletException, IOException{
        request.addParameter("moderatorsPerRequest","1");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreModeratorsNone() throws ServletException, IOException{
        request.addParameter("moderatorsPerRequest","0");
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