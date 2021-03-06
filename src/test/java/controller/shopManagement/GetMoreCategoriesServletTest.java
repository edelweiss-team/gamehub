package controller.shopManagement;

import controller.RequestParametersException;
import controller.shopManagement.GetMoreCategoriesServlet;
import model.bean.Category;
import model.dao.CategoryDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetMoreCategoriesServletTest {
    private GetMoreCategoriesServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static CategoryDAO dao = new CategoryDAO();
    private static Category c1;
    private static  Category c2;

    @BeforeEach
    void setUp() {
        servlet = new GetMoreCategoriesServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    static void populate(){
        c1 = new Category("catProva", "descrizionenenenenn", "immagine.jpg");
        c2 = new Category("catProva2", "descrizionenenenenn", "immagine.jpg");
        dao.doSave(c1);
        dao.doSave(c2);
    }

    @Test
    public void searchStringNotOk() throws ServletException, IOException {
        request.addParameter("search","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void getMoreCategoriesWithoutSearch() throws ServletException, IOException{
        request.addParameter("categoriesPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreCategoriesSearching() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("categoriesPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreCategoriesWithoutLength() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreCategoriesIndexNotOk() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("categoriesPerRequest","8");
        request.addParameter("startingIndex","12&/a");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void getMoreCategoriesNone() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("categoriesPerRequest","0");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreCategoriesOne() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("categoriesPerRequest","1");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @AfterAll
    public static void clear(){
        dao.doDeleteByName("catProva");
        dao.doDeleteByName("catProva2");
    }
}