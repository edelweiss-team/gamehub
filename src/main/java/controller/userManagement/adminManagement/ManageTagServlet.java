package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RequestParametersException;
import model.bean.Tag;
import model.dao.TagDAO;

/**
 * this servlet let Admin to add/remove/edit a tag saved in the DB.
 */
@WebServlet(urlPatterns = {"/manageTag-servlet", "/manage-tag"})
public class ManageTagServlet extends HttpServlet {

    public static final int TAG_MAX_LENGTH = 20;
    public static final int TAG_MIN_LENGTH = 3;

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

        TagDAO td = new TagDAO();
        Tag t;
        JsonObject responseObject = new JsonObject();
        String operation = req.getParameter("manage_tag");

        if (operation != null) {
            if (operation.equals("remove_tag")) {
                if (req.getParameter("removeTag") != null) {
                    if (req.getParameter("removeTag").length() <= TAG_MAX_LENGTH
                        && req.getParameter("removeTag").length() >= TAG_MIN_LENGTH) {
                        String name = req.getParameter("removeTag");
                        t = td.doRetrieveByName(name);
                        if (t != null) {
                            td.doDelete(name);
                            responseObject.addProperty("removedTag", name);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Tag "
                                    + name + " successfully removed.");
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Tag "
                                    + name + " doesn't exists and cannot be removed.");
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The tag is "
                                + "too long or too short");
                    }
                    resp.getWriter().println(responseObject);
                    resp.flushBuffer();
                } else {
                    throw new RequestParametersException("\nError in the parameter "
                            + "passing: one of the parameters is null.");
                }
            } else if (operation.equals("update_tag")) {
                String name = req.getParameter("editable-name");
                if (name != null) {
                    name = name.trim();
                    if (name.length() >= TAG_MIN_LENGTH && name.length() <= TAG_MAX_LENGTH) {
                        String oldName = req.getParameter("old-name");
                        t = td.doRetrieveByName(oldName);
                        Tag tNuovo = td.doRetrieveByName(name);
                        JsonObject responseTag = new JsonObject();
                        JsonObject responseJson = new JsonObject();

                        if (tNuovo != null && !tNuovo.equals(t)) {
                            responseJson.addProperty("type", "error");
                            responseJson.addProperty("message", "Category "
                                    + name + " already exists!");
                            responseTag.addProperty("name", t.getName());
                        } else {
                            t.setName(name);
                            td.doUpdate(t, oldName);
                            responseJson.addProperty("type", "success");
                            responseJson.addProperty("message", "Update completed successfully!");
                            responseTag.addProperty("name", t.getName());
                        }
                    } else {
                        throw new RequestParametersException("error in the request parameters: "
                                + "exceeded maximum or minimum text length.");
                    }
                } else {
                    throw new RequestParametersException("error in the request parameters: "
                            + "null parameters.");
                }
            } else if (operation.equals("add_tag")) {
                String name = req.getParameter("name");
                JsonObject tagJson;

                if (name != null) {
                    if (name.length() >= TAG_MIN_LENGTH && name.length() <= TAG_MAX_LENGTH) {
                        t = new Tag(name);
                        Tag t1 = td.doRetrieveByName(t.getName());
                        if (t1 != null) {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Tag " + t.getName()
                                    + " cannot be added, because it already exists!");
                        } else {
                            td.doSave(t);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Tag "
                                    + t.getName() + " added successfully!");
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "tag " + name
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
