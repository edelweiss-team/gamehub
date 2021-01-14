package model.dao;

import model.bean.Category;
import model.bean.Tag;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TagDAOTest {

    private final @NotNull TagDAO tagdao = new TagDAO();

    @Test
    void doSaveOk() {
        Tag tag1 = new Tag("Tag");
        tagdao.doSave(tag1);
        Tag tag2 = tagdao.doRetrieveByName("Tag");
        if(tag2 != null)
            assertTrue(tag1.getName().equals(tag2.getName()));

        else
            fail();
        tagdao.doDelete(tag1.getName());
    }

    @Test
    void doSaveNotOk() {
        Tag tag1 = new Tag("Tag");
        tagdao.doSave(tag1);
        assertThrows(RuntimeException.class,()->tagdao.doSave(tag1));
        tagdao.doDelete(tag1.getName());
    }

    @Test
    void doUpdateOk() {
        Tag t = new Tag("Tag1");
        TagDAO td = new TagDAO();
        td.doSave(t);
        String name = t.getName();
        t.setName("Tag2");
        td.doUpdate(t, name);
        t = td.doRetrieveByName(t.getName());
        if (t != null) {
            assertFalse(t.getName().equals(name));
        } else {
            fail();
        }
        td.doDelete(t.getName());
    }

    @Test
    void doUpdateNotOk() {
        Tag t = new Tag("Tag4");
        TagDAO td = new TagDAO();
        assertThrows(RuntimeException.class, ()->td.doUpdate(t, "notexists"));
    }

    @Test
    void doRetrieveByNameOk() {
        Tag tag1 = new Tag("Tag");
        tagdao.doSave(tag1);
        Tag tag2 = tagdao.doRetrieveByName("Tag");
        if(tag2 != null)
            assertTrue(tag1.getName().equals(tag2.getName()));

        else
            fail();
        tagdao.doDelete(tag1.getName());
    }

    @Test
    void doRetrieveByNameNotOkNull() {
        assertNull(tagdao.doRetrieveByName("'limit"));

    }

    @Test
    void doRetrieveByNameNotOk() {
        assertNull(tagdao.doRetrieveByName("/'1"));

    }

    @Test
    void doDeleteOk() {
        Tag tag1 = new Tag("Tag");
        tagdao.doSave(tag1);
        tagdao.doDelete(tag1.getName());
        assertNull(tagdao.doRetrieveByName(tag1.getName()));
    }

    @Test
    void doDeleteNotOkNull() {
        assertThrows(RuntimeException.class,()->tagdao.doDelete("'limit"));
    }

    @Test
    void doDeleteNotOk() {
        assertThrows(RuntimeException.class,()->tagdao.doDelete("/'1"));
    }

    @Test
    void doRetrieveByNameFragmentxNone() {
        ArrayList<Tag> list = tagdao.doRetrieveByNameFragment("NameFragment", 0, 1000);
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveByNameFragmentxOne() {
        Tag tag1 = new Tag("TestxRetrieveByNameFragmentxOne");
        tagdao.doSave(tag1);
        ArrayList<Tag> list = tagdao.doRetrieveByNameFragment("NameFragment", 0, 1000);
        assertEquals(tag1, list.get(0));
        tagdao.doDelete(tag1.getName());
    }

    @Test
    void doRetrieveByNameFragmentxAll() {
        Tag tag1 = new Tag("TestxRetrieveByNameFragmentxOne1");
        Tag tag2 = new Tag("TestxRetrieveByNameFragmentxOne2");
        tagdao.doSave(tag1);
        tagdao.doSave(tag2);
        ArrayList<Tag> list = tagdao.doRetrieveByNameFragment("NameFragment", 0, 1000);
        assertEquals(tag1, list.get(0));
        assertEquals(tag2, list.get(1));
        tagdao.doDelete(tag1.getName());
        tagdao.doDelete(tag2.getName());
    }
}