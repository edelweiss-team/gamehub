package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.RequestParametersException;
import model.bean.Category;
import model.dao.CategoryDAO;
import org.jetbrains.annotations.NotNull;

/**
 * this servlet let Admin to add/remove/edit a category
 */
@WebServlet(urlPatterns = {"/manageCategory-servlet", "/manage-category"})
@MultipartConfig
public class ManageCategoryServlet extends HttpServlet {

    public static final int CATEGORY_MAX_LENGTH = 20;
    public static final int CATEGORY_MIN_LENGTH = 3;
    public static final int DESCRIPTION_CATEGORY_MIN_LENGTH = 3;
    public static final int DESCRIPTION_CATEGORY_MAX_LENGTH = 1000;
    public static final @NotNull String NAME_REGEX = "^(([A-Za-z][a-z0-9]*"
            + "([-'\\s\\.]))*([A-Za-z0-9][A-Za-z0-9]*))$";

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
        CategoryDAO cd = new CategoryDAO();
        Category c;
        JsonObject responseObject = new JsonObject();
        String operation = req.getParameter("manage_category");
        String nome = req.getParameter("removeCategory");
        if (operation != null) {
            if (operation.equals("remove_category")) {
                if (req.getParameter("removeCategory") != null) {
                    if (req.getParameter("removeCategory").length() <= CATEGORY_MAX_LENGTH
                            && req.getParameter("removeCategory").length() >= CATEGORY_MIN_LENGTH) {
                        String name = req.getParameter("removeCategory");
                        c = cd.doRetrieveByName(name);
                        if (c != null) {
                            cd.doDeleteByName(name);
                            responseObject.addProperty("removedCategory", name);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Category "
                                    + name + " successfully removed.");
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Category "
                                    + name + " doesn't exists and cannot be removed.");
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The category is "
                                + "too long or too short");
                    }
                    resp.getWriter().println(responseObject);
                    resp.flushBuffer();
                } else {
                    throw new RequestParametersException("\nError in the parameter "
                            + "passing: one of the parameters is null.");
                }
            } else if (operation.equals("update_category")) {
                String categoryName = req.getParameter("editable-name");
                String description = req.getParameter("editable-description");
                Part categoryImage = req.getPart("fileCategory");
                if (categoryName != null && description != null) {
                    categoryName = categoryName.trim();
                    description = description.trim();
                    if (categoryName.length() >= CATEGORY_MIN_LENGTH
                            && categoryName.length() <= CATEGORY_MAX_LENGTH
                            && description.length() >= DESCRIPTION_CATEGORY_MIN_LENGTH
                            && description.length() <= DESCRIPTION_CATEGORY_MAX_LENGTH) {


                        if (categoryImage != null) {
                            //se la nuova immagine esiste sovrascriviamo quella vecchia
                            InputStream is = categoryImage.getInputStream();
                            BufferedInputStream bin = new BufferedInputStream(is);
                            FileOutputStream fos =
                                    new FileOutputStream(new File(
                                            getServletContext().getRealPath("/img/categories")
                                                    + File.separator,
                                            categoryName.toLowerCase().replace(" ", "-")
                                                    + "-Image.jpg"));


                            int ch = 0;
                            while ((ch = bin.read()) != -1) {
                                fos.write(ch);
                                //fos2.write(ch);
                            }
                            fos.flush();
                            //fos2.flush();
                            fos.close();
                            //fos2.close();
                            bin.close();
                        }


                        String oldName = req.getParameter("old-name");
                        c = cd.doRetrieveByName(oldName);
                        Category check = cd.doRetrieveByName(categoryName);
                        JsonObject responseCategory = new JsonObject();
                        JsonObject responseJson = new JsonObject();

                        //se già esiste un'altra categoria con quel nome restituiamo errore
                        // e ritornaimo la vecchia categoria
                        if (check != null && !check.equals(c)) {
                            responseJson.addProperty("type", "error");
                            responseJson.addProperty("message", "Category "
                                    + categoryName + " already exists!");
                            responseCategory.addProperty("name", c.getName());
                            responseCategory.addProperty("description", c.getDescription());
                            responseCategory.addProperty("imagePath", c.getImage());
                        } else { //altrimenti aggiorniamola nel context e nel db,
                            // e scriviamo quella aggiornata nella risposta json
                            c.setName(categoryName);
                            c.setDescription(description);

                            if (categoryImage != null) {
                                //se c'è una nuova immagine aggiorniamo l'imagePath
                                c.setImage(categoryName.toLowerCase().replace(" ",
                                        "-") + "-Image.jpg");
                            }

                            cd.doUpdateByName(c, oldName);
                            responseJson.addProperty("type", "success");
                            responseJson.addProperty("message", "update completed successfully!");
                            responseCategory.addProperty("name", c.getName());
                            responseCategory.addProperty("description", c.getDescription());
                            responseCategory.addProperty("imagePath", c.getImage());
                        }

                        //scriviamo quindi nell'oggetto json di risposta il vecchio nome
                        // della categoria e l'oggetto categoria
                        responseJson.addProperty("oldName", oldName);
                        responseJson.add("updatedCategory", responseCategory);
                        resp.getWriter().println(responseJson.toString());
                        resp.flushBuffer();
                    } else {
                        //se uno dei parametri è null o non è stato passato correttamente,
                        // lanciamo un'eccezione
                        throw new RequestParametersException("error in the request parameters: "
                                + "exceeded maximum or minimum text length.");
                    }


                } else {
                    throw new RequestParametersException("error in the request parameters: "
                            + "null parameters.");
                }
            } else if (operation.equals("add_category")) {
                String categoryName = req.getParameter("categoryName");
                String description = req.getParameter("description_category");

                Part categoryImage = req.getPart("image_path");

                JsonObject categoryJson;

                if (categoryName != null && description != null && categoryImage != null) {
                    if (categoryName.length() >= CATEGORY_MIN_LENGTH
                            && categoryName.length() <= CATEGORY_MAX_LENGTH
                            && description.length() >= DESCRIPTION_CATEGORY_MIN_LENGTH
                            && description.length() <= DESCRIPTION_CATEGORY_MAX_LENGTH
                            && categoryName.matches(NAME_REGEX)) {


                        InputStream is = categoryImage.getInputStream();
                        BufferedInputStream bin = new BufferedInputStream(is);
                        // commenti precedenti**********************************************************
                        /*FileOutputStream fos2 = new FileOutputStream(
                                new File("C:\\apache-tomcat-9.0.31\\webapps\\studium\\"
                                        + "resources\\images\\categoryImages"
                                        + File.separator,
                                        categoryName.toLowerCase().replace(" ", "-")
                                                + "-Image.jpg"));
                                                */
                        // commenti precedenti**********************************************************

                        FileOutputStream fos =
                                new FileOutputStream(new File(
                                        getServletContext().getRealPath("/img/categories")
                                        + File.separator,
                                        categoryName.toLowerCase().replace(" ", "-")
                                                + "-Image.jpg"));
                        int ch = 0;
                        while ((ch = bin.read()) != -1) {
                            fos.write(ch);
                            //fos2.write(ch);
                        }
                        fos.flush();
                        //fos2.flush();
                        fos.close();
                        //fos2.close();
                        bin.close();



                        Category cat = new Category(categoryName, description,
                                categoryName.toLowerCase().replace(" ", "-")
                                        + "-Image.jpg");

                        Category o = cd.doRetrieveByName(categoryName);
                        if (o != null) {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "category " + o.getName()
                                    + " cannot be added, because it already exists!");
                        } else {
                            cd.doSave(cat);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "category "
                                    + categoryName + " added successfully!");
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "category " + categoryName
                                + " cannot be added, because some parameters don't"
                                + " fit the criteria!");
                    }
                    resp.getWriter().println(responseObject);
                    resp.flushBuffer();
                } else {
                    throw new RequestParametersException("\nError in the parameter passing: "
                            + "one of the parameters is null.\n");
                }
            } else {
                throw new RequestParametersException("Error in the operation value that it's not "
                        + "remove or update or add");
            }
        } else {
            throw new RequestParametersException("Error in the parameter passing: one of "
                    + "the parametrs is null");
        }
    }
}
