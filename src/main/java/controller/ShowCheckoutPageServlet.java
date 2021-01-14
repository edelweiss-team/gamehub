package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Cart;

@WebServlet(urlPatterns = {"/checkout.html", "/purchase-items"})
public class ShowCheckoutPageServlet extends HttpServlet {

    /**
     *This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * this method shows, to the user which wants to proceed to the checkout, the
     * checkout page only if these conditions are satisfied:
     *      the cart in the session is NOT null
     *      the cart in the session in NOT empty
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
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
