package controller;

import java.io.IOException;
import model.bean.DigitalProduct;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/shop.html"})
public class ShowShopPageServlet extends HttpServlet {
    private static final int LIMIT = 8;
    private static final int OFFSET = 0;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<DigitalProduct> digProducts;
        DigitalProductDAO dpd = new DigitalProductDAO();
        digProducts = dpd.doRetrieveAllByCategory(req.getParameter("categoryName"), OFFSET, LIMIT);
        req.setAttribute("products", digProducts);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/Shop.jsp");
        rd.forward(req, resp);
    }
}
