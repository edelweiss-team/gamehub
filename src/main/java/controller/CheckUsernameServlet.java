package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.dao.UserDAO;

@WebServlet("/check-username")
public class CheckUsernameServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

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
