package controller;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.bean.Category;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.Product;
import model.dao.CategoryDAO;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;

@WebServlet("/index.html")
public class HomeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        CategoryDAO c = new CategoryDAO();
        DigitalProductDAO dpDao = new DigitalProductDAO();
        PhysicalProductDAO fpDao = new PhysicalProductDAO();
        ArrayList<DigitalProduct> prodottiDig;
        ArrayList<PhysicalProduct> prodottiFis;
        prodottiDig = dpDao.doRetrieveAllByTag("New", 0, 2);
        prodottiFis = fpDao.doRetrieveAllByTag("New", 0, 2);
        ArrayList<Product> nuoviProdotti = new ArrayList<>();
        nuoviProdotti.addAll(prodottiDig);
        nuoviProdotti.addAll(prodottiFis);

        ArrayList<Category> categorie = null;
        try {
            categorie = c.doRetrieveAll();
        } catch (SQLException throwables) {
            //lanciare eccezioni
        }
        getServletContext().setAttribute("newProducts", nuoviProdotti);
        getServletContext().setAttribute("categorie", categorie);
        super.init();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        this.doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/Home.jsp");
        rd.forward(request, response);
    }


}