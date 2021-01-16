package controller.shopManagement;

import com.google.gson.JsonObject;
import controller.RequestParametersException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.User;
import model.personalization.ListNotInitializedException;
import model.personalization.RecommendedProductList;

/**
 * This servlet handles the voting of the {@link RecommendedProductList} for the user, and
 * its registration on the dataset.
 */
@WebServlet(urlPatterns = {"/vote-personalization"})
public class VotePersonalizationServlet extends HttpServlet {


    /**
     * This method manages Post request calling doGet method.
     *
     * @param request a HttpServletRequest
     * @param response an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    /**
     * This method manages Get requests.
     *
     * @param request a HttpServletRequest
     * @param response an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");
        boolean vote = Boolean.parseBoolean(request.getParameter("vote"));
        RecommendedProductList rpl;
        rpl = (RecommendedProductList) session.getAttribute("recommendedProducts");

        if (loggedUser == null) {
            throw new RequestParametersException(
                    "Error: you cannot access to the recommended product list without being logged!"
            );
        }

        JsonObject responseObject = new JsonObject();
        synchronized (session) {
            try {
                rpl.setVote(vote);
                responseObject.addProperty("type", "success");
                responseObject.addProperty("msg", "Voting completed successfully!");
            } catch (Exception e) {
                responseObject.addProperty("type", "error");
                responseObject.addProperty("msg", "Voting not completed: " + e);
            } finally {
                response.getWriter().println(responseObject);
                response.flushBuffer();
            }
        }
    }
}
