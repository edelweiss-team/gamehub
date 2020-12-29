package model.dao;

import java.sql.*;
import java.util.ArrayList;
import model.bean.Admin;
import model.bean.Moderator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 *  AdminDAO is used to do operation inside the table 'admin' of database.
 *  AdminDAO allow to do the CRUD operation on database (create, read, update, delete)
 *  It's possible to add an Admin, update an Admin, delete an Admin, read all the admins
 *  saved into the database, read an Admin given his username.
 *  To use AdminDAO it's essential to instance a ModeratorDAO because as said in Admin that
 *  class extends Moderator.
 *
 */

public class AdminDAO {

    @NotNull
    private final ModeratorDAO mdao = new ModeratorDAO();

    /**
     * This method allow to save an Admin into the database.
     *
     * @param a the object Admin to save
     * @return true if Admin is saved correctly, false otherwise
     * @throws RuntimeException if an exception is occurred
     */

    public boolean doSave(@NotNull Admin a) {
        String username = a.getUsername();

        // checks if the 'user' external-key exists in DB.
        if (mdao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("INSERT INTO admin(moderator, superAdmin)"
                                                                + " VALUES (?,?);");
                st.setString(1, username);
                st.setBoolean(2, a.isSuperAdmin());

                st.executeUpdate();
                st.close();
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return true;
        }

        return false;
    }

    /**
     * This method allow to update an Admin into database.
     *
     * @param a the object Admin to update
     * @return true if Admin is updated correctly, false otherwise
     * @throws RuntimeException if an exception is occurred
     */

    public boolean doUpdate(@NotNull Admin a) {
        String username = a.getUsername();

        // checks if the 'user' external-key exists in DB.
        if (mdao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("UPDATE admin SET superAdmin = ?"
                                                                + " WHERE admin.moderator = ?");

                st.setBoolean(1, a.isSuperAdmin());
                st.setString(2, username);

                st.executeUpdate();
                st.close();
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return true;
        }

        return false;
    }

    /**
     * This method allow to remove an Admin from the database
     *
     * @param username an unique String that identify an Admin
     * @return true if Admin is removed correctly, false otherwise
     * @throws RuntimeException if an exception is occurred
     */

    public boolean doDeleteByUsername(@NotNull String username) {
        // checks if the 'user' external-key exists in DB.
        if (mdao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement(
                        "DELETE FROM admin WHERE admin.moderator=?;"
                );
                st.setString(1, username);
                st.executeUpdate();
                st.close();
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return true;
        }

        return false;
    }

    /**
     * This method allows to find all the Admins saved into the database
     *
     * @return an ArrayList formed by Admin, if there are Admins saved into the database
     * it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     */

    @NotNull
    public ArrayList<Admin> doRetrieveAll() {
        ArrayList<Admin> admins = new ArrayList<>();

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM admin");
            ResultSet rs = st.executeQuery();

            boolean isSuperAdmin;
            String username;
            Moderator moderator;
            Admin a;

            while (rs.next()) {
                isSuperAdmin = rs.getBoolean(1);
                username = rs.getString(2);

                moderator = mdao.doRetrieveByUsername(username);

                a = new Admin(moderator, isSuperAdmin);
                admins.add(a);
            }
            rs.close();
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException("can't retrieve admins");
        }

        return admins;
    }

    /**
     * This method allow to find an Admin given his username
     *
     * @param username a String that it's a key for a search into the database
     * @return an Admin that corresponds to the username given from param, null otherwise
     *
     * @throws RuntimeException if an exception is occurred
     */

    @Nullable
    public Admin doRetrieveByUsername(@NotNull String username) {
        Admin a = null;
        Moderator moderator;

        if ((moderator = mdao.doRetrieveByUsername(username)) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement(
                        "SELECT * FROM admin WHERE admin.moderator=?;"
                );
                st.setString(1, username);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    boolean isSuperAdmin = rs.getBoolean(1);
                    a = new Admin(moderator, isSuperAdmin);
                }
                rs.close();
                st.close();
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException("can't retrieve an admin");
            }
        }

        return a;
    }
}
