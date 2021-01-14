package controller.shopManagement;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RequestParametersException;
import model.bean.Product;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;


@WebServlet(urlPatterns = {"/showProduct.html"})
public class ShowProductServlet extends HttpServlet {
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
     * this method shows, to the user which wants visualize a product, the
     * product page only if these conditions are satisfied:
     *      'productType' of the product is either "digital" or "physical" (ignore case)
     *          any other string will result in a 'RequestParameterException'
     *          there's a product in the DB that matches the 'productType' and the
     *          'productId' (product not null) otherwise will result in
     *          'RequestParameterException'
     *
     *      if these conditions are satisfied the information about the product are
     *          inserted in the request.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DigitalProductDAO ddao = new DigitalProductDAO();
        PhysicalProductDAO pdao = new PhysicalProductDAO();
        Product product;

        int idProduct = Integer.parseInt(req.getParameter("productId"));
        String productType = req.getParameter("productType");

        if (productType == null) {
            throw new RequestParametersException("Error: product type cannot be null!");
        } else if (productType.equalsIgnoreCase("digital")) {
            product = ddao.doRetrieveById(idProduct);
        } else if (productType.equalsIgnoreCase("physical")) {
            product = pdao.doRetrieveById(idProduct);
        } else {
            throw new RequestParametersException("Error: product type '" + productType
                    + "' doesn't exist!");
        }

        if (product == null) {
            throw new RequestParametersException("Error: " + productType + " product '"
                    + idProduct + "' doesn't exist!");
        }

        // aggiungiamo questa stringa in quanto nella view
        // va fatto il cast nella rispettiva classe (digitale/fisico)
        // in modo da poter accedere ai getters.
        req.setAttribute("productType", productType);
        req.setAttribute("product", product);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/product.jsp");
        rd.forward(req, resp);
    }
}
