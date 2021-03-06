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
import model.bean.Operator;
import model.bean.User;
import model.dao.OperatorDAO;
import model.dao.UserDAO;
import org.jetbrains.annotations.NotNull;


/**
 * this servlet let Admin to add/remove/edit an Operator profile.
 */
@WebServlet(urlPatterns = {"/manageOperator-servlet", "/manage-operator"})
@MultipartConfig
public class ManageOperatorServlet extends HttpServlet {

    public static final int OPERATOR_MAX_LENGTH = 20;
    public static final int OPERATOR_MIN_LENGTH = 6;
    public static final int CV_MAX_LENGTH = 10000;
    public static final int CV_MIN_LENGTH = 3;
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

        OperatorDAO od = new OperatorDAO();
        Operator o;
        JsonObject responseObject = new JsonObject();
        String operation = req.getParameter("manage_operator");

        if (operation != null) {
            if (operation.equals("remove_operator")) {
                if (req.getParameter("removeOperator") != null) {
                    if (req.getParameter("removeOperator").length() <= OPERATOR_MAX_LENGTH
                            && req.getParameter("removeOperator").length() >= OPERATOR_MIN_LENGTH) {
                        String name = req.getParameter("removeOperator");
                        o = od.doRetrieveByUsername(name);
                        if (o != null) {
                            od.doDeleteByUsername(name);
                            responseObject.addProperty("username", name);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Operator "
                                    + name + " successfully removed.");
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Operator "
                                    + name + " doesn't exists and cannot be removed.");
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The operator is "
                                + "too long or too short");
                    }
                    resp.getWriter().println(responseObject);
                    resp.flushBuffer();
                } else {
                    throw new RequestParametersException("\nError in the parameter "
                            + "passing: one of the parameters is null.");
                }
            } else if (operation.equals("update_operator")) {
                String cv = req.getParameter("editable-cv");
                String contractTime = req.getParameter("editable-contractTime");
                if (cv != null && contractTime != null) {
                    cv = cv.trim();
                    contractTime = contractTime.trim();
                    if (cv.length() >= CV_MIN_LENGTH && cv.length() <= CV_MAX_LENGTH
                            && contractTime.matches(DATE_REGEX)) {
                        String oldName = req.getParameter("old-name");
                        o = od.doRetrieveByUsername(oldName);

                        JsonObject responseJson = new JsonObject();

                        if (o == null) {
                            responseJson.addProperty("type", "error");
                            responseJson.addProperty("message", "Operator "
                                    + oldName + " doesn't exists!");
                            responseJson.addProperty("name", oldName);
                            resp.getWriter().println(responseJson);
                            resp.flushBuffer();
                        } else {
                            o.setCv(cv);
                            o.setContractTime(contractTime);
                            od.doUpdate(o);
                            responseJson.addProperty("type", "success");
                            responseJson.addProperty(
                                    "message",
                                    "Update completed successfully!"
                            );
                            responseJson.addProperty("cv", o.getCv());
                            responseJson.addProperty("contractTime", o.getContractTime());
                            responseJson.addProperty("username", o.getUsername());
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
            } else if (operation.equals("add_operator")) {
                String name = req.getParameter("userName");
                String cv = req.getParameter("curriculum");
                String contractTime = req.getParameter("contractTime");
                JsonObject tagJson;

                if (name != null) {
                    if (name.length() >= OPERATOR_MIN_LENGTH
                            && name.length() <= OPERATOR_MAX_LENGTH) {

                        o = od.doRetrieveByUsername(name);
                        if (o != null) {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Operator " + name
                                    + " cannot be added, because it already exists!");
                        } else {
                            UserDAO ud = new UserDAO();
                            User u = ud.doRetrieveByUsername(name);
                            if (u == null) {
                                responseObject.addProperty("type", "error");
                                responseObject.addProperty("msg", "Operator because"
                                         + " because it's not a user!");
                            } else {
                                o = new Operator(u, contractTime, cv);
                                od.doPromote(o);
                                responseObject.addProperty("type", "success");
                                responseObject.addProperty("msg", "Operator "
                                        + o.getUsername() + " added successfully!");
                            }
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "Operator " + name
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
