package model.dao;

import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private final @NotNull UserDAO ud = new UserDAO();

    @Test
    void doRetrieveByUsernameOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u);
        User u2 = ud.doRetrieveByUsername("username");
        if(u2 != null)
            assertTrue(u.getUsername().equals(u2.getUsername()) && u.getPasswordHash().equals(u2.getPasswordHash())
            && u.getName().equals(u2.getName()) && u.getSurname().equals(u2.getSurname())
            && u.getAddress().equals(u2.getAddress()) && u.getCity().equals(u2.getCity())
            && u.getCountry().equals(u2.getCountry()) && u.getBirthDate().equals(u2.getBirthDate())
            && u.getMail().equals(u2.getMail()) && u.getSex() == u2.getSex() && u.getTelephone().equals(u2.getTelephone()));
        else
            fail();
        ud.doDeleteFromUsername("username");
    }

    @Test
    void doRetrieveByUsernameNotOkNull() {
        assertNull(ud.doRetrieveByUsername("'limit'"));
    }

    @Test
    void doRetrieveByUsernameNotOk() {
        assertNull(ud.doRetrieveByUsername("/'1"));
    }

    @Test
    void doRetrieveByUsernamePasswordOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u);
        User u2 = ud.doRetrieveByUsernamePassword("username", "password");
        if(u2 != null)
            assertTrue(u.getUsername().equals(u2.getUsername()) &&
                    u.getPasswordHash().equals(u2.getPasswordHash()) && u.getName().equals(u2.getName())
                    && u.getSurname().equals(u2.getSurname()) && u.getAddress().equals(u2.getAddress())
                    && u.getCity().equals(u2.getCity()) && u.getCountry().equals(u2.getCountry())
                    && u.getBirthDate().equals(u2.getBirthDate()) && u.getMail().equals(u2.getMail())
                    && u.getSex() == u2.getSex() && u.getTelephone().equals(u2.getTelephone()));
        else
            fail();
        ud.doDeleteFromUsername("username");
    }

    @Test
    void doRetrieveByUsernamePasswordNotOkNull() {
        assertNull(ud.doRetrieveByUsernamePassword("'limit", "'limit"));
    }

    @Test
    void doRetrieveByUsernamePasswordNotOk() {
        assertNull(ud.doRetrieveByUsernamePassword("/'1", "/'1"));
    }

    @Test
    void doRetrieveByMailOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u);
        User u2 = ud.doRetrieveByMail("Mail");
        if(u2 != null)
            assertTrue(u.getUsername().equals(u2.getUsername()) && u.getPasswordHash().equals(u2.getPasswordHash())
                    && u.getName().equals(u2.getName()) && u.getSurname().equals(u2.getSurname())
                    && u.getAddress().equals(u2.getAddress()) && u.getCity().equals(u2.getCity())
                    && u.getCountry().equals(u2.getCountry()) && u.getBirthDate().equals(u2.getBirthDate())
                    && u.getMail().equals(u2.getMail()) && u.getSex() == u2.getSex() && u.getTelephone().equals(u2.getTelephone()));
        else
            fail();
        ud.doDeleteFromUsername("username");
    }

    @Test
    void doRetrieveByMailNotOkNull() {
        assertNull(ud.doRetrieveByMail("'limit'"));
    }

    @Test
    void doRetrieveByMailNotOk() {
        assertNull(ud.doRetrieveByMail("/'1"));
    }


    @Test
    void doDeleteByUsernameOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u);
        ud.doDeleteFromUsername(u.getUsername());
        assertNull(ud.doRetrieveByUsername(u.getUsername()));
    }

    @Test
    void doDeleteByUsernameNotOkNull() {
        assertThrows(RuntimeException.class, ()->ud.doDeleteFromUsername("'limit"));
    }

    @Test
    void doDeleteByUsernameNotOk() {
        assertThrows(RuntimeException.class, ()->ud.doDeleteFromUsername("/'1"));
    }

    @Test
    void doSaveOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u);
        User u2 = ud.doRetrieveByUsername("username");
        if(u2 != null)
            assertTrue(u.getUsername().equals(u2.getUsername()) && u.getPasswordHash().equals(u2.getPasswordHash()) &&
                    u.getName().equals(u2.getName()) && u.getSurname().equals(u2.getSurname())
                    && u.getAddress().equals(u2.getAddress()) && u.getCity().equals(u2.getCity())
                    && u.getCountry().equals(u2.getCountry()) && u.getBirthDate().equals(u2.getBirthDate())
                    && u.getMail().equals(u2.getMail()) && u.getSex() == u2.getSex()
                    && u.getTelephone().equals(u2.getTelephone()));
        else
            fail();
        ud.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doSaveNotOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u);
        assertThrows(RuntimeException.class, ()->ud.doSave(u));
        ud.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doUpdateOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u);
        String oldUsername = u.getUsername();
        String oldPassword = u.getPasswordHash();
        String oldName = u.getName();
        String oldSurname = u.getSurname();
        String oldAddress = u.getAddress();
        String oldCity = u.getCity();
        String oldCountry = u.getCountry();
        String oldBirthDate = u.getBirthDate();
        String oldMail = u.getMail();
        char oldSex = u.getSex();
        String oldTelephone = u.getTelephone();
        u.setPassword("nuovapassword");
        u.setName("nuovoName");
        u.setSurname("nuovoSurname");
        u.setAddress("nuovoAddress");
        u.setCity("nuovoCity");
        u.setCountry("nuovoCountry");
        u.setBirthDate("1999-08-10");
        u.setMail("nuovaMail");
        u.setSex('F');
        u.setTelephone("2222222222");
        ud.doUpdate(u);
        u = ud.doRetrieveByUsername(u.getUsername());
        if(u != null)
            assertFalse(u.getUsername().equals(oldUsername) && u.getPasswordHash().equals(oldPassword) &&
                    u.getName().equals(oldName) && u.getSurname().equals(oldSurname) &&
                    u.getAddress().equals(oldAddress) && u.getCity().equals(oldCity) &&
                    u.getCountry().equals(oldCountry) && u.getBirthDate().equals(oldBirthDate)
                    && u.getMail().equals(oldMail) && u.getSex() == oldSex &&
                    u.getTelephone().equals(oldTelephone));
        else
            fail();
        ud.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doUpdateNotOk() {
        User u = new User("'limit","NotWork", "NotWork", "NotWork", "NotWork", "NotWork", "NotWork", "2020-11-16", "NotWork", 'M', "1111111111");
        assertThrows(RuntimeException.class, ()->ud.doUpdate(u));
    }

    @Test
    void doRetrieveAllxNone() {
        ArrayList<User> list = ud.doRetrieveAll();
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveAllxOne() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u);
        ArrayList<User> list = ud.doRetrieveAll();
        assertEquals(u, list.get(0));
        ud.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doRetrieveAllxAll() {
        User u1 = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        User u2 = new User("username2","password2", "Name2", "Surname2", "Address2", "City2", "Country2", "2020-11-13", "Mail2", 'F', "1111111112");
        ud.doSave(u1);
        ud.doSave(u2);
        ArrayList<User> list = ud.doRetrieveAll();
        assertEquals(u1, list.get(0));
        assertEquals(u2, list.get(1));
        ud.doDeleteFromUsername(u1.getUsername());
        ud.doDeleteFromUsername(u2.getUsername());
    }

    @Test
    void doRetrieveByUsernameFragmentxNone() {
        ArrayList<User> list = ud.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveByUsernameFragmentxOne() {
        User u1 = new User("NameFragmentUsername","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        ud.doSave(u1);
        ArrayList<User> list = ud.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertEquals(u1, list.get(0));
        ud.doDeleteFromUsername(u1.getUsername());
    }

    @Test
    void doRetrieveByUsernameFragmentxAll() {
        User u1 = new User("NameFragmentUsername","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        User u2 = new User("username2NameFragment","password2", "Name2", "Surname2", "Address2", "City2", "Country2", "2020-11-13", "Mail2", 'F', "1111111112");
        ud.doSave(u1);
        ud.doSave(u2);
        ArrayList<User> list = ud.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertEquals(u1, list.get(0));
        assertEquals(u2, list.get(1));
        ud.doDeleteFromUsername(u1.getUsername());
        ud.doDeleteFromUsername(u2.getUsername());
    }

}