package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import model.bean.Operator;
import model.bean.User;
import model.dao.OperatorDAO;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ManageOperatorServletTest {
    private ManageOperatorServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static User u;
    private static Operator m;
    private static UserDAO ud = new UserDAO();
    private static OperatorDAO md;

    @BeforeEach
    void setUp() {
        servlet = new ManageOperatorServlet();
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
        md = new OperatorDAO();
        m= new Operator(u,"2021-01-15","ciao");
        md.doSave(m);
    }

    @Test
    public void operationNull() throws ServletException, IOException {
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","gigino");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractNullCvNull() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractNotOkCvNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime","13ff)");
        request.addParameter("editable-cv","13ff)");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractOkCvNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime","2021-01-15");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractNotOkCvOk() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime","fd3)");
        request.addParameter("editable-cv","13ff)");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractOkCvMinLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime","2021-01-15");
        request.addParameter("editable-cv","ci");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractOkCvMaxLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime","2021-01-15");
        String s = "a";
        for(int i=0;i<10000;i++)
            s = s + "a";
        request.addParameter("editable-cv",s);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkUpdateOkContractOkUpdateNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime","2021-01-15");
        request.addParameter("editable-cv","ciao");
        request.addParameter("old-name","MyUser1234");
        md.doDeleteByUsername(m.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkUpdateOkContractOkUpdateOk() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime","2021-01-15");
        request.addParameter("editable-cv","ciao");
        request.addParameter("old-name","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationUpdateContractTimeNull() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime", (String) null);
        request.addParameter("editable-cv","ciao");
        request.addParameter("old-name","MyUser1234");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationUpdateCVNull() throws ServletException, IOException {
        request.addParameter("manage_operator","update_operator");
        request.addParameter("editable-contractTime","2021-01-15");
        request.addParameter("editable-cv",(String) null);
        request.addParameter("old-name","MyUser1234");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }



    @Test
    public void operationOkRemoveNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","remove_operator");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkRemoveOkLengthOkUserNotNull() throws ServletException, IOException {
        request.addParameter("manage_operator","remove_operator");
        request.addParameter("removeOperator","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkLengthOkUserNull() throws ServletException, IOException {
        request.addParameter("manage_operator","remove_operator");
        request.addParameter("removeOperator","MyUser1234");
        md.doDeleteByUsername(m.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkMinLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","remove_operator");
        request.addParameter("removeOperator","MyUs");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkRemoveOkMaxLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","remove_operator");
        request.addParameter("removeOperator","MyUseraaaaasdasdasdasd");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationOkAddNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","add_operator");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkAddOkContractOkOperatorNameNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","add_operator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("curriculum","ciao");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationOkAddOkContractOkMinLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","add_operator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("curriculum","ciao");
        request.addParameter("userName","MyUs");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkContractOkMaxLengthNotOk() throws ServletException, IOException {
        request.addParameter("manage_operator","add_operator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("curriculum","ciao");
        request.addParameter("userName","MyUseraaaaasdasdasdasd");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkContractOkLengthOkUserNotNull() throws ServletException, IOException {
        request.addParameter("manage_operator","add_operator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("curriculum","ciao");
        request.addParameter("userName","MyUser1234");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkContractOkLengthOkUserNullOperatorNull() throws ServletException, IOException {
        request.addParameter("manage_operator","add_operator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("curriculum","ciao");
        request.addParameter("userName","MyUser1234");
        md.doDeleteByUsername(m.getUsername());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @Test
    public void operationOkAddOkContractOkLengthOkUserNullOperatorNotNull() throws ServletException, IOException {
        request.addParameter("manage_operator","add_operator");
        request.addParameter("contractTime","2021-01-15");
        request.addParameter("curriculum","ciao");
        request.addParameter("userName","MyUser1234");
        md.doDeleteByUsername(m.getUsername());
        ud.doSave(u);
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());

    }

    @AfterEach
    void addIf(){
        if(md.doRetrieveByUsername(m.getUsername())==null)
            md.doSave(m);
    }

    @AfterAll
    public static void clearAll(){
        if(md.doRetrieveByUsername(m.getUsername())!=null)
            md.doDeleteByUsername(m.getUsername());
        if(ud.doRetrieveByUsername(u.getUsername())!=null)
            ud.doDeleteFromUsername(u.getUsername());
    }
}