package controller;

import model.bean.User;
import model.dao.UserDAO;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "index.html", value = "/index.html")
public class HelloServlet extends HttpServlet {
    private String message;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        this.doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/Home.jsp");
        rd.forward(request, response);
    }


}