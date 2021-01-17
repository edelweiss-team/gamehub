package controller;

import java.io.*;
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
import org.jetbrains.annotations.NotNull;

/**
 * This servlet allows to initialize the Homepage.
 */
@WebServlet(urlPatterns = {"/index.html", "/home", "/Home", "/HOME"}, loadOnStartup = 0)
public class HomeServlet extends HttpServlet {
    @NotNull
    public static String EXECUTION_PATH;

    @Override
    public void init() throws ServletException {
        EXECUTION_PATH = getServletContext().getRealPath(".");
        DigitalProductDAO dpDao = new DigitalProductDAO();
        PhysicalProductDAO fpDao = new PhysicalProductDAO();
        ArrayList<DigitalProduct> prodottiDig;
        ArrayList<PhysicalProduct> prodottiFis;

        prodottiDig = dpDao.doRetrieveAllByTag("New", 0, 2);
        prodottiFis = fpDao.doRetrieveAllByTag("New", 0, 2);
        ArrayList<Product> nuoviProdotti = new ArrayList<>();
        nuoviProdotti.addAll(prodottiDig);
        nuoviProdotti.addAll(prodottiFis);

        TagDAO tagdao = new TagDAO();
        ArrayList<Tag> tags;
        tags = tagdao.doRetrieveByNameFragment("", 0, 100);
        Random rand = new Random();
        int n;
        if (tags.isEmpty()) {
            n = 0;
            tags.add(new Tag("New"));
        } else {
            n = rand.nextInt(tags.size());
        }
        ArrayList<DigitalProduct> prodottiSecond;
        Tag tag = tags.get(n);
        prodottiSecond = dpDao.doRetrieveByAllFragment(
                "", "", 999999.0,  "", tag.getName(), "",
                0, 4
        );

        CategoryDAO c = new CategoryDAO();
        ArrayList<Category> categorie = c.doRetrieveAll();

        getServletContext().setAttribute("secondProducts", prodottiSecond);
        getServletContext().setAttribute("newProducts", nuoviProdotti);
        getServletContext().setAttribute("categorie", categorie);
        super.init();
    }

    /**
     * This method calls the doGet method.
     *
     * @param request the HttpServletRequest from the client
     * @param response the HttpServletResponse
     * @throws ServletException if an exception occurs
     * @throws IOException if an exception occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        this.doGet(request, response);
    }

    /**
     * this method manages Get requests.
     *
     * @param request a HttpServletRequest
     * @param response an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/Home.jsp");
        rd.forward(request, response);
    }


}