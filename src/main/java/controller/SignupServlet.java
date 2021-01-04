package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.User;
import model.dao.UserDAO;
import org.jetbrains.annotations.NotNull;


@WebServlet(urlPatterns = {"/signup-servlet"})
public class SignupServlet extends HttpServlet {
    public static final @NotNull String USERNAME_REGEX = LoginServlet.USERNAME_REGEX;
    public static final @NotNull String PASSWORD_REGEX = LoginServlet.PASSWORD_REGEX;
    public static final @NotNull String MAIL_REGEX = "^[a-zA-Z][\\w\\.!#$%&'*+/=?^_`"
            + "{|}~-]+@([a-zA-Z]\\w+\\.)+[a-z]{2,5}$";
    public static final @NotNull String NAME_REGEX = "^(([A-Z][a-z]*([-'\\s\\.]))*([A-Z][a-z]*))$";
    public static final @NotNull String ADDRESS_REGEX = "^(((Via|Contrada|Piazza|Vicolo|Co"
            + "rso|Viale|Piazzale)\\s)?(([A-Z]?[a-z0-9]*([-'\\.\\s]))*([A-Z]?[a-z0-9]+)))$";
    public static final @NotNull String CITY_NAME_REGEX = "^(([A-Z][a-z]*([-"
            + "'\\.\\s]))*([A-Z]?[a-z]+))$";
    public static final @NotNull String COUNTRY_NAME_REGEX = "^(([A-Z][a-z]*([-"
            + "'\\.\\s]))*([A-Z]?[a-z]+))$";
    public static final @NotNull String TELEPHONE_REGEX = "(([+]|00)39)?((3[0-9]{2})(\\d{7}))$";
    public static final int USERNAME_MIN = LoginServlet.USERNAME_MIN_LENGTH;
    public static final int USERNAME_MAX = LoginServlet.USERNAME_MAX_LENGTH;
    public static final int MAIL_MAX = 40;
    public static final int NAME_MIN = 2;
    public static final int NAME_MAX = 30;
    public static final int STREET_MIN = 4;
    public static final int STREET_MAX = 50;
    public static final int CITY_MIN = 2;
    public static final int CITY_MAX = 25;
    public static final int COUNTRY_MIN = 3;
    public static final int COUNTRY_MAX = 25;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd;
        String address = ".";
        UserDAO ud = new UserDAO();
        HttpSession session = req.getSession();
        if (req.getParameter("username") == null
                || req.getParameter("password") == null
                || req.getParameter("name") == null
                || req.getParameter("surname") == null
                || req.getParameter("address") == null
                || req.getParameter("city") == null
                || req.getParameter("country") == null
                || req.getParameter("birthdate") == null
                || req.getParameter("mail") == null
                || req.getParameter("telephone") == null
                || req.getParameter("sex") == null) {
            req.setAttribute("showCredentialError", "Errore: credenziali di registrazione vuote");
            address = "/WEB-INF/view/Signup.jsp";
        } else {
            if (req.getParameter("sex").length() > 1 || req.getParameter("sex").charAt(0) != 'M'
                || req.getParameter("sex").charAt(0) != 'F') {
                req.setAttribute("showCredentialError", "Erorre: il sesso è lungo più di un carattere oppure contiene un"
                        + "carattere diverso da M o F");
                address = "/WEB-INF/view/Signup.jsp";
            } else {
                User u = new User(req.getParameter("username"), req.getParameter("password"),
                        req.getParameter("name"), req.getParameter("surname"),
                        req.getParameter("address"), req.getParameter("city"),
                        req.getParameter("country"), req.getParameter("birthdate"),
                        req.getParameter("mail"), req.getParameter("sex").charAt(0),
                        req.getParameter("telephone"));
                if (!u.getUsername().matches(USERNAME_REGEX)
                        || !req.getParameter("password").matches(PASSWORD_REGEX)
                        || !u.getMail().matches(MAIL_REGEX) || !u.getName().matches(NAME_REGEX)
                        || !u.getSurname().matches(NAME_REGEX) || !u.getCity().matches(CITY_NAME_REGEX)
                        || !u.getAddress().matches(ADDRESS_REGEX)
                        || !u.getTelephone().matches(TELEPHONE_REGEX)
                        || !u.getCountry().matches(COUNTRY_NAME_REGEX)
                        || u.getMail().length() > MAIL_MAX || u.getUsername().length() < USERNAME_MIN
                        || u.getUsername().length() > USERNAME_MAX || u.getMail().length() > 40
                        || u.getName().length() > NAME_MAX || u.getName().length() < NAME_MIN
                        || u.getSurname().length() < NAME_MIN || u.getSurname().length() > NAME_MAX
                        || u.getAddress().length() > STREET_MAX || u.getAddress().length() < STREET_MIN
                        || u.getCity().length() < CITY_MIN || u.getCity().length() > CITY_MAX
                        || u.getCountry().length() < COUNTRY_MIN
                        || u.getCountry().length() > COUNTRY_MAX) {
                    req.setAttribute("showCredentialError",
                            "Errore: credenziali di registrazione errate.");
                    req.setAttribute("sectionName", "login");
                    address = "/WEB-INF/view/Signup.jsp";
                } else {
                    try {
                        ud.doSave(u);
                    } catch (Exception e) {
                        req.setAttribute("showCredentialError", "Errore: utente già esistente.");
                        address = "/WEB-INF/view/Signup.jsp";
                    }
                    synchronized (session) {
                        session = req.getSession();
                        session.setAttribute("loggedUser", u);
                    }
                }
            }
        }
        rd = req.getRequestDispatcher(address);
        rd.forward(req, resp);
    }
}
