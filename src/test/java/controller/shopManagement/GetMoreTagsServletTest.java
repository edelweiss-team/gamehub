package controller.shopManagement;

import controller.RequestParametersException;
import controller.shopManagement.GetMoreTagsServlet;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetMoreTagsServletTest {
    private GetMoreTagsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        servlet = new GetMoreTagsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @Test
    public void getMoreTagsWithSearch() throws ServletException, IOException {
        request.addParameter("search","New");
        request.addParameter("tagsPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreTagsWithoutSearch() throws ServletException, IOException {
        request.addParameter("search","");
        request.addParameter("tagsPerRequest","8");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreTagsWithoutLength() throws ServletException, IOException{
        request.addParameter("search","New");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreTagsIndexNotOk() throws ServletException, IOException{
        request.addParameter("search","New");
        request.addParameter("tagsPerRequest","8");
        request.addParameter("startingIndex","12&/a");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void getMoreTagsNone() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("tagsPerRequest","0");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreCategoriesOne() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("tagsPerRequest","1");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }
}