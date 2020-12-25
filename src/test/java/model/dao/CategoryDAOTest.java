package model.dao;

import model.bean.Category;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOTest {

    private final @NotNull CategoryDAO cd=new CategoryDAO();

    @Test
    void doRetrieveByNameOk() {
        Category cat1= new Category("Sparatutto","sparatutto per tutti","path");
        cd.doSave(cat1);
        Category cat2=cd.doRetrieveByName("Sparatutto");
        if(cat2 != null)
            assertTrue(cat1.getName().equals(cat2.getName())
                    && cat1.getDescription().equals(cat2.getDescription())
                    && cat1.getImage().equals(cat2.getImage()));
        else
            fail();
        cd.doDeleteByName(cat1.getName());
    }

    @Test
    void doRetrieveByNameNotOkNull() {
        assertNull(cd.doRetrieveByName("'limit"));

    }

    @Test
    void doRetrieveByNameNotOk() {
        assertNull(cd.doRetrieveByName("/'1"));

    }

    @Test
    void doDeleteByNameOk() {
        Category cat1 = new Category("CategoriaTest","descrizione per il testing","path");
        cd.doSave(cat1);
        cd.doDeleteByName(cat1.getName());
        assertNull(cd.doRetrieveByName(cat1.getName()));
    }

    @Test
    void doDeleteByNameNotOkNull() {
        assertThrows(RuntimeException.class,()->cd.doDeleteByName("'limit"));
    }

    @Test
    void doDeleteByNameNotOk() {
        assertThrows(RuntimeException.class,()->cd.doDeleteByName("/'1"));
    }

    @Test
    void doSaveOk() {
        Category cat1=new Category("Avventura","Avventura per tutti","path");
        cd.doSave(cat1);
        Category cat2=cd.doRetrieveByName("Avventura");
        if(cat2 != null)
            assertTrue(cat1.getName().equals(cat2.getName()) && cat1.getDescription().equals(cat2.getDescription())
                                && cat1.getImage().equals(cat2.getImage()));
        else
            fail();
        cd.doDeleteByName(cat1.getName());
    }

    @Test
    void doSaveNotOk() {
        Category cat1= new Category("TestxSaveNotOk","test di prova","path");
        cd.doSave(cat1);
        assertThrows(RuntimeException.class,()->cd.doSave(cat1));
        cd.doDeleteByName(cat1.getName());
    }

    @Test
    void doUpdateByNameOk() {
        Category cat1 = new Category("TestxUpdateOk1","test di prova1","path1");
        cd.doSave(cat1);
        String oldName = cat1.getName();
        String oldDesc = cat1.getDescription();
        String oldImage = cat1.getImage();
        cat1.setName("TestxUpdateOk2");
        cat1.setDescription("test di prova2");
        cat1.setImage("path2");
        cd.doUpdateByName(cat1, oldName);
        cat1=cd.doRetrieveByName(cat1.getName());
        if(cat1 != null)
            assertFalse(cat1.getName().equals(oldName) && cat1.getDescription().equals(oldDesc) &&
                                cat1.getImage().equals(oldImage));
        else
            fail();
        cd.doDeleteByName(cat1.getName());
    }

    @Test
    void doUpdateByNameNotOk() {
        Category cat1 = new Category("TestxUpdateNotOk","test di prova1","path1");
        assertThrows(RuntimeException.class,()->cd.doUpdateByName(cat1,"/'1"));
    }

    @Test
    void doUpdateByNameNotOkNull() {
        Category cat1 = new Category("TestxUpdateNotOkNull","test di prova1","path1");
        assertThrows(RuntimeException.class,()->cd.doUpdateByName(cat1,"'limit"));

    }

    @Test
    void doRetrieveAllxNone() {
        try {
            ArrayList<Category> list =cd.doRetrieveAll();
            assertTrue(list.isEmpty());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void doRetrieveAllxOne() {
        Category cat1=new Category("TestxRetrieveAllxOne","test di prova1","path1");
        cd.doSave(cat1);
        try {
            ArrayList<Category> list=cd.doRetrieveAll();
            assertEquals(cat1, list.get(0));
            cd.doDeleteByName(cat1.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void doRetrieveAllxAll() {
        Category cat1=new Category("TestxRetrieveAllxOne1","test di prova1","path1");
        Category cat2=new Category("TestxRetrieveAllxOne2","test di prova1","path1");
        cd.doSave(cat1);
        cd.doSave(cat2);
        try {
            ArrayList<Category> list=cd.doRetrieveAll();
            assertEquals(cat1, list.get(0));
            assertEquals(cat1, list.get(1));
            cd.doDeleteByName(cat1.getName());
            cd.doDeleteByName(cat2.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}