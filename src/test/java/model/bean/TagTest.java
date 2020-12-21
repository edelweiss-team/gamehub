package model.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {
    @Test
    void testEqualsOk() {
        Tag t0 = new Tag("Tag0"), t1 = new Tag("Tag0");
        assertTrue(t0.equals(t1));
    }

    @Test
    void testEqualsNotOkSameClass() {
        Tag t0 = new Tag("Tag0"), t1 = new Tag("Tag1");
        assertFalse(t0.equals(t1));
    }

    @Test
    void testEqualsNotOkOtherClass() {
        Tag t0 = new Tag("Tag0");
        String t1 = "String";
        assertFalse(t0.equals(t1));
    }

    @Test
    void testEqualsNotOkNull() {
        Tag t0 = new Tag("Tag0");
        assertFalse(t0.equals(null));
    }
}