package controller.shopManagement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RequestParametersException;
import model.bean.Tag;
import model.dao.TagDAO;

@WebServlet(urlPatterns = {"/get-more-tags"})
public class GetMoreTagsServlet extends HttpServlet {
    public static final int TAGS_PER_REQUEST_DEFAULT = 4;

    /**
     * This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * Add more Tag to the response.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int startingIndex;
        JsonObject tag;
        JsonArray newTags = new JsonArray();
        int tagsPerRequest = (req.getParameter("tagsPerRequest") != null)
                ? (Integer.parseInt(req.getParameter("tagsPerRequest")))
                : (TAGS_PER_REQUEST_DEFAULT);
        try {
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        } catch (NumberFormatException e) {
            throw new RequestParametersException("Error in the parameters, "
                    + "tags number must be an integer");
        }

        TagDAO ud = new TagDAO();
        ArrayList<Tag> tagList;
        ArrayList<Tag> tagListFull = ud.doRetrieveByNameFragment(
                "", 0, 20000000
        );
        if (req.getParameter("search").equals("")) {
            tagList = ud.doRetrieveByNameFragment("%", startingIndex, tagsPerRequest);
        } else {
            tagList = ud.doRetrieveByNameFragment(req.getParameter("search"), startingIndex,
                    tagsPerRequest);
            tagListFull = ud.doRetrieveByNameFragment(req.getParameter("search"), 0,
                    tagListFull.size());
        }

        JsonObject responseObject = new JsonObject();
        for (int i = 0; i < tagsPerRequest && i < tagList.size(); i++) {
            tag = new JsonObject();
            tag.addProperty("name", tagList.get(i).getName());
            newTags.add(tag);
        }
        responseObject.add("newTags", newTags);
        responseObject.addProperty("newMaxPages",
                Math.max(Math.ceil(tagListFull.size() / (double) tagsPerRequest), 1));
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }
}
