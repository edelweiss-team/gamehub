package model.controller;

import controller.SignupServlet;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;



public class SignupServletTest {

    private SignupServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    public void setUp() {
        servlet = new SignupServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

   //TC_1.1_1
   @Test
    public void usernameEmpty() throws ServletException, IOException {
        request.addParameter("username", "");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
   }

    //TC_1.1_2
    @Test
    public void usernameFormatNotValid() throws ServletException, IOException {
        request.addParameter("username", "MyUsername!");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_3
    @Test
    public void passwordTooShort() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "pass");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_4
    @Test
    public void passwordFormatNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "passwordw");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_5
    @Test
    public void mailTooshort() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "ut@u.it");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }


    //TC_1.1_6
    @Test
    public void mailFormatNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente.it");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }


    //TC_1.1_7
    @Test
    public void nomeTooshort() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("nome", "L");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }


    //TC_1.1_8
    @Test
    public void nomeFormatNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi1");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }


    //TC_1.1_9
    @Test
    public void surnameTooShort() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "R");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }


    //TC_1.1_10
    @Test
    public void surnameFormatNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi1");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_11
    @Test
    public void addressTooShort() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_12
    @Test
    public void addressNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via!!!");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_13
    @Test
    public void cityTooShort() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "F");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_14
    @Test
    public void cityNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano1");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_15
    @Test
    public void countryTooShort() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "F");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_16
    @Test
    public void countryFormatNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "Francia1");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_17
    @Test
    public void dateNotSelected() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "Francia");
        request.addParameter("birthdate", "");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_18
    @Test
    public void dateFormatNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "Francia");
        request.addParameter("birthdate", "22-05-1999");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_19
    @Test
    public void phoneTooShort() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "Francia");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "35426");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_20
    @Test
    public void phoneFormatNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "Francia");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3542ab746");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_21
    @Test
    public void sexTooLong() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "Francia");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "paschio");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_22
    @Test
    public void sexFormatNotValid() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "Francia");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "g");
        servlet.doPost(request,response);
        assertEquals("/WEB-INF/view/Signup.jsp" , response.getForwardedUrl());
    }

    //TC_1.1_23
    @Test
    public void signupOk() throws ServletException , IOException {
        request.addParameter("username", "MyUsername");
        request.addParameter("password", "Password1");
        request.addParameter("mail", "Utente@gmail.it");
        request.addParameter("name", "Luigi");
        request.addParameter("surname", "Rossi");
        request.addParameter("address", "Via Castello");
        request.addParameter("city", "Fisciano");
        request.addParameter("country", "Francia");
        request.addParameter("birthdate", "1999-05-22");
        request.addParameter("telephone", "3281883997");
        request.addParameter("sex", "m");
        servlet.doPost(request,response);
        UserDAO udao = new UserDAO();
        udao.doDeleteFromUsername("MyUsername");
        assertEquals("." , response.getForwardedUrl());

    }


}
