package controller;

import model.bean.Category;
import model.dao.CategoryDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/category.html"})
public class ShowCategoryPageServlet extends HttpServlet {

    private static final int LIMIT = 8;
    private static final int OFFSET = 0;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/Category.jsp");
        List<Category> categories = null;

        CategoryDAO cd = new CategoryDAO();
        categories = cd.doRetrieveAll();
        req.setAttribute("categories", categories);
        rd.forward(req, resp);
    }
}
