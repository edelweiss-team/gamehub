package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import model.bean.Category;
import model.dao.CategoryDAO;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ManageCategoryServletTest {
    private ManageCategoryServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private final static CategoryDAO categoryDao = new CategoryDAO();
    private static  Category categoryToBeRemoved;
    private static Category categoryToBeUpdated;
    private static Category categoryWithCoolName;
    private static Category categoryToBeAdded;
/*

    @BeforeAll
    public static void init() {
        categoryToBeRemoved = new Category(
                "needToBeRemoved",
                "description",
                "imgPath");
        categoryDao.doSave(categoryToBeRemoved);
        // this will be removed by the servlet

        categoryToBeUpdated = new Category(
                "needToBeUpdated",
                "description",
                "imgPath");
        categoryDao.doSave(categoryToBeUpdated);

        categoryWithCoolName = new Category(
                "coolName",
                "description",
                "imgPath");
        categoryDao.doSave(categoryWithCoolName);

        categoryToBeAdded = new Category(
                "newCategoryName",
                "description",
                "imgPath");
        // this will be added to the DB by the servlet
    }

    @AfterAll
    public static void destroy() {
        categoryDao.doDeleteByName(categoryWithCoolName.getName());
        categoryDao.doDeleteByName(categoryToBeUpdated.getName());
        categoryDao.doDeleteByName(categoryToBeAdded.getName());
    }

    @BeforeEach
    public void singleSetup() {
        servlet = new ManageCategoryServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void n1_operationIsNull() {
        request.addParameter("manage_category", (String) null);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void n2_operationIsRemoveAndCategoryNameIsNull() {
        request.addParameter("manage_category", "remove_category");
        // 'removeCategory' (categoryName [ragazzi, per cortesia usate nomi migliori]) missing
        // from request, so it will be null
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void n3_operationIsRemoveAndCategoryNameNotNullButExceedsLength() throws ServletException, IOException {
        request.addParameter("manage_category", "remove_category");
        request.addParameter("removeCategory", "categoryName_categoryName_categoryName_categoryName");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    @Test
    public void n4_operationIsRemoveAndCategoryNameNotNullButNotInDB() throws ServletException, IOException {
        request.addParameter("manage_category", "remove_category");
        request.addParameter("removeCategory", "not_in_DB_category");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    @Test
    public void n5_operationIsRemoveOK() throws ServletException, IOException {
        request.addParameter("manage_category", "remove_category");
        request.addParameter("removeCategory", categoryToBeRemoved.getName());
        servlet.doPost(request, response);
        assertNull(categoryDao.doRetrieveByName(categoryToBeRemoved.getName()));
    }

    @Test
    public void n6_operationIsRemoveCategoryButNewNameIsNull() {
        request.addParameter("manage_category", "update_category");
        // editable-name/editable-description missing from request, so they will be null
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void n7_operationIsRemoveCategoryNewNameNotNullButExceedsLength() {
        request.addParameter("manage_category", "update_category");
        request.addParameter("editable-name", "categoryName_categoryName_categoryName_categoryName");
        request.addParameter("editable-description", "descriptionMinLength");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void n8_operationIsRemoveCategoryNewNameOkAndNewImg() {
        // blank test: Spring cannot manage Part class.
    }

    @Test
    public void n9_1_operationIsRemoveCategoryButNewNameAlreadyTook() throws ServletException, IOException {
        request.addParameter("manage_category", "update_category");
        request.addParameter("old-name", categoryToBeUpdated.getName());
        request.addParameter("editable-name", categoryWithCoolName.getName());
        request.addParameter("editable-description", "descriptionMinLength");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    @Test
    public void n_9_2operationIsRemoveOKButNoNewCategoryImg() {
        // blank test: Spring cannot manage Part class.
    }

    @Test
    public void n_9_3operationIsUpdateOK() throws ServletException, IOException {
        String newCategoryName = "NameNotSoCoolButOk";

        request.addParameter("manage_category", "update_category");
        request.addParameter("old-name", categoryToBeUpdated.getName());
        request.addParameter("editable-name", newCategoryName);
        request.addParameter("editable-description", "descriptionMinLength");

        categoryToBeUpdated.setName(newCategoryName);

        servlet.doPost(request, response);
        assertEquals(categoryDao.doRetrieveByName(newCategoryName), categoryToBeUpdated);
    }

    @Test
    public void n_9_4_operationIsAddButCategoryNameIsNull() {
        request.addParameter("manage_category", "add_category");
        // category name/description/image missing from request, so they will be null
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void n_9_5_operationIsAddCategoryNameNotNullButExceedsLength() throws ServletException, IOException {
        request.addParameter("manage_category", "add_category");
        request.addParameter("categoryName", "newNewNewNewNewNewNewNewNewName");
        request.addParameter("description_category", "genericDescription");
        request.addParameter("image_path", "imgPath");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    @Test
    public void n_9_6_operationIsAddCategoryButCategoryNameAlreadyTook() throws ServletException, IOException {
        request.addParameter("manage_category", "add_category");
        request.addParameter("categoryName", categoryWithCoolName.getName());
        request.addParameter("description_category", "genericDescription");
        request.addParameter("image_path", "imgPath");
        servlet.doPost(request, response);
        assertTrue(!response.getContentAsString().isEmpty());
    }

    @Test
    public void n_9_7_operationIsAddCategoryOk() throws ServletException, IOException {
        request.addParameter("manage_category", "add_category");
        request.addParameter("categoryName", categoryToBeAdded.getName());
        request.addParameter("description_category", "genericDescription");
        request.addParameter("image_path", "imgPath");
        servlet.doPost(request, response);
        assertNotNull(categoryDao.doRetrieveByName(categoryToBeAdded.getName()));
    }
*/
}