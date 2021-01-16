package controller.shopManagement;

import controller.RequestParametersException;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.Product;
import model.bean.User;
import model.personalization.RecommendedProductList;

/**
 * This servlet handles the creation of the {@link RecommendedProductList} for the user.
 * As the aforementioned class uses the proxy design pattern, an empty list is stored in the
 * session, until the user calls this servlet, which calls the method to fill the empty list
 * stored in the session.
 */
@WebServlet(urlPatterns = {"recommendedProducts.html", "recommended-products"})
public class ShowRecommendedProductsServlet extends HttpServlet {


    /**
     * This method manages Post request calling doGet method.
     *
     * @param request a HttpServletRequest
     * @param response an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    /**
     * This method manages Get requests.
     *
     * @param request a HttpServletRequest
     * @param response an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");
        List<Product> actualProductList;
        RecommendedProductList rpl;
        rpl = (RecommendedProductList) session.getAttribute("recommendedProducts");

        if (loggedUser == null) {
            throw new RequestParametersException(
                    "Error: you cannot access to the recommended product list without being logged!"
            );
        }

        synchronized (session) {
            actualProductList = rpl.getList(); //calcoliamo la lista dei prodotti consigliati
        }


        request.setAttribute("products", actualProductList);
        RequestDispatcher rd = request.getRequestDispatcher(
                "/WEB-INF/view/recommendedProducts.jsp"
        );
        rd.forward(request, response);
    }
}
