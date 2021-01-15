package controller.shopManagement;

import controller.RequestParametersException;
import controller.shopManagement.ShowShopPageServlet;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShowShopPageServletTest {
    private ShowShopPageServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        servlet = new ShowShopPageServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @Test
    public void showShopWithoutFiltering() throws ServletException, IOException{
        request.addParameter("productType","Physical");
        request.addParameter("categoryName","Azione");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Shop.jsp", response.getForwardedUrl());
    }

    @Test
    public void showShopFiltering() throws ServletException, IOException{
        request.addParameter("search","Dig1");
        request.addParameter("productType","Digital");
        request.addParameter("description","Sono un bel gioco, acquistami!");
        request.addParameter("tag","New");
        request.addParameter("price","24.44");
        request.addParameter("categoryName","Azione");;
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Shop.jsp", response.getForwardedUrl());
    }

    @Test
    public void showShopPriceNotOk() throws ServletException, IOException{
        request.addParameter("price","14.14alkhjh)?");
        request.addParameter("categoryName","Azione");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void showShopPriceNotOkEmpty() throws ServletException, IOException{
        request.addParameter("price","");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Shop.jsp", response.getForwardedUrl());
    }
}