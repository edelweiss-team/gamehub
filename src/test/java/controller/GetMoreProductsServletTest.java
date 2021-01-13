package controller;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetMoreProductsServletTest {
    private GetMoreProductsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        servlet = new GetMoreProductsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @Test
    public void GetMoreProductsWithoutFilters() throws ServletException, IOException{
        request.addParameter("productsPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void GetMoreProductsProductXRequestNull() throws ServletException, IOException{
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void GetMoreProductsWithFiltersDigitals() throws ServletException, IOException{
        request.addParameter("search","Dig1");
        request.addParameter("productType","Digital");
        request.addParameter("description","Sono un bel gioco, acquistami!");
        request.addParameter("tag","New");
        request.addParameter("price","24.44");
        request.addParameter("category","Azione");
        request.addParameter("productsPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void GetMoreProductsWithFiltersPhysical() throws ServletException, IOException{
        request.addParameter("search","GiocoFisico1");
        request.addParameter("productType","Digital");
        request.addParameter("description","Sono un bel gioco, acquistami!");
        request.addParameter("tag","New");
        request.addParameter("price","24.44");
        request.addParameter("category","Azione");
        request.addParameter("productsPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void GetMoreProductsPriceNotOk() throws ServletException, IOException{
        request.addParameter("price","14.14alkhjh)?");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void getMoreProductsIndexNotOk() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("categoriesPerRequest","8");
        request.addParameter("startingIndex","12&/a");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void GetMoreProductsOne() throws ServletException, IOException{
        request.addParameter("search","Dig1");
        request.addParameter("productType","Digital");
        request.addParameter("description","Sono un bel gioco, acquistami!");
        request.addParameter("tag","New");
        request.addParameter("price","24.44");
        request.addParameter("category","Azione");
        request.addParameter("productsPerRequest","1");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void GetMoreProductsNone() throws ServletException, IOException{
        request.addParameter("search","Dig1");
        request.addParameter("productType","Digital");
        request.addParameter("description","Sono un bel gioco, acquistami!");
        request.addParameter("tag","New");
        request.addParameter("price","24.44");
        request.addParameter("category","Azione");
        request.addParameter("productsPerRequest","0");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

}