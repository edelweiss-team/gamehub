package model.dao;


import model.bean.Moderator;
import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModeratorDAOTest {
    private final @NotNull UserDAO uDAO = new UserDAO();
    private final @NotNull ModeratorDAO mDAO = new ModeratorDAO();
    private final @NotNull String contractTime = "2020-10-26";
    private final @NotNull User u = new User("username",
            "password", "Name", "Surname",
            "Address", "City", "Country",
            "2020-11-16", "Mail", 'M',
            "1111111111");
    private final @NotNull User u2 = new User("anotherUsername",
            "password", "Name", "Surname",
            "Address", "City", "Country",
            "2020-11-16", "anotherMail", 'M',
            "1111111111");

    @Test
    void doSaveOk() {
        // Inserting user in DB
        uDAO.doSave(u);

        Moderator m = new Moderator(u, contractTime);
        Moderator m2;

        mDAO.doSave(m);
        m2 = mDAO.doRetrieveByUsername(u.getUsername());

        // Deleting user and moderator from DB
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());

        assertEquals(m2, m);
    }

    @Test
    void doSaveNotOk() {
        // user missing from DB

        Moderator m = new Moderator(u, contractTime);
        assertFalse(mDAO.doSave(m));
    }


    @Test
    void doUpdateOk() {
        // saving user in DB.
        uDAO.doSave(u);

        Moderator m = new Moderator(u, contractTime);
        Moderator m2;

        mDAO.doSave(m);
        m.setContractTime("2020-10-20");
        mDAO.doUpdate(m);

        m2 = mDAO.doRetrieveByUsername(u.getUsername());
        // deleting user and moderator from DB.
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());

        // comparing the local and db-retrived moderator
        assertEquals(m, m2);
    }

    @Test
    void doUpdateNotOk() {
        Moderator m = new Moderator(u, contractTime);
        m.setUsername("usernameNotInDB");
        assertFalse(mDAO.doUpdate(m));
    }

    @Test
    void doRetrieveByUsernameFragmentxNone() {
        ArrayList<Moderator> list = mDAO.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertTrue(list.isEmpty());
    }

    @Test
    void doRetrieveByUsernameFragmentxOne() {
        User u1 = new User("NameFragmentUsername", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Moderator o1 = new Moderator(u1, "2020-11-11");
        uDAO.doSave(u1);
        mDAO.doSave(o1);
        ArrayList<Moderator> list = mDAO.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertEquals(o1, list.get(0));
        mDAO.doDeleteByUsername(u1.getUsername());
        uDAO.doDeleteFromUsername(u1.getUsername());
    }

    @Test
    void doRetrieveByUsernameFragmentxAll() {
        User u1 = new User("aNameFragmentUsername", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "Mail", 'M', "1111111111");
        Moderator o1 = new Moderator(u1, "2020-11-11");
        uDAO.doSave(u1);
        User u2 = new User("ausername2NameFragment", "password2", "Name2", "Surname2", "Address2", "City2", "Country2", "2020-11-13", "Mail2", 'F', "1111111112");
        Moderator o2 = new Moderator(u2, "2020-11-11");
        uDAO.doSave(u2);
        mDAO.doSave(o1);
        mDAO.doSave(o2);
        ArrayList<Moderator> list = mDAO.doRetrieveByUsernameFragment("NameFragment", 0, 1000);
        assertEquals(o1, list.get(0));
        assertEquals(o2, list.get(1));
        uDAO.doDeleteFromUsername(u1.getUsername());
        uDAO.doDeleteFromUsername(u2.getUsername());
        mDAO.doDeleteByUsername(u1.getUsername());
        mDAO.doDeleteByUsername(u2.getUsername());
    }

    @Test
    void doDeleteByUsernameOk() {
        // saving user and moderator in DB.
        uDAO.doSave(u);
        Moderator m = new Moderator(u, contractTime);
        mDAO.doSave(m);

        // deleting user and moderator from DB.
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());

        assertNull(mDAO.doRetrieveByUsername(u.getUsername()));
    }

    @Test
    void doDeleteByUsernameNotOk() {
        Moderator m = new Moderator(u, contractTime);
        m.setUsername("usernameNotInDB");
        assertFalse(mDAO.doDeleteByUsername(m.getUsername()));
    }

    @Test
    void doRetrieveAllxNone() {
        ArrayList<Moderator> moderators = mDAO.doRetrieveAll();
        assertTrue(moderators.isEmpty());
    }

    @Test
    void doRetriveAllxOne() {
        uDAO.doSave(u);
        Moderator m = new Moderator(u, contractTime);
        mDAO.doSave(m);

        ArrayList<Moderator> moderators = mDAO.doRetrieveAll();
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());

        assertEquals(m, moderators.get(0));
    }

    @Test
    void doRetriveAllxAll() {
        // Saving users and moderators in DB.
        uDAO.doSave(u);
        uDAO.doSave(u2);

        Moderator m = new Moderator(u, contractTime);
        Moderator m2 = new Moderator(u2, contractTime);

        mDAO.doSave(m);
        mDAO.doSave(m2);

        ArrayList<Moderator> moderators;
        moderators = mDAO.doRetrieveAll();

        // Deleting moderators and users in db
        mDAO.doDeleteByUsername(m.getUsername());
        mDAO.doDeleteByUsername(m2.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());
        uDAO.doDeleteFromUsername(u2.getUsername());

        boolean result = m2.equals((Moderator) moderators.get(0)) && m.equals((Moderator) moderators.get(1));

        assertTrue(result);
    }

    @Test
    void doRetriveByUsernameOk() {
        // inserting user and moderator in db
        uDAO.doSave(u);
        Moderator m = new Moderator(u, contractTime);
        Moderator m2;
        mDAO.doSave(m);

        m2 = mDAO.doRetrieveByUsername(m.getUsername());

        // deleting user and moderator from db
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());

        assertEquals(m, m2);
    }

    @Test
    void doRetriveByUsernameNotOk() {
        Moderator m = new Moderator(u, contractTime);
        m.setUsername("usernameNotInDB");
        assertNull(mDAO.doRetrieveByUsername(m.getUsername()));
    }

    @Test
    void doRetrieveByUsernamePasswordOk() {
        // inserting user and moderator in db
        User u = new User("gigino", "Name", "Surname", "Address",
                "City", "Country", "2020-11-16", "giginoMail", 'M',
                "1111111111");
        u.setPassword("password");
        uDAO.doSave(u);
        Moderator m = new Moderator(u, contractTime);
        Moderator m2;
        mDAO.doSave(m);

        m2 = mDAO.doRetrieveByUsernamePassword(m.getUsername(), "password");

        assertEquals(m, m2);

        // deleting user and moderator from db
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());

    }

    @Test
    void doRetriveByUsernamePasswordNotOk() {
        Moderator m = new Moderator(u, contractTime);
        m.setUsername("usernameNotInDB");
        assertNull(mDAO.doRetrieveByUsernamePassword(m.getUsername(), "password"));
    }

}