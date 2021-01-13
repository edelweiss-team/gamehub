package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.bean.User;
import model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/get-more-users"})
public class GetMoreUsersServlet extends HttpServlet {
    public static final int USERS_PER_REQUEST_DEFAULT = 4;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int startingIndex;
        JsonObject responseObject = new JsonObject(), user;
        JsonArray newUsers = new JsonArray();
        int usersPerRequest = (req.getParameter("usersPerRequest") != null)?
                (Integer.parseInt(req.getParameter("usersPerRequest"))):(USERS_PER_REQUEST_DEFAULT);
        try{
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        }
        catch (NumberFormatException e){
            throw new RequestParametersException("Error in the parameters, user number must be an integer");
        }
        UserDAO ud = new UserDAO();
        ArrayList<User> userList;
        ArrayList<User> userListFull = ud.doRetrieveAll();;

        userList = ud.doRetrieveByUsernameFragment("%", startingIndex, usersPerRequest);


        for(int i = 0; i < usersPerRequest && i < userList.size(); i++){
            user = new JsonObject();
            user.addProperty("username", userList.get(i).getUsername());
            user.addProperty("mail", userList.get(i).getMail());
            user.addProperty("name", userList.get(i).getName());
            user.addProperty("surname", userList.get(i).getSurname());
            user.addProperty("birthDate", userList.get(i).getBirthDate());
            newUsers.add(user);
        }
        responseObject.add("newUsers", newUsers);
        responseObject.addProperty("newMaxPages",
                Math.max(Math.ceil(userListFull.size()/(double)usersPerRequest), 1));
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }

}
