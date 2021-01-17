package controller.shopManagement;

import controller.RequestParametersException;
import model.bean.Tag;
import model.dao.TagDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetMoreTagsServletTest {
    private GetMoreTagsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static TagDAO td = new TagDAO();
    private static Tag t1;
    private static Tag t2;

    @BeforeEach
    void setUp() {
        servlet = new GetMoreTagsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    static void populate(){
        t1 = new Tag("Tag1");
        t2 = new Tag("Tag2");
        td.doSave(t1);
        td.doSave(t2);

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
    public void getMoreTagsListNone() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("tagsPerRequest","5");
        request.addParameter("startingIndex","1");
        td.doDelete(t1.getName());
        td.doDelete(t2.getName());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void getMoreTagsOne() throws ServletException, IOException{
        request.addParameter("search","Azione");
        request.addParameter("tagsPerRequest","1");
        request.addParameter("startingIndex","1");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @AfterEach
    void addIf(){
        if(td.doRetrieveByName(t1.getName())==null)
            td.doSave(t1);
        if(td.doRetrieveByName(t2.getName())==null)
            td.doSave(t2);
    }

    @AfterAll
    static void clear(){
        if(td.doRetrieveByName(t1.getName())!=null)
            td.doDelete("Tag1");
        if(td.doRetrieveByName(t2.getName())!=null)
            td.doDelete("Tag2");
    }
}