package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.UserDAO;

@WebServlet("/check-mail")
public class CheckMailServlet extends HttpServlet {

    /**
     * This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * check in an email from the request is valid (match the regex and not already saved in DB).
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String mail = req.getParameter("mail");
        UserDAO ud = new UserDAO();
        if (mail.matches(
                "^[a-zA-Z][\\w\\.!#$%&'*+/=?^_`{|}~-]+@([a-zA-Z]\\w+\\.)+[a-z]{2,5}$")
                && ud.doRetrieveByMail(mail) == null) {
            resp.getWriter().println("<ok/>");
        } else {
            resp.getWriter().println("<notOk/>");
        }
        resp.flushBuffer();
    }
}
