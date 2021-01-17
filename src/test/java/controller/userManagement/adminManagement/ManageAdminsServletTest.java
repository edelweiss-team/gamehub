package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import model.bean.Admin;
import model.bean.Moderator;
import model.bean.User;
import model.dao.AdminDAO;
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

class ManageAdminsServletTest {
    private ManageAdminsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static User u;
    private static Moderator m;
    private static Admin a;
    private static UserDAO ud;
    private static ModeratorDAO md;
    private static AdminDAO ad;

    @BeforeEach
    void setUp() {
        servlet = new ManageAdminsServlet();
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
        ad = new AdminDAO();
        ud.doSave(u);
        m= new Moderator(u,"2021-01-15");
        a= new Admin(m,false);
        md.doSave(m);
        ad.doSave(a);
    }

    @Test
    public void operationNull() throws ServletException, IOException {
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin", "notValidOperation");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkSuperadminNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","update_admin");
        request.addParameter("editable-isSuperAdmin","5g");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkUpdateOkSuperadminOkUpdateNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","update_admin");
        request.addParameter("editable-isSuperAdmin","true");
        request.addParameter("old-name","MyUser1234");
        ad.doDeleteByUsername(a.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkUpdateOkSuperadminOkUpdateOk() throws ServletException, IOException {
        request.addParameter("manage_admin","update_admin");
        request.addParameter("editable-isSuperAdmin","true");
        request.addParameter("old-name","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","remove_admin");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkRemoveOkLengthOkUserNotNull() throws ServletException, IOException {
        request.addParameter("manage_admin","remove_admin");
        request.addParameter("removeAdmin","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkLengthOkUserNull() throws ServletException, IOException {
        request.addParameter("manage_admin","remove_admin");
        request.addParameter("removeAdmin","MyUser1234");
        ad.doDeleteByUsername(a.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkMinLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","remove_admin");
        request.addParameter("removeAdmin","MyUs");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkMaxLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","remove_admin");
        request.addParameter("removeAdmin","MyUseraaaaasdasdasdasd");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkAddNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","add_admin");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkAddOkSuperadminNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","add_admin");
        request.addParameter("editable-isSuperAdmin","5g");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkAddOkSuperadminOkAdminNameNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","add_admin");
        request.addParameter("editable-isSuperAdmin","true");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkAddOkSuperadminOkMinLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","add_admin");
        request.addParameter("editable-isSuperAdmin","true");
        request.addParameter("adminName","MyUs");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkSuperadminOkMaxLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_admin","add_admin");
        request.addParameter("editable-isSuperAdmin","true");
        request.addParameter("adminName","MyUseraaaaasdasdasdasd");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkSuperadminOkLengthOkUserNotNull() throws ServletException, IOException {
        request.addParameter("manage_admin","add_admin");
        request.addParameter("editable-isSuperAdmin","true");
        request.addParameter("adminName","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkSuperadminOkLengthOkUserNullModeratorNull() throws ServletException, IOException {
        request.addParameter("manage_admin","add_admin");
        request.addParameter("editable-isSuperAdmin","true");
        request.addParameter("adminName","MyUser1234");
        ad.doDeleteByUsername(a.getUsername());
        md.doDeleteByUsername(m.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkSuperadminOkLengthOkUserNullModeratorNotNull() throws ServletException, IOException {
        request.addParameter("manage_admin","add_admin");
        request.addParameter("editable-isSuperAdmin","true");
        request.addParameter("adminName","MyUser1234");
        ad.doDeleteByUsername(a.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @AfterEach
    void addIf(){
        if(ad.doRetrieveByUsername(a.getUsername())==null){
            ad.doSave(a);
        }
        if(md.doRetrieveByUsername(m.getUsername())==null)
            md.doSave(m);
    }

    @AfterAll
    public static void clearAll(){
        if(ad.doRetrieveByUsername(a.getUsername())!=null)
            ad.doDeleteByUsername(a.getUsername());
        if(md.doRetrieveByUsername(m.getUsername())!=null)
            md.doDeleteByUsername(m.getUsername());
        if(ud.doRetrieveByUsername(u.getUsername())!=null)
            ud.doDeleteFromUsername(u.getUsername());
    }
}