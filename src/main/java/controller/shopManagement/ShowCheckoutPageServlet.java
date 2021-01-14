package controller.shopManagement;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RequestParametersException;
import controller.userManagement.userProfileManagement.SignupServlet;
import model.bean.Cart;
/**
 * this servlet shows, to the user which wants to proceed to the checkout, the
 * checkout page only if these conditions are satisfied:
 *      the cart in the session is NOT null
 *      the cart in the session in NOT empty
 */
@WebServlet(urlPatterns = {"/checkout.html", "/purchase-items"})
public class ShowCheckoutPageServlet extends HttpServlet {

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
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null || cart.getNumberOfItems() == 0) {
            throw new RequestParametersException(
                    "Error: you can't access checkout area without having an active cart!"
            );
        }
        req.setAttribute("countries", SignupServlet.COUNTRY_LIST);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/checkout.jsp");
        rd.forward(req, resp);
    }
}
