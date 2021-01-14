package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.bean.Operator;
import model.dao.OperatorDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/get-more-operators"})
public class GetMoreOperatorsServlet extends HttpServlet {
    public static final int OPERATORS_PER_REQUEST_DEFAULT = 4;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int startingIndex;
        JsonObject responseObject = new JsonObject(), operator;
        JsonArray newOperators = new JsonArray();
        int operatorsPerRequest = (req.getParameter("operatorsPerRequest") != null)?
                (Integer.parseInt(req.getParameter("operatorsPerRequest"))):(OPERATORS_PER_REQUEST_DEFAULT);
        try{
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        }
        catch (NumberFormatException e){
            throw new RequestParametersException("Error in the parameters, operator number must be an integer");
        }
        OperatorDAO od = new OperatorDAO();
        ArrayList<Operator> operatorList;
        ArrayList<Operator> operatorListFull = od.doRetrieveAll();;

        operatorList = od.doRetrieveByUsernameFragment("%", startingIndex, operatorsPerRequest);


        for(int i = 0; i < operatorsPerRequest && i < operatorList.size(); i++){
            operator = new JsonObject();
            operator.addProperty("username", operatorList.get(i).getUsername());
            operator.addProperty("contractTime", operatorList.get(i).getContractTime());
            operator.addProperty("cv", operatorList.get(i).getCv());
            newOperators.add(operator);
        }
        responseObject.add("newOperators", newOperators);
        responseObject.addProperty("newMaxPages",
                Math.max(Math.ceil(operatorListFull.size()/(double)operatorsPerRequest), 1));
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }

}
