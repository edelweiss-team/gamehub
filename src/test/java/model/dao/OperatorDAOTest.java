package model.dao;

import model.bean.Operator;
import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OperatorDAOTest {

    private final @NotNull OperatorDAO od = new OperatorDAO();

    @Test
    void doRetrieveByUsernameOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o = new Operator(u, "2020-11-11", "cv");
        od.doSave(o);
        Operator o2 = od.doRetrieveByUsername("username");
        if(o2 != null)
            assertTrue(o.getUsername().equals(o2.getUsername()) && o.getPasswordHash().equals(o2.getPasswordHash())
                    && o.getName().equals(o2.getName()) && o.getSurname().equals(o2.getSurname())
                    && o.getAddress().equals(o2.getAddress()) && o.getCity().equals(o2.getCity())
                    && o.getCountry().equals(o2.getCountry()) && o.getBirthDate().equals(o2.getBirthDate())
                    && o.getMail().equals(o2.getMail()) && o.getSex() == o2.getSex() && o.getTelephone().equals(o2.getTelephone())
                    && o.getContractTime().equals(o2.getContractTime()) && o.getCv().equals(o2.getCv()));
        else
            fail();
        od.doDeleteByUsername("username");
    }

    @Test
    void doRetrieveByUsernameNotOkNull() {
        assertNull(od.doRetrieveByUsername("'limit'"));
    }

    @Test
    void doRetrieveByUsernameNotOk() {
        assertNull(od.doRetrieveByUsername("/'1"));
    }

    @Test
    void doRetrieveByUsernamePasswordOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o = new Operator(u, "2020-11-11", "cv");
        od.doSave(o);
        Operator o2 = od.doRetrieveByUsernamePassword("username", "password");
        if(o2 != null)
            assertTrue(o.getUsername().equals(o2.getUsername()) && o.getPasswordHash().equals(o2.getPasswordHash())
                    && o.getName().equals(o2.getName()) && o.getSurname().equals(o2.getSurname())
                    && o.getAddress().equals(o2.getAddress()) && o.getCity().equals(o2.getCity())
                    && o.getCountry().equals(o2.getCountry()) && o.getBirthDate().equals(o2.getBirthDate())
                    && o.getMail().equals(o2.getMail()) && o.getSex() == o2.getSex() && o.getTelephone().equals(o2.getTelephone())
                    && o.getContractTime().equals(o2.getContractTime()) && o.getCv().equals(o2.getCv()));
        else
            fail();
        od.doDeleteByUsername("username");
    }

    @Test
    void doRetrieveByUsernamePasswordNotOkNull() {
        assertNull(od.doRetrieveByUsernamePassword("'limit", "'limit"));
    }

    @Test
    void doRetrieveByUsernamePasswordNotOk() {
        assertNull(od.doRetrieveByUsernamePassword("/'1", "/'1"));
    }

    @Test
    void doDeleteByUsernameOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o = new Operator(u, "2020-11-11", "cv");
        od.doSave(o);
        od.doDeleteByUsername(u.getUsername());
        assertNull(od.doRetrieveByUsername(u.getUsername()));
    }

    @Test
    void doDeleteByUsernameNotOkNull() {
        assertThrows(RuntimeException.class, ()->od.doDeleteByUsername("'limit"));
    }

    @Test
    void doDeleteByUsernameNotOk() {
        assertThrows(RuntimeException.class, ()->od.doDeleteByUsername("/'1"));
    }

    @Test
    void doSaveOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o = new Operator(u, "2020-11-11", "cv");
        od.doSave(o);
        Operator o2 = od.doRetrieveByUsername("username");
        if(o2 != null)
            assertTrue(o.getUsername().equals(o2.getUsername()) && o.getPasswordHash().equals(o2.getPasswordHash()) &&
                    o.getName().equals(o2.getName()) && o.getSurname().equals(o2.getSurname())
                    && o.getAddress().equals(o2.getAddress()) && o.getCity().equals(o2.getCity())
                    && o.getCountry().equals(o2.getCountry()) && o.getBirthDate().equals(o2.getBirthDate())
                    && o.getMail().equals(o2.getMail()) && o.getSex() == o2.getSex()
                    && o.getTelephone().equals(o2.getTelephone())
                    && o.getContractTime().equals(o2.getContractTime()) && o.getCv().equals(o2.getCv()));
        else
            fail();
        od.doDeleteByUsername(u.getUsername());
    }

    @Test
    void doSaveNotOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o = new Operator(u, "2020-11-11", "cv");
        od.doSave(o);
        assertThrows(RuntimeException.class, ()->od.doSave(o));
        od.doDeleteByUsername(u.getUsername());
    }

    @Test
    void doUpdateOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o = new Operator(u, "2020-11-11", "cv");
        od.doSave(o);
        String oldContractTime = o.getContractTime();
        String oldCv = o.getCv();
        o.setContractTime("2021-12-12");
        o.setCv("new cv");
        od.doUpdate(o);
        o = od.doRetrieveByUsername(o.getUsername());
        if(o != null)
            assertFalse( o.getContractTime().equals(oldContractTime) && o.getCv().equals(oldCv));
        else
            fail();
        od.doDeleteByUsername(o.getUsername());
    }

    @Test
    void doUpdateNotOk() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o = new Operator(u, "2020-11-11", "cv");
        assertThrows(RuntimeException.class, ()->od.doUpdate(o));
    }


    @Test
    void doRetrieveAllxNone() {
        ArrayList<Operator> list = od.doRetrieveAll();
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveAllxOne() {
        User u = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o = new Operator(u, "2020-11-11", "cv");
        od.doSave(o);
        ArrayList<Operator> list = od.doRetrieveAll();
        assertEquals(o, list.get(0));
        od.doDeleteByUsername(u.getUsername());
    }

    @Test
    void doRetrieveAllxAll() {
        User u1 = new User("username","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o1 = new Operator(u1, "2020-11-11", "cv");
        User u2 = new User("username2","password2", "Name2", "Surname2", "Address2", "City2", "Country2", "2020-11-13", "Mail2", 'F', "1111111112");
        Operator o2 = new Operator(u2, "2020-11-11", "cv");
        od.doSave(o1);
        od.doSave(o2);
        ArrayList<Operator> list = od.doRetrieveAll();
        assertEquals(o1, list.get(0));
        assertEquals(o2, list.get(1));
        od.doDeleteByUsername(u1.getUsername());
        od.doDeleteByUsername(u2.getUsername());
    }

    @Test
    void doRetrieveByUsernameFragmentxNone() {
        ArrayList<Operator> list = od.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveByUsernameFragmentxOne() {
        User u1 = new User("NameFragmentUsername","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o1 = new Operator(u1, "2020-11-11", "cv");
        od.doSave(o1);
        ArrayList<Operator> list = od.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertEquals(o1, list.get(0));
        od.doDeleteByUsername(u1.getUsername());
    }

    @Test
    void doRetrieveByUsernameFragmentxAll() {
        User u1 = new User("NameFragmentUsername","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Operator o1 = new Operator(u1, "2020-11-11", "cv");
        User u2 = new User("username2NameFragment","password2", "Name2", "Surname2", "Address2", "City2", "Country2", "2020-11-13", "Mail2", 'F', "1111111112");
        Operator o2 = new Operator(u2, "2020-11-11", "cv");
        od.doSave(o1);
        od.doSave(o2);
        ArrayList<Operator> list = od.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertEquals(o1, list.get(0));
        assertEquals(o2, list.get(1));
        od.doDeleteByUsername(u1.getUsername());
        od.doDeleteByUsername(u2.getUsername());
    }

}