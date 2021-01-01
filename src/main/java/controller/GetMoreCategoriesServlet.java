package controller;
import com.google.gson.*;
import model.bean.Category;
import model.dao.CategoryDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/get-more-categories"})
public class GetMoreCategoriesServlet extends HttpServlet {
    public static final int CATEGORIES_PER_REQUEST_DEFAULT = 8;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int startingIndex;
        CategoryDAO cd = new CategoryDAO();
        ArrayList<Category> categoryListFull = cd.doRetrieveAll();
        JsonObject responseObject = new JsonObject(), categoryJson;
        JsonArray newCategories = new JsonArray();
        String searchString = req.getParameter("search");

        int categoriesPerRequest = (req.getParameter("categoriesPerRequest") != null)?
                (Integer.parseInt(req.getParameter("categoriesPerRequest"))):(CATEGORIES_PER_REQUEST_DEFAULT);
        try{
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        }
        catch(NumberFormatException e){
            throw new RequestParametersException("Error in the parameters, category number must be an integer, " +
                    req.getParameter("startingIndex") + " is not an integer.");
        }
        ArrayList<Category> categoryList = cd.doRetrieveByNameFragment("",startingIndex,
                CATEGORIES_PER_REQUEST_DEFAULT);

        for(int i = 0; i < categoryList.size() && i < categoriesPerRequest; i++) {
            categoryJson = new JsonObject();
            categoryJson.addProperty("name", categoryList.get(i).getName());
            categoryJson.addProperty("imagePath", categoryList.get(i).getImage());
            categoryJson.addProperty("description", categoryList.get(i).getDescription());
            newCategories.add(categoryJson);
        }
        responseObject.add("newCategories", newCategories);
        responseObject.addProperty("newMaxPages",
                Math.max(Math.ceil(categoryListFull.size()/(double)categoriesPerRequest), 1));
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }
}