package controller.shopManagement;

import static controller.shopManagement.GetMoreProductsServlet.PRODUCTS_PER_REQUEST_DEFAULT;

import controller.RequestParametersException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.Product;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;


/**
 * This servlet shows, to the user which wants visualize the shop, the shop page.
 * It gets the from the request parameters supposed to be the filters.
 * Gets a list of products based on these filters and add them to request.
 */
@WebServlet(urlPatterns = {"/shop.html", "/show-products"})
public class ShowShopPageServlet extends HttpServlet {
    private static final int LIMIT = 8;
    private static final int OFFSET = 0;
    private static final int LIMIT_MAX = 2000000;

    /**
     * This method manages Post request calling doGet method.
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
     * This method manages Get requests.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        DigitalProductDAO dpd = new DigitalProductDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();

        String searchString = (req.getParameter("search") != null)
                ? req.getParameter("search") : "";

        String productType = (req.getParameter("productType") != null)
                ? req.getParameter("productType") : "Digital";
        String description = (req.getParameter("description") != null)
                ? req.getParameter("description") : "";
        String tag = (req.getParameter("tag") != null)
                ? req.getParameter("tag") : "";
        String category = (req.getParameter("categoryName") != null)
                ? req.getParameter("categoryName") : "";
        double price;
        try {
            price = (req.getParameter("price") !=  null
                    && req.getParameter("price").length() > 0)
                    ? Double.parseDouble(req.getParameter("price")) : LIMIT_MAX;
        } catch (NumberFormatException e) {
            throw new RequestParametersException("Error in the parameters, price "
                    + "number must be a double, "
                    + req.getParameter("price") + " is not a double.");
        }

        ArrayList<Product> productList = new ArrayList<>();
        int maxPage;

        if (productType.equalsIgnoreCase("Digital")) {
            productList = new ArrayList<>(dpd.doRetrieveByAllFragment(searchString,
                    description, price, "", tag, category, 0,
                    PRODUCTS_PER_REQUEST_DEFAULT));
            Collection<DigitalProduct> full = dpd.doRetrieveByAllFragment(searchString,
                    description, price, "", tag, category, 0,
                    LIMIT_MAX);
            maxPage = (int) Math.ceil(full.size() / (double) PRODUCTS_PER_REQUEST_DEFAULT);
        } else {
            productList = new ArrayList<>(ppd.doRetrieveByAllFragment(searchString,
                    description, price, tag, category, 0,
                    PRODUCTS_PER_REQUEST_DEFAULT));
            Collection<PhysicalProduct> full = ppd.doRetrieveByAllFragment(searchString,
                    description, price, tag, category, 0,
                    LIMIT_MAX);
            maxPage = (int) Math.ceil(full.size() / (double) PRODUCTS_PER_REQUEST_DEFAULT);
        }
        req.setAttribute("products", productList);
        req.setAttribute("categoryName", category);
        req.setAttribute("maxPage", maxPage);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/Shop.jsp");
        rd.forward(req, resp);
    }
}
