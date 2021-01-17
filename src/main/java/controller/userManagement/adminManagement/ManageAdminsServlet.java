package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import controller.RequestParametersException;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Admin;
import model.bean.Moderator;
import model.bean.User;
import model.dao.AdminDAO;
import model.dao.ModeratorDAO;
import model.dao.UserDAO;


/**
 * This servlet let Admin to add/remove/edit an Admin profile.
 */
@WebServlet(urlPatterns = {"/manageAdmin-servlet", "/manage-admin"})
@MultipartConfig
public class ManageAdminsServlet extends HttpServlet {

    public static final int ADMIN_MAX_LENGTH = 20;
    public static final int ADMIN_MIN_LENGTH = 6;

    /**
     * This method manages Post request calling doGet method.
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

        AdminDAO ad = new AdminDAO();
        Admin a;
        JsonObject responseObject = new JsonObject();
        String operation = req.getParameter("manage_admin");

        if (operation != null) {
            if (operation.equals("remove_admin")) {
                if (req.getParameter("removeAdmin") != null) {
                    if (req.getParameter("removeAdmin").length() <= ADMIN_MAX_LENGTH
                            && req.getParameter("removeAdmin").length() >= ADMIN_MIN_LENGTH) {
                        String name = req.getParameter("removeAdmin");
                        a = ad.doRetrieveByUsername(name);
                        if (a != null) {
                            ad.doDeleteByUsername(name);
                            responseObject.addProperty("username", name);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Admin "
                                    + name + " successfully removed.");
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Admin "
                                    + name + " doesn't exists and cannot be removed.");
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The admin is "
                                + "too long or too short");
                    }
                    resp.getWriter().println(responseObject);
                    resp.flushBuffer();
                } else {
                    throw new RequestParametersException("\nError in the parameter "
                            + "passing: one of the parameters is null.");
                }
            } else if (operation.equals("update_admin")) {
                boolean superAdmin;
                if (req.getParameter("editable-isSuperAdmin").toLowerCase().matches(
                        "(true|false)")) {
                    superAdmin = Boolean.parseBoolean(
                            req.getParameter("editable-isSuperAdmin")
                    );
                } else {
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("type", "success");
                    responseJson.addProperty(
                            "msg",
                            "Error superAdmin must be either true or false,"
                                   + " and you must insert a valid username!"
                    );
                    resp.getWriter().println(responseJson);
                    resp.flushBuffer();
                    return;
                }

                String oldName = req.getParameter("old-name");
                a = ad.doRetrieveByUsername(oldName);

                JsonObject responseJson = new JsonObject();

                if (a == null) {
                    responseJson.addProperty("type", "error");
                    responseJson.addProperty("message", "Admin "
                            + oldName + " doesn't exists!");
                    responseJson.addProperty("name", oldName);
                    resp.getWriter().println(responseJson);
                    resp.flushBuffer();
                } else {
                    a.setSuperAdmin(superAdmin);
                    ad.doUpdate(a);
                    responseJson.addProperty("type", "success");
                    responseJson.addProperty(
                            "message",
                            "Update completed successfully!"
                    );
                    responseJson.addProperty("isSuperAdmin", a.isSuperAdmin());
                    responseJson.addProperty("username", a.getUsername());
                    resp.getWriter().println(responseJson);
                    resp.flushBuffer();
                }

            } else if (operation.equals("add_admin")) {
                String name = req.getParameter("adminName");
                boolean superAdmin;
                if (req.getParameter("editable-isSuperAdmin") != null
                        && req.getParameter("editable-isSuperAdmin").toLowerCase().matches(
                        "(true|false)")) {
                    superAdmin = Boolean.parseBoolean(
                            req.getParameter("editable-isSuperAdmin")
                    );
                } else {
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("type", "success");
                    responseJson.addProperty(
                            "msg",
                            "Error superAdmin must be either true or false,"
                                    + " and you must insert a valid username!"
                    );
                    resp.getWriter().println(responseJson);
                    resp.flushBuffer();
                    return;
                }

                if (name != null) {
                    if (name.length() >= ADMIN_MIN_LENGTH
                            && name.length() <= ADMIN_MAX_LENGTH) {

                        a = ad.doRetrieveByUsername(name);
                        if (a != null) {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Admin " + name
                                    + " cannot be added, because it already exists!");
                        } else {
                            ModeratorDAO ud = new ModeratorDAO();
                            Moderator u = ud.doRetrieveByUsername(name);
                            if (u == null) {
                                responseObject.addProperty("type", "error");
                                responseObject.addProperty("msg", "Admin because"
                                         + " because it's not a user!");
                            } else {
                                a = new Admin(u, superAdmin);
                                ad.doSave(a);
                                responseObject.addProperty("type", "success");
                                responseObject.addProperty("msg", "Admin "
                                            + a.getUsername() + " added successfully!");
                            }
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "Admin " + name
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
