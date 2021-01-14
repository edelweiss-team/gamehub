package controller.userManagement.operatorManagement;

import controller.RequestParametersException;
import model.bean.EmailUtility;
import model.bean.Order;
import model.dao.OrderDAO;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/declineOrder-servlet"})
public class DeclineOrderServlet extends HttpServlet {

    /**
     * this method manages Post request calling doGet method.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String idString = req.getParameter("declineOrder");
        OrderDAO od = new OrderDAO();
        String address;
        RequestDispatcher rd;
        int id;

        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException n) {
            throw new RequestParametersException("Error: you're trying to approve an order with"
                    + "a wrong id");
        }

        Order o = od.doRetrieveById(id);
        if (o == null) {
            throw new RequestParametersException("Error: it doesn't exist an order with this id");
        } else {
            od.doDelete(id);

            try {
                EmailUtility.sendEmail(
                        "smtp.gmail.com",
                        "587",
                        "atatbj.22@gmail.com",
                        "Billjobs22", o.getUser().getMail(),
                        "Order #" + o.getId() + " Confirmed",
                        "The order #" + o.getId()
                                + " has not been confirmed!");
            } catch (MessagingException e) {
                throw new ServletException(e);
            }
        }
        ArrayList<Order> orderList;
        orderList = od.doRetrieveNonApproved(0, 3);
        address = "/WEB-INF/view/OperatorArea.jsp";
        req.setAttribute("orders", orderList);
        rd = req.getRequestDispatcher(address);
        rd.forward(req, resp);
    }
}
