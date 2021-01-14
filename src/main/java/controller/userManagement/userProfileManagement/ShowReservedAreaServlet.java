package controller.userManagement.userProfileManagement;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RequestParametersException;
import model.bean.Order;
import model.bean.User;
import model.dao.OrderDAO;

/**
 * this method shows, to the user which wants to access to his personal area,
 * the the personal area page on condition that there's a user logged (user in
 *      session).
 * otherwise it will lead to a RequestParameterException.
 */
@WebServlet(urlPatterns = {"/reservedArea.html"})
public class ShowReservedAreaServlet extends HttpServlet {

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

        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        OrderDAO od = new OrderDAO();
        ArrayList<Order> orderList = new ArrayList<>();

        if (loggedUser == null) {
            throw new RequestParametersException("Error: you are trying to enter the "
                    + "reserved area while not logged, or while not being an administrator.");
        }
        if (od.doRetrieveByUsername(loggedUser.getUsername()) != null) {
            orderList = od.doRetrieveByUsername(loggedUser.getUsername());
        }

        req.setAttribute("orders", orderList);
        req.setAttribute("countries", SignupServlet.COUNTRY_LIST);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/ReservedArea.jsp");
        rd.forward(req, resp);
    }
}
