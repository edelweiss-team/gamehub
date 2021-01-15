package controller.userManagement.userProfileManagement;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.RequestParametersException;
import controller.userManagement.userProfileManagement.LoginServlet;
import controller.userManagement.userProfileManagement.SignupServlet;
import model.bean.User;
import model.dao.UserDAO;
import org.jetbrains.annotations.NotNull;

/**
 * this servlet updates an user, that wants to update his information, in the DB
 * on condition that all required parameters are
 * non-null, match their corrispective regex and the user is present in the session.
 * If that happens the user is re-added to the session too.
 */
@WebServlet(urlPatterns = {"/changeUser"})
@MultipartConfig
public class UpdateUserServlet extends HttpServlet {

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
    public static final @NotNull String
            BIRTHDATE_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    public static final int MAIL_MAX = 40;
    public static final int NAME_MIN = 2;
    public static final int NAME_MAX = 30;
    public static final int STREET_MIN = 4;
    public static final int STREET_MAX = 50;
    public static final int CITY_MIN = 2;
    public static final int CITY_MAX = 25;
    public static final int COUNTRY_MIN = 3;
    public static final int COUNTRY_MAX = 25;

    /**
     * this method manages Post request calling doGet method.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        this.doGet(req, resp);
    }

    /**
     * this method manages Get requests.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        UserDAO ud = new UserDAO();
        User u;
        String username = req.getParameter("editable-username");
        String name = req.getParameter("editable-name");
        String surname = req.getParameter("editable-surname");
        String birthdate = req.getParameter("editable-birthDate");
        String telephone = req.getParameter("editable-telephone");
        String mail = req.getParameter("editable-mail");
        String sex = req.getParameter("editable-sex");
        String address = req.getParameter("editable-address");
        String city = req.getParameter("editable-city");
        String country = req.getParameter("editable-country");
        String password = req.getParameter("editable-password");
        String tableTriggered = req.getParameter("table-triggered");
        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");
        JsonObject responseUser = new JsonObject();
        JsonObject responseJson = new JsonObject();


        if (loggedUser == null) {
            throw new RequestParametersException(
                    "Error: you can't update your user data being not logged!"
            );
        }
        if (username != null && name != null && surname != null && birthdate != null
               && telephone != null) {
            username = username.trim();
            name = name.trim();
            surname = surname.trim();
            birthdate = birthdate.trim();
            telephone = telephone.trim();
            mail = mail.trim();
            sex = sex.trim();
            address = address.trim();
            city = city.trim();
            country = country.trim();
            tableTriggered = tableTriggered.trim();
            if (username.length() >= USERNAME_MIN
                    && username.length() <= USERNAME_MAX
                    && name.length() >= NAME_MIN
                    && name.length() <= NAME_MAX
                    && surname.length() >= NAME_MIN
                    && surname.length() <= NAME_MAX
                    && username.matches(USERNAME_REGEX)
                    && name.matches(NAME_REGEX)
                    && surname.matches(NAME_REGEX)
                    && telephone.matches(TELEPHONE_REGEX)
                    && birthdate.matches(BIRTHDATE_REGEX)
                    && sex.length() == 1
                    && (sex.toLowerCase().charAt(0) == 'm'
                        || sex.toLowerCase().charAt(0) == 'f')) {

                String oldUsername = loggedUser.getUsername();
                oldUsername = oldUsername.trim();

                User u1 = ud.doRetrieveByUsername(username);
                u = ud.doRetrieveByUsername(oldUsername);
                User u2 = ud.doRetrieveByMail(mail);
                if (u == null) {
                    throw new RequestParametersException(
                            "Error: the user '" + oldUsername + "' doesn't exist!"
                    );
                } else if (!SignupServlet.COUNTRIES.containsKey(country)) {
                    responseJson.addProperty("type", "error");
                    responseJson.addProperty("message", "Country not valid");
                    responseUser.addProperty("username", u.getUsername());
                    responseUser.addProperty("name", u.getName());
                    responseUser.addProperty("surname", u.getSurname());
                    responseUser.addProperty("birthDate", u.getBirthDate());
                    responseUser.addProperty("telephone", u.getTelephone());
                    responseUser.addProperty("password", u.getPasswordHash());
                    responseUser.addProperty("mail", u.getMail());
                    responseUser.addProperty("sex", u.getSex());
                    responseUser.addProperty("address", u.getAddress());
                    responseUser.addProperty("city", u.getCity());
                    responseUser.addProperty("country", u.getCountry());
                } else if (!mail.matches(MAIL_REGEX)) {
                    responseJson.addProperty("type", "error");
                    responseJson.addProperty("message", "Mail not valid");
                    responseUser.addProperty("username", u.getUsername());
                    responseUser.addProperty("name", u.getName());
                    responseUser.addProperty("surname", u.getSurname());
                    responseUser.addProperty("birthDate", u.getBirthDate());
                    responseUser.addProperty("telephone", u.getTelephone());
                    responseUser.addProperty("password", u.getPasswordHash());
                    responseUser.addProperty("mail", u.getMail());
                    responseUser.addProperty("sex", u.getSex());
                    responseUser.addProperty("address", u.getAddress());
                    responseUser.addProperty("city", u.getCity());
                    responseUser.addProperty("country", u.getCountry());
                } else if (u1 != null && !u1.equals(u)) {
                    responseJson.addProperty("type", "error");
                    responseJson.addProperty("message", "User with "
                            + u1.getUsername() + " already exists!");
                    responseUser.addProperty("username", u.getUsername());
                    responseUser.addProperty("name", u.getName());
                    responseUser.addProperty("surname", u.getSurname());
                    responseUser.addProperty("birthDate", u.getBirthDate());
                    responseUser.addProperty("telephone", u.getTelephone());
                    responseUser.addProperty("password", u.getPasswordHash());
                    responseUser.addProperty("mail", u.getMail());
                    responseUser.addProperty("sex", u.getSex());
                    responseUser.addProperty("address", u.getAddress());
                    responseUser.addProperty("city", u.getCity());
                    responseUser.addProperty("country", u.getCountry());
                } else if (u2 != null && !u2.equals(u)) {
                    responseJson.addProperty("type", "error");
                    responseJson.addProperty("message", "User with "
                            + u2.getMail() + " already exists!");
                    responseUser.addProperty("username", u.getUsername());
                    responseUser.addProperty("name", u.getName());
                    responseUser.addProperty("surname", u.getSurname());
                    responseUser.addProperty("birthDate", u.getBirthDate());
                    responseUser.addProperty("telephone", u.getTelephone());
                    responseUser.addProperty("password", u.getPasswordHash());
                    responseUser.addProperty("mail", u.getMail());
                    responseUser.addProperty("sex", u.getSex());
                    responseUser.addProperty("address", u.getAddress());
                    responseUser.addProperty("city", u.getCity());
                    responseUser.addProperty("country", u.getCountry());
                } else {
                    if (password != null) {
                        password = password.trim();
                        if (!password.matches(PASSWORD_REGEX)) {

                            responseJson.addProperty("type", "error");
                            responseJson.addProperty("message", "Password is not"
                                    +" valid");
                            responseUser.addProperty("username", u.getUsername());
                            responseUser.addProperty("name", u.getName());
                            responseUser.addProperty("surname", u.getSurname());
                            responseUser.addProperty("birthDate", u.getBirthDate());
                            responseUser.addProperty("telephone", u.getTelephone());
                            responseUser.addProperty("password", u.getPasswordHash());
                            responseUser.addProperty("mail", u.getMail());
                            responseUser.addProperty("sex", u.getSex());
                            responseUser.addProperty("address", u.getAddress());
                            responseUser.addProperty("city", u.getCity());
                            responseUser.addProperty("country", u.getCountry());
                            resp.getWriter().println(responseJson.toString());
                            resp.flushBuffer();
                            return;
                        } else {
                            u.setPassword(password);
                        }
                    }
                    u.setMail(mail);
                    u.setSex(sex.charAt(0));
                    u.setAddress(address);
                    u.setCity(city);
                    u.setCountry(country);
                    u.setUsername(username);
                    u.setName(name);
                    u.setSurname(surname);
                    u.setBirthDate(birthdate);
                    u.setTelephone(telephone);
                    ud.doUpdate(u, oldUsername);
                    synchronized (session) {
                        session.setAttribute("loggedUser", u);
                    }
                    responseJson.addProperty("type", "success");
                    responseJson.addProperty(
                            "message", "Update completed successfully!"
                    );
                    responseUser.addProperty("username", u.getUsername());
                    responseUser.addProperty("name", u.getName());
                    responseUser.addProperty("surname", u.getSurname());
                    responseUser.addProperty("birthdate", u.getBirthDate());
                    responseUser.addProperty("telephone", u.getTelephone());
                    responseUser.addProperty("mail", u.getMail());
                    responseUser.addProperty("sex", u.getSex());
                    responseUser.addProperty("address", u.getAddress());
                    responseUser.addProperty("city", u.getCity());
                    responseUser.addProperty("country", u.getCountry());
                }

                responseJson.addProperty("oldUsername", oldUsername);
                responseJson.add("updatedUser", responseUser);
                responseJson.addProperty("targetRow", tableTriggered);
                resp.getWriter().println(responseJson.toString());
                resp.flushBuffer();
            } else {
                responseJson.addProperty("type", "error");
                responseJson.addProperty("message", "One or more fields doesn't match");
                responseUser.addProperty("username", loggedUser.getUsername());
                responseUser.addProperty("name", loggedUser.getName());
                responseUser.addProperty("surname", loggedUser.getSurname());
                responseUser.addProperty("birthDate", loggedUser.getBirthDate());
                responseUser.addProperty("telephone", loggedUser.getTelephone());
                responseUser.addProperty("password", loggedUser.getPasswordHash());
                responseUser.addProperty("mail", loggedUser.getMail());
                responseUser.addProperty("sex", loggedUser.getSex());
                responseUser.addProperty("address", loggedUser.getAddress());
                responseUser.addProperty("city", loggedUser.getCity());
                responseUser.addProperty("country", loggedUser.getCountry());
                resp.getWriter().println(responseJson.toString());
                resp.flushBuffer();
            }
        } else {
            throw new RequestParametersException("Error in the request parameters: "
                    + "null parameters");
        }
    }
}
