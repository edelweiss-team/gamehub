package model.dao;

import model.bean.Category;
import model.dao.TagDAO;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.Tag;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class PhysicalProductDAOTest {

    private final @NotNull PhysicalProductDAO dao = new PhysicalProductDAO();
    private final @NotNull CategoryDAO catdao = new CategoryDAO();
    private final @NotNull TagDAO tagdao = new TagDAO();

    @Test
    void doRetrieveByIdOkAndDoSaveOk() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        Category c2= new Category("CategoriaTest2","descrizione","immagine");
        p.addCategory(c2);
        catdao.doSave(c1);
        catdao.doSave(c2);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        Tag t2 = new Tag ("TagTest2");
        p.addTag(t2);
        tagdao.doSave(t1);
        tagdao.doSave(t2);

        PhysicalProduct dp = dao.doSave(p);

        dp = dao.doRetrieveById(dp.getId());

        if(dp != null) {
            assertTrue(dp.getName().equals(p.getName()) && dp.getPrice() == p.getPrice() && dp.getDescription().equals(p.getDescription()) &&
                    dp.getImage().equals(p.getImage()) && dp.getQuantity() == p.getQuantity() && dp.getSize().equals(p.getSize())
                    && dp.getWeight()==p.getWeight());
            assertIterableEquals(p.getCategories(), dp.getCategories());
            assertIterableEquals(p.getTags(), dp.getTags());
        } else {
            fail();
        }

        dao.doDelete(dp.getId());
        catdao.doDeleteByName(c1.getName());
        catdao.doDeleteByName(c2.getName());
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());
    }


    @Test
    void doRetrieveByIdNotExixts(){
        assertNull(dao.doRetrieveById(10000));
    }

    @Test
    void doRetrieveAllXOne() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        Category c2= new Category("CategoriaTest2","descrizione","immagine");
        p.addCategory(c2);
        catdao.doSave(c1);
        catdao.doSave(c2);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        Tag t2 = new Tag ("TagTest2");
        p.addTag(t2);
        tagdao.doSave(t1);
        tagdao.doSave(t2);

        PhysicalProduct dp = dao.doSave(p);

        ArrayList<PhysicalProduct> listp = dao.doRetrieveAll(0,100);
        assertTrue(listp.contains(dp));

        dao.doDelete(dp.getId());
        catdao.doDeleteByName(c1.getName());
        catdao.doDeleteByName(c2.getName());
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());

    }

    @Test
    void doRetrieveAllXAll() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        Category c2= new Category("CategoriaTest2","descrizione","immagine");
        p.addCategory(c2);
        catdao.doSave(c1);
        catdao.doSave(c2);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        Tag t2 = new Tag ("TagTest2");
        p.addTag(t2);
        tagdao.doSave(t1);
        tagdao.doSave(t2);

        PhysicalProduct p2 = new PhysicalProduct(7, "NuovoProdottoTesting2", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        p2.addCategory(c1);
        p2.addTag(t2);

        p = dao.doSave(p);
        p2 = dao.doSave(p2);

        ArrayList<PhysicalProduct> listp = dao.doRetrieveAll(0,100);
        assertTrue(listp.contains(p));
        assertTrue(listp.contains(p2));

        dao.doDelete(p.getId());
        dao.doDelete(p2.getId());
        catdao.doDeleteByName(c1.getName());
        catdao.doDeleteByName(c2.getName());
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());

    }

    @Test
    void doRetrieveALLXZero() {
        ArrayList<PhysicalProduct> list = dao.doRetrieveAll(0,0);
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveAllNotvalid() {
        assertThrows(RuntimeException.class, () -> {
            dao.doRetrieveAll(-3,-3);
        });
    }

    @Test
    void doSaveOneCategoryOneTag() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        PhysicalProduct dp = dao.doSave(p);

        dp = dao.doRetrieveById(dp.getId());

        if(dp != null) {
            assertTrue(dp.getName().equals(p.getName()) && dp.getPrice() == p.getPrice() && dp.getDescription().equals(p.getDescription()) &&
                    dp.getImage().equals(p.getImage()) && dp.getQuantity() == p.getQuantity() && dp.getSize().equals(p.getSize())
                    && dp.getWeight()==p.getWeight());
            assertIterableEquals(p.getCategories(), dp.getCategories());
            assertIterableEquals(p.getTags(), dp.getTags());
        } else {
            fail();
        }

        dao.doDelete(dp.getId());
        catdao.doDeleteByName(c1.getName());
        tagdao.doDelete(t1.getName());
    }


    @Test
    void doSaveNoCategoryNoTag() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);


        PhysicalProduct dp = dao.doSave(p);

        dp = dao.doRetrieveById(dp.getId());

        if(dp != null) {
            assertTrue(dp.getName().equals(p.getName()) && dp.getPrice() == p.getPrice() && dp.getDescription().equals(p.getDescription()) &&
                    dp.getImage().equals(p.getImage()) && dp.getQuantity() == p.getQuantity() && dp.getSize().equals(p.getSize())
                    && dp.getWeight()==p.getWeight());
            assertIterableEquals(p.getCategories(), dp.getCategories());
            assertIterableEquals(p.getTags(), dp.getTags());
        } else {
            fail();
        }

        dao.doDelete(dp.getId());
    }

    @Test
    void doSaveNotValid(){
        assertThrows(RuntimeException.class, () -> {
            PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                    "testing", "imagetest", null , new ArrayList<Tag>(),
                    200, "sizetest", 20.05);
            dao.doSave(p);
        });
    }

    @Test
    void doRetrieveAllByCategoryOneProduct() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        PhysicalProduct dp = dao.doSave(p);

        ArrayList<PhysicalProduct> dg;
        dg = dao.doRetrieveAllByCategory("CategoriaTest1",0,100);

        assertTrue(dg.get(0).equals(dp));

        dao.doDelete(dp.getId());
        catdao.doDeleteByName(c1.getName());

    }

    @Test
    void doRetrieveAllByCategoryMoreProduct() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        PhysicalProduct p2 = new PhysicalProduct(7, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest2", 20.05);

        p2.addCategory(c1);

        PhysicalProduct dp = dao.doSave(p);
        PhysicalProduct dp2 = dao.doSave(p2);

        ArrayList<PhysicalProduct> dg;
        dg = dao.doRetrieveAllByCategory("CategoriaTest1",0,100);

        assertTrue(dg.contains(dp) && dg.contains(dp2));

        dao.doDelete(dp.getId());
        dao.doDelete(dp2.getId());
        catdao.doDeleteByName(c1.getName());

    }

    @Test
    void doRetrieveAllByCategoryNoProduct() {
        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        catdao.doSave(c1);

        ArrayList<PhysicalProduct> dg;
        dg = dao.doRetrieveAllByCategory("CategoriaTest1",0,100);

        assertTrue(dg.size()==0);

        catdao.doDeleteByName(c1.getName());
    }


    @Test
    void doRetrieveByCategoryNotValid() {
        assertThrows(RuntimeException.class, () -> {
            ArrayList<PhysicalProduct> dg;
            dg = dao.doRetrieveAllByCategory("CategoriaTest1",-9,100);
        });
    }

    @Test
    void doUpdateMoreCategoryMoreTag() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        Category c2= new Category("CategoriaTest2","descrizione","immagine");
        p.addCategory(c2);
        catdao.doSave(c1);
        catdao.doSave(c2);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        Tag t2 = new Tag ("TagTest2");
        p.addTag(t2);
        tagdao.doSave(t1);
        tagdao.doSave(t2);

        p = dao.doSave(p);

        p.setQuantity(500);

        dao.doUpdate(p);

        PhysicalProduct dp = dao.doRetrieveById(p.getId());

        assertTrue(dp.getQuantity()==p.getQuantity());

        dao.doDelete(p.getId());
        catdao.doDeleteByName(c1.getName());
        catdao.doDeleteByName(c2.getName());
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());

    }


    @Test
    void doUpdateOneCategoryOneTag() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        p = dao.doSave(p);
        p.setQuantity(500);

        dao.doUpdate(p);
        PhysicalProduct dp = dao.doRetrieveById(p.getId());

        assertTrue(dp.getQuantity()==p.getQuantity());

        dao.doDelete(p.getId());
        catdao.doDeleteByName(c1.getName());
        tagdao.doDelete(t1.getName());
    }


    @Test
    void doUpdateNoCategoryNoTag() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);


        p = dao.doSave(p);
        p.setQuantity(500);

        dao.doUpdate(p);
        PhysicalProduct dp = dao.doRetrieveById(p.getId());

        assertEquals(dp.getQuantity(), p.getQuantity());

        dao.doDelete(p.getId());
    }


    @Test
    void doUpdateProductNotValid() {
        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);
        assertThrows(RuntimeException.class, () -> {
            dao.doSave(p);
            p.setName(null);
            dao.doUpdate(p);
        });
        dao.doDelete(p.getId());
    }

    @Test
    void doRetrieveByAllFragmentxNone() {
        ArrayList<PhysicalProduct> list = dao.doRetrieveByAllFragment("nomeeee", "xkxkxkxk", 0.0, "xkxkxkxkx",
                "xkxkxkxk", 0 , 0);
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveByAllFragmentxOne() {

        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        dao.doSave(p);

        ArrayList<PhysicalProduct> list = dao.doRetrieveByAllFragment("NuovoProdottoTesting", "", 20000.0, "",
                "",0 , 100);

        assertTrue(list.size()==1);

        dao.doDelete(p.getId());
        catdao.doDeleteByName(c1.getName());
        tagdao.doDelete(t1.getName());
    }

    @Test
    void doRetrieveByAllFragmentxAll() {

        PhysicalProduct p = new PhysicalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest", 20.05);

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        PhysicalProduct p2 = new PhysicalProduct(7, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "sizetest2", 20.05);
        p2.addCategory(c1);
        p2.addTag(t1);

        p = dao.doSave(p);
        p2 = dao.doSave(p2);


        ArrayList<PhysicalProduct> list = dao.doRetrieveByAllFragment("NuovoProdottoTesting", "", 20000.0, "",
                "",0 , 100);

        assertTrue(list.size()==2);

        dao.doDelete(p.getId());
        dao.doDelete(p2.getId());
        catdao.doDeleteByName(c1.getName());
        tagdao.doDelete(t1.getName());
    }

    @Test
    void doRetrieveByAllFragmentNotValid() {
        assertThrows(RuntimeException.class, () -> {
            ArrayList<PhysicalProduct> list = dao.doRetrieveByAllFragment("nomeeee", "xkxkxkxk", 0.0, "xkxkxkxkx",
                    "xkxkxkxk", -3 , 0);
        });
    }


}