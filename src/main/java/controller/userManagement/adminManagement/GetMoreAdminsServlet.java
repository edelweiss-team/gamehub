package controller.userManagement.adminManagement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.RequestParametersException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Admin;
import model.dao.AdminDAO;


/**
 * This servlet adds more admins to the response.
 *
 */
@WebServlet(urlPatterns = {"/get-more-admins"})
public class GetMoreAdminsServlet extends HttpServlet {
    public static final int ADMINS_PER_REQUEST_DEFAULT = 4;

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
        int startingIndex;

        JsonArray newAdmins = new JsonArray();
        int adminsPerRequest = (req.getParameter("adminsPerRequest") != null)
                ? (Integer.parseInt(req.getParameter("adminsPerRequest")))
                : (ADMINS_PER_REQUEST_DEFAULT);
        try {
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        } catch (NumberFormatException e) {
            throw new RequestParametersException(
                    "Error in the parameters, admin number must be an integer"
            );
        }
        AdminDAO od = new AdminDAO();
        ArrayList<Admin> adminList;

        JsonObject responseObject = new JsonObject();
        JsonObject admin;
        adminList = od.doRetrieveAll(startingIndex, adminsPerRequest);
        ArrayList<Admin> adminListFull = od.doRetrieveAll(0, 2000000);

        for (int i = 0; i < adminsPerRequest && i < adminList.size(); i++) {
            admin = new JsonObject();
            admin.addProperty("username", adminList.get(i).getUsername());
            admin.addProperty("isSuperAdmin", adminList.get(i).isSuperAdmin());
            newAdmins.add(admin);
        }
        responseObject.add("newAdmins", newAdmins);
        responseObject.addProperty("newMaxPages",
                Math.max(Math.ceil(adminListFull.size() / (double) adminsPerRequest), 1));
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }

}
