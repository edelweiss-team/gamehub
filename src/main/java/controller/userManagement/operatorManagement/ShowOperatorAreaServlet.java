package controller.userManagement.operatorManagement;

import controller.RequestParametersException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Order;
import model.bean.User;
import model.dao.OrderDAO;



/**
 * This servlet forwards the operator to its personal area and
 * adds to the response the orders the still need approvation
 */
@WebServlet(urlPatterns = {"/operatorArea.html"})
public class ShowOperatorAreaServlet extends HttpServlet {

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
        ArrayList<Order> ordersList = new ArrayList<>();

        if (loggedUser == null) {
            throw new RequestParametersException("Error: you are trying to enter the "
                    + "operator area while not logged.");
        }

        if (od.doRetrieveNonApproved(0, 100) != null) {
            ordersList = od.doRetrieveNonApproved(0, 100);
        }

        req.setAttribute("orders", ordersList);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/OperatorArea.jsp");
        rd.forward(req, resp);
    }
}
