package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import controller.userManagement.adminManagement.GetMoreOperatorsServlet;
import model.bean.Moderator;
import model.bean.Operator;
import model.bean.User;
import model.dao.ModeratorDAO;
import model.dao.OperatorDAO;
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

class GetMoreOperatorsServletTest {
    private GetMoreOperatorsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    static UserDAO ud = new UserDAO();
    static OperatorDAO od = new OperatorDAO();
    static User u;
    static User u2;
    static Operator op;
    static Operator op2;

    @BeforeEach
    void setUp() {
        servlet = new GetMoreOperatorsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    static void populate(){

        u = new User("NuovoUser", "22popo", "Gerard", "Bresc", "Via acqua", "Solofra", "IT", "1999-05-12", "gerrybres@gmail.it", 'm', "3917542031");
        u2 = new User("NuovoUser45", "22popo", "Gerard", "Bresc", "Via acqua", "Solofra",
                "IT", "1999-05-12", "gerrybresu@gmail.it", 'm', "3917542031");
        op = new Operator(u, "2020-03-03", "ldkmomsoppwdpmsmpmpps");
        op2 = new Operator(u2, "2020-03-03" , "oosnfvonovnodnod" );
        od.doSave(op);
        od.doSave(op2);

    }

    @Test
    public void GetMoreOperatorsIndexNotOk() throws ServletException, IOException {
        request.addParameter("startingIndex","1df$7)");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void getMoreOperatorsRequestNull() throws ServletException, IOException{
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreOperatorsMore() throws ServletException, IOException{
        request.addParameter("operatorsPerRequest","3");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreOperatorsOne() throws ServletException, IOException{
        request.addParameter("operatorsPerRequest","1");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreOperatorsNone() throws ServletException, IOException{
        request.addParameter("operatorsPerRequest","0");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @AfterAll
    static void clear(){

        ud.doDeleteFromUsername("NuovoUser");
        ud.doDeleteFromUsername("NuovoUser45");

    }

}