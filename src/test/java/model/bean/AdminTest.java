package model.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void testEqualsOk() {
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        Admin a = new Admin(u,"2020-05-22");
        assertTrue(a.equals(a));
    }

    @Test
    void testEqualsNotInstance() {
        User u = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        Admin a = new Admin(u,"2020-05-22");
        Tag t = new Tag("ciccio");
        assertFalse(a.equals(t));
    }

    @Test
    void testEqualsNotSuper() {
        User u = new User("gigino22", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "1999-12-12", "gigino22@gmail.com",
                'M', "3351212121");
        Admin a = new Admin(u,"2020-05-22");
        User u2 = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        Admin a2 = new Admin(u2,"2020-05-22");

        assertFalse(a.equals(a2));
    }

    @Test
    void testEqualsNotSuperAdmin() {
        User u = new User("gigino22", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "1999-12-12", "gigino22@gmail.com",
                'M', "3351212121");
        Admin a = new Admin(u,"2020-05-22",true);
        User u2 = new User("gigino", "password", "Luigi", "Tufano", "Via Marchese", "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        Admin a2 = new Admin(u2,"2020-05-22",false);

        assertFalse(a.equals(a2));
    }


}