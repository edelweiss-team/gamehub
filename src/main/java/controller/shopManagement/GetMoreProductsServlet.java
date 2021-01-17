package controller.shopManagement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.RequestParametersException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Product;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;

/**
 *  This servlet adds more products to the response.
 *
 */
@WebServlet(urlPatterns = {"/get-more-products"})
public class GetMoreProductsServlet extends HttpServlet {
    public static final int PRODUCTS_PER_REQUEST_DEFAULT = 8;
    public static final int PRODUCT_NAME_LENGTH = 46;
    private static final int LIMIT_MAX = 2000000;

    /**
     * this method manages Post request calling doGet method.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int startingIndex;
        DigitalProductDAO dpd = new DigitalProductDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();
        JsonObject productJson;
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
            price = (req.getParameter("price") !=  null
                    && req.getParameter("price").length() > 0)
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

        ArrayList<Product> productList;
        if (productType.equalsIgnoreCase("Digital")) {
            productList = new ArrayList<>(dpd.doRetrieveByAllFragment(searchString,
                    description, price, "", tag, category, startingIndex,
                    PRODUCTS_PER_REQUEST_DEFAULT));
        } else {
            productList = new ArrayList<>(ppd.doRetrieveByAllFragment(searchString,
                    description, price, tag, category, startingIndex,
                    PRODUCTS_PER_REQUEST_DEFAULT));
        }

        //risposta json
        for (int i = 0; i < productList.size() && i < productsPerRequest; i++) {
            productJson = new JsonObject();
            productJson.addProperty("name", productList.get(i).getName());
            productJson.addProperty("imagePath", productList.get(i).getImage());
            productJson.addProperty("id", productList.get(i).getId());
            productJson.addProperty("quantity", productList.get(i).getQuantity());
            productJson.addProperty("description", productList.get(i).getDescription());
            productJson.addProperty("price", productList.get(i).getPrice());

            //informazioni specifiche per il tipo di prodotto
            JsonObject finalProductJson = productJson;
            productList.get(i).getAdditionalInformations().forEach((k, v) -> {
                finalProductJson.addProperty(k, v);
            });

            //categorie
            String[] categories = new String[1]; //array per ovviare al problema dell'espressione lambda
            categories[0] = "";
            finalProductJson.addProperty("categories", "");
            productList.get(i).getCategories().forEach(c -> {
                categories[0] += c.getName() + ",";
            });
            categories[0] = categories[0].replaceAll(",$", "");
            finalProductJson.addProperty("categories", categories[0]);

            //tags
            String[] tags = new String[1]; //array per ovviare al problema dell'espressione lambda
            tags[0] = "";
            productList.get(i).getTags().forEach(t -> {
                tags[0] += t.getName() + ",";
            });
            tags[0] = tags[0].replaceAll(",$", "");
            finalProductJson.addProperty("tags", tags[0]);

            //tipo di prodotto
            productJson.addProperty(
                    "productType",
                    productList.get(i).getClass().getSimpleName().replaceAll(
                            "Product", ""
                    )
            );
            newProducts.add(productJson);
        }

        JsonObject responseObject = new JsonObject();
        responseObject.add("newProducts", newProducts);
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }

    /*public static void main(String[] args) {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing",
        "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        p.addCategory(new Category("cat0", "desc0", "image"));
        p.addCategory(new Category("cat1", "desc0", "image"));
        p.addCategory(new Category("cat2", "desc0", "image"));
        p.addTag(new Tag("tag0"));
        p.addTag(new Tag("tag1"));
        p.addTag(new Tag("tag2"));
        DigitalProduct p2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing2",
        "imagetesting2", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox2", "1999-02-05", 18, "testing2", "testingpub2");
        p2.addCategory(new Category("cat0", "desc0", "image"));
        p2.addCategory(new Category("cat1", "desc0", "image"));
        p2.addCategory(new Category("cat2", "desc0", "image"));
        p2.addTag(new Tag("tag0"));
        p2.addTag(new Tag("tag1"));
        p2.addTag(new Tag("tag2"));

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(p2);
        productList.add(p);

        JsonArray newProducts = new JsonArray();
        JsonObject productJson;
        for (int i = 0; i < productList.size() && i < productsPerRequest; i++) {
            productJson = new JsonObject();
            productJson.addProperty("name", productList.get(i).getName());
            productJson.addProperty("imagePath", productList.get(i).getImage());
            productJson.addProperty("id", productList.get(i).getId());
            productJson.addProperty("quantity", productList.get(i).getQuantity());
            productJson.addProperty("description", productList.get(i).getDescription());
            productJson.addProperty("price", productList.get(i).getPrice());

            //informazioni specifiche per il tipo di prodotto
            JsonObject finalProductJson = productJson;
            productList.get(i).getAdditionalInformations().forEach((k, v) -> {
                finalProductJson.addProperty(k, v);
            });

            //categorie
            String[] categories = new String[1]; //per il problema della lambda
            categories[0] = "";
            finalProductJson.addProperty("categories", "");
            productList.get(i).getCategories().forEach(c -> {
                categories[0] += c.getName() + ",";
            });
            categories[0] = categories[0].replaceAll(",$", "");
            finalProductJson.addProperty("categories", categories[0]);

            //tags
            String[] tags = new String[1]; //array per ovviare al problema dell'espressione lambda
            tags[0] = "";
            productList.get(i).getTags().forEach(t -> {
                tags[0] += t.getName() + ",";
            });
            tags[0] = tags[0].replaceAll(",$", "");
            finalProductJson.addProperty("tags", tags[0]);

            //tipo di prodotto
            productJson.addProperty(
                    "productType",
                    productList.get(i).getClass().getSimpleName().replaceAll(
                            "Product", ""
                    )
            );
            newProducts.add(productJson);
        }

        System.out.println(newProducts);
    }*/
}
