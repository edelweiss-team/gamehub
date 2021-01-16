package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import controller.RequestParametersException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Moderator;
import model.bean.User;
import model.dao.ModeratorDAO;
import model.dao.UserDAO;
import org.jetbrains.annotations.NotNull;


/**
 * this servlet let Admin to add/remove/edit an Moderator profile.
 */
@WebServlet(urlPatterns = {"/manageModerator-servlet", "/manage-moderator"})
@MultipartConfig
public class ManageModeratorServlet extends HttpServlet {

    public static final int MODERATOR_MAX_LENGTH = 20;
    public static final int MODERATOR_MIN_LENGTH = 6;
    @NotNull
    public static final String DATE_REGEX =
            "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    /**
     * this method manages Post request calling doGet method.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ModeratorDAO md = new ModeratorDAO();
        Moderator m;
        JsonObject responseObject = new JsonObject();
        String operation = req.getParameter("manage_moderator");

        if (operation != null) {
            if (operation.equals("remove_moderator")) {
                if (req.getParameter("removeModerator") != null) {
                    if (req.getParameter("removeModerator").length() <= MODERATOR_MAX_LENGTH
                        && req.getParameter("removeModerator").length() >= MODERATOR_MIN_LENGTH) {
                        String name = req.getParameter("removeModerator");
                        m = md.doRetrieveByUsername(name);
                        if (m != null) {
                            md.doDeleteByUsername(name);
                            responseObject.addProperty("username", name);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Moderator "
                                    + name + " successfully removed.");
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Moderator "
                                    + name + " doesn't exists and cannot be removed.");
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The moderator is "
                                + "too long or too short");
                    }
                    resp.getWriter().println(responseObject);
                    resp.flushBuffer();
                } else {
                    throw new RequestParametersException("\nError in the parameter "
                            + "passing: one of the parameters is null.");
                }
            } else if (operation.equals("update_moderator")) {
                String contractTime = req.getParameter("editable-contractTime");
                if (contractTime != null) {
                    contractTime = contractTime.trim();
                    if (contractTime.matches(DATE_REGEX)) {
                        String oldName = req.getParameter("old-name");
                        m = md.doRetrieveByUsername(oldName);

                        JsonObject responseTag = new JsonObject();
                        JsonObject responseJson = new JsonObject();

                        if (m == null) {
                            responseJson.addProperty("type", "error");
                            responseJson.addProperty("message", "Moderator "
                                    + oldName + " doesn't exists!");
                            responseTag.addProperty("name", oldName);
                        } else {
                            m.setContractTime(contractTime);
                            md.doUpdate(m);
                            responseJson.addProperty("type", "success");
                            responseJson.addProperty(
                                    "message",
                                    "Update completed successfully!"
                            );
                            responseJson.addProperty("contractTime", m.getContractTime());
                            responseJson.addProperty("username", m.getUsername());
                            resp.getWriter().println(responseJson);
                            resp.flushBuffer();
                        }
                    } else {
                        throw new RequestParametersException("error in the request parameters: "
                                + "exceeded maximum or minimum text length.");
                    }
                } else {
                    throw new RequestParametersException("error in the request parameters: "
                            + "null parameters.");
                }
            } else if (operation.equals("add_moderator")) {
                String name = req.getParameter("moderatorName");
                String contractTime = req.getParameter("contractTime");

                if (name != null) {
                    if (name.length() >= MODERATOR_MIN_LENGTH
                            && name.length() <= MODERATOR_MAX_LENGTH) {

                        m = md.doRetrieveByUsername(name);
                        if (m != null) {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Moderator " + name
                                    + " cannot be added, because it already exists!");
                        } else {
                            UserDAO ud = new UserDAO();
                            User u = ud.doRetrieveByUsername(name);
                            if (u == null) {
                                responseObject.addProperty("type", "error");
                                responseObject.addProperty("msg", "Moderator because"
                                         + " because it's not a user!");
                            } else {
                                m = new Moderator(u, contractTime);
                                md.doSave(m);
                                responseObject.addProperty("type", "success");
                                responseObject.addProperty("msg", "Moderator "
                                        + m.getUsername() + " added successfully!");
                            }
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "Moderator " + name
                                + " cannot be added, because some parameters don't"
                                + " fit the criteria!");
                    }
                    resp.getWriter().println(responseObject);
                    resp.flushBuffer();
                } else {
                    throw new RequestParametersException("\nError in the parameter passing: "
                            + "one of the parameters is null.\n");
                }
            } else {
                throw new RequestParametersException("Error in the operation value that it's not "
                        + "remove or update or add");
            }
        } else {
            throw new RequestParametersException("Error in the parameter passing: one of "
                    + "the parameters is null");
        }
    }

}
