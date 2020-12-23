package model.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUsernameOk(){
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertEquals(u.getUsername(), "gigino");
    }

    @Test
    void getUsernameNotOk(){
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertNotEquals(u.getUsername(), "prova");
    }

    @Test
    void getNameOk(){
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertEquals(u.getName(), "Luigi");
    }

    @Test
    void getNameNotOk(){
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertNotEquals(u.getName(), "Pasquale");
    }


    @Test
    void getSurnameOk(){
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertEquals(u.getSurname(), "Tufano");
    }

    @Test
    void getSurnameNotOk(){
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertNotEquals(u.getSurname(), "Pasquale");
    }

    @Test
    void testEqualsOk() {
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        User u2 = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertTrue(u.equals(u2));
    }

    @Test
    void testEqualsNotOkNull() {
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertFalse(u.equals(null));
    }

    @Test
    void testEqualsNotOkAnotherClass(){
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        String str = "gigino";
        assertFalse(u.equals(str));
    }

    @Test
    void testEqualsNotOkDifferentValues(){
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        User u2 = new User("luca", "password", "Pasquale", "Tufano", "Via Marchese", "Boscoreale", "Italia", "12-12-1999", "gigino@gmail.com",
                'M', "3351212121");
        assertFalse(u.equals(u2));
    }
}