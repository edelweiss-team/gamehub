package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserDAO {

    public void doUpdate(@NotNull User u) {

        Connection cn = null;
        try {
            cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("UPDATE user set password=?,"
                    + " name=?, surname=?, address=?, city=?, country=?, birthDate=?,"
                    + " mail=?, sex=?, telephone=? WHERE username=?;");
            st.setString(1, u.getPasswordHash());
            st.setString(2, u.getName());
            st.setString(3, u.getSurname());
            st.setString(4, u.getAddress());
            st.setString(5, u.getCity());
            st.setString(6, u.getCountry());
            st.setString(7, u.getBirthDate());
            st.setString(8, u.getMail());
            st.setString(9, Character.toString(u.getSex()));
            st.setString(10, u.getTelephone());
            st.setString(11, u.getUsername());
            if (st.executeUpdate() != 1) {
                throw new RuntimeException();
            }
            st.close();
            cn.close();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }


    }

    public void doSave(@NotNull User u) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("INSERT INTO user(username, password,"
                    + " name, surname, address, city, country, birthDate, mail, sex, telephone)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?);");
            st.setString(1, u.getUsername());
            st.setString(2, u.getPasswordHash());
            st.setString(3, u.getName());
            st.setString(4, u.getSurname());
            st.setString(5, u.getAddress());
            st.setString(6, u.getCity());
            st.setString(7, u.getCountry());
            st.setString(8, u.getBirthDate());
            st.setString(9, u.getMail());
            st.setString(10, Character.toString(u.getSex()));
            st.setString(11, u.getTelephone());
            st.executeUpdate();
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public User doRetrieveByUsernamePassword(@NotNull String username, @NotNull String password) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM user U WHERE "
                    + "U.username=? AND U.password=SHA1(?);");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            User u = null;
            if (rs.next()) {
                u = new User();
                u.setUsername(rs.getString(1));
                u.setPasswordHash(rs.getString(2));
                u.setName(rs.getString(3));
                u.setSurname(rs.getString(4));
                u.setAddress(rs.getString(5));
                u.setCity(rs.getString(6));
                u.setCountry(rs.getString(7));
                u.setBirthDate(rs.getString(8));
                u.setMail(rs.getString(9));
                u.setSex(rs.getString(10).charAt(0));
                u.setTelephone(rs.getString(11));
            }
            rs.close();
            st.close();
            cn.close();
            return u;
        } catch (SQLException e) {
            return null;
        }
    }

    @Nullable
    public User doRetrieveByUsername(@NotNull String username) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM user U WHERE U.username=?;");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            User u = null;
            if (rs.next()) {
                u = new User();
                u.setUsername(rs.getString(1));
                u.setPasswordHash(rs.getString(2));
                u.setName(rs.getString(3));
                u.setSurname(rs.getString(4));
                u.setAddress(rs.getString(5));
                u.setCity(rs.getString(6));
                u.setCountry(rs.getString(7));
                u.setBirthDate(rs.getString(8));
                u.setMail(rs.getString(9));
                u.setSex(rs.getString(10).charAt(0));
                u.setTelephone(rs.getString(11));
            }
            rs.close();
            st.close();
            cn.close();
            return u;
        } catch (SQLException e) {
            return null;
        }
    }

    @Nullable
    public User doRetrieveByMail(@NotNull String mail) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM user U WHERE"
                    + " U.mail=?;");
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();
            User u = null;
            if (rs.next()) {
                u = new User();
                u.setUsername(rs.getString(1));
                u.setPasswordHash(rs.getString(2));
                u.setName(rs.getString(3));
                u.setSurname(rs.getString(4));
                u.setAddress(rs.getString(5));
                u.setCity(rs.getString(6));
                u.setCountry(rs.getString(7));
                u.setBirthDate(rs.getString(8));
                u.setMail(rs.getString(9));
                u.setSex(rs.getString(10).charAt(0));
                u.setTelephone(rs.getString(11));
            }
            rs.close();
            st.close();
            cn.close();
            return u;
        } catch (SQLException e) {
            return null;
        }
    }

    public void doDeleteFromUsername(@NotNull String username) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("DELETE FROM user WHERE username=?;");
            st.setString(1, username);
            if (st.executeUpdate() != 1) {
                throw new RuntimeException();
            }
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public ArrayList<User> doRetrieveAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM user U WHERE 1=1;");
            ResultSet rs = st.executeQuery();
            User u = null;
            while (rs.next()) {
                u = new User();
                u.setUsername(rs.getString(1));
                u.setPasswordHash(rs.getString(2));
                u.setName(rs.getString(3));
                u.setSurname(rs.getString(4));
                u.setAddress(rs.getString(5));
                u.setCity(rs.getString(6));
                u.setCountry(rs.getString(7));
                u.setBirthDate(rs.getString(8));
                u.setMail(rs.getString(9));
                u.setSex(rs.getString(10).charAt(0));
                u.setTelephone(rs.getString(11));
                users.add(u);
            }
            rs.close();
            st.close();
            cn.close();
            return users;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @NotNull
    public ArrayList<User> doRetrieveByUsernameFragment(@NotNull
        String usernameFragment, int offset, int limit) {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * "
                + "FROM user U WHERE LOWER(U.username) LIKE ? LIMIT ? OFFSET ?;");
            st.setString(1, "%" + usernameFragment.toLowerCase() + "%");
            st.setInt(2, limit);
            st.setInt(3, offset);
            ResultSet rs = st.executeQuery();
            User u = null;
            while (rs.next()) {
                u = new User();
                u.setUsername(rs.getString(1));
                u.setPasswordHash(rs.getString(2));
                u.setName(rs.getString(3));
                u.setSurname(rs.getString(4));
                u.setAddress(rs.getString(5));
                u.setCity(rs.getString(6));
                u.setCountry(rs.getString(7));
                u.setBirthDate(rs.getString(8));
                u.setMail(rs.getString(9));
                u.setSex(rs.getString(10).charAt(0));
                u.setTelephone(rs.getString(11));
                users.add(u);
            }
            rs.close();
            st.close();
            cn.close();
            return users;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
