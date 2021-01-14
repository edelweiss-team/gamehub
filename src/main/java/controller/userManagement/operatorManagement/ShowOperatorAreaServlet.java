package controller.userManagement.operatorManagement;

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




@WebServlet(urlPatterns = {"/operatorArea.html"})
public class ShowOperatorAreaServlet extends HttpServlet {

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
     * This method allows to show all the orders to confirm.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
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
