package controller;

import model.bean.PhysicalProduct;
import model.bean.Product;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/showProduct.html"})
public class ShowProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DigitalProductDAO ddao = new DigitalProductDAO();
        PhysicalProductDAO pdao = new PhysicalProductDAO();
        Product product;

        int idProdcut = Integer.parseInt(req.getParameter("idProduct"));
        Boolean isDigital = Boolean.parseBoolean(req.getParameter("isDigital"));

        if (isDigital)
            product = ddao.doRetrieveById(idProdcut);
        else
            product = pdao.doRetrieveById(idProdcut);

        // aggiungiamo questo booleano in quanto nella view
        // va fatto il cast nella rispettiva classe (digitale/fisico)
        // in modo da poter accedere ai getters.
        req.setAttribute("isDigitalProduct", isDigital);
        req.setAttribute("product", product);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/product.jsp");
        rd.forward(req, resp);
    }
}
