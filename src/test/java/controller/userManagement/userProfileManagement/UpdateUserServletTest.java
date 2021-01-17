package controller.userManagement.userProfileManagement;

import controller.RequestParametersException;
import controller.userManagement.userProfileManagement.UpdateUserServlet;
import model.bean.User;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateUserServletTest {

    private UpdateUserServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static MockHttpSession session;
    private static UserDAO dao = new UserDAO();
    private static User u2;

    @BeforeEach
    public void setUp() {
        servlet = new UpdateUserServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setSession(session);
        BasicConfigurator.configure();
    }

    @BeforeAll
    static public void inizializeVariable() {
        User u = new User("OtherUsername", "Password1", "Nomenuovo", "Cognomenuovo",
                "Via Castello", "Fisciano", "FR",
                "1999-05-22", "Utente99@gmail.it", 'm',
                "3281883997");
        u2 = new User("MyUsername", "Password1","Nomenuovo", "Cognomenuovo",
                "Via Castello","Fisciano","FR",
                "1999-05-22", "Utente@gmail.it", 'm',
                "3281883997");
        dao.doSave(u);
        dao.doSave(u2);
        session = new MockHttpSession();
        session.setAttribute("loggedUser" , u2);

    }

    //TC_3a.1_1
    @Test
    public void usernameTooShort() throws ServletException, IOException {
        request.addParameter("editable-username", "M");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());

    }

    //TC_3a.1_12
    @Test
    public void firstInformationtOk() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    //TC_3a.1_2
    @Test
    public void usernameFormatNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername!");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3a.1_3
    @Test
    public void usernameAlreadyExists() throws ServletException, IOException {
        request.addParameter("editable-username", "OtherUsername!");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3a.1_4
    @Test
    public void nameTooShort() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "L");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3a.1_5
    @Test
    public void nameFormatNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi1");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }


    //TC_3a.1_6
    @Test
    public void  surnameTooshort() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "R");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }


    //TC_3a.1_7
    @Test
    public void surnamFormatNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi1");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3a.1_8
    @Test
    public void dateNotSelect() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));
    }

    //TC_3a.1_9
    @Test
    public void dateNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "22-05-1999");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }


    //TC_3a.1_10
    @Test
    public void telephoneTooShort() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3542");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }


    //TC_3a.1_11
    @Test
    public void phoneNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3542ab746");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }


    //TC_3b.1_1
    @Test
    public void passwordTooShort() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "pass");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3b.1_2
    @Test
    public void passwordNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "pa ss");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3b.1_3
    @Test
    public void passwordOk() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_1
    @Test
    public void mailTooShort() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Ut@u.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_2
    @Test
    public void mailNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_3
    @Test
    public void emailAlreadyExist() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_4
    @Test
    public void sexTooLongm() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "paschio");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_5
    @Test
    public void  sexNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "g");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }


    //TC_3.1c_6
    @Test
    public void addressTooShort() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Vi");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_7
    @Test
    public void addressNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello!!!");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_8
    @Test
    public void cityTooShort() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "F");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_9
    @Test
    public void cityNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano1");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_10
    @Test
    public void  nationTooShort() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "F");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    //TC_3.1c_11
    @Test
    public void nationFormatNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "F1");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }


    //TC_3.1c_11
    @Test
    public void nationNotValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "AY");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }


    //TC_3.1c_11
    @Test
    public void secondPartValid() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "2");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }





    @Test
    public void NotLoggedSelect() throws ServletException, IOException {
        MockHttpSession sessionNuova;
        sessionNuova = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sessionNuova);
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));
    }

    @Test
    public void NotUsername() throws ServletException, IOException {
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));
    }

    @Test
    public void NotPassword() throws ServletException, IOException {
        MockHttpSession sessionNuova;
        sessionNuova = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sessionNuova);
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));
    }
    @Test
    public void NotEmail() throws ServletException, IOException {
        MockHttpSession sessionNuova;
        sessionNuova = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sessionNuova);
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));
    }

    @Test
    public void firstInformationtOkUserNull() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername2");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        dao.doDeleteFromUsername(u2.getUsername());
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));
    }

    @Test
    public void firstInformationtOkUserExists() throws ServletException, IOException {
        request.addParameter("editable-username", "OtherUsername");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente80@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    @Test
    public void firstInformationtOkMailExists() throws ServletException, IOException {
        request.addParameter("editable-username", "MyUsername456");
        request.addParameter("editable-password", "Password1");
        request.addParameter("editable-mail", "Utente99@gmail.it");
        request.addParameter("editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter("editable-address", "Via Castello");
        request.addParameter("editable-city", "Fisciano");
        request.addParameter("editable-country", "FR");
        request.addParameter("editable-birthDate", "1999-05-22");
        request.addParameter("editable-telephone", "3281883997");
        request.addParameter("editable-sex", "m");
        request.addParameter("table-triggered", "1");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    @AfterEach
    public void reInsert(){
        if(dao.doRetrieveByUsername(u2.getUsername())==null)
            dao.doSave(u2);
    }

    @AfterAll
    static public void DeSetup() {
        dao.doDeleteFromUsername("OtherUsername");
        if(dao.doRetrieveByUsername("MyUsername2")!=null)
            dao.doDeleteFromUsername("MyUsername2");
        if(dao.doRetrieveByUsername("MyUsername")!=null)
            dao.doDeleteFromUsername("MyUsername");

    }
    
}
