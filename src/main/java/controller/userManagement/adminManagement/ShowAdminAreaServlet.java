package controller.userManagement.adminManagement;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RequestParametersException;
import model.bean.*;
import model.dao.*;

/**
 * this servlet redirects the admin to the admin page and adds to the
 * request information about: users, products, categories, tags, operator
 * moderators and other admin.
 */
@WebServlet(urlPatterns = {"/adminArea.html"})
public class ShowAdminAreaServlet extends HttpServlet {

    public static final int DIMENSION_PAGE = 4;

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

        User loggedUser = (User) req.getSession().getAttribute("loggedUser");

        if (loggedUser == null || !loggedUser.getClass().getSimpleName().equals("Admin")) {
            throw new RequestParametersException("Error: you are trying to enter the "
                    + "admin area while not logged.");
        }

        UserDAO ud = new UserDAO();
        DigitalProductDAO dpd = new DigitalProductDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();
        CategoryDAO cd = new CategoryDAO();
        TagDAO td = new TagDAO();
        OperatorDAO od = new OperatorDAO();
        ModeratorDAO md = new ModeratorDAO();
        AdminDAO ad = new AdminDAO();

        ArrayList<User> users = ud.doRetrieveAll();
        ArrayList<DigitalProduct> dproducts = dpd.doRetrieveAll(0, 1000);
        ArrayList<PhysicalProduct> pproducts = ppd.doRetrieveAll(0, 1000);
        ArrayList<Category> categories = cd.doRetrieveAll();
        ArrayList<Tag> tags = td.doRetrieveByNameFragment("%", 0, 1000);
        ArrayList<Operator> operators = od.doRetrieveAll();
        ArrayList<Moderator> moderators = md.doRetrieveAll();
        ArrayList<Admin> admins = ad.doRetrieveAll();

        req.setAttribute("usersLength", users.size());
        req.setAttribute("digitalProductsLength", dproducts.size());
        req.setAttribute("physicalProductsLength", pproducts.size());
        req.setAttribute("categoriesLength", categories.size());
        req.setAttribute("tagsLength", tags.size());
        req.setAttribute("operatorsLength", operators.size());
        req.setAttribute("moderatorsLength", moderators.size());
        req.setAttribute("adminsLength", admins.size());

        int usersIndex = DIMENSION_PAGE;
        int dproductsIndex = DIMENSION_PAGE;
        int pproductsIndex = DIMENSION_PAGE;
        int categoriesIndex = DIMENSION_PAGE;
        int tagsIndex = DIMENSION_PAGE;
        int operatorsIndex = DIMENSION_PAGE;
        int moderatorsIndex = DIMENSION_PAGE;
        int adminsIndex = DIMENSION_PAGE;

        if (users.size() < DIMENSION_PAGE) {
            usersIndex = users.size();
        }
        if (dproducts.size() < DIMENSION_PAGE) {
            dproductsIndex = dproducts.size();
        }
        if (pproducts.size() < DIMENSION_PAGE) {
            pproductsIndex = pproducts.size();
        }
        if (categories.size() < DIMENSION_PAGE) {
            categoriesIndex = categories.size();
        }
        if (tags.size() < DIMENSION_PAGE) {
            tagsIndex = tags.size();
        }
        if (operators.size() < DIMENSION_PAGE) {
            operatorsIndex = operators.size();
        }
        if (moderators.size() < DIMENSION_PAGE) {
            moderatorsIndex = moderators.size();
        }
        if (admins.size() < DIMENSION_PAGE) {
            adminsIndex = admins.size();
        }

        req.setAttribute("firstUsers", users.subList(0, usersIndex));
        req.setAttribute("firstDigitalProducts", dproducts.subList(0, dproductsIndex));
        req.setAttribute("firstPhysicalProducts", pproducts.subList(0, pproductsIndex));
        req.setAttribute("firstCategories", categories.subList(0, categoriesIndex));
        req.setAttribute("firstTags", tags.subList(0, tagsIndex));
        req.setAttribute("firstOperators", operators.subList(0, operatorsIndex));
        req.setAttribute("firstModerators", moderators.subList(0, moderatorsIndex));
        req.setAttribute("firstAdmins", admins.subList(0, adminsIndex));

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/AdminArea.jsp");
        rd.forward(req, resp);
    }
}