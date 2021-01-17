package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import controller.shopManagement.GetMoreProductsServlet;
import model.bean.Category;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.Tag;
import model.dao.CategoryDAO;
import model.dao.DigitalProductDAO;
import model.dao.PhysicalProductDAO;
import model.dao.TagDAO;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManageProductServletTest {

    private ManageProductServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static DigitalProductDAO dpd = new DigitalProductDAO();
    private static PhysicalProductDAO ppd = new PhysicalProductDAO();
    private static TagDAO tdao = new TagDAO();
    private static CategoryDAO catdao = new CategoryDAO();
    private static DigitalProduct d1;
    private static DigitalProduct d2;
    private static PhysicalProduct p1;
    private static PhysicalProduct p2;
    private static Tag t1;
    private static Category cat1;

    @BeforeEach
    void setUp() {
        servlet = new ManageProductServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void populate(){
        d1 = new DigitalProduct(1, "Dig1", 24.44,
                "Sono un bel gioco, acquistami!", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12", 10,
                "SoftwareHouse", "publisher");
        d2 = new DigitalProduct(2, "GiocoDigitale2", 12.3,
                "DescrizioneDigitale2", "immagine2", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12", 10,
                "SoftwareHouse", "publisher");
        p1 = new PhysicalProduct(1,"GiocoFisico1", 24.44,
                "Sono un bel gioco, acquistami!", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 12.1);
        p2 = new PhysicalProduct(2,"GiocoFisico2", 12.1,
                "DescrizioneFisico2", "imamgine2", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 12.1);
        t1 = new Tag("TestTag1");
        tdao.doSave(t1);
        d1.addTag(t1);
        d2.addTag(t1);
        p1.addTag(t1);
        p2.addTag(t1);
        cat1 = new Category("TestCategory1","asaasdasd","path");
        catdao.doSave(cat1);
        d1.addCategory(cat1);
        d2.addCategory(cat1);
        p1.addCategory(cat1);
        p2.addCategory(cat1);
        d1 = dpd.doSave(d1);
        d2 = dpd.doSave(d2);
        p1 = ppd.doSave(p1);
        p2 = ppd.doSave(p2);
    }

    @Test
    public void operationNull() throws ServletException, IOException{
        assertThrows(RequestParametersException.class, () -> servlet.doPost(request, response) );
    }

    @Test
    public void operationOkTypeNull() throws ServletException, IOException{
        request.addParameter("manage_product", "knvsfovnfdnv");
        assertThrows(RequestParametersException.class, () -> servlet.doPost(request, response) );
    }

    @Test
    public void  oprationOkTypeNotOk() throws ServletException, IOException{
        request.addParameter("manage_product", "knvsfovnfdnv");
        request.addParameter("product_type", "cdokwdpkp");
        assertThrows(RequestParametersException.class, () -> servlet.doPost(request, response) );
    }

    @Test
    public void  oprationOkTypeOkTooShort() throws ServletException, IOException{
        request.addParameter("manage_product", "remove_product");
        request.addParameter("product_type", "ciao");
        request.addParameter("removeProduct", "ow");
        assertThrows(RequestParametersException.class, () -> servlet.doPost(request, response) );
    }

    @Test
    public void  oprationOkTypeOkTooLong() throws ServletException, IOException{
        request.addParameter("manage_product", "remove_product");
        request.addParameter("product_type", "ciao");
        request.addParameter("removeProduct", "owuhhiidfodjznodnvnzpdnvpzndpv" +
                "ndzpnvpdnvpjnopznpdznvpndv");
        assertThrows(RequestParametersException.class, () -> servlet.doPost(request, response) );
    }

    @Test
    public void  oprationOkTypeNotExist() throws ServletException, IOException {
        request.addParameter("manage_product", "remove_product");
        request.addParameter("product_type", "digitalP");
        request.addParameter("removeProduct", d1.getId()+"");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkRemoveOK() throws ServletException, IOException {
        request.addParameter("manage_product", "remove_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("removeProduct", d1.getId()+"");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkRemoveNotOK() throws ServletException, IOException {
        request.addParameter("manage_product", "remove_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("removeProduct", d1.getId()+"");
        dpd.doDelete(d1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkRemoveOKPHY() throws ServletException, IOException {
        request.addParameter("manage_product", "remove_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("removeProduct", p1.getId()+"");
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkRemoveNotOKPHY() throws ServletException, IOException {
        request.addParameter("manage_product", "remove_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("removeProduct", p1.getId()+"");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOK() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "njnjnkjd");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKDIG() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOk() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-platform", "platform");
        request.addParameter("editable-releaseDate", "2020-12-12");
        request.addParameter("editable-requiredAge", "16");
        request.addParameter("editable-softwareHouse", "soft");
        request.addParameter("editable-publisher", "pubblica");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOk2() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("editable-price", "23.5");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-platform", "platform");
        request.addParameter("editable-releaseDate", "2020-12-12");
        request.addParameter("editable-requiredAge", "16");
        request.addParameter("editable-softwareHouse", "soft");
        request.addParameter("editable-publisher", "pubblica");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOk3() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("editable-name", "nnnnn");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-platform", "platform");
        request.addParameter("editable-releaseDate", "2020-12-12");
        request.addParameter("editable-requiredAge", "16");
        request.addParameter("editable-publisher", "pubblica");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOk4() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("editable-name", "nnnnn");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-platform", "platform");
        request.addParameter("editable-releaseDate", "2020-12-12");
        request.addParameter("editable-requiredAge", "16");
        request.addParameter("editable-softwareHouse", "soft");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOk5() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("editable-name", "nnnnn");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-platform", "platform");
        request.addParameter("editable-releaseDate", "2020-12-12");
        request.addParameter("editable-requiredAge", "16");
        request.addParameter("editable-softwareHouse", "soft");
        request.addParameter("editable-publisher", "pubblica");
        request.addParameter("editable-quantity", "29");
        request.addParameter("editable-categories", "categorie");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOk6() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("editable-name", "nnnnn");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-platform", "platform");
        request.addParameter("editable-requiredAge", "16");
        request.addParameter("editable-softwareHouse", "soft");
        request.addParameter("editable-publisher", "pubblica");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKValueNotValid() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("editable-name", "n");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-platform", "platform");
        request.addParameter("editable-releaseDate", "2020-12-12");
        request.addParameter("editable-requiredAge", "16");
        request.addParameter("editable-softwareHouse", "soft");
        request.addParameter("editable-publisher", "pubblica");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }



    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKPHY() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "physicalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHY() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-weight", "descrizione");
        request.addParameter("editable-size", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-weight", "descrizione");
        request.addParameter("editable-size", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP3() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-name", "nome");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-size", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP4() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-name", "nome");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-weight", "descrizione");
        request.addParameter("editable-size", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP5() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-name", "nome");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-weight", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkAddNotOKOk() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("name", "n");
        request.addParameter("price", "17.0");
        request.addParameter("description", "descrizione");
        request.addParameter("platform", "platform");
        request.addParameter("releaseDate", "2020-12-12");
        request.addParameter("requiredAge", "16");
        request.addParameter("softwareHouse", "soft");
        request.addParameter("publisher", "pubblica");
        request.addParameter("quantity", "19");
        request.addParameter("categories", "categorie");
        request.addParameter("tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkAddNotOKOk9() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("price", "17.0");
        request.addParameter("description", "descrizione");
        request.addParameter("platform", "platform");
        request.addParameter("releaseDate", "2020-12-12");
        request.addParameter("requiredAge", "16");
        request.addParameter("softwareHouse", "soft");
        request.addParameter("publisher", "pubblica");
        request.addParameter("quantity", "19");
        request.addParameter("categories", "categorie");
        request.addParameter("tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkAddNotOKOk10() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("price", "17.0");
        request.addParameter("platform", "platform");
        request.addParameter("releaseDate", "2020-12-12");
        request.addParameter("requiredAge", "16");
        request.addParameter("softwareHouse", "soft");
        request.addParameter("publisher", "pubblica");
        request.addParameter("quantity", "19");
        request.addParameter("categories", "categorie");
        request.addParameter("tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkAddNotOKOk11() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "digitalProduct");
        request.addParameter("name", "nome");
        request.addParameter("price", "17.0");
        request.addParameter("platform", "platform");
        request.addParameter("releaseDate", "2020-12-12");
        request.addParameter("requiredAge", "16");
        request.addParameter("softwareHouse", "soft");
        request.addParameter("publisher", "pubblica");
        request.addParameter("quantity", "19");
        request.addParameter("categories", "categorie");
        request.addParameter("tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkAddNotOKOk2() throws ServletException, IOException {
        request.addParameter("manage_product", "update_product");
        request.addParameter("product_type", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkAddNotOKPHY() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "njdnjnosvn");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP13() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "physicalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP14() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-name", "n");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-size", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }


    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP15() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-size", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP16() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-description", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP17() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-size", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-categories", "categorie");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @Test
    public void  oprationOkTypeOkLongOkUpdateNotOKOkPHYP19() throws ServletException, IOException {
        request.addParameter("manage_product", "add_product");
        request.addParameter("product_type", "physicalProduct");
        request.addParameter("editable-price", "17.0");
        request.addParameter("editable-size", "descrizione");
        request.addParameter("editable-quantity", "19");
        request.addParameter("editable-tags", "digitalProduct");
        ppd.doDelete(p1.getId());
        servlet.doPost(request, response);
        assertTrue( !response.getContentAsString().isEmpty());
    }

    @AfterEach
    public void reInsert(){
        if(dpd.doRetrieveById(d1.getId())==null)
            d1 = dpd.doSave(d1);
        if(dpd.doRetrieveById(d2.getId())==null)
           d2 = dpd.doSave(d2);
        if(ppd.doRetrieveById(p1.getId())==null)
            p1 = ppd.doSave(p1);
        if(ppd.doRetrieveById(p2.getId())==null)
           p2 = ppd.doSave(p2);
    }

    @AfterAll
    static void clear(){
        if(dpd.doRetrieveById(d1.getId())!=null)
            dpd.doDelete(d1.getId());
        if(dpd.doRetrieveById(d2.getId())!=null)
            dpd.doDelete(d2.getId());
        if(ppd.doRetrieveById(p1.getId())!=null)
            ppd.doDelete(p1.getId());
        if(ppd.doRetrieveById(p2.getId())!=null)
            ppd.doDelete(p2.getId());
        tdao.doDelete(t1.getName());
        catdao.doDeleteByName(cat1.getName());
    }

}