package controller;

import java.io.IOException;
import java.util.*;
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


@WebServlet(urlPatterns = {"/signup-servlet", "/signup"}, loadOnStartup = 0)
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
    /*public static final @NotNull String COUNTRY_NAME_REGEX = "^(([A-Z][a-z]*([-"
            + "'\\.\\s]))*([A-Z]?[a-z]+))$";*/
    public static final @NotNull String COUNTRY_NAME_REGEX = "^[A-Z]{2}$";
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
    /*public static final int COUNTRY_MIN = 3;
    public static final int COUNTRY_MAX = 25;*/
    public static final @NotNull HashMap<String, String> COUNTRIES = new HashMap<>();
    public static final @NotNull List<String> COUNTRY_LIST = Arrays.asList(
            "AF", "AX", "AL", "DZ", "AS", "AD", "AO", "AI", "AQ", "AG", "AR", "AM", "AW", "AU",
            "AT", "AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ", "BJ", "BM", "BT", "BO", "BA",
            "BW", "BV", "BR", "VG", "IO", "BN", "BG", "BF", "BI", "KH", "CM", "CA", "CV", "KY",
            "CF", "TD", "CL", "CN", "HK", "MO", "CX", "CC", "CO", "KM", "CG", "CD", "CK", "CR",
            "CI", "HR", "CU", "CY", "CZ", "DK", "DJ", "DM", "DO", "EC", "EG", "SV", "GQ", "ER",
            "EE", "ET", "FK", "FO", "FJ", "FI", "FR", "GF", "PF", "TF", "GA", "GM", "GE", "DE",
            "GH", "GI", "GR", "GL", "GD", "GP", "GU", "GT", "GG", "GN", "GW", "GY", "HT", "HM",
            "VA", "HN", "HU", "IS", "IN", "ID", "IR", "IQ", "IE", "IM", "IL", "IT", "JM", "JP",
            "JE", "JO", "KZ", "KE", "KI", "KP", "KR", "KW", "KG", "LA", "LV", "LB", "LS", "LR",
            "LY", "LI", "LT", "LU", "MK", "MG", "MW", "MY", "MV", "ML", "MT", "MH", "MQ", "MR",
            "MU", "YT", "MX", "FM", "MD", "MC", "MN", "ME", "MS", "MA", "MZ", "MM", "NA", "NR",
            "NP", "NL", "AN", "NC", "NZ", "NI", "NE", "NG", "NU", "NF", "MP", "NO", "OM", "PK",
            "PW", "PS", "PA", "PG", "PY", "PE", "PH", "PN", "PL", "PT", "PR", "QA", "RE", "RO",
            "RU", "RW", "BL", "SH", "KN", "LC", "MF", "PM", "VC", "WS", "SM", "ST", "SA", "SN",
            "RS", "SC", "SL", "SG", "SK", "SI", "SB", "SO", "ZA", "GS", "SS", "ES", "LK", "SD",
            "SR", "SJ", "SZ", "SE", "CH", "SY", "TW", "TJ", "TZ", "TH", "TL", "TG", "TK", "TO",
            "TT", "TN", "TR", "TM", "TC", "TV", "UG", "UA", "AE", "GB", "US", "UM", "UY", "UZ",
            "VU", "VE", "VN", "VI", "WF", "EH", "YE", "ZM", "ZW"
    );

    static {
        for (String country : COUNTRY_LIST) {
            COUNTRIES.put(country, country);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String address = ".";
        UserDAO ud = new UserDAO();
        HttpSession session = req.getSession();
        if (req.getSession().getAttribute("loggedUser") != null) {
            throw new RequestParametersException(
                    "Error: you can't signup because you're already logged!"
            );
        }
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
                || req.getParameter("sex") == null
                || req.getParameter("repeatPassword") == null
                || req.getParameter("sex").length() > 1
                || (req.getParameter("sex").toLowerCase().charAt(0) != 'm'
                && req.getParameter("sex").toLowerCase().charAt(0) != 'f')) {
            req.setAttribute("showCredentialError", "Errore: credenziali di registrazione vuote"
                    + "oppure il sesso non rispetto la lunghezza di un carattere o e' diverso da "
                    + "m oppure f");
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
                    || !u.getSurname().matches(NAME_REGEX)
                    || !u.getCity().matches(CITY_NAME_REGEX)
                    || !u.getAddress().matches(ADDRESS_REGEX)
                    || !u.getTelephone().matches(TELEPHONE_REGEX)
                    || !u.getCountry().matches(COUNTRY_NAME_REGEX)
                    || !COUNTRIES.containsKey(u.getCountry().trim())
                    || u.getMail().length() > MAIL_MAX
                    || u.getUsername().length() < USERNAME_MIN
                    || u.getUsername().length() > USERNAME_MAX || u.getMail().length() > 40
                    || u.getName().length() > NAME_MAX || u.getName().length() < NAME_MIN
                    || u.getSurname().length() < NAME_MIN || u.getSurname().length() > NAME_MAX
                    || u.getAddress().length() > STREET_MAX
                    || u.getAddress().length() < STREET_MIN
                    || u.getCity().length() < CITY_MIN || u.getCity().length() > CITY_MAX
                    || !req.getParameter("repeatPassword").equals(req.getParameter("password"))) {
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
        RequestDispatcher rd;
        req.setAttribute("countries", COUNTRY_LIST);
        rd = req.getRequestDispatcher(address);
        rd.forward(req, resp);
    }
}
