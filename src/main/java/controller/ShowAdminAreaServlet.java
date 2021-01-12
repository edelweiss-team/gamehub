package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.*;
import model.dao.*;


@WebServlet(urlPatterns = {"/adminArea.html"})
public class ShowAdminAreaServlet extends HttpServlet {

    public static final int DIMENSION_PAGE = 4;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User loggedUser = (User) req.getSession().getAttribute("loggedUser");

        if (loggedUser == null || !loggedUser.getClass().getSimpleName().equals("Admin")) {
            throw new RequestParametersException("Error: you are trying to enter the "
                    + "admin area while not logged.");
        }

        UserDAO ud = new UserDAO();
        DigitalProductDAO dpd = new DigitalProductDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();
        CategoryDAO cd = new CategoryDAO();

        ArrayList<User> users = ud.doRetrieveAll();
        ArrayList<DigitalProduct> dproducts = dpd.doRetrieveAll(0, 1000);
        ArrayList<PhysicalProduct> pproducts = ppd.doRetrieveAll(0, 1000);
        ArrayList<Category> categories = cd.doRetrieveAll();

        req.setAttribute("usersLength", users.size());
        req.setAttribute("digitalProductsLength", dproducts.size());
        req.setAttribute("physicalProductsLength", pproducts.size());
        req.setAttribute("categoriesLength", categories.size());

        int usersIndex = DIMENSION_PAGE;
        int dproductsIndex = DIMENSION_PAGE;
        int pproductsIndex = DIMENSION_PAGE;
        int categoriesIndex = DIMENSION_PAGE;

        if (users.size() < DIMENSION_PAGE) {
            usersIndex = users.size();
        }
        if (dproducts.size() < DIMENSION_PAGE) {
            dproductsIndex = DIMENSION_PAGE;
        }
        if (pproducts.size() < DIMENSION_PAGE) {
            pproductsIndex = DIMENSION_PAGE;
        }
        if (categories.size() < DIMENSION_PAGE) {
            categoriesIndex = DIMENSION_PAGE;
        }

        req.setAttribute("firstUsers", users.subList(0, usersIndex));
        req.setAttribute("firstDigitalProducts", dproducts.subList(0, dproductsIndex));
        req.setAttribute("firstPhysicalProducts", pproducts.subList(0, pproductsIndex));
        req.setAttribute("firstCategories", categories.subList(0, categoriesIndex));

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/AdminArea.jsp");
        rd.forward(req, resp);
    }
}