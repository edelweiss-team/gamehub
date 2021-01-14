package controller.userManagement.userProfileManagement;

import controller.RequestParametersException;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * this servlert shows, to the user which wants to proceed to the login, the
 * login page only if these conditions are satisfied:
 *      there's no logged user (no user in the session).
 */
@WebServlet(urlPatterns = {"/login.html"})
public class ShowLoginPageServlet extends HttpServlet {

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
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/Login.jsp");
        if (req.getSession().getAttribute("loggedUser") != null) {
            throw new RequestParametersException(
                    "Error: you can't login because you're already logged!"
            );
        }
        rd.forward(req, resp);
    }
}
