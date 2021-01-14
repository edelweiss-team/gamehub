package controller.userManagement.userProfileManagement;

import controller.RequestParametersException;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    /**
     * This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        this.doGet(req, resp);
    }

    /**
     * This method process the logout.
     * If there's no loggedUser in the session, we get an exception
     * otherwise the user is redirected to the homepage.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("loggedUser") == null) {
            throw new RequestParametersException("Error: you can't logout without being logged!");
        }
        synchronized (session) {
            session.invalidate();
        }
        resp.sendRedirect(".");
    }
}
