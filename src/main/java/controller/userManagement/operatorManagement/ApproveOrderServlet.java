package controller.userManagement.operatorManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.RequestParametersException;
import model.bean.*;
import model.dao.OperatorDAO;
import model.dao.OrderDAO;
import model.dao.UserDAO;
/**
 * This Servlet allows the operator to confirm an order.
 * When a user approves an order, the request is processed by this servlet.
 */
@WebServlet(urlPatterns = {"/approveOrder-servlet"})
public class ApproveOrderServlet extends HttpServlet {
    /**
     * This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     *This method process the request to approve an order.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String idString = req.getParameter("approveOrder");
        OrderDAO orderDAO = new OrderDAO();
        int id;
        HttpSession session = req.getSession();
        User operator;
        RequestDispatcher rd;
        String address;
        ArrayList<Order> orderList;

        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException n) {
            throw new RequestParametersException("Error: you're trying to approve an order with"
                    + "a wrong id");
        }

        Order order = orderDAO.doRetrieveById(id);
        if (order == null) {
            throw new RequestParametersException("Error: it doesn't exist an order with this id");
        } else {
            synchronized (session) {
                User u = (User) session.getAttribute("loggedUser");
                operator = Objects.requireNonNull(
                        (new OperatorDAO()).doRetrieveByUsername(u.getUsername())
                );
            }

            //mail di conferma dell'ordine con i codici di attivazione
            String[] activationCode = new String[1]; //per permettere alla lambda di modificarlo
            activationCode[0] = "\nActivation codes: \n";
            Collection<Product> digitalProducts = order.getAllProducts();
            digitalProducts.removeIf(p -> !(p instanceof DigitalProduct));
            digitalProducts.forEach(
                    p -> activationCode[0] +=
                            p.getName() + ": " + req.getParameter("activation-code" + p.getId())
                            + "\n"
            );

            try {
                EmailUtility.sendEmail(
                        "smtp.gmail.com",
                        "587",
                        "atatbj.22@gmail.com",
                        "Billjobs22", order.getUser().getMail(),
                        "Order #" + order.getId() + " Confirmed",
                        "The order #" + order.getId()
                                + " has been confirmed!" + activationCode[0]
                );
            } catch (MessagingException e) {
                throw new ServletException(e);
            }
            //controlli sull'ordine di un utente non registrato
            if (order.getUser().getUsername().equalsIgnoreCase(order.getUser().getMail())) {
                orderDAO.doDelete(order.getId());

                //se l'utente fittizio non ha più ordini eliminiamolo
                if (Objects.requireNonNull(
                        orderDAO.doRetrieveByUsername(order.getUser().getUsername())).isEmpty()) {
                    (new UserDAO()).doDeleteFromUsername(order.getUser().getUsername());
                }
            } else {
                orderDAO.doUpdateOperator(id, operator.getUsername());
            }
            orderList = orderDAO.doRetrieveNonApproved(0, 3);
            address = "/WEB-INF/view/OperatorArea.jsp";
            req.setAttribute("orders", orderList);
            rd = req.getRequestDispatcher(address);
            rd.forward(req, resp);
        }
    }
}
