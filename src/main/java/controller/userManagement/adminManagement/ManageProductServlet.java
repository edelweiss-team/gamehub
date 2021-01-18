package controller.userManagement.adminManagement;

import com.google.gson.JsonObject;
import controller.RequestParametersException;
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
                        resp.getWriter().println(responseObject);
                        resp.flushBuffer();
                        return;
                    }
                } else {
                    throw new RequestParametersException("Length or Regex aren't valid");
                }
            } else if (operation.equals("update_product")) {
                if (type.equals("digitalProduct")) {

                    if (req.getParameter("editable-name") != null
                            && req.getParameter("editable-description") != null
                            && req.getParameter("editable-platform") != null
                        && req.getParameter("editable-releaseDate") != null
                            && req.getParameter("editable-softwareHouse") != null
                        && req.getParameter("editable-publisher") != null
                            && req.getParameter("editable-categories") != null
                            && req.getParameter("editable-tags") != null) {
                        String name = req.getParameter("editable-name").trim();
                        double price = Double.parseDouble(
                                req.getParameter("editable-price").trim());
                        String description = req.getParameter("editable-description").trim();
                        String platform = req.getParameter("editable-platform").trim();
                        String releaseDate = req.getParameter("editable-releaseDate").trim();
                        int requiredAge = Integer.parseInt(
                                req.getParameter("editable-requiredAge").trim());
                        String softwareHouse = req.getParameter("editable-softwareHouse").trim();
                        String publisher = req.getParameter("editable-publisher").trim();
                        int quantity = Integer.parseInt(
                                req.getParameter("editable-quantity").trim());
                        //Part digitalProductImage = req.getPart("editable-imagePath");
                        String categories = req.getParameter("editable-categories").trim();
                        String tags = req.getParameter("editable-tags").trim();
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
                            Part digitalProductImage = req.getPart("editable-imagePath");
                            if (digitalProductImage != null) {
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
                                }
                                fos.flush();
                                fos.close();
                                bin.close();
                            }
                            int id = Integer.parseInt(req.getParameter("old-name"));
                            d = dpd.doRetrieveById(id);
                            JsonObject responseDigitalProduct = new JsonObject();
                            JsonObject responseJson = new JsonObject();
                            if (d == null) {
                                responseObject.addProperty("type", "error");
                                responseObject.addProperty("msg", "DigitalProduct "
                                        + id + " cannot be added, "
                                        + "because it doesn't"
                                        + " exists!");
                                responseObject.addProperty("name", d.getName());
                                responseObject.addProperty("price", d.getPrice());
                                responseObject.addProperty("description",
                                        d.getDescription());
                                responseObject.addProperty("platform",
                                        d.getPlatform());
                                responseObject.addProperty("releaseDate",
                                        d.getReleaseDate());
                                responseObject.addProperty("requiredAge",
                                        d.getRequiredAge());
                                responseObject.addProperty("softwareHouse",
                                        d.getSoftwareHouse());
                                responseObject.addProperty("publisher",
                                        d.getPublisher());
                                responseObject.addProperty("quantity",
                                        d.getQuantity());
                                responseObject.addProperty("image", d.getImage());
                                resp.getWriter().println(responseObject);
                                resp.flushBuffer();
                                return;
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
                                        Category c = cd.doRetrieveByName(matcher.group(1).trim());
                                        if (c != null) {
                                            categoriesList.add(c);
                                            categories = categories.substring(
                                                    matcher.group(1).trim().length() + 1);
                                        } else {
                                            throw new RequestParametersException("Error");
                                        }
                                    } else if (!categories.contains(",")) {
                                        Category c = cd.doRetrieveByName(categories);
                                        if (c != null) {
                                            categoriesList.add(c);
                                            categories = "";
                                        } else {
                                            throw new RequestParametersException("Error");
                                        }
                                    }
                                }
                                d.setCategories(categoriesList);

                                matcher = catAndTagRegex.matcher(tags);
                                ArrayList<Tag> tagsList = new ArrayList<>();
                                TagDAO td = new TagDAO();
                                while (!tags.equals("")) {
                                    if (matcher.find()) {
                                        Tag t = td.doRetrieveByName(matcher.group(1).trim());
                                        if (t != null) {
                                            tagsList.add(t);
                                            tags = tags.substring(
                                                    matcher.group(1).trim().length() + 1);
                                        } else {
                                            throw new RequestParametersException("Error");
                                        }
                                    } else if (!tags.contains(",")) {
                                        Tag t = td.doRetrieveByName(tags);
                                        if (t != null) {
                                            tagsList.add(t);
                                            tags = "";
                                        } else {
                                            throw new RequestParametersException("Error");
                                        }
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
                                responseObject.addProperty("name", d.getName());
                                responseObject.addProperty("price", d.getPrice());
                                responseObject.addProperty("description",
                                        d.getDescription());
                                responseObject.addProperty("platform",
                                        d.getPlatform());
                                responseObject.addProperty("releaseDate",
                                        d.getReleaseDate());
                                responseObject.addProperty("requiredAge",
                                        d.getRequiredAge());
                                responseObject.addProperty("softwareHouse",
                                        d.getSoftwareHouse());
                                responseObject.addProperty("publisher",
                                        d.getPublisher());
                                responseObject.addProperty("quantity",
                                        d.getQuantity());
                                responseObject.addProperty("image", d.getImage());

                            }

                            responseJson.addProperty("oldName", d.getName());
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

                    if (req.getParameter("editable-name") != null
                            && req.getParameter("editable-description") != null
                            && req.getParameter("editable-weight") != null
                            && req.getParameter("editable-size") != null
                            && req.getParameter("editable-categories") != null
                            && req.getParameter("editable-tags") != null) {
                        String name = req.getParameter("editable-name").trim();
                        double price = Double.parseDouble(req.getParameter("editable-price"));
                        String description = req.getParameter("editable-description").trim();
                        String weight = req.getParameter("editable-weight").trim();
                        String size = req.getParameter("editable-size").trim();
                        int quantity = Integer.parseInt(
                                req.getParameter("editable-quantity").trim());
                        String categories = req.getParameter("editable-categories").trim();
                        String tags = req.getParameter("editable-tags").trim();
                        //Part physicalProductImage = req.getPart("editable-imagePath");
                        if (name.length() >= PRODUCT_MIN_LENGTH
                                && name.length() <= PRODUCT_MAX_LENGTH
                                && description.length() >= DESCRIPTION_MIN_LENGTH
                                && description.length() <= DESCRIPTION_MAX_LENGTH) {
                            Part physicalProductImage = req.getPart("editable-imagePath");
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

                            int id = Integer.parseInt(req.getParameter("old-name"));
                            p = ppd.doRetrieveById(id);
                            JsonObject responsePhysicalProduct = new JsonObject();
                            JsonObject responseJson = new JsonObject();
                            if (p == null) {
                                responseJson.addProperty("type", "error");
                                responseJson.addProperty("msg", "Digital Product "
                                        + name + " already exists!");
                                resp.getWriter().println(responseObject);
                                resp.flushBuffer();
                                return;
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
                                        Category c = cd.doRetrieveByName(matcher.group(1).trim());
                                        if (c != null) {
                                            categoriesList.add(c);
                                            categories = categories.substring(
                                                    matcher.group(1).trim().length() + 1);
                                        } else {
                                            throw new RequestParametersException("Error");
                                        }
                                    } else if (!categories.contains(",")) {
                                        Category c = cd.doRetrieveByName(categories);
                                        if (c != null) {
                                            categoriesList.add(c);
                                            categories = "";
                                        } else {
                                            throw new RequestParametersException("Error");
                                        }
                                    }
                                }
                                p.setCategories(categoriesList);

                                matcher = catAndTagRegex.matcher(tags);
                                ArrayList<Tag> tagsList = new ArrayList<>();
                                TagDAO td = new TagDAO();
                                while (!tags.equals("")) {
                                    if (matcher.find()) {
                                        Tag t = td.doRetrieveByName(matcher.group(1).trim());
                                        if (t != null) {
                                            tagsList.add(t);
                                            tags = tags.substring(
                                                    matcher.group(1).trim().length() + 1);
                                        } else {
                                            throw new RequestParametersException("Error");
                                        }
                                    } else if (!tags.contains(",")) {
                                        Tag t = td.doRetrieveByName(tags);
                                        if (t != null) {
                                            tagsList.add(t);
                                            tags = "";
                                        } else {
                                            throw new RequestParametersException("Error");
                                        }
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
                            responseJson.addProperty("oldName", p.getName());
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
                    if (req.getParameter("name") != null
                            && req.getParameter("description") != null
                            && req.getParameter("platform") != null
                            && req.getParameter("releaseDate") != null
                            && req.getParameter("softwareHouse") != null
                            && req.getParameter("publisher") != null
                            && req.getParameter("categories") != null
                            && req.getParameter("tags") != null) {
                        String name = req.getParameter("name").trim();
                        double price = Double.parseDouble(req.getParameter("price"));
                        String description = req.getParameter("description").trim();
                        String platform = req.getParameter("platform").trim();
                        String releaseDate = req.getParameter("releaseDate").trim();
                        int requiredAge = Integer.parseInt(req.getParameter("requiredAge"));
                        String softwareHouse = req.getParameter("softwareHouse").trim();
                        String publisher = req.getParameter("publisher").trim();
                        int quantity = Integer.parseInt(req.getParameter("quantity"));
                        //Part digitalProductImage = req.getPart("image");
                        String categories = req.getParameter("categories").trim();
                        String tags = req.getParameter("tags").trim();
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
                            Part digitalProductImage = req.getPart("image");
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
                                    name.toLowerCase().replace(" ", "-")
                                            + "-DigitalImage.jpg", new ArrayList<>(),
                                    new ArrayList<>(), quantity, platform, releaseDate, requiredAge,
                                    softwareHouse, publisher);

                            Pattern catAndTagRegex = Pattern.compile("(.*?),");
                            Matcher matcher = catAndTagRegex.matcher(categories);
                            ArrayList<Category> categoriesList = new ArrayList<>();
                            CategoryDAO cd = new CategoryDAO();
                            while (!categories.equals("")) {
                                if (matcher.find()) {
                                    Category c = cd.doRetrieveByName(matcher.group(1).trim());
                                    if (c != null) {
                                        categoriesList.add(c);
                                        categories = categories.substring(
                                                matcher.group(1).trim().length() + 1);
                                    } else {
                                        responseObject.addProperty("type", "error");
                                        responseObject.addProperty("msg", "DigitalProduct "
                                                + matcher.group(1).trim() + " cannot be added "
                                                + "because the cagegory doesn't"
                                                + " exists!");
                                        resp.getWriter().println(responseObject);
                                        resp.flushBuffer();
                                        return;
                                    }
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
                                    Tag t = td.doRetrieveByName(matcher.group(1).trim());
                                    if (t != null) {
                                        tagsList.add(t);
                                        tags = tags.substring(
                                                matcher.group(1).trim().length() + 1);
                                    } else {
                                        responseObject.addProperty("type", "error");
                                        responseObject.addProperty("msg",
                                                "DigitalProduct "
                                                + matcher.group(1).trim() + " cannot be added "
                                                        + "because the product doesn't"
                                                + " exists!");
                                        resp.getWriter().println(responseObject);
                                        resp.flushBuffer();
                                        return;
                                    }
                                } else if (!tags.contains(",")) {
                                    tagsList.add(td.doRetrieveByName(tags));
                                    tags = "";
                                }
                            }
                            d.setTags(tagsList);

                            ArrayList<DigitalProduct> dCheck = dpd.doRetrieveByAllFragment(name,
                                    "%",
                                    Double.parseDouble("10000"), "%", "%",
                                    "%", 0, 1000);
                            DigitalProduct dProduct = null;
                            if (dCheck.size() > 0) {
                                dProduct = dCheck.get(0);
                            }

                            if (dProduct != null) {
                                responseObject.addProperty("type", "error");
                                responseObject.addProperty("msg", "DigitalProduct "
                                        + dProduct.getName() + " cannot be added, "
                                        + "because it already"
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
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "The product parameters"
                                + " are null.");
                        resp.getWriter().println(responseObject);
                        resp.flushBuffer();
                        return;
                    }
                } else if (type.equals("physicalProduct")) {

                    if (req.getParameter("name") != null
                            && req.getParameter("description") != null
                            && req.getParameter("weight") != null
                            && req.getParameter("size") != null
                            && req.getParameter("categories") != null
                            && req.getParameter("tags") != null) {
                        String name = req.getParameter("name").trim();
                        double price = Double.parseDouble(req.getParameter("price"));
                        String description = req.getParameter("description").trim();
                        String weight = req.getParameter("weight").trim();
                        String size = req.getParameter("size").trim();
                        int quantity = Integer.parseInt(req.getParameter("quantity"));
                        //Part physicalProductImage = req.getPart("image");
                        String categories = req.getParameter("categories").trim();
                        String tags = req.getParameter("tags").trim();
                        if (name.length() >= PRODUCT_MIN_LENGTH
                                && name.length() <= PRODUCT_MAX_LENGTH
                                && description.length() >= DESCRIPTION_MIN_LENGTH
                                && description.length() <= DESCRIPTION_MAX_LENGTH) {
                            Part physicalProductImage = req.getPart("image");
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
                                    name.toLowerCase().replace(" ", "-")
                                            + "-PhysicalImage.jpg",
                                    new ArrayList<>(), new ArrayList<>(), quantity, size,
                                    Double.parseDouble(weight));

                            Pattern catAndTagRegex = Pattern.compile("(.*?),");
                            Matcher matcher = catAndTagRegex.matcher(categories);
                            ArrayList<Category> categoriesList = new ArrayList<>();
                            CategoryDAO cd = new CategoryDAO();
                            while (!categories.equals("")) {
                                if (matcher.find()) {
                                    Category c = cd.doRetrieveByName(matcher.group(1).trim());
                                    if (c != null) {
                                        categoriesList.add(c);
                                        categories = categories.substring(
                                                matcher.group(1).trim().length() + 1);
                                    } else {
                                        responseObject.addProperty("type", "error");
                                        responseObject.addProperty("msg",
                                                "PhysicalProduct "
                                                + " cannot be added "
                                                + "because the cagegory doesn't"
                                                + " exists!");
                                        resp.getWriter().println(responseObject);
                                        resp.flushBuffer();
                                        return;
                                    }
                                } else if (!categories.contains(",")) {
                                    Category c = cd.doRetrieveByName(categories);
                                    if (c != null) {
                                        categoriesList.add(c);
                                        categories = "";
                                    } else {
                                        responseObject.addProperty("type", "error");
                                        responseObject.addProperty("msg",
                                                "PhysicalProduct "
                                                + " cannot be added "
                                                + "because the category doesn't"
                                                + " exists!");
                                        resp.getWriter().println(responseObject);
                                        resp.flushBuffer();
                                        return;
                                    }
                                }
                            }
                            p.setCategories(categoriesList);

                            matcher = catAndTagRegex.matcher(tags);
                            ArrayList<Tag> tagsList = new ArrayList<>();
                            TagDAO td = new TagDAO();
                            while (!tags.equals("")) {
                                if (matcher.find()) {
                                    Tag t = td.doRetrieveByName(matcher.group(1).trim());
                                    if (t != null) {
                                        tagsList.add(t);
                                        tags = tags.substring(
                                                matcher.group(1).trim().length() + 1);
                                    } else {
                                        responseObject.addProperty("type", "error");
                                        responseObject.addProperty("msg",
                                                "DigitalProduct "
                                                + " cannot be added "
                                                + "because the taag doesn't"
                                                + " exists!");
                                        resp.getWriter().println(responseObject);
                                        resp.flushBuffer();
                                        return;
                                    }
                                } else if (!tags.contains(",")) {
                                    Tag t = td.doRetrieveByName(tags);
                                    if (t != null) {
                                        tagsList.add(t);
                                        tags = "";
                                    } else {
                                        responseObject.addProperty("type", "error");
                                        responseObject.addProperty("msg",
                                                "PhysicalProduct "
                                                        + " cannot be added "
                                                        + "because the tag doesn't"
                                                        + " exists!");
                                        resp.getWriter().println(responseObject);
                                        resp.flushBuffer();
                                        return;
                                    }
                                }
                            }
                            p.setTags(tagsList);

                            ArrayList<PhysicalProduct> pCheck = ppd.doRetrieveByAllFragment(name,
                                    "%", Double.parseDouble("1000"), "%",
                                    "%", 0, 1000);
                            PhysicalProduct pProduct = null;
                            if (pCheck.size() > 0) {
                                pProduct = pCheck.get(0);
                            }

                            if (pProduct != null) {
                                responseObject.addProperty("type", "error");
                                responseObject.addProperty("msg", "Physical Product "
                                        + pProduct.getName() + " cannot be added,"
                                        + " because it already"
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
                            resp.getWriter().println(responseObject);
                            resp.flushBuffer();
                            return;
                        }
                    } else {
                        responseObject.addProperty("type", "error");
                        responseObject.addProperty("msg", "Physical Product "
                                + " cannot be added,"
                                + " because parameters null");
                        resp.getWriter().println(responseObject);
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
            } else {
                throw new RequestParametersException("Operation wrong");
            }
        } else {
            throw new RequestParametersException("Null Parameters");
        }
    }
}
