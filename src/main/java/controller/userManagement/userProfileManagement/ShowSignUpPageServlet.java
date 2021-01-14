package controller.userManagement.userProfileManagement;

import controller.RequestParametersException;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/signup.html"})
public class ShowSignUpPageServlet extends HttpServlet {

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
     * this method shows, to the user which wants to signup to the website, the signup page
     * if there's already a user in the session this will result in a RequestParameterException.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/Signup.jsp");
        if (req.getSession().getAttribute("loggedUser") != null) {
            throw new RequestParametersException(
                    "Error: you can't signup because you're already logged!"
            );
        }
        req.setAttribute("countries", SignupServlet.COUNTRY_LIST);
        rd.forward(req, resp);
    }
}
