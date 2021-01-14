package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RequestParametersException;
import model.bean.User;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"/removeUser-servlet", "/remove-user"})
public class ManageUserServlet extends HttpServlet {

    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int USERNAME_MIN_LENGTH = 6;

    /**
     *This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * This method allows the admin to remove an user from the website:
     * if there's no user to remove in the request we get an exception
     * if the username length exceeds (in both direction) the min and
     *     the max length OR there's no user with that username we get
     *     an exception in we get an error in the response.
     * otherwise we get a success message in the response.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserDAO ud = new UserDAO();
        User u;
        JsonObject responseObject = new JsonObject();
        if (req.getParameter("removeUser") != null) {
            if (req.getParameter("removeUser").length() <= USERNAME_MAX_LENGTH
                    && req.getParameter("removeUser").length() >= USERNAME_MIN_LENGTH) {
                String username = req.getParameter("removeUser");
                u = ud.doRetrieveByUsername(username);
                if (u != null) {
                    ud.doDeleteFromUsername(username);
                    responseObject.addProperty("removedUsername", username);
                    responseObject.addProperty("type", "success");
                    responseObject.addProperty("msg", "User "
                            + username + " successfully removed.");
                } else {
                    responseObject.addProperty("type", "error");
                    responseObject.addProperty("msg", "User "
                            + username + " doesn't exists and cannot be removed.");
                }
            } else {
                responseObject.addProperty("type", "error");
                responseObject.addProperty("msg", "The username is too long or too "
                        + "short or it's an invalid username");
            }
            resp.getWriter().println(responseObject);
            resp.flushBuffer();
        } else {
            throw new RequestParametersException("Error in the parameter passing: one of "
                    + "the parametrs is null");
        }
    }
}
