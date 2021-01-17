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

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetMoreAdminsServletTest {
    private GetMoreAdminsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static User u;
    private static User u2;
    private static Moderator m;
    private static Moderator m2;
    private static Admin a;
    private static Admin a2;
    private static UserDAO ud;
    private static ModeratorDAO md;
    private static AdminDAO ad;

    @BeforeEach
    void setUp() {
        servlet = new GetMoreAdminsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void populate(){
        u = new User("MyUser1234", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente401@gmail.it", 'm',
                "3281883997");
        u2 = new User("MyUser12345", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente402@gmail.it", 'm',
                "3281883997");
        ud = new UserDAO();
        md = new ModeratorDAO();
        ad = new AdminDAO();
        ud.doSave(u);
        ud.doSave(u2);
        m= new Moderator(u,"2021-01-15");
        m2= new Moderator(u2,"2021-01-15");
        a= new Admin(m,false);
        a2= new Admin(m2,false);
        md.doSave(m);
        ad.doSave(a);
    }

    @Test
    public void GetMoreAdminsIndexNotOk() throws ServletException, IOException {
        request.addParameter("startingIndex","1df$7)");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void getMoreAdminsNone() throws ServletException, IOException{
        request.addParameter("AdminsPerRequest","0");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreAdminsListNone() throws ServletException, IOException{
        request.addParameter("AdminsPerRequest","8");
        request.addParameter("startingIndex","1");
        ad.doDeleteByUsername(a.getUsername());
        ad.doDeleteByUsername(a2.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreAdminsOne() throws ServletException, IOException{
        request.addParameter("AdminsPerRequest","1");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreAdminsMore() throws ServletException, IOException{
        request.addParameter("AdminsPerRequest","3");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @AfterEach
    void addIf(){
        if(ad.doRetrieveByUsername(a.getUsername())==null)
            ad.doSave(a);
        if(ad.doRetrieveByUsername(a2.getUsername())==null)
            ad.doSave(a2);

    }

    @AfterAll
    public static void clearAll(){
        if(ad.doRetrieveByUsername(a.getUsername())!=null)
            ad.doDeleteByUsername(a.getUsername());
        if(md.doRetrieveByUsername(m.getUsername())!=null)
            md.doDeleteByUsername(m.getUsername());
        if(ud.doRetrieveByUsername(u.getUsername())!=null)
            ud.doDeleteFromUsername(u.getUsername());
        if(ad.doRetrieveByUsername(a2.getUsername())!=null)
            ad.doDeleteByUsername(a2.getUsername());
        if(md.doRetrieveByUsername(m2.getUsername())!=null)
            md.doDeleteByUsername(m2.getUsername());
        if(ud.doRetrieveByUsername(u2.getUsername())!=null)
            ud.doDeleteFromUsername(u2.getUsername());
    }
}