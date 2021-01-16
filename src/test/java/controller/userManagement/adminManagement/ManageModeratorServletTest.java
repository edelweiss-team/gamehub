package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import model.bean.Moderator;
import model.bean.User;
import model.dao.ModeratorDAO;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ManageModeratorServletTest {
    private ManageModeratorServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static User u;
    private static Moderator m;
    private static UserDAO ud;
    private static ModeratorDAO md;

    @BeforeEach
    void setUp() {
        servlet = new ManageModeratorServlet();
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
        md = new ModeratorDAO();
        ud.doSave(u);
        m= new Moderator(u,"2021-01-15");
        md.doSave(m);
    }

    @Test
    public void operationNull() throws ServletException, IOException {
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractNull() throws ServletException, IOException {
        request.addParameter("manage_moderator","update_moderator");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","update_moderator");
        request.addParameter("editable-contractTime","13ff)");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractOkUpdateNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","update_moderator");
        request.addParameter("editable-contractTime","2021-01-15");
        request.addParameter("old-name","MyUser1234");
        md.doDeleteByUsername(m.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkUpdateOkContractOkUpdateOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","update_moderator");
        request.addParameter("editable-contractTime","2021-01-15");
        request.addParameter("old-name","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","remove_moderator");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkRemoveOkLengthOkUserNotNull() throws ServletException, IOException {
        request.addParameter("manage_moderator","remove_moderator");
        request.addParameter("removeModerator","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkLengthOkUserNull() throws ServletException, IOException {
        request.addParameter("manage_moderator","remove_moderator");
        request.addParameter("removeModerator","MyUser1234");
        md.doDeleteByUsername(m.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkMinLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","remove_moderator");
        request.addParameter("removeModerator","MyUs");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkMaxLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","remove_moderator");
        request.addParameter("removeModerator","MyUseraaaaasdasdasdasd");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkAddNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","add_moderator");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkAddOkContractOkModeratorNameNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","add_moderator");
        request.addParameter("contractTime","2021-01-15");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkAddOkContractOkMinLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","add_moderator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("moderatorName","MyUs");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkContractOkMaxLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_moderator","add_moderator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("moderatorName","MyUseraaaaasdasdasdasd");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkContractOkLengthOkUserNotNull() throws ServletException, IOException {
        request.addParameter("manage_moderator","add_moderator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("moderatorName","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkContractOkLengthOkUserNullModeratorNull() throws ServletException, IOException {
        request.addParameter("manage_moderator","add_moderator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("moderatorName","MyUser1234");
        md.doDeleteByUsername(m.getUsername());
        ud.doDeleteFromUsername(u.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkContractOkLengthOkUserNullModeratorNotNull() throws ServletException, IOException {
        request.addParameter("manage_moderator","add_moderator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("moderatorName","MyUser1234");
        md.doDeleteByUsername(m.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @AfterEach
    void addIf(){
        if(md.doRetrieveByUsername(m.getUsername())==null)
            md.doSave(m);
        if(ud.doRetrieveByUsername(u.getUsername())==null)
            ud.doSave(u);
    }

    @AfterAll
    public static void clearAll(){
        if(md.doRetrieveByUsername(m.getUsername())!=null)
            md.doDeleteByUsername(m.getUsername());
        if(ud.doRetrieveByUsername(u.getUsername())!=null)
            ud.doDeleteFromUsername(u.getUsername());
        if(ud.doRetrieveByUsername(u.getUsername())!=null)
            ud.doDeleteFromUsername(u.getUsername());
    }
}