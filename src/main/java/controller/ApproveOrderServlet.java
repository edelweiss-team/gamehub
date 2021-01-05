package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.Order;
import model.bean.User;
import model.dao.OrderDAO;

@WebServlet(urlPatterns = {"/approveOrder-servlet"})
public class ApproveOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String idString = req.getParameter("approveOrder");
        OrderDAO od = new OrderDAO();
        int id;
        HttpSession session = req.getSession();
        User operator;
        RequestDispatcher rd;
        String address;
        ArrayList<Order> orderList = new ArrayList<>();

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
            synchronized (session) {
                operator = (User) session.getAttribute("loggedUser");
            }
            od.doUpdateOperator(id, operator.getUsername());
            orderList = od.doRetrieveNonApproved(0, 3);
            address = "/WEB-INF/view/OperatorArea.jsp";
            req.setAttribute("orders", orderList);
            rd = req.getRequestDispatcher(address);
            rd.forward(req, resp);
        }
    }
}
