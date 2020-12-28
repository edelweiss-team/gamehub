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
    void doRetiveByUsernameNotOk() {
        Moderator m = new Moderator(u, contractTime);
        m.setUsername("usernameNotInDB");
        assertNull(mDAO.doRetrieveByUsername(m.getUsername()));
    }
}