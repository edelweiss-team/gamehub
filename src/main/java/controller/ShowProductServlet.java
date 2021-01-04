package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Product;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;

@WebServlet(urlPatterns = {"/showProduct.html"})
public class ShowProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DigitalProductDAO ddao = new DigitalProductDAO();
        PhysicalProductDAO pdao = new PhysicalProductDAO();
        Product product;

        int idProdcut = Integer.parseInt(req.getParameter("productId"));
        String productType = req.getParameter("productType");

        if (productType.equalsIgnoreCase("digital")) {
            product = ddao.doRetrieveById(idProdcut);
        } else {
            product = pdao.doRetrieveById(idProdcut);
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
