package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;

@WebServlet(urlPatterns = {"/manageProduct-servlet", "/manage-product"})
@MultipartConfig
public class ManageProductServlet extends HttpServlet {

    public static final int PRODUCT_MAX_LENGTH = 20;
    public static final int PRODUCT_MIN_LENGTH = 3;
    public static final int DESCRIPTION_MAX_LENGTH = 1000;
    public static final int DESCRIPTION_MIN_LENGTH = 3;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DigitalProductDAO dpd = new DigitalProductDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();
        DigitalProduct d;
        PhysicalProduct p;
        JsonObject responseObject = new JsonObject();
        String operation = req.getParameter("manage_product");
        String type = req.getParameter("product_type");

        if (operation != null && type != null) {
            if (operation.equals("remove_product")) {
                if (req.getParameter("removeProduct").length() <= PRODUCT_MAX_LENGTH
                    && req.getParameter("removeProduct").length() >= PRODUCT_MIN_LENGTH) {
                    if (type.equals("digitalProduct")) {
                        // Rimozione prodotto digitale
                    } else if (type.equals("physicalProduct")) {
                        // Rimozione prodotto fisico
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The product type is "
                                + "invalid.");
                    }
                }
            } else if (operation.equals("update_product")) {
                if (type.equals("digitalProduct")) {
                    String name = req.getParameter("editable-name").trim();
                    double price = Double.parseDouble(req.getParameter("editable-price"));
                    String description = req.getParameter("editable-description").trim();
                    String platform = req.getParameter("editable-platform").trim();
                    String releaseDate = req.getParameter("editable-releaseDate").trim();
                    int requiredAge = Integer.parseInt(req.getParameter("editable-requiredAge"));
                    String softwareHouse = req.getParameter("editable-softwareHouse").trim();
                    String publisher = req.getParameter("editable-publisher").trim();
                    int quantity = Integer.parseInt(req.getParameter("editable-quantity"));
                    // IMMAGINE
                    if (name != null && description != null && platform != null
                        && releaseDate != null && softwareHouse != null
                        && publisher != null) {
                        if (name.length() >= PRODUCT_MIN_LENGTH
                                && name.length() <= PRODUCT_MAX_LENGTH
                                && description.length() >= DESCRIPTION_MIN_LENGTH
                                && description.length() <= DESCRIPTION_MAX_LENGTH
                                && platform.length() >= PRODUCT_MIN_LENGTH
                                && platform.length() <= PRODUCT_MAX_LENGTH
                                && softwareHouse.length() >= PRODUCT_MIN_LENGTH
                                && softwareHouse.length() <= PRODUCT_MAX_LENGTH
                                && publisher.length() >= PRODUCT_MIN_LENGTH
                                && publisher.length() <= PRODUCT_MAX_LENGTH) {
                            // OPERAZIONI
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "The product parameters"
                                    + " aren't valid.");
                        }
                    }
                } else if (type.equals("physicalProduct")) {
                    String name = req.getParameter("editable-name").trim();
                    double price = Double.parseDouble(req.getParameter("editable-price"));
                    String description = req.getParameter("editable-description").trim();
                    String weight = req.getParameter("editable-weight").trim();
                    String size = req.getParameter("editable-size").trim();
                    int quantity = Integer.parseInt(req.getParameter("editable-quantity"));
                    // IMMAGINE
                    if (name != null && description != null && weight != null && size != null) {
                        if (name.length() >= PRODUCT_MIN_LENGTH
                                && name.length() <= PRODUCT_MAX_LENGTH
                                && description.length() >= DESCRIPTION_MIN_LENGTH
                                && description.length() <= DESCRIPTION_MAX_LENGTH) {
                            // OPERAZIONI
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "The product parameters"
                                    + " aren't valid.");
                        }
                    }

                } else {
                    responseObject.addProperty("type", "error");
                    responseObject.addProperty("msg", "The product type is "
                            + "invalid.");
                }
            } else if (operation.equals("add_product")) {
                if (type.equals("digitalProduct")) {
                    String name = req.getParameter("name").trim();
                    double price = Double.parseDouble(req.getParameter("price"));
                    String description = req.getParameter("description").trim();
                    String platform = req.getParameter("platform").trim();
                    String releaseDate = req.getParameter("releaseDate").trim();
                    int requiredAge = Integer.parseInt(req.getParameter("requiredAge"));
                    String softwareHouse = req.getParameter("oftwareHouse").trim();
                    String publisher = req.getParameter("publisher").trim();
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    if (name != null && description != null && platform != null
                            && releaseDate != null && softwareHouse != null
                            && publisher != null) {
                        if (name.length() >= PRODUCT_MIN_LENGTH
                                && name.length() <= PRODUCT_MAX_LENGTH
                                && description.length() >= DESCRIPTION_MIN_LENGTH
                                && description.length() <= DESCRIPTION_MAX_LENGTH
                                && platform.length() >= PRODUCT_MIN_LENGTH
                                && platform.length() <= PRODUCT_MAX_LENGTH
                                && softwareHouse.length() >= PRODUCT_MIN_LENGTH
                                && softwareHouse.length() <= PRODUCT_MAX_LENGTH
                                && publisher.length() >= PRODUCT_MIN_LENGTH
                                && publisher.length() <= PRODUCT_MAX_LENGTH) {
                            // OPERAZIONI
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "The product parameters"
                                    + " aren't valid.");
                        }
                    }
                } else if (type.equals("physicalProduct")) {
                    String name = req.getParameter("name").trim();
                    double price = Double.parseDouble(req.getParameter("price"));
                    String description = req.getParameter("description").trim();
                    String weight = req.getParameter("weight").trim();
                    String size = req.getParameter("size").trim();
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    // IMMAGINE
                    if (name != null && description != null && weight != null && size != null) {
                        if (name.length() >= PRODUCT_MIN_LENGTH
                                && name.length() <= PRODUCT_MAX_LENGTH
                                && description.length() >= DESCRIPTION_MIN_LENGTH
                                && description.length() <= DESCRIPTION_MAX_LENGTH) {
                            // OPERAZIONI
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "The product parameters"
                                    + " aren't valid.");
                        }
                    }
                } else {
                    responseObject.addProperty("type", "error");
                    responseObject.addProperty("msg", "The product type is "
                            + "invalid.");
                }
            }
        }

    }
}
