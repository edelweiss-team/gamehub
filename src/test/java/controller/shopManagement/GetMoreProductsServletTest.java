package controller.shopManagement;

import controller.RequestParametersException;
import controller.shopManagement.GetMoreProductsServlet;
import model.bean.Category;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.Tag;
import model.dao.CategoryDAO;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;
import model.dao.TagDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GetMoreProductsServletTest {
    private GetMoreProductsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static DigitalProductDAO dpd = new DigitalProductDAO();
    private static PhysicalProductDAO ppd = new PhysicalProductDAO();
    private static TagDAO tdao = new TagDAO();
    private static CategoryDAO catdao = new CategoryDAO();
    private static DigitalProduct d1;
    private static DigitalProduct d2;
    private static PhysicalProduct p1;
    private static PhysicalProduct p2;
    private static Tag t1;
    private static Category cat1;

    @BeforeEach
    void setUp() {
        servlet = new GetMoreProductsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void populate(){
        d1 = new DigitalProduct(1, "Dig1", 24.44,
                "Sono un bel gioco, acquistami!", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12", 10,
                "SoftwareHouse", "publisher");
        d2 = new DigitalProduct(2, "GiocoDigitale2", 12.3,
                "DescrizioneDigitale2", "immagine2", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12", 10,
                "SoftwareHouse", "publisher");
        p1 = new PhysicalProduct(1,"GiocoFisico1", 24.44,
                "Sono un bel gioco, acquistami!", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 12.1);
        p2 = new PhysicalProduct(2,"GiocoFisico2", 12.1,
                "DescrizioneFisico2", "imamgine2", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 12.1);
        t1 = new Tag("TestTag1");
        tdao.doSave(t1);
        d1.addTag(t1);
        d2.addTag(t1);
        p1.addTag(t1);
        p2.addTag(t1);
        cat1 = new Category("TestCategory1","asaasdasd","path");
        catdao.doSave(cat1);
        d1.addCategory(cat1);
        d2.addCategory(cat1);
        p1.addCategory(cat1);
        p2.addCategory(cat1);
        d1 = dpd.doSave(d1);
        d2 = dpd.doSave(d2);
        p1 = ppd.doSave(p1);
        p2 = ppd.doSave(p2);
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
        request.addParameter("tag","TestTag1");
        request.addParameter("price","24.44");
        request.addParameter("category","TestCategory1");
        request.addParameter("productsPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void GetMoreProductsWithFiltersPhysical() throws ServletException, IOException{
        request.addParameter("search","GiocoFisico1");
        request.addParameter("productType","Physical");
        request.addParameter("description","Sono un bel gioco, acquistami!");
        request.addParameter("tag","TestTag1");
        request.addParameter("price","24.44");
        request.addParameter("category","TestCategory1");
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
    public void GetMoreProductsPriceVoid() throws ServletException, IOException{
        request.addParameter("price","");
        request.addParameter("productsPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
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
        request.addParameter("tag","TestTag1");
        request.addParameter("price","24.44");
        request.addParameter("category","TestCategory1");
        request.addParameter("productsPerRequest","1");
        request.addParameter("startingIndex","0");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void GetMoreProductsNone() throws ServletException, IOException{
        request.addParameter("search","Dig1");
        request.addParameter("productType","Digital");
        request.addParameter("description","Sono un bel gioco, acquistami!");
        request.addParameter("tag","TestTag1");
        request.addParameter("price","24.44");
        request.addParameter("category","TestCategory1");
        request.addParameter("productsPerRequest","0");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void GetMoreProductsListNone() throws ServletException, IOException{
        request.addParameter("search","Dig1");
        request.addParameter("productType","Digital");
        request.addParameter("description","Sono un bel gioco, acquistami!");
        request.addParameter("tag","TestTag1");
        request.addParameter("price","24.44");
        request.addParameter("category","TestCategory1");
        request.addParameter("productsPerRequest","8");
        request.addParameter("startingIndex","1");
        dpd.doDelete(d1.getId());
        dpd.doDelete(d2.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @AfterAll
    static void clear(){
        if(dpd.doRetrieveById(d1.getId())!=null)
            dpd.doDelete(d1.getId());
        if(dpd.doRetrieveById(d2.getId())!=null)
            dpd.doDelete(d2.getId());
        if(ppd.doRetrieveById(p1.getId())!=null)
            ppd.doDelete(p1.getId());
        if(ppd.doRetrieveById(p2.getId())!=null)
            ppd.doDelete(p2.getId());
        tdao.doDelete(t1.getName());
        catdao.doDeleteByName(cat1.getName());
    }

}