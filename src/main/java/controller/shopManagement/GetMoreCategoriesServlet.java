package controller.shopManagement;

import com.google.gson.*;
import controller.RequestParametersException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.bean.Category;
import model.dao.CategoryDAO;

/**
 * This servlet adds more categories to the response.
 */
@WebServlet(urlPatterns = {"/get-more-categories"})
public class GetMoreCategoriesServlet extends HttpServlet {
    public static final int CATEGORIES_PER_REQUEST_DEFAULT = 8;
    public static final int CATEGORY_NAME_LENGTH = 46;
    private static final int LIMIT_MAX = 2000000;

    /**
     * this method manages Post request calling doGet method.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }


    /**
     * this method manages Get requests.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int startingIndex;
        CategoryDAO cd = new CategoryDAO();
        JsonObject categoryJson;
        JsonArray newCategories = new JsonArray();
        String searchString = (req.getParameter("search") != null)
                ? req.getParameter("search") : "";

        if (searchString.length() > CATEGORY_NAME_LENGTH) {
            throw new RequestParametersException("Error in the parameters: "
                     + "searchString exceeds max length of " + (CATEGORY_NAME_LENGTH - 1));
        }

        int categoriesPerRequest = (req.getParameter("categoriesPerRequest") != null)
                ? (Integer.parseInt(req.getParameter("categoriesPerRequest")))
                : (CATEGORIES_PER_REQUEST_DEFAULT);
        try {
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        } catch (NumberFormatException e) {
            throw new RequestParametersException("Error in the parameters, category "
                    + "number must be an integer, "
                    + req.getParameter("startingIndex") + " is not an integer.");
        }
        ArrayList<Category> categoryList = cd.doRetrieveByNameFragment(searchString, startingIndex,
                CATEGORIES_PER_REQUEST_DEFAULT);
        ArrayList<Category> categoryListFull = cd.doRetrieveByNameFragment(searchString, 0,
                LIMIT_MAX);

        for (int i = 0; i < categoryList.size() && i < categoriesPerRequest; i++) {
            categoryJson = new JsonObject();
            categoryJson.addProperty("name", categoryList.get(i).getName());
            categoryJson.addProperty("imagePath", categoryList.get(i).getImage());
            categoryJson.addProperty("description", categoryList.get(i).getDescription());
            newCategories.add(categoryJson);
        }

        JsonObject responseObject = new JsonObject();
        responseObject.add("newCategories", newCategories);
        responseObject.addProperty("newMaxPages",
                Math.max(Math.ceil(categoryListFull.size() / (double) categoriesPerRequest), 1));
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }
}