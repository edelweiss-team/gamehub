package controller.userManagement.userProfileManagement;

import controller.RequestParametersException;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.User;
import model.dao.*;
import model.personalization.RecommendedProductList;
import org.jetbrains.annotations.NotNull;

/**
 * This Servlet allows the user to login.
 * When a user logs in the request is processed by this servlet. If the login is completed
 * successfully, the logged user is stored in the session, as well as an empty
 * {@link RecommendedProductList}.
 */
@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {

    public static final @NotNull String PASSWORD_REGEX = "^[^\\s]{8,30}$";
    public static final @NotNull String USERNAME_REGEX = "^[A-Za-z0-9]{6,20}$";
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int USERNAME_MIN_LENGTH = 6;

    /**
     * This method manages Post request calling doGet method.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * This method manages Get requests.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd;
        String address;
        UserDAO ud = new UserDAO();
        OperatorDAO od = new OperatorDAO();
        ModeratorDAO md = new ModeratorDAO();
        AdminDAO ad = new AdminDAO();
        User u;
        HttpSession session = req.getSession();

        if (session.getAttribute("loggedUser") != null) {
            throw new RequestParametersException(
                    "Error: you can't login because you're already logged!"
            );
        }
        if (req.getParameter("login") != null) {
            if (req.getParameter("username").length() < USERNAME_MIN_LENGTH
                    || req.getParameter("username").length() > USERNAME_MAX_LENGTH
                    || !req.getParameter("username").matches(USERNAME_REGEX)
                    || !req.getParameter("password").matches(PASSWORD_REGEX)) {
                u = null;
            } else {
                u = ad.doRetrieveByUsernamePassword(
                        req.getParameter("username"), req.getParameter("password")
                );
                if (u == null) {
                    u = md.doRetrieveByUsernamePassword(
                            req.getParameter("username"), req.getParameter("password")
                    );
                    if (u == null) {
                        u = od.doRetrieveByUsernamePassword(
                                req.getParameter("username"), req.getParameter("password")
                        );
                        if (u == null) {
                            u = ud.doRetrieveByUsernamePassword(
                                    req.getParameter("username"), req.getParameter("password")
                            );
                        }
                    }
                }
            }
            if (u != null) {
                synchronized (session) {
                    session.invalidate();
                    session = req.getSession();
                    session.setAttribute("loggedUser", u);
                    session.setAttribute("recommendedProducts", new RecommendedProductList(u));
                }
                address = ".";

                rd = req.getRequestDispatcher(address);
                rd.forward(req, resp);
            } else {
                req.setAttribute("showCredentialError", "Errore: username o password errate");
                address = "/WEB-INF/view/Login.jsp";
                rd = req.getRequestDispatcher(address);
                rd.forward(req, resp);
            }
        } else {
            throw new RequestParametersException("Error: you passed the wrong "
                    + "parameters to the login resource!");
        }
    }
}
