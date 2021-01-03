package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.bean.DigitalProduct;
import model.bean.Product;
import model.dao.DigitalProductDAO;
import model.bean.PhysicalProduct;
import model.dao.PhysicalProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/get-more-products"})
public class GetMoreProductsServlet extends HttpServlet {
    public static final int PRODUCTS_PER_REQUEST_DEFAULT = 8;
    public static final int PRODUCT_NAME_LENGTH = 46;
    private static final int LIMIT_MAX = 2000000;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int startingIndex;
        DigitalProductDAO dpd = new DigitalProductDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();

        JsonObject productJson;
        JsonObject responseObject = new JsonObject();
        JsonArray newProducts = new JsonArray();
        String searchString = (req.getParameter("search") != null)
                ? req.getParameter("search") : "";

        String productType = (req.getParameter("productType") != null)
                ? req.getParameter("productType") : "Digital";
        String description = (req.getParameter("description") != null)
                ? req.getParameter("description") : "";
        String tag = (req.getParameter("tag") != null)
                ? req.getParameter("tag") : "";
        String category = (req.getParameter("category") != null)
                ? req.getParameter("category") : "";
        double price;
        try {
            price = (req.getParameter("price") !=  null && req.getParameter("price").length()>0)
                    ? Double.parseDouble(req.getParameter("price")) : LIMIT_MAX;
        } catch (NumberFormatException e) {
            throw new RequestParametersException("Error in the parameters, price "
                    + "number must be a double, "
                    + req.getParameter("price") + " is not a double.");
        }
        
        int productsPerRequest = (req.getParameter("productsPerRequest") != null)
                ? (Integer.parseInt(req.getParameter("productsPerRequest")))
                : (PRODUCTS_PER_REQUEST_DEFAULT);
        try {
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        } catch (NumberFormatException e) {
            throw new RequestParametersException("Error in the parameters, category "
                    + "number must be an integer, "
                    + req.getParameter("startingIndex") + " is not an integer.");
        }
        ArrayList<Product> productList = new ArrayList<>();
        
        if(productType.equalsIgnoreCase("Digital")) {
            productList = new ArrayList<>(dpd.doRetrieveByAllFragment(searchString,
                    description, price, "", tag, category, startingIndex,
                    PRODUCTS_PER_REQUEST_DEFAULT));
        }
        else{
            productList = new ArrayList<>(ppd.doRetrieveByAllFragment(searchString,
                    description, price, tag, category, startingIndex,
                    PRODUCTS_PER_REQUEST_DEFAULT));
        }
        for (int i = 0; i < productList.size() && i < productsPerRequest; i++) {
            productJson = new JsonObject();
            productJson.addProperty("name", productList.get(i).getName());
            productJson.addProperty("imagePath", productList.get(i).getImage());
            productJson.addProperty("id", productList.get(i).getId());
            productJson.addProperty("quantity", productList.get(i).getQuantity());
            productJson.addProperty("description", productList.get(i).getDescription());
            productJson.addProperty("price", productList.get(i).getPrice());
            productJson.addProperty(
                    "productType",
                    productList.get(i).getClass().getSimpleName().replaceAll("Product", "")
            );
            newProducts.add(productJson);
        }
        responseObject.add("newProducts", newProducts);
        resp.getWriter().println(responseObject);
        resp.flushBuffer();

    }
}
