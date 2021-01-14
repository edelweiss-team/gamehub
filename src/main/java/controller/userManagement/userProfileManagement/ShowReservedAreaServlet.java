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

@WebServlet(urlPatterns = {"/reservedArea.html"})
public class ShowReservedAreaServlet extends HttpServlet {

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
     * this method shows, to the user which wants to access to his personal area,
     * the the personal area page on condition that there's a user logged (user in
     *      session).
     * otherwise it will lead to a RequestParameterException.
     *
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