package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.bean.Category;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.Tag;
import model.dao.CategoryDAO;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;
import model.dao.TagDAO;

/**
 * this servlet let Admin to add/remove/edit a product saved in the DB.
 */
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
                    String name = req.getParameter("removeProduct");
                    if (type.equals("digitalProduct")) {
                        d = dpd.doRetrieveById(Integer.parseInt(name));
                        if (d == null) {
                            responseObject.addProperty("removedDigitalProduct", name);
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Digital Product "
                                    + "has not been removed because it doesn't exist.");
                            resp.getWriter().println(responseObject);
                            resp.flushBuffer();
                            return;
                        } else {
                            dpd.doDelete(d.getId());
                            responseObject.addProperty("removedDigitalProduct", name.trim());
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Digital Product "
                                    + " successfully removed.");
                            resp.getWriter().println(responseObject);
                            resp.flushBuffer();
                            return;
                        }
                    } else if (type.equals("physicalProduct")) {
                        p = ppd.doRetrieveById(Integer.parseInt(name));
                        if (p == null) {
                            responseObject.addProperty("name", name);
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "Physical Product "
                                    + "has not been removed because it doesn't exist.");
                            resp.getWriter().println(responseObject);
                            resp.flushBuffer();
                            return;
                        } else {
                            ppd.doDelete(p.getId());
                            responseObject.addProperty("name", name);
                            responseObject.addProperty("type", "success");
                            responseObject.addProperty("msg", "Physical Product "
                                    + " successfully removed.");
                            resp.getWriter().println(responseObject);
                            resp.flushBuffer();
                            return;
                        }
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
                    Part digitalProductImage = req.getPart("editable-image");
                    String categories = req.getParameter("editable-categories");
                    String tags = req.getParameter("editable-tags");
                    if (name != null && description != null && platform != null
                        && releaseDate != null && softwareHouse != null
                        && publisher != null && categories != null && tags != null) {
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
                            if (digitalProductImage != null) {
                                //se la nuova immagine esiste sovrascriviamo quella vecchia
                                InputStream is = digitalProductImage.getInputStream();
                                BufferedInputStream bin = new BufferedInputStream(is);
                                FileOutputStream fos =
                                        new FileOutputStream(new File(
                                                getServletContext().getRealPath("/img/products")
                                                        + File.separator,
                                                name.toLowerCase().replace(" ", "-")
                                                        + "-DigitalImage.jpg"));

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
                            d = dpd.doRetrieveByAllFragment(oldName, "%",
                                    Double.parseDouble("1000"), "%", "%",
                                    "%", 0, 1000).get(0);
                            DigitalProduct dCheck = dpd.doRetrieveByAllFragment(name, "%",
                                    Double.parseDouble("1000"), "%", "%",
                                    "%", 0, 1000).get(0);
                            JsonObject responseDigitalProduct = new JsonObject();
                            JsonObject responseJson = new JsonObject();
                            if (dCheck != null && !dCheck.equals(d)) {
                                responseJson.addProperty("type", "error");
                                responseJson.addProperty("message", "Digital Product "
                                        + name + " already exists!");
                                responseDigitalProduct.addProperty("name", d.getName());
                                responseDigitalProduct.addProperty("price", d.getPrice());
                                responseDigitalProduct.addProperty("description",
                                        d.getDescription());
                                responseDigitalProduct.addProperty("platform",
                                        d.getPlatform());
                                responseDigitalProduct.addProperty("releaseDate",
                                        d.getReleaseDate());
                                responseDigitalProduct.addProperty("requiredAge",
                                        d.getRequiredAge());
                                responseDigitalProduct.addProperty("softwareHouse",
                                        d.getSoftwareHouse());
                                responseDigitalProduct.addProperty("publisher",
                                        d.getPublisher());
                                responseDigitalProduct.addProperty("quantity",
                                        d.getQuantity());
                                responseDigitalProduct.addProperty("image", d.getImage());
                            } else {
                                d.setName(name);
                                d.setPrice(price);
                                d.setDescription(description);
                                d.setPlatform(platform);
                                d.setReleaseDate(releaseDate);
                                d.setRequiredAge(requiredAge);
                                d.setSoftwareHouse(softwareHouse);
                                d.setPublisher(publisher);
                                d.setQuantity(quantity);
                                Pattern catAndTagRegex = Pattern.compile("(.*?),");
                                Matcher matcher = catAndTagRegex.matcher(categories);
                                ArrayList<Category> categoriesList = new ArrayList<>();
                                CategoryDAO cd = new CategoryDAO();
                                while (!categories.equals("")) {
                                    if (matcher.find()) {
                                        categoriesList.add(cd.doRetrieveByName(
                                                matcher.group(1).trim()));
                                        categories = categories.substring(
                                                matcher.group(1).trim().length() + 1);
                                    } else if (!categories.contains(",")) {
                                        categoriesList.add(cd.doRetrieveByName(categories));
                                        categories = "";
                                    }
                                }
                                d.setCategories(categoriesList);

                                matcher = catAndTagRegex.matcher(tags);
                                ArrayList<Tag> tagsList = new ArrayList<>();
                                TagDAO td = new TagDAO();
                                while (!tags.equals("")) {
                                    if (matcher.find()) {
                                        tagsList.add(td.doRetrieveByName(matcher.group(1).trim()));
                                        tags = tags.substring(matcher.group(1).trim().length() + 1);
                                    } else if (!tags.contains(",")) {
                                        tagsList.add(td.doRetrieveByName(tags));
                                        tags = "";
                                    }
                                }
                                d.setTags(tagsList);

                                if (digitalProductImage != null) {
                                    d.setImage(name.toLowerCase().replace(" ", "-")
                                            + "-DigitalImage.jpg");
                                }

                                dpd.doUpdate(d);
                                responseJson.addProperty("type", "success");
                                responseJson.addProperty("message",
                                        "update completed successfully");
                                responseDigitalProduct.addProperty("name", d.getName());
                                responseDigitalProduct.addProperty("price", d.getPrice());
                                responseDigitalProduct.addProperty("description",
                                        d.getDescription());
                                responseDigitalProduct.addProperty("platform",
                                        d.getPlatform());
                                responseDigitalProduct.addProperty("releaseDate",
                                        d.getReleaseDate());
                                responseDigitalProduct.addProperty("requiredAge",
                                        d.getRequiredAge());
                                responseDigitalProduct.addProperty("softwareHouse",
                                        d.getSoftwareHouse());
                                responseDigitalProduct.addProperty("publisher",
                                        d.getPublisher());
                                responseDigitalProduct.addProperty("quantity",
                                        d.getQuantity());
                                responseDigitalProduct.addProperty("image", d.getImage());
                            }

                            responseJson.addProperty("oldName", oldName);
                            responseJson.add("updatedDigitalProduct", responseDigitalProduct);
                            resp.getWriter().println(responseJson.toString());
                            resp.flushBuffer();
                            return;
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "The product parameters"
                                    + " aren't valid.");
                            resp.getWriter().println(responseObject.toString());
                            resp.flushBuffer();
                            return;
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The product parameters"
                                + " are null.");
                        resp.getWriter().println(responseObject.toString());
                        resp.flushBuffer();
                        return;
                    }
                } else if (type.equals("physicalProduct")) {
                    String name = req.getParameter("editable-name").trim();
                    double price = Double.parseDouble(req.getParameter("editable-price"));
                    String description = req.getParameter("editable-description").trim();
                    String weight = req.getParameter("editable-weight").trim();
                    String size = req.getParameter("editable-size").trim();
                    int quantity = Integer.parseInt(req.getParameter("editable-quantity"));
                    String categories = req.getParameter("editable-categories");
                    String tags = req.getParameter("editable-tags");
                    Part physicalProductImage = req.getPart("editable-image");
                    if (name != null && description != null && weight != null && size != null) {
                        if (name.length() >= PRODUCT_MIN_LENGTH
                                && name.length() <= PRODUCT_MAX_LENGTH
                                && description.length() >= DESCRIPTION_MIN_LENGTH
                                && description.length() <= DESCRIPTION_MAX_LENGTH) {
                            if (physicalProductImage != null) {
                                //se la nuova immagine esiste sovrascriviamo quella vecchia
                                InputStream is = physicalProductImage.getInputStream();
                                BufferedInputStream bin = new BufferedInputStream(is);
                                FileOutputStream fos =
                                        new FileOutputStream(new File(
                                                getServletContext().getRealPath("/img/products")
                                                        + File.separator,
                                                name.toLowerCase().replace(" ", "-")
                                                        + "-PhysicalImage.jpg"));

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
                            p = ppd.doRetrieveByAllFragment(oldName, "%",
                                    Double.parseDouble("1000"), "%",
                                    "%", 0, 10000).get(0);
                            PhysicalProduct pCheck = ppd.doRetrieveByAllFragment(name, "%",
                                    Double.parseDouble("1000"), "%",
                                    "%", 0, 10000).get(0);
                            JsonObject responsePhysicalProduct = new JsonObject();
                            JsonObject responseJson = new JsonObject();
                            if (pCheck != null && !pCheck.equals(p)) {
                                responseJson.addProperty("type", "error");
                                responseJson.addProperty("message", "Digital Product "
                                        + name + " already exists!");
                                responsePhysicalProduct.addProperty("name", p.getName());
                                responsePhysicalProduct.addProperty("price", p.getPrice());
                                responsePhysicalProduct.addProperty("description",
                                        p.getDescription());
                                responsePhysicalProduct.addProperty("quantity",
                                        p.getQuantity());
                                responsePhysicalProduct.addProperty("image", p.getImage());
                                responsePhysicalProduct.addProperty("weight",
                                        p.getWeight());
                                responsePhysicalProduct.addProperty("size", p.getSize());
                            } else {
                                p.setName(name);
                                p.setPrice(price);
                                p.setDescription(description);
                                p.setWeight(Double.parseDouble(weight));
                                p.setSize(size);
                                p.setQuantity(quantity);

                                Pattern catAndTagRegex = Pattern.compile("(.*?),");
                                Matcher matcher = catAndTagRegex.matcher(categories);
                                ArrayList<Category> categoriesList = new ArrayList<>();
                                CategoryDAO cd = new CategoryDAO();
                                while (!categories.equals("")) {
                                    if (matcher.find()) {
                                        categoriesList.add(cd.doRetrieveByName(
                                                matcher.group(1).trim()));
                                        categories = categories.substring(
                                                matcher.group(1).trim().length() + 1);
                                    } else if (!categories.contains(",")) {
                                        categoriesList.add(cd.doRetrieveByName(categories));
                                        categories = "";
                                    }
                                }
                                p.setCategories(categoriesList);

                                matcher = catAndTagRegex.matcher(tags);
                                ArrayList<Tag> tagsList = new ArrayList<>();
                                TagDAO td = new TagDAO();
                                while (!tags.equals("")) {
                                    if (matcher.find()) {
                                        tagsList.add(td.doRetrieveByName(matcher.group(1).trim()));
                                        tags = tags.substring(matcher.group(1).trim().length() + 1);
                                    } else if (!tags.contains(",")) {
                                        tagsList.add(td.doRetrieveByName(tags));
                                        tags = "";
                                    }
                                }
                                p.setTags(tagsList);

                                if (physicalProductImage != null) {
                                    p.setImage(name.toLowerCase().replace(" ",
                                            "-") + "-PhysicalImage.jpg");
                                }

                                ppd.doUpdate(p);
                                responseJson.addProperty("type", "success");
                                responseJson.addProperty("message",
                                        "update completed successfully");
                                responsePhysicalProduct.addProperty("name", p.getName());
                                responsePhysicalProduct.addProperty("price", p.getPrice());
                                responsePhysicalProduct.addProperty("description",
                                        p.getDescription());
                                responsePhysicalProduct.addProperty("quantity",
                                        p.getQuantity());
                                responsePhysicalProduct.addProperty("image", p.getImage());
                                responsePhysicalProduct.addProperty("weight",
                                        p.getWeight());
                                responsePhysicalProduct.addProperty("size", p.getSize());
                            }
                            responseJson.addProperty("oldName", oldName);
                            responseJson.add("updatedPhysicalProduct", responsePhysicalProduct);
                            resp.getWriter().println(responseJson.toString());
                            resp.flushBuffer();
                            return;
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "The product parameters"
                                    + " aren't valid.");
                            resp.getWriter().println(responseObject.toString());
                            resp.flushBuffer();
                            return;
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The product parameters"
                                + " are null.");
                        resp.getWriter().println(responseObject.toString());
                        resp.flushBuffer();
                        return;
                    }
                } else {
                    responseObject.addProperty("type", "error");
                    responseObject.addProperty("msg", "The product type is "
                            + "invalid.");
                    resp.getWriter().println(responseObject);
                    resp.flushBuffer();
                    return;
                }
            } else if (operation.equals("add_product")) {
                if (type.equals("digitalProduct")) {
                    String name = req.getParameter("name").trim();
                    double price = Double.parseDouble(req.getParameter("price"));
                    String description = req.getParameter("description").trim();
                    String platform = req.getParameter("platform").trim();
                    String releaseDate = req.getParameter("releaseDate").trim();
                    int requiredAge = Integer.parseInt(req.getParameter("requiredAge"));
                    String softwareHouse = req.getParameter("softwareHouse").trim();
                    String publisher = req.getParameter("publisher").trim();
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    Part digitalProductImage = req.getPart("image");
                    String categories = req.getParameter("categories");
                    String tags = req.getParameter("tags");

                    if (name != null && description != null && platform != null
                            && releaseDate != null && softwareHouse != null
                            && publisher != null && categories != null && tags != null) {
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
                            InputStream is = digitalProductImage.getInputStream();
                            BufferedInputStream bin = new BufferedInputStream(is);

                            FileOutputStream fos =
                                    new FileOutputStream(new File(
                                            getServletContext().getRealPath("/img/products")
                                                    + File.separator,
                                            name.toLowerCase().replace(" ", "-")
                                                    + "-DigitalImage.jpg"));
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

                            d = new DigitalProduct(1, name, price, description,
                                    name + "-DigitalImage.jpg", new ArrayList<>(),
                                    new ArrayList<>(), quantity, platform, releaseDate, requiredAge,
                                    softwareHouse, publisher);

                            Pattern catAndTagRegex = Pattern.compile("(.*?),");
                            Matcher matcher = catAndTagRegex.matcher(categories);
                            ArrayList<Category> categoriesList = new ArrayList<>();
                            CategoryDAO cd = new CategoryDAO();
                            while (!categories.equals("")) {
                                if (matcher.find()) {
                                    categoriesList.add(cd.doRetrieveByName(
                                            matcher.group(1).trim()));
                                    categories = categories.substring(
                                            matcher.group(1).trim().length() + 1);
                                } else if (!categories.contains(",")) {
                                    categoriesList.add(cd.doRetrieveByName(categories));
                                    categories = "";
                                }
                            }
                            d.setCategories(categoriesList);

                            matcher = catAndTagRegex.matcher(tags);
                            ArrayList<Tag> tagsList = new ArrayList<>();
                            TagDAO td = new TagDAO();
                            while (!tags.equals("")) {
                                if (matcher.find()) {
                                    tagsList.add(td.doRetrieveByName(matcher.group(1).trim()));
                                    tags = tags.substring(matcher.group(1).trim().length() + 1);
                                } else if (!tags.contains(",")) {
                                    tagsList.add(td.doRetrieveByName(tags));
                                    tags = "";
                                }
                            }
                            d.setTags(tagsList);

                            DigitalProduct dCheck = dpd.doRetrieveByAllFragment(name, "%",
                                    Double.parseDouble("10000"), "%", "%",
                                    "%", 0, 1000).get(0);

                            if (dCheck != null) {
                                responseObject.addProperty("type", "error");
                                responseObject.addProperty("msg", "DigitalProduct "
                                        + dCheck.getName() + " cannot be added, because it already"
                                        + " exists!");
                                resp.getWriter().println(responseObject);
                                resp.flushBuffer();
                                return;
                            } else {
                                dpd.doSave(d);
                                responseObject.addProperty("type", "success");
                                responseObject.addProperty("msg", "DigitalProduct "
                                        + name + " added successfully!");
                                resp.getWriter().println(responseObject);
                                resp.flushBuffer();
                                return;
                            }
                        } else {
                            responseObject.addProperty("type", "error");
                            responseObject.addProperty("msg", "The product parameters"
                                    + " aren't valid.");
                            resp.getWriter().println(responseObject);
                            resp.flushBuffer();
                            return;
                        }
                    }
                } else if (type.equals("physicalProduct")) {
                    String name = req.getParameter("name").trim();
                    double price = Double.parseDouble(req.getParameter("price"));
                    String description = req.getParameter("description").trim();
                    String weight = req.getParameter("weight").trim();
                    String size = req.getParameter("size").trim();
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    Part physicalProductImage = req.getPart("image");
                    String categories = req.getParameter("categories");
                    String tags = req.getParameter("tags");
                    if (name != null && description != null && weight != null && size != null
                            && categories != null && tags != null) {
                        if (name.length() >= PRODUCT_MIN_LENGTH
                                && name.length() <= PRODUCT_MAX_LENGTH
                                && description.length() >= DESCRIPTION_MIN_LENGTH
                                && description.length() <= DESCRIPTION_MAX_LENGTH) {
                            InputStream is = physicalProductImage.getInputStream();
                            BufferedInputStream bin = new BufferedInputStream(is);

                            FileOutputStream fos = new FileOutputStream(new File(
                                    getServletContext().getRealPath("/img/products")
                                    + File.separator,
                                    name.toLowerCase().replace(" ", "-")
                                    + "-PhysicalImage.jpg"));

                            int ch = 0;
                            while ((ch = bin.read()) != -1) {
                                fos.write(ch);
                            }
                            fos.flush();
                            fos.close();
                            bin.close();

                            p = new PhysicalProduct(1, name, price, description,
                                    name + "-PhysicalImage.jpg",
                                    new ArrayList<>(), new ArrayList<>(), quantity, size,
                                    Double.parseDouble(weight));

                            Pattern catAndTagRegex = Pattern.compile("(.*?),");
                            Matcher matcher = catAndTagRegex.matcher(categories);
                            ArrayList<Category> categoriesList = new ArrayList<>();
                            CategoryDAO cd = new CategoryDAO();
                            while (!categories.equals("")) {
                                if (matcher.find()) {
                                    categoriesList.add(cd.doRetrieveByName(
                                            matcher.group(1).trim()));
                                    categories = categories.substring(
                                            matcher.group(1).trim().length() + 1);
                                } else if (!categories.contains(",")) {
                                    categoriesList.add(cd.doRetrieveByName(categories));
                                    categories = "";
                                }
                            }
                            p.setCategories(categoriesList);

                            matcher = catAndTagRegex.matcher(tags);
                            ArrayList<Tag> tagsList = new ArrayList<>();
                            TagDAO td = new TagDAO();
                            while (!tags.equals("")) {
                                if (matcher.find()) {
                                    tagsList.add(td.doRetrieveByName(matcher.group(1).trim()));
                                    tags = tags.substring(matcher.group(1).trim().length() + 1);
                                } else if (!tags.contains(",")) {
                                    tagsList.add(td.doRetrieveByName(tags));
                                    tags = "";
                                }
                            }
                            p.setTags(tagsList);

                            PhysicalProduct pCheck = ppd.doRetrieveByAllFragment(name,
                                    "%", Double.parseDouble("1000"), "%",
                                    "%", 0, 1000).get(0);
                            if (pCheck != null) {
                                responseObject.addProperty("type", "error");
                                responseObject.addProperty("msg", "Physical Product "
                                        + pCheck.getName() + " cannot be added, because it already"
                                        + " exists!");
                                resp.getWriter().println(responseObject);
                                resp.flushBuffer();
                                return;
                            } else {
                                ppd.doSave(p);
                                responseObject.addProperty("type", "success");
                                responseObject.addProperty("msg", "DigitalProduct "
                                        + name + " added successfully!");
                                resp.getWriter().println(responseObject);
                                resp.flushBuffer();
                                return;
                            }
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
