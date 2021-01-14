package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import controller.RequestParametersException;
import model.bean.Operator;
import model.bean.User;
import model.dao.OperatorDAO;
import model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/manageOperator-servlet", "/manage-operator"})
public class ManageOperatorServlet extends HttpServlet {

    public static final int OPERATOR_MAX_LENGTH = 20;
    public static final int OPERATOR_MIN_LENGTH = 3;
    public static final int CV_MAX_LENGTH = 1000;
    public static final int CV_MIN_LENGTH = 3;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

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
                            responseObject.addProperty("removedOperator", name);
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
                if (cv != null) {
                    cv = cv.trim();
                    if (cv.length() >= CV_MIN_LENGTH && cv.length() <= CV_MAX_LENGTH) {
                        String oldName = req.getParameter("old-name");
                        o = od.doRetrieveByUsername(oldName);

                        JsonObject responseTag = new JsonObject();
                        JsonObject responseJson = new JsonObject();

                        if (o == null) {
                            responseJson.addProperty("type", "error");
                            responseJson.addProperty("message", "Operator "
                                    + oldName + " doesn't exists!");
                            responseTag.addProperty("name", oldName);
                        } else {
                            o.setCv(cv);
                            od.doUpdate(o);
                            responseJson.addProperty("type", "success");
                            responseJson.addProperty("message", "Update completed successfully!");
                            responseTag.addProperty("name", o.getUsername());
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
                String name = req.getParameter("name");
                String cv = req.getParameter("cv");
                JsonObject tagJson;

                if (name != null) {
                    if (name.length() >= OPERATOR_MIN_LENGTH && name.length() <= OPERATOR_MAX_LENGTH) {
                        UserDAO ud = new UserDAO();
                        User u = ud.doRetrieveByUsername(name);
                        if (u == null) {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Tag " + name
                                    + " cannot be added, because it doesn't already exists!");
                            resp.getWriter().println(responseObject);
                            resp.flushBuffer();
                        }
                        o = new Operator(u, name, cv);
                        Operator o1 = od.doRetrieveByUsername(name);
                        if (o1 != null) {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Operator " + o.getUsername()
                                    + " cannot be added, because it already exists!");
                        } else {
                            od.doSave(o);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Operator "
                                    + o.getUsername() + " added successfully!");
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
                    + "the parametrs is null");
        }
    }

}
