package model.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testEqualsOk() {
        Category c0 = new Category("c0", "desc", "path0");
        Category c1 = new Category("c0", "desc2", "path1");
        assertTrue(c0.equals(c1));
    }

    @Test
    void testEqualsNotOkSameClass() {
        Category c0 = new Category("c0", "desc", "path0");
        Category c1 = new Category("c1", "desc1", "path1");
        assertFalse(c0.equals(c1));
    }

    @Test
    void testEqualsNotOkOtherClass() {
        Category c0 = new Category("c0", "desc", "path0");
        Tag c1 = new Tag("aa");
        assertFalse(c0.equals(c1));
    }

    @Test
    void testEqualsNotOkNull() {
        Category c0 = new Category("c0", "desc", "path0");
        assertFalse(c0.equals(null));
    }
}