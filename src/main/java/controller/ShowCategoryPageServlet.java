package controller;

import static controller.GetMoreCategoriesServlet.CATEGORY_NAME_LENGTH;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Category;
import model.dao.CategoryDAO;

@WebServlet(urlPatterns = {"/category.html", "/show-categories"})
public class ShowCategoryPageServlet extends HttpServlet {

    private static final int LIMIT = 8;
    private static final int LIMIT_MAX = 2000000;
    private static final int OFFSET = 0;

    /**
     *This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * This method shows to the users the page containing categories.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String searchString = (req.getParameter("search") != null)
                ? req.getParameter("search") : "";
        if (searchString.length() > CATEGORY_NAME_LENGTH) {
            throw new RequestParametersException("Error in the parameters: "
                    + "searchString exceeds max length of " + (CATEGORY_NAME_LENGTH - 1));
        }
        List<Category> categories;
        CategoryDAO cd = new CategoryDAO();
        categories = cd.doRetrieveByNameFragment(searchString, 0, LIMIT_MAX);

        req.setAttribute("categories", categories);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/Category.jsp");
        rd.forward(req, resp);
    }
}
