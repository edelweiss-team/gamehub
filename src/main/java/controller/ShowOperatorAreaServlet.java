package controller;

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




@WebServlet(urlPatterns = {"/operatorArea.html"})
public class ShowOperatorAreaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        OrderDAO od = new OrderDAO();
        ArrayList<Order> orderApprovedList = new ArrayList<>();

        if (loggedUser == null) {
            throw new RequestParametersException("Error: you are trying to enter the "
                    + "operator area while not logged.");
        }

        if (od.doRetrieveByOperator(loggedUser.getUsername()) != null) {
            orderApprovedList = od.doRetrieveByOperator(loggedUser.getUsername());
        }

        req.setAttribute("orders", orderApprovedList);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/OperatorArea.jsp");
        rd.forward(req, resp);
    }
}
