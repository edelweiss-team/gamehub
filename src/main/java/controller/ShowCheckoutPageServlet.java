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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

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
