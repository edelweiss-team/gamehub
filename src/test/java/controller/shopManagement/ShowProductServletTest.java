package controller.shopManagement;

import controller.RequestParametersException;
import controller.shopManagement.ShowProductServlet;
import model.bean.Category;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.Tag;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;
import org.apache.log4j.BasicConfigurator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ShowProductServletTest {
    private @NotNull ShowProductServlet servlet;
    private @NotNull MockHttpServletRequest request;
    private @NotNull MockHttpServletResponse response;
    private @Nullable MockHttpSession session;
    private static @NotNull final DigitalProductDAO dpdao = new DigitalProductDAO();
    private static @NotNull final PhysicalProductDAO ppdao = new PhysicalProductDAO();
    private static @NotNull DigitalProduct dProd;
    private static @NotNull PhysicalProduct pProd;
    private static int dProdId;
    private static int pProdId;

    @BeforeAll
    static public void init() {
        dProd = new DigitalProduct(
                1,
                "nomeProdotto",
                2,
                "descrizione",
                "imgPath",
                new ArrayList<Category>(),
                new ArrayList<Tag>(),
                1,
                "platformName",
                "11/11/11",
                18,
                "softwareHouse",
                "publisher");
        dProdId = (dpdao.doSave(dProd)).getId();

        pProd = new PhysicalProduct(
                1,
                "nomeProdotto",
                2,
                "descrizione",
                "imgPath",
                new ArrayList<Category>(),
                new ArrayList<Tag>(),
                1,
                "3x3x3",
                1
        );
        pProdId = (ppdao.doSave(pProd)).getId();
    }

    @AfterAll
    static public void terminate() {
        dpdao.doDelete(dProd.getId());
        ppdao.doDelete(pProd.getId());
    }

    @BeforeEach
    public void setUp() {
        servlet = new ShowProductServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setSession(session);
        BasicConfigurator.configure();
    }

    @Test
    public void productTypeNotRecognized() throws ServletException, IOException {
        request.addParameter("productId", "1");
        request.addParameter("productType", "unknownProductType");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void productTypeNotNull() throws ServletException, IOException {
        request.addParameter("productId", "1");
        request.addParameter("productType", (String) null);
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void productDigitalOk() throws ServletException, IOException {
        request.addParameter("productId", "" + dProdId);
        request.addParameter("productType", "digital");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/product.jsp" , response.getForwardedUrl());
    }

    @Test
    public void productDigitalNull() throws ServletException, IOException {
        request.addParameter("productId", "10293847");
        request.addParameter("productType", "digital");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void productPhysicalOk() throws ServletException, IOException {
        request.addParameter("productId", "" + pProdId);
        request.addParameter("productType", "physical");
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/product.jsp" , response.getForwardedUrl());
    }

    @Test
    public void productPhysicalNull() throws ServletException, IOException {
        request.addParameter("productId", "10293847");
        request.addParameter("productType", "physical");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }
}