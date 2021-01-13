package controller;

import org.apache.log4j.BasicConfigurator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShowCategoryPageServletTest {
    private @NotNull ShowCategoryPageServlet servlet;
    private @NotNull MockHttpServletRequest request;
    private @NotNull MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        servlet = new ShowCategoryPageServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @Test
    public void searchStringOk() throws ServletException, IOException {
        request.addParameter("search","Azione");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Category.jsp", response.getForwardedUrl());
    }
    @Test
    public void searchStringNotOk() throws ServletException, IOException{
        request.addParameter("search","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }
    @Test
    public void searchStringNull() throws ServletException, IOException {
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Category.jsp", response.getForwardedUrl());
    }

}