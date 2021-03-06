package model.dao;

import model.bean.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DigitalProductDAOTest {

    private final @NotNull DigitalProductDAO dao = new DigitalProductDAO();
    private final @NotNull CategoryDAO catdao = new CategoryDAO();
    private final @NotNull TagDAO tagdao = new TagDAO();

    @Test
    void doRetrieveByIdOkAndDoSaveOk() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

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

        DigitalProduct dp = dao.doSave(p);

        dp = dao.doRetrieveById(dp.getId());

        if(dp != null) {
            assertTrue(dp.getName().equals(p.getName()) && dp.getPrice() == p.getPrice() && dp.getDescription().equals(p.getDescription()) &&
                    dp.getImage().equals(p.getImage()) && dp.getQuantity() == p.getQuantity() &&
                    dp.getPlatform().equals(p.getPlatform()) && dp.getReleaseDate().equals(p.getReleaseDate())
                    && dp.getRequiredAge() == p.getRequiredAge() && dp.getSoftwareHouse().equals(p.getSoftwareHouse())
                    && dp.getPublisher().equals(p.getPublisher()));
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

/*
    Non si riesce ad entrare nel catch della doRetrieveById
    @Test
    void doRetrieveByIdNotValid(){
        assertThrows(RuntimeException.class, () -> {
            dao.doRetrieveById(-6);
        });
    }
 */

    @Test
    void doRetrieveAllXOne() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

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

        DigitalProduct dp = dao.doSave(p);

        ArrayList<DigitalProduct> listp = dao.doRetrieveAll(0,100);
        assertTrue(listp.contains(dp));

        dao.doDelete(dp.getId());
        catdao.doDeleteByName(c1.getName());
        catdao.doDeleteByName(c2.getName());
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());

    }

    @Test
    void doRetrieveAllXAll() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

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

        DigitalProduct p2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing2", "imagetesting2", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox2", "1999-02-05", 18, "testing2", "testingpub2");
        p2.addCategory(c1);
        p2.addTag(t2);

        p = dao.doSave(p);
        p2 = dao.doSave(p2);

        ArrayList<DigitalProduct> listp = dao.doRetrieveAll(0,100);
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
        ArrayList<DigitalProduct> list = dao.doRetrieveAll(0,0);
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
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        DigitalProduct dp = dao.doSave(p);

        dp = dao.doRetrieveById(dp.getId());

        if(dp != null) {
            assertTrue(dp.getName().equals(p.getName()) && dp.getPrice() == p.getPrice() && dp.getDescription().equals(p.getDescription()) &&
                    dp.getImage().equals(p.getImage()) && dp.getQuantity() == p.getQuantity() &&
                    dp.getPlatform().equals(p.getPlatform()) && dp.getReleaseDate().equals(p.getReleaseDate())
                    && dp.getRequiredAge() == p.getRequiredAge() && dp.getSoftwareHouse().equals(p.getSoftwareHouse())
                    && dp.getPublisher().equals(p.getPublisher()));
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
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");


        DigitalProduct dp = dao.doSave(p);

        dp = dao.doRetrieveById(dp.getId());

        if(dp != null) {
            assertTrue(dp.getName().equals(p.getName()) && dp.getPrice() == p.getPrice() && dp.getDescription().equals(p.getDescription()) &&
                    dp.getImage().equals(p.getImage()) && dp.getQuantity() == p.getQuantity() &&
                    dp.getPlatform().equals(p.getPlatform()) && dp.getReleaseDate().equals(p.getReleaseDate())
                    && dp.getRequiredAge() == p.getRequiredAge() && dp.getSoftwareHouse().equals(p.getSoftwareHouse())
                    && dp.getPublisher().equals(p.getPublisher()));
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
            DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                    "xbox", "1999-05-052", 18, "testing", "testingpub");
            dao.doSave(p);
        });
    }

    /*
    Non si riesce ad entrare nel catch del doDelete
        @Test
        void doDeleteNotValid() {
            assertThrows(RuntimeException.class, () -> {
                dao.doDelete(999999999);
            });
        }
    */

    @Test
    void doRetrieveAllByCategoryOneProduct() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        DigitalProduct dp = dao.doSave(p);

        ArrayList<DigitalProduct> dg;
        dg = dao.doRetrieveAllByCategory("CategoriaTest1",0,100);

        assertTrue(dg.get(0).equals(dp));

        dao.doDelete(dp.getId());
        catdao.doDeleteByName(c1.getName());

    }

    @Test
    void doRetrieveAllByCategoryMoreProduct() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        DigitalProduct p2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing2", "imagetesting2", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox2", "1999-02-05", 18, "testing2", "testingpub2");
        p2.addCategory(c1);

        DigitalProduct dp = dao.doSave(p);
        DigitalProduct dp2 = dao.doSave(p2);

        ArrayList<DigitalProduct> dg;
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

        ArrayList<DigitalProduct> dg;
        dg = dao.doRetrieveAllByCategory("CategoriaTest1",0,100);

        assertTrue(dg.size()==0);

        catdao.doDeleteByName(c1.getName());
    }


    @Test
    void doRetrieveByCategoryNotValid() {
        assertThrows(RuntimeException.class, () -> {
            ArrayList<DigitalProduct> dg;
            dg = dao.doRetrieveAllByCategory("CategoriaTest1",-9,100);
        });
    }

    @Test
    void doUpdateMoreCategoryMoreTag() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

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

        DigitalProduct dp = dao.doRetrieveById(p.getId());

        assertTrue(dp.getQuantity()==p.getQuantity());

        dao.doDelete(p.getId());
        catdao.doDeleteByName(c1.getName());
        catdao.doDeleteByName(c2.getName());
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());

    }

    @Test
    void doUpdateOneCategoryOneTag() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        p = dao.doSave(p);
        p.setQuantity(500);

        dao.doUpdate(p);
        DigitalProduct dp = dao.doRetrieveById(p.getId());

        assertTrue(dp.getQuantity()==p.getQuantity());

        dao.doDelete(p.getId());
        catdao.doDeleteByName(c1.getName());
        tagdao.doDelete(t1.getName());
    }

    @Test
    void doUpdateNoCategoryNoTag() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");


        p = dao.doSave(p);
        p.setQuantity(500);

        dao.doUpdate(p);
        DigitalProduct dp = dao.doRetrieveById(p.getId());

        assertEquals(dp.getQuantity(), p.getQuantity());

        dao.doDelete(p.getId());
    }

    @Test
    void doUpdateProductNotValid() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56,
                "testing", "imagetesting", new ArrayList<>() , new ArrayList<>(), 330,
                "xbox", "1999-05-02", 18, "testing",
                "testingpub");
        assertThrows(RuntimeException.class, () -> {
            dao.doSave(p);
            p.setReleaseDate("1999-05-0522");
            dao.doUpdate(p);
        });
        dao.doDelete(p.getId());
    }

    @Test
    void doRetrieveByAllFragmentxNone() {
        ArrayList<DigitalProduct> list = dao.doRetrieveByAllFragment("nomeeee", "xkxkxkxk", 0.0, "xkxkxkxkx",
        "xkxkxkxk", "xkxkxkxk", 0 , 0);
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveByAllFragmentxOne() {

        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        dao.doSave(p);

        ArrayList<DigitalProduct> list = dao.doRetrieveByAllFragment("NuovoProdottoTesting", "", 20000.0, "",
                "", "", 0 , 100);

        assertTrue(list.size()==1);

        dao.doDelete(p.getId());
        catdao.doDeleteByName(c1.getName());
        tagdao.doDelete(t1.getName());
    }

    @Test
    void doRetrieveByAllFragmentxAll() {

        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Category c1= new Category("CategoriaTest1","descrizione","immagine");
        p.addCategory(c1);
        catdao.doSave(c1);

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        DigitalProduct p2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing2", "imagetesting2", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox2", "1999-02-05", 18, "testing2", "testingpub2");
        p2.addCategory(c1);
        p2.addTag(t1);

        p = dao.doSave(p);
        p2 = dao.doSave(p2);


        ArrayList<DigitalProduct> list = dao.doRetrieveByAllFragment("NuovoProdottoTesting", "", 20000.0, "",
                "", "", 0 , 100);

        assertTrue(list.size()==2);

        dao.doDelete(p.getId());
        dao.doDelete(p2.getId());
        catdao.doDeleteByName(c1.getName());
        tagdao.doDelete(t1.getName());
    }

    @Test
    void doRetrieveByAllFragmentNotValid() {
        assertThrows(RuntimeException.class, () -> {
            ArrayList<DigitalProduct> list = dao.doRetrieveByAllFragment("nomeeee", "xkxkxkxk", 0.0, "xkxkxkxkx",
                    "xkxkxkxk", "xkxkxkxk", -3 , 0);
        });
    }

    @Test
    void doRetrieveAllByTagOneProduct() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        DigitalProduct dp = dao.doSave(p);

        ArrayList<DigitalProduct> dg;
        dg = dao.doRetrieveAllByTag("TagTest1",0,100);

        assertTrue(dg.get(0).equals(dp));

        dao.doDelete(dp.getId());
        tagdao.doDelete(t1.getName());

    }

    @Test
    void doRetrieveAllByTagMoreProduct() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);

        DigitalProduct p2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing2", "imagetesting2", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox2", "1999-02-05", 18, "testing2", "testingpub2");
        p2.addTag(t1);

        DigitalProduct dp = dao.doSave(p);
        DigitalProduct dp2 = dao.doSave(p2);

        ArrayList<DigitalProduct> dg;
        dg = dao.doRetrieveAllByTag("TagTest1",0,100);

        assertTrue(dg.contains(dp) && dg.contains(dp2));

        dao.doDelete(dp.getId());
        dao.doDelete(dp2.getId());
        tagdao.doDelete(t1.getName());

    }

    @Test
    void doRetrieveAllByTagNoProduct() {
        Tag t1= new Tag("TagTest1");
        tagdao.doSave(t1);

        ArrayList<DigitalProduct> dg;
        dg = dao.doRetrieveAllByTag("TagTest1",0,100);

        assertTrue(dg.size()==0);

        tagdao.doDelete(t1.getName());
    }

    @Test
    void doRetrieveByTagNotValid() {
        assertThrows(RuntimeException.class, () -> {
            ArrayList<DigitalProduct> dg;
            dg = dao.doRetrieveAllByTag("TagTest1",-9,100);
        });
    }

    @Test
    void doRetrieveAllByTagsListEmpty() {
        assertThrows(RuntimeException.class, () -> {
            ArrayList<Tag> tags = new ArrayList<>();
            @NotNull ArrayList<DigitalProduct> dg = dao.doRetrieveAllByTags(tags, 0, 100);
        });
    }

    @Test
    void doRetrieveAllByTagsTwoTags() {
        Tag t1= new Tag("TagTestAncora");
        Tag t2 = new Tag ("TagTestAncora2");
        tagdao.doSave(t1);
        tagdao.doSave(t2);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(t1);
        tags.add(t2);
        @NotNull ArrayList<DigitalProduct> dg = dao.doRetrieveAllByTags(tags, 0, 0);
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());
        assertTrue(dg.size()==0);
    }

    @Test
    void doRetrieveAllByTagsThreeTags() {
        Tag t1= new Tag("TagTestAncora");
        Tag t2 = new Tag ("TagTestAncora2");
        Tag t3 = new Tag ("TagTestAncora3");
        tagdao.doSave(t1);
        tagdao.doSave(t2);
        tagdao.doSave(t3);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(t1);
        tags.add(t2);
        @NotNull ArrayList<DigitalProduct> dg = dao.doRetrieveAllByTags(tags, 0, 0);
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());
        tagdao.doDelete(t3.getName());
        assertTrue(dg.size()==0);
    }

    @Test
    void doRetrieveOneProduct() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);
        Tag t2 = new Tag ("TagTest2");
        Tag t3 = new Tag ("TagTest3");
        tagdao.doSave(t2);
        tagdao.doSave(t3);
        DigitalProduct dp = dao.doSave(p);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(t1);
        @NotNull ArrayList<DigitalProduct> dg = dao.doRetrieveAllByTags(tags, 0, 100);
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());
        tagdao.doDelete(t3.getName());
        dao.doDelete(dp.getId());
        assertTrue(dg.size()==1);
    }


    @Test
    void doRetrieveMoreProduct() {
        DigitalProduct p = new DigitalProduct(7, "NuovoProdottoTesting", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");

        Tag t1= new Tag("TagTest1");
        p.addTag(t1);
        tagdao.doSave(t1);
        Tag t2 = new Tag ("TagTest2");
        Tag t3 = new Tag ("TagTest3");
        tagdao.doSave(t2);
        tagdao.doSave(t3);
        p.addTag(t2);
        DigitalProduct p2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing2", "imagetesting2", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox2", "1999-02-05", 18, "testing2", "testingpub2");
        p2.addTag(t1);

        DigitalProduct dp = dao.doSave(p);
        DigitalProduct dp2 = dao.doSave(p2);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(t1);
        tags.add(t2);
        @NotNull ArrayList<DigitalProduct> dg = dao.doRetrieveAllByTags(tags, 0, 100);
        tagdao.doDelete(t1.getName());
        tagdao.doDelete(t2.getName());
        tagdao.doDelete(t3.getName());
        dao.doDelete(dp.getId());
        dao.doDelete(dp2.getId());
        assertTrue(dg.size()==2);
    }



}