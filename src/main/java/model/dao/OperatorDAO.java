package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Operator;
import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * OperatorDAO is used to do operation inside the table 'operator' of database.
 * OperatorDAO allow to do the CRUD operation on database (create, read, update, delete)
 * It's possible to add an Operator that is already signed as user, update an Operator,
 * delete an Operator, read all the operators saved into the database, read an Operator
 * given his username.
 * To use OperatorDAO it's essential to instance an UserDAO because as said in Operator that
 * class extends User.
 *
 */

public class OperatorDAO {

    /**
     * This method allow to update an Operator into database.
     *
     * @param o the object Operator to update
     * @throws RuntimeException if an exception is occurred
     */

    public void doUpdate(@NotNull Operator o) {
        try {
            UserDAO ud = new UserDAO();
            ud.doUpdate(o);
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("UPDATE operator SET contractTime = ?, "
                    + "cv = ? WHERE operator.user = ?;");
            st.setString(1, o.getContractTime());
            st.setString(2, o.getCv());
            st.setString(3, o.getUsername());
            if (st.executeUpdate() != 1) {
                throw new RuntimeException();
            }
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allow to save an Operator into the database,
     * that is not signed as User.
     *
     * @param o the object Operator to save
     * @throws RuntimeException if an exception is occurred
     */

    public void doSave(@NotNull Operator o) {
        try {
            UserDAO ud = new UserDAO();
            ud.doSave(o);
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("INSERT INTO operator(contractTime, "
                    + "cv, user) " + "VALUES (?, ?, ?);");
            st.setString(1, o.getContractTime());
            st.setString(2, o.getCv());
            st.setString(3, o.getUsername());
            st.executeUpdate();
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allow to promote an Operator into the database,
     * that already exists as user inside the database.
     *
     * @param o the object Operator to save
     * @throws RuntimeException if an exception is occurred
     */

    public void doPromote(@NotNull Operator o) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("INSERT INTO "
                    + "operator(contractTime, cv, user) VALUES (?, ?, ?);");
            st.setString(1, o.getContractTime());
            st.setString(2, o.getCv());
            st.setString(3, o.getUsername());
            st.executeUpdate();
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allow to find an Operator given his username and password.
     *
     * @param username a String that it's a key for a search into the database
     * @param password a String that it's a key for a search into the database
     * @return an Operator that corresponds to the username and password given from param,
     *      null otherwise
     * @throws RuntimeException if an exception is occurred
     */

    @Nullable
    public Operator doRetrieveByUsernamePassword(
            @NotNull String username, @NotNull String password) {
        try {
            UserDAO ud = new UserDAO();
            User u = ud.doRetrieveByUsernamePassword(username, password);
            Operator o = null;
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT O.user, contractTime, "
                    + "cv FROM operator O, user U WHERE O.user = ?;");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                o = new Operator(u, rs.getString("contractTime"), rs.getString("cv"));
            }
            cn.close();
            return o;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method allow to find an Operator given his username.
     *
     * @param username a String that it's a key for a search into the database
     * @return an Operator that corresponds to the username given from param, null otherwise
     * @throws RuntimeException if an exception is occurred
     */

    @Nullable
    public Operator doRetrieveByUsername(@NotNull String username) {
        try {
            UserDAO ud = new UserDAO();
            User u = ud.doRetrieveByUsername(username);
            Operator o = null;
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT O.user, contractTime,"
                    + " cv FROM operator O WHERE O.user = ?;");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                o = new Operator(u, rs.getString("contractTime"), rs.getString("cv"));
            }
            cn.close();
            return o;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method allow to remove an Operator from the database.
     *
     * @param username an unique String that identify an Operator
     * @throws RuntimeException if an exception is occurred
     */

    public void doDeleteByUsername(@NotNull String username) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("DELETE FROM operator"
                    + " WHERE operator.user = ?;");
            st.setString(1, username);
            if (st.executeUpdate() != 1) {
                throw new RuntimeException();
            }
            st.close();
            cn.close();
            UserDAO ud = new UserDAO();
            ud.doDeleteFromUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allows to find all the Operators saved into the database.
     *
     * @return an ArrayList formed by Operator, if there are Operators saved into the database
     *      it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     */

    @NotNull
    public ArrayList<Operator> doRetrieveAll() {
        ArrayList<Operator> operators = new ArrayList<>();
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM "
                    + "user U, operator O WHERE O.user = U.username;");
            ResultSet rs = st.executeQuery();
            User u = null;
            Operator o = null;
            while (rs.next()) {
                u = new User();
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password"));
                u.setName(rs.getString("name"));
                u.setSurname(rs.getString("surname"));
                u.setAddress(rs.getString("address"));
                u.setCity(rs.getString("city"));
                u.setCountry(rs.getString("country"));
                u.setBirthDate(rs.getString("birthDate"));
                u.setMail(rs.getString("mail"));
                u.setSex(rs.getString("sex").charAt(0));
                u.setTelephone(rs.getString("telephone"));
                o = new Operator(u, rs.getString("contractTime"), rs.getString("cv"));
                operators.add(o);
            }
            rs.close();
            st.close();
            cn.close();
            return operators;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * This method allow to find an Operator given his username,
     * using an offset and a limit.
     *
     * @param usernameFragment a String that it's a key for a search into the database.
     *                         It cannot be null
     * @param offset to select the starting range value of the Operator to retrieve
     * @param limit to select the ending range value of the Operator to retrieve
     * @return an ArrayList of Operator that corresponds to the usernamefragment given from param
     *      with offset and limit, null otherwise
     *
     * @throws RuntimeException if an exception is occurred
     *
     */

    @NotNull
    public ArrayList<Operator> doRetrieveByUsernameFragment(
            @NotNull String usernameFragment, int offset, int limit) {
        ArrayList<Operator> operators = new ArrayList<>();
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM user U,"
                    + " operator O WHERE U.username = o.user AND lower(O.user) like ?"
                    + " limit ? offset ?;");
            st.setString(1, "%" + usernameFragment.toLowerCase() + "%");
            st.setInt(2, limit);
            st.setInt(3, offset);
            ResultSet rs = st.executeQuery();
            User u = null;
            Operator o = null;
            while (rs.next()) {
                u = new User();
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password"));
                u.setName(rs.getString("name"));
                u.setSurname(rs.getString("surname"));
                u.setAddress(rs.getString("address"));
                u.setCity(rs.getString("city"));
                u.setCountry(rs.getString("country"));
                u.setBirthDate(rs.getString("birthDate"));
                u.setMail(rs.getString("mail"));
                u.setSex(rs.getString("sex").charAt(0));
                u.setTelephone(rs.getString("telephone"));
                o = new Operator(u, rs.getString("contractTime"), rs.getString("cv"));
                operators.add(o);
            }
            rs.close();
            st.close();
            cn.close();
            return operators;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

}
