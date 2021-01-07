package model.controller;

import com.mysql.cj.Session;
import controller.SignupServlet;
import model.bean.User;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SignupServletTest {

    private SignupServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static UserDAO dao = new UserDAO();

    @BeforeEach
    public void setUp() {
        servlet = new SignupServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    static public void inizializeVariable() {
        User u = new User("OtherUsername", "Password1", "Nomenuovo", "Cognomenuovo",
                "Inidirizzo", "Città", "FR",
                "1999-05-22", "Utente99@gmail.it", 'm',
                "3281883997");
        dao.doSave(u);
    }

    //TC_1.1_27
    @Test
    public void signupOk() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals(".", response.getForwardedUrl());
        UserDAO udao = new UserDAO();
        udao.doDeleteFromUsername("MyUsername");
    }

    //TC_1.1_1
    @Test
    public void usernameTooShort() throws ServletException, IOException {
        request.addParameter("username", "M");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_2
    @Test
    public void usernameFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername!");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_3
    @Test
    public void usernameAlreadyExists() throws ServletException, IOException {
        request.addParameter("username", "OtherUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_4
    @Test
    public void passwordTooShort() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "pass");
        request.addParameter("repeatPassword", "pass");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_5
    @Test
    public void passwordFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "1234567");
        request.addParameter("repeatPassword", "1234567");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_6
    @Test
    public void confirmPassword() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Pass");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_7
    @Test
    public void mailTooshort() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "ut@u.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_8
    @Test
    public void mailFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_9
    @Test
    public void mailAlreadyExsist() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente99@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_10
    @Test
    public void nomeTooshort() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "L");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_11
    @Test
    public void nomeFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi1");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_12
    @Test
    public void surnameTooShort() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "R");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_13
    @Test
    public void surnameFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi1");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_14
    @Test
    public void addressTooShort() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_15
    @Test
    public void addressNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via!!!");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_16
    @Test
    public void cityTooShort() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "F");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_17
    @Test
    public void cityNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano1");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_18
    @Test
    public void countryTooShort() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "F");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_19
    @Test
    public void countryFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "F1");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_20
    @Test
    public void countryNotExist() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "AY");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_21
    @Test
    public void dateNotSelected() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_22
    @Test
    public void dateFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "22-05-1999");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_23
    @Test
    public void phoneTooShort() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "35426");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_24
    @Test
    public void phoneFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3542ab746");
        request.addParameter("sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_25
    @Test
    public void sexTooLong() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "paschio");
        servlet.doPost(request, response);
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_26
    @Test
    public void sexFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("repeatPassword", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "g");
        servlet.doPost(request, response);

        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    @AfterAll
    static public void DeSetup() {
        dao.doDeleteFromUsername("OtherUsername");
    }

}
