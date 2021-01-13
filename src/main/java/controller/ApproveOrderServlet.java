package controller;

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

import model.bean.*;
import model.dao.OperatorDAO;
import model.dao.OrderDAO;
import model.dao.UserDAO;

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
        ArrayList<Order> orderList;

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
                User u = (User) session.getAttribute("loggedUser");
                operator = Objects.requireNonNull(
                        (new OperatorDAO()).doRetrieveByUsername(u.getUsername())
                );
            }

            //mail di conferma dell'ordine con i codici di attivazione
            String[] activationCode = new String[1]; //per permettere alla lambda di modificarlo
            activationCode[0] = "\nActivation codes: \n";
            Collection<Product> digitalProducts = o.getAllProducts();
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
                        "Billjobs22", o.getUser().getMail(),
                        "Order #" + o.getId() + " Confirmed",
                        "The order #" + o.getId()
                                + " has been confirmed!" + activationCode[0]
                );
            } catch (MessagingException e) {
                throw new ServletException(e);
            }
            //controlli sull'ordine di un utente non registrato
            if (o.getUser().getUsername().equalsIgnoreCase(o.getUser().getMail())) {
                od.doDelete(o.getId());
                //se l'utente fittizio non ha più ordini eliminiamolo
                if (Objects.requireNonNull(
                        od.doRetrieveByUsername(o.getUser().getUsername())).isEmpty()) {
                    (new UserDAO()).doDeleteFromUsername(o.getUser().getUsername());
                }
            } else {
                od.doUpdateOperator(id, operator.getUsername());
            }
            orderList = od.doRetrieveNonApproved(0, 3);
            address = "/WEB-INF/view/OperatorArea.jsp";
            req.setAttribute("orders", orderList);
            rd = req.getRequestDispatcher(address);
            rd.forward(req, resp);
        }
    }
}
