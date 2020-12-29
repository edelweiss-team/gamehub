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

/**
 * ModeratorAO is used to do operation inside the table 'moderator' of database.
 * ModeratorDAO allow to do the CRUD operation on database (create, read, update, delete)
 * It's possible to add a Moderator, update a Moderator,
 * delete a Moderator , read all the moderators
 * saved into the database, read a Moderator given his username.
 * To use ModeratorDAO it's essential to instance a UserDAO because as said in Moderator that
 * class extends User.
 */

public class ModeratorDAO {

    @NotNull
    private final UserDAO udao = new UserDAO();

    /**
     * This method allows to save a Moderator into the database.
     *
     * @param m the object Moderator to save
     * @return true if Moderator is saved correctly, false otherwise
     * @throws RuntimeException if an exception is occurred
     */
    public boolean doSave(@NotNull Moderator m) {
        String username = m.getUsername();

        // checks if the 'user' external-key exists in DB.
        if (udao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("INSERT INTO "
                        + "moderator(user, contractTime)"
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

    /**
     * This method allow to update a Moderator into database.
     *
     * @param m the object Admin to update
     * @return true if Moderator is updated correctly, false otherwise
     * @throws RuntimeException if an exception is occurred
     */
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

    /**
     * This method allow to remove a Moderator from the database.
     *
     * @param username an unique String that identify a Moderator
     * @return true if Moderator is removed correctly, false otherwise
     * @throws RuntimeException if an exception is occurred
     */
    public boolean doDeleteByUsername(@NotNull String username) {
        // checks if the 'user' external-key exists in DB.
        if (udao.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("DELETE FROM moderator WHERE user=?;");
                st.setString(1, username);

                if (st.executeUpdate() != 1) {
                    st.close();
                    cn.close();
                    throw new RuntimeException("can't delete");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return true;
        }

        return false;
    }

    /**
     * This method allows to find all the Moderators saved into the database.
     *
     * @return an ArrayList formed by Moderators, if there are Moderators saved into the database
     * it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     */

    @NotNull
    public ArrayList<Moderator> doRetrieveAll() {
        ArrayList<Moderator> moderators = new ArrayList<>();

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM moderator");
            ResultSet rs = st.executeQuery();

            Moderator m;
            String username;
            String contractTime;
            User user;

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
            throw new RuntimeException("can't retrieve moderators");
        }

        return moderators;
    }

    /**
     * This method allow to find a Moderator given his username.
     *
     * @param username a String that it's a key for a search into the database
     * @return a Moderator that corresponds to the username given from param, null otherwise
     *
     * @throws RuntimeException if an exception is occurred
     */
    @Nullable
    public Moderator doRetrieveByUsername(@NotNull String username) {
        User user;
        Moderator moderator = null;

        if ((user = udao.doRetrieveByUsername(username)) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("SELECT * FROM moderator "
                        + "WHERE moderator.user=?;");
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
                throw new RuntimeException("can't retrieve a moderator");
            }
        }

        return moderator;
    }
}
