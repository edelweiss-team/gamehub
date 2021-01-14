package controller.userManagement.userProfileManagement;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import controller.userManagement.userProfileManagement.LoginServlet;
import model.dao.UserDAO;

@WebServlet("/check-username")
public class CheckUsernameServlet extends HttpServlet {

    /**
     * this method manages Post request calling doGet method.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * check in an username from the request is valid (match the regex and not already saved in DB).
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String username = req.getParameter("username");
        UserDAO ud = new UserDAO();
        if (username.matches(LoginServlet.USERNAME_REGEX)
                && ud.doRetrieveByUsername(username) == null) {
            resp.getWriter().println("<ok/>");
        } else {
            resp.getWriter().println("<notOk/>");
        }
        resp.flushBuffer();
    }
}
