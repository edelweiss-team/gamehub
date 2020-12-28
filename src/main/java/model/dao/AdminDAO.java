package model.dao;

import model.bean.Admin;
import model.bean.Moderator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.sql.*;
import java.util.ArrayList;


public class AdminDAO {
    @NotNull
    private final ModeratorDAO mdao = new ModeratorDAO();

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

    public boolean doDeleteByUsername(@NotNull String username) {
        // checks if the 'user' external-key exists in DB.
        if (mdao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("DELETE FROM admin WHERE admin.moderator=?;");
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

    @NotNull
    public ArrayList<Admin> doRetrieveAll() {
        ArrayList<Admin> admins = new ArrayList<>();

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM admin");
            ResultSet rs = st.executeQuery();

            boolean isSuperAdmin;
            String username = null;
            Moderator moderator = null;
            Admin a = null;

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
            return new ArrayList<>();
        }

        return admins;
    }

    @Nullable
    public Admin doRetrieveByUsername(@NotNull String username) {
        Admin a = null;
        Moderator moderator;

        if ((moderator = mdao.doRetrieveByUsername(username)) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("SELECT * FROM admin WHERE admin.moderator=?;");
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
                return null;
            }
        }

        return a;
    }
}
