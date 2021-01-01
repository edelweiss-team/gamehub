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
import model.bean.Tag;
import model.dao.CategoryDAO;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;
import model.dao.TagDAO;

/**
 * This class allows to inizialize the Homepage.
 */
@WebServlet("/index.html")
public class HomeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        CategoryDAO c = new CategoryDAO();
        DigitalProductDAO dpDao = new DigitalProductDAO();
        PhysicalProductDAO fpDao = new PhysicalProductDAO();
        ArrayList<DigitalProduct> prodottiDig;
        ArrayList<PhysicalProduct> prodottiFis;
        ArrayList<DigitalProduct> prodottiSecond;
        prodottiDig = dpDao.doRetrieveAllByTag("New", 0, 2);
        prodottiFis = fpDao.doRetrieveAllByTag("New", 0, 2);
        ArrayList<Product> nuoviProdotti = new ArrayList<>();
        nuoviProdotti.addAll(prodottiDig);
        nuoviProdotti.addAll(prodottiFis);

        TagDAO tagDAO = new TagDAO();
        ArrayList<Tag> tags;
        tags = tagDAO.doRetrieveByNameFragment("",0,100);
        Random rand = new Random();
        int n = rand.nextInt(tags.size());
        Tag tag = tags.get(n);
        System.out.println(tag.getName());
        prodottiSecond = dpDao.doRetrieveByAllFragment("","",999999.0,  "", tag.getName(),"", 0,4);

        ArrayList<Category> categorie = null;
        try {
            categorie = c.doRetrieveAll();
        } catch (SQLException throwables) {
            //lanciare eccezioni
        }

        getServletContext().setAttribute("secondProducts", prodottiSecond);
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