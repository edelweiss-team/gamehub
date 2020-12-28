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


public class OperatorDAO {

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

    @Nullable
    public Operator doRetrieveByUsernamePassword(
            @NotNull String username, @NotNull String password) {
        try {
            UserDAO ud = new UserDAO();
            User u = ud.doRetrieveByUsernamePassword(username, password);
            Operator o = null;
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT O.user, contractTime, "
                    + "cv FROM operator O, user U WHERE O.user = U.username AND O.user = ? "
                    + "AND U.password = SHA1(?);");
            st.setString(1, username);
            st.setString(2, password);
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
