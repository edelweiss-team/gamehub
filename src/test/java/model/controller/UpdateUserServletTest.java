package model.controller;

import controller.UpdateUserServlet;
import model.bean.User;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserServletTest {

    private UpdateUserServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static UserDAO dao = new UserDAO();
    private static User u2;

    @BeforeEach
    public void setUp() {
        servlet = new UpdateUserServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        session.setAttribute("loggedUser" , u2);
        request.setSession(session);
        BasicConfigurator.configure();
    }

    @BeforeAll
    static public void inizializeVariable() {
        User u = new User("OtherUsername", "Password1", "Nomenuovo", "Cognomenuovo",
                "Inidirizzo", "Città", "FR",
                "1999-05-22", "Utente99@gmail.it", 'm',
                "3281883997");
        u2 = new User("MyUsername", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente@gmail.it", 'm',
                "3281883997");
        dao.doSave(u);
        dao.doSave(u2);


    }

    //TC_1.1_26
    @Test
    public void modifyOk() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter("editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter("editable-birthdate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals(".", response.getForwardedUrl());
        UserDAO udao = new UserDAO();
        udao.doDeleteFromUsername("MyUsername");
    }

    //TC_1.1_1
    @Test
    public void usernameTooShort() throws ServletException, IOException {
        request.addParameter(" editable-username", "M");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_2
    @Test
    public void usernameFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername!");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_3
    @Test
    public void usernameAlreadyExists() throws ServletException, IOException {
        request.addParameter(" editable-username", "OtherUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_4
    @Test
    public void passwordTooShort() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "pass");
        request.addParameter("repeatPassword", "pass");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_5
    @Test
    public void passwordFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "1234567");
        request.addParameter("repeatPassword", "1234567");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_7
    @Test
    public void  mailTooshort() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "ut@u.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_8
    @Test
    public void mailFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_9
    @Test
    public void mailAlreadyExsist() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente99@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_10
    @Test
    public void nomeTooshort() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "L");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_11
    @Test
    public void nomeFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi1");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_12
    @Test
    public void surnameTooShort() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "R");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_13
    @Test
    public void surnameFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi1");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_14
    @Test
    public void  addressTooShort() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_15
    @Test
    public void addressNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via!!!");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_16
    @Test
    public void cityTooShort() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "F");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_17
    @Test
    public void cityNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano1");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_18
    @Test
    public void countryTooShort() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "F");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_19
    @Test
    public void countryFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "F1");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_20
    @Test
    public void  countryNotExist() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "AY");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    //TC_1.1_21
    @Test
    public void dateNotSelected() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_22
    @Test
    public void dateFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "22-05-1999");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_23
    @Test
    public void phoneTooShort() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "35426");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_24
    @Test
    public void phoneFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3542ab746");
        request.addParameter(" editable-sex", "m");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_25
    @Test
    public void  sexTooLong() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "paschio");
        servlet.doPost(request, response);
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }

    //TC_1.1_26
    @Test
    public void sexFormatNotValid() throws ServletException, IOException {
        request.addParameter(" editable-username", "MyUsername");
        request.addParameter(" editable-password", "Password1");
        request.addParameter(" editable-mail", "Utente@gmail.it");
        request.addParameter(" editable-name", "Luigi");
        request.addParameter(" editable-surname", "Rossi");
        request.addParameter(" editable-address", "Via Castello");
        request.addParameter(" editable-city", "Fisciano");
        request.addParameter(" editable-country", "FR");
        request.addParameter(" editable-birthDate", "1999-05-22");
        request.addParameter(" editable-telephone", "3281883997");
        request.addParameter(" editable-sex", "g");
        servlet.doPost(request, response);

        assertEquals("/WEB-INF/view/Signup.jsp", response.getForwardedUrl());
    }


    @AfterAll
    static public void DeSetup() {
        dao.doDeleteFromUsername("OtherUsername");
    }
    
}
