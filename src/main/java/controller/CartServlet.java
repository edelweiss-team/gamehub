package controller;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.text.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import model.bean.*;
import model.dao.CartDAO;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;

@WebServlet(urlPatterns = {
        "/add-cart", "/remove-cart", "/show-cart"
})
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd;
        String address;
        int id; //magari sostituire con courseName
        CartDAO cd = new CartDAO();
        PhysicalProductDAO ppd = new PhysicalProductDAO();
        DigitalProductDAO dpd = new DigitalProductDAO();
        Product product = null;
        Cart cart;
        JsonObject responseJson = new JsonObject();
        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");
        cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            if (loggedUser != null) {
                cart = cd.doRetrieveByUsername(loggedUser.getUsername());
            }
            if (cart == null) {
                cart = new Cart();
                if (loggedUser != null) {
                    cart.setUser(loggedUser);
                    cd.doSaveOrUpdate(cart);
                }
            }
            synchronized (session) {
                session.setAttribute("cart", cart);
            }
        }

        if (req.getParameter("addCart") != null) {
            id = Integer.parseInt(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String type = req.getParameter("productType");

            if (type == null) {
                throw new RequestParametersException("Request is missing 'productType' parameter.");
            } else if (type.equalsIgnoreCase("physical")) {
                product = ppd.doRetrieveById(id);
            } else if (type.equalsIgnoreCase("digital")) {
                product = dpd.doRetrieveById(id);
            } else {
                throw new RequestParametersException("Request has wrong 'productType' parameter.");
            }

            if (product == null) {
                throw new RequestParametersException("The product with the"
                         + " requested id doesn't exist.");
            }

            if (quantity + cart.getQuantitySingleProduct(id, product.getClass())
                    > Objects.requireNonNull(product).getQuantity()) {
                throw new RequestParametersException("Max quantity ("
                        + product.getQuantity() + ") exceeded. Passed " + quantity);
            } else {
                synchronized (session) {
                    cart.addProduct(product, quantity);
                }
                if (loggedUser != null) {
                    cd.doAddProduct(cart, product);
                }
                responseJson.addProperty("type", "success");
                responseJson.addProperty("message",
                        "item added successfully to the cart!"); //messaggio di successo
                responseJson.addProperty("newTotalPrice",
                        (new DecimalFormat("#.##")).format(cart.getTotalPrice())
                                .replace(",", ".")); //nuovo prezzo
                responseJson.addProperty(
                        "newQuantity",
                        cart.getQuantitySingleProduct(product.getId(), product.getClass())
                );
                responseJson.addProperty(
                        "newProductPrice",
                        (new DecimalFormat("#.##")).format(
                                product.getPrice()
                                        * cart.getQuantitySingleProduct(
                                                product.getId(),
                                                product.getClass()
                                        )
                        ).replaceAll(",", ".")
                );
                resp.getWriter().println(responseJson.toString());
                resp.flushBuffer();
            }
        } else if (req.getParameter("removeCart") != null) {
            id = Integer.parseInt(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String type = req.getParameter("productType");

            if (type == null) {
                throw new RequestParametersException("Request is missing 'productType' parameter.");
            } else if (type.equalsIgnoreCase("physical")) {
                product = ppd.doRetrieveById(id);
            } else if (type.equalsIgnoreCase("digital")) {
                product = dpd.doRetrieveById(id);
            } else {
                throw new RequestParametersException("Request has wrong 'productType' parameter.");
            }
            if (product == null) {
                throw new RequestParametersException("The product with the requested"
                         + " id doesn't exist.");
            }

            String msg = "item removed successfully from the cart!";
            String typeMsg = "success";
            synchronized (session) {
                try {
                    cart.removeProduct(product, quantity);
                    if (loggedUser != null) {
                        cd.doRemoveProduct(cart, product);
                    }
                } catch (IllegalArgumentException e) {
                    msg = "item could not be removed from the cart!";
                    typeMsg = "error";
                }
            }
            responseJson.addProperty("type", typeMsg);
            responseJson.addProperty("message", msg); //messaggio
            responseJson.addProperty("newTotalPrice",
                    (new DecimalFormat("#.##")).format(cart.getTotalPrice())
                            .replace(",", ".")); //nuovo prezzo
            responseJson.addProperty(
                    "newQuantity",
                    cart.getQuantitySingleProduct(product.getId(), product.getClass())
            );
            responseJson.addProperty(
                    "newProductPrice",
                    (new DecimalFormat("#.##")).format(
                            product.getPrice()
                                    * cart.getQuantitySingleProduct(
                                    product.getId(),
                                    product.getClass()
                            )
                    ).replaceAll(",", ".")
            );
            resp.getWriter().println(responseJson.toString());
            resp.flushBuffer();
        } else if (req.getParameter("showCart") != null) {
            address = "/WEB-INF/view/cart.jsp";
            req.setAttribute("sectionName", "cart");
            rd = req.getRequestDispatcher(address);
            rd.forward(req, resp);
        } else {
            throw new RequestParametersException("Error: wrong parameters "
                     + "passed to the cart servlet.");
        }
    }
}
