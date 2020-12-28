package model.dao;

import model.bean.Admin;
import model.bean.Moderator;
import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOTest {
    private final @NotNull UserDAO uDAO = new UserDAO();
    private final @NotNull ModeratorDAO mDAO = new ModeratorDAO();
    private final @NotNull AdminDAO aDAO = new AdminDAO();
    private final @NotNull String contractTime = "2020-10-26";
    private final @NotNull User u = new User("username",
            "password", "Name", "Surname",
            "Address", "City", "Country",
            "2020-11-16", "Mail", 'M',
            "1111111111");
    private final @NotNull Moderator m = new Moderator(u, contractTime);
    private final @NotNull Admin a = new Admin(m, false);

    @Test
    void doSaveOk() {
        uDAO.doSave(u);
        mDAO.doSave(m);
        aDAO.doSave(a);

        Admin a2 = aDAO.doRetrieveByUsername(a.getUsername());

        aDAO.doDeleteByUsername(a.getUsername());
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());

        assertEquals(a, a2);
    }

    @Test
    void doSaveNotOk () {
        // the admin won't be saved since there's no fk 'moderator' in DB.
        assertFalse(aDAO.doSave(a));
    }

    @Test
    void doUpdateOk() {
        Admin a2 = new Admin(m, true);

        uDAO.doSave(u);
        mDAO.doSave(m);
        aDAO.doSave(a2);

        a2.setSuperAdmin(false);
        aDAO.doUpdate(a2);

        assertEquals(a, aDAO.doRetrieveByUsername(a2.getUsername()));

        aDAO.doDeleteByUsername(a.getUsername());
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doUpdateNotOk() {
        // the admin won't be updated since there's no fk 'moderator' in DB.
        assertFalse(aDAO.doUpdate(a));
    }

    @Test
    void doDeleteByUsernameOk() {
        uDAO.doSave(u);
        mDAO.doSave(m);
        aDAO.doSave(a);

        aDAO.doDeleteByUsername(a.getUsername());

        assertNull(aDAO.doRetrieveByUsername(a.getUsername()));

        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doDeleteByUsernameNotOk() {
        // the admin won't be deleted since there's no fk 'moderator' in DB.
        assertFalse(aDAO.doDeleteByUsername(a.getUsername()));
    }

    @Test
    void doRetrieveAllxNone() {
        // the result list is empty since there's no admin saved in the DB.
        assertTrue((aDAO.doRetrieveAll()).isEmpty());
    }

    @Test
    void doRetrieveAllxOne() {
        uDAO.doSave(u);
        mDAO.doSave(m);
        aDAO.doSave(a);

        ArrayList<Admin> admins;

        if ((admins = aDAO.doRetrieveAll()).size() == 1 )
            assertEquals(admins.get(0), a);
        else
            fail();

        aDAO.doDeleteByUsername(a.getUsername());
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doRetrieveAllxAll() {
        @NotNull User u2 = new User("anotherUsername",
                "password", "Name", "Surname",
                "Address", "City", "Country",
                "2020-11-16", "anotherMail", 'M',
                "1111111111");
        @NotNull Moderator m2 = new Moderator(u2, contractTime);
        @NotNull Admin a2 = new Admin(m2, false);

        uDAO.doSave(u);
        mDAO.doSave(m);
        aDAO.doSave(a);

        uDAO.doSave(u2);
        mDAO.doSave(m2);
        aDAO.doSave(a2);

        ArrayList<Admin> admins = aDAO.doRetrieveAll();

        boolean result = ((Admin) admins.get(0)).equals(a2) && ((Admin) admins.get(1)).equals(a);

        aDAO.doDeleteByUsername(a.getUsername());
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());

        aDAO.doDeleteByUsername(a2.getUsername());
        mDAO.doDeleteByUsername(m2.getUsername());
        uDAO.doDeleteFromUsername(u2.getUsername());

        assertTrue(result);
    }

    @Test
    void doRetrieveByUsernameOk() {
        uDAO.doSave(u);
        mDAO.doSave(m);
        aDAO.doSave(a);

        assertEquals(a, aDAO.doRetrieveByUsername(a.getUsername()));

        aDAO.doDeleteByUsername(a.getUsername());
        mDAO.doDeleteByUsername(m.getUsername());
        uDAO.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doRetrieveByUsernameNotOk() {
        // the admin won't be returned since there's no fk 'moderator' in DB.
        assertNull(aDAO.doRetrieveByUsername(a.getUsername()));
    }
}