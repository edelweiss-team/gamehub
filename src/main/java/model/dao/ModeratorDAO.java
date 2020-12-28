package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Moderator;
import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class ModeratorDAO {

    @NotNull
    private final UserDAO udao = new UserDAO();

    public boolean doSave(@NotNull Moderator m) {
        String username = m.getUsername();

        // checks if the 'user' external-key exists in DB.
        if (udao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("INSERT INTO moderator(user, contractTime)"
                        + " VALUES (?,?);");
                st.setString(1, username);
                st.setString(2, m.getContractTime());

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

    public boolean doUpdate(@NotNull Moderator m) {
        String username = m.getUsername();

        // checks if the 'user' external-key exists in DB.
        if (udao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("UPDATE moderator SET contractTime = ?"
                        + " WHERE moderator.user = ?");

                st.setString(1, m.getContractTime());
                st.setString(2, username);

                if (st.executeUpdate() != 1) {
                    st.close();
                    cn.close();
                    throw new RuntimeException("can't update");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return true;
        }

        return false;
    }

    public boolean doDeleteByUsername(@NotNull String username) {

        // checks if the 'user' external-key exists in DB.
        if (udao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("DELETE FROM moderator WHERE user=?;");
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
    public ArrayList<Moderator> doRetrieveAll() {
        ArrayList<Moderator> moderators = new ArrayList<>();

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM moderator");
            ResultSet rs = st.executeQuery();

            Moderator m = null;
            String username = null;
            String contractTime = null;
            User user = null;

            while (rs.next()) {
                contractTime = rs.getString(1);
                username = rs.getString(2);

                user = udao.doRetrieveByUsername(username);
                assert user != null;
                m = new Moderator(user, contractTime);
                moderators.add(m);
            }
            rs.close();
            st.close();
            cn.close();

        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return moderators;
    }

    @Nullable
    public Moderator doRetriveByUsername(@NotNull String username) {
        User user;
        Moderator moderator = null;

        if ((user = udao.doRetrieveByUsername(username)) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("SELECT * FROM moderator WHERE moderator.user=?;");
                st.setString(1, username);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    String contractTime = rs.getString(1);
                    moderator = new Moderator(user, contractTime);
                }

                rs.close();
                st.close();
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }

        return moderator;
    }
}
