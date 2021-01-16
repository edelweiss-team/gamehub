package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import model.bean.Admin;
import model.bean.Moderator;
import model.bean.Tag;
import model.bean.User;
import model.dao.AdminDAO;
import model.dao.ModeratorDAO;
import model.dao.TagDAO;
import model.dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManageTagServletTest {

    private ManageTagServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static TagDAO td;



    @BeforeEach
    void setUp() {
        servlet = new ManageTagServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        BasicConfigurator.configure();
        td=new TagDAO();
        Tag t = new Tag("TagTestingServlet");
        td.doSave(t);
    }



    @Test
    public void operationNull() throws ServletException, IOException {
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationNotValid() throws ServletException, IOException {
        request.addParameter("manage_tag" , "ttttttttttt");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationValidNoRemoveTagParameter() throws ServletException, IOException {
        request.addParameter("manage_tag" , "remove_tag");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationValidRemoveTagTooLong() throws ServletException, IOException {
        request.addParameter("manage_tag" , "remove_tag");
        request.addParameter("removeTag" , "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationValidRemoveTagTooShort() throws ServletException, IOException {
        request.addParameter("manage_tag" , "remove_tag");
        request.addParameter("removeTag" , "");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationValidRemoveNotExist() throws ServletException, IOException {
        request.addParameter("manage_tag" , "remove_tag");
        request.addParameter("removeTag" , "TagTesting");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void operationValidRemovedOk() throws ServletException, IOException {
        request.addParameter("manage_tag" , "remove_tag");
        request.addParameter("removeTag" , "TagTestingServlet");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationUpdate() throws ServletException, IOException {
        request.addParameter("manage_tag" , "update_tag");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationUpdateNameTooLong() throws ServletException, IOException {
        request.addParameter("manage_tag" , "update_tag");
        request.addParameter("editable-name" , "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }


    @Test
    public void operationUpdateNameTooShort() throws ServletException, IOException {
        request.addParameter("manage_tag" , "update_tag");
        request.addParameter("editable-name" , "");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationoldnameNotValid() throws ServletException, IOException {
        request.addParameter("manage_tag" , "update_tag");
        request.addParameter("editable-name" , "TagTestingServlet");
        request.addParameter("old-name", "TagTestingServlet");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationoldnameValid() throws ServletException, IOException {
        request.addParameter("manage_tag" , "update_tag");
        request.addParameter("editable-name" , "TagTestingServlet2");
        request.addParameter("old-name", "TagTestingServlet");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void operationAddTag() throws ServletException, IOException {
        request.addParameter("manage_tag" , "add_tag");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void operationTagNameTooLong() throws ServletException, IOException {
        request.addParameter("manage_tag" , "add_tag");
        request.addParameter("tagName", "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationTagNameTooShort() throws ServletException, IOException {
        request.addParameter("manage_tag" , "add_tag");
        request.addParameter("tagName", "");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationTagNameExist() throws ServletException, IOException {
        request.addParameter("manage_tag" , "add_tag");
        request.addParameter("tagName", "TagTestingServlet");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void operationAddTagName() throws ServletException, IOException {
        request.addParameter("manage_tag" , "add_tag");
        request.addParameter("tagName", "TagTestingServlet3");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @AfterEach
    public void depopulate(){
        if(td.doRetrieveByName("TagTestingServlet")!=null)
            td.doDelete("TagTestingServlet");
        if(td.doRetrieveByName("TagTestingServlet2")!=null)
            td.doDelete("TagTestingServlet2");
        if(td.doRetrieveByName("TagTestingServlet3")!=null)
            td.doDelete("TagTestingServlet3");
    }


}
