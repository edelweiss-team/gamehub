package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.*;
import model.dao.*;
import org.jetbrains.annotations.NotNull;

@WebServlet(urlPatterns = {"/proceed-checkout"})
public class ProceedCheckoutServlet extends HttpServlet {
    public static final @NotNull String CC_EXPIRATION_DATE_REGEX =
            "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$";
    public static final @NotNull String CC_NAME_REGEX = SignupServlet.NAME_REGEX;
    public static final @NotNull String CC_CVV_REGEX = "^[0-9]{3,4}$";
    public static final @NotNull String CC_NUMBER_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7]"
            + "[0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]"
            + "{11}|(?:2131|1800|35\\d{3})\\d{11})$";
    public static final @NotNull String PAYMENT_METHOD_REGEX = "^(paypal|creditCard|debitCard)$";
    private static final @NotNull String DUMMY_USER_BIRTH_DATE = "2000-12-12";
    private static final char DUMMY_USER_SEX = 'M';

    /**
     *This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * This method complete the checkout if:
     *      payment informations are valid,
     *      there's a cart in the session and is not empty,
     *      there's an user in the session,
     *      the user informations are valid,
     *
     * after that the we clean the session's cart and
     * save an order in the db.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        Cart cart;

        if ((cart = (Cart) session.getAttribute("cart")) == null
                || cart.getNumberOfItems() == 0) {
            throw new RequestParametersException(
                    "Error: you can't proceed checkout without having an active, non-empty cart!"
            );
        }

        if (req.getParameter("cc-expiration") == null
                || req.getParameter("cc-name") == null
                || req.getParameter("cc-number") == null
                || req.getParameter("cc-cvv") == null
                || req.getParameter("paymentMethod") == null) {
            throw new RequestParametersException(
                    "Credit card expiration date, name, number and "
                            + "cvv and payment method cannot be null"
            );
        }

        if (!req.getParameter("cc-expiration").matches(CC_EXPIRATION_DATE_REGEX)
                || !req.getParameter("cc-name").matches(CC_NAME_REGEX)
                || req.getParameter("cc-name").length() > 100
                || !req.getParameter("cc-number").matches(CC_NUMBER_REGEX)
                || !req.getParameter("cc-cvv").matches(CC_CVV_REGEX)
                || !req.getParameter("paymentMethod").matches(PAYMENT_METHOD_REGEX)) {
            throw new RequestParametersException(
                    "Credit card expiration date, name, number and "
                            + "cvv and payment method is/are not valid!"
            );
        }
        User u;
        if ((u = (User) session.getAttribute("loggedUser")) == null) {
            if (req.getParameter("lastName") == null
                    || req.getParameter("firstName") == null
                    || req.getParameter("mail") == null
                    || req.getParameter("address") == null
                    || req.getParameter("country") == null
                    || req.getParameter("city") == null
                    || req.getParameter("telephone") == null) {
                throw new RequestParametersException(
                        "Name, surname, mail, address, country, city and telephone cannot be null!"
                );
            }

            if (!req.getParameter("lastName").matches(SignupServlet.NAME_REGEX)
                    || !req.getParameter("firstName").matches(SignupServlet.NAME_REGEX)
                    || req.getParameter("firstName").length() < SignupServlet.NAME_MIN
                    || req.getParameter("firstName").length() > SignupServlet.NAME_MAX
                    || req.getParameter("lastName").length() < SignupServlet.NAME_MIN
                    || req.getParameter("lastName").length() > SignupServlet.NAME_MAX
                    || !req.getParameter("mail").matches(SignupServlet.MAIL_REGEX)
                    || req.getParameter("mail").length() > SignupServlet.MAIL_MAX
                    || !req.getParameter("address").matches(SignupServlet.ADDRESS_REGEX)
                    || !req.getParameter("city").matches(SignupServlet.CITY_NAME_REGEX)
                    || !req.getParameter("telephone").matches(SignupServlet.TELEPHONE_REGEX)
                    || !SignupServlet.COUNTRIES.containsKey(req.getParameter("country"))) {
                throw new RequestParametersException(
                        "Name, surname, mail, address, country, city and telephone are not valid!"
                );
            }

            UserDAO ud = new UserDAO();

            //controlliamo che non sia un utente fittizio, con mail uguale allo username
            u = ud.doRetrieveByMail(req.getParameter("mail"));

            if (u != null && !u.getUsername().equalsIgnoreCase(u.getMail())) {
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/Login.jsp");
                rd.forward(req, resp);
                return;
            } else if (u == null) {
                u = new User(
                        req.getParameter("mail"), "NotLogged",
                        req.getParameter("firstName"), req.getParameter("lastName"),
                        req.getParameter("address"), req.getParameter("city"),
                        req.getParameter("country"), DUMMY_USER_BIRTH_DATE,
                        req.getParameter("mail"), DUMMY_USER_SEX,
                        req.getParameter("telephone")
                );
                ud.doSave(u);
            } else if (u.getUsername().equalsIgnoreCase(u.getMail())) {
                u = new User(
                        req.getParameter("mail"), "NotLogged",
                        req.getParameter("firstName"), req.getParameter("lastName"),
                        req.getParameter("address"), req.getParameter("city"),
                        req.getParameter("country"), DUMMY_USER_BIRTH_DATE,
                        req.getParameter("mail"), DUMMY_USER_SEX,
                        req.getParameter("telephone")
                );
                ud.doUpdate(u, u.getUsername());
            }
        }

        OrderDAO od = new OrderDAO();
        Order order = new Order(
                0, u, null,
                (new SimpleDateFormat("yyyy-MM-dd")).format(new Date())
        );

        //aggiungiamo i prodotti all'ordine e aggiorniamo le quantità nel db
        Collection<Product> products = cart.getAllProducts();
        for (Product p : products) {
            order.addProduct(p, cart.getQuantitySingleProduct(p.getId(), p.getClass()));
            //rimuoviamo i prodotti dal carrello
            int prevQuantity = cart.getQuantitySingleProduct(p.getId(), p.getClass());
            synchronized (cart) {
                cart.removeProduct(p, prevQuantity);
            }
            p.setQuantity(p.getQuantity() - prevQuantity);
            if (p instanceof DigitalProduct) {
                (new DigitalProductDAO()).doUpdate((DigitalProduct) p);
            } else if (p instanceof PhysicalProduct) {
                (new PhysicalProductDAO()).doUpdate((PhysicalProduct) p);
            }
        }
        //se l'utente è loggato aggiorniamo il carrello nel db
        if (session.getAttribute("loggedUser") != null) {
            (new CartDAO()).doSaveOrUpdate(cart);
        }
        order = od.doSave(order); //salviamo l'ordine nel db
        //gestione del pagamento, da implementare
        //mail di conferma della ricezione dell'ordine
        try {
            EmailUtility.sendEmail(
                    "smtp.gmail.com", "" + 587,
                    "atatbj.22@gmail.com", "Billjobs22", u.getMail(),
                    "Order #" + order.getId() + " Received",
                    "The order #" + order.getId()
                            + " has been received, we'll notify you once an operator approves it."
            );
        } catch (MessagingException e) {
            throw new ServletException("Error: mail cannot be send. Details: \n" + e);
        }

        req.setAttribute("order", order);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/purchaseConfirmed.jsp");
        rd.forward(req, resp);
    }
}
