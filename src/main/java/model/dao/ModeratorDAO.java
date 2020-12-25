package model.dao;

import model.bean.Moderator;
import model.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ModeratorDAO {
    public boolean doSave(Moderator m) {
        UserDAO uDAO = new UserDAO();
        String username = m.getUsername();

        // checks if the 'user' external-key exists in DB.
        if (uDAO.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("INSERT INTO moderator(user, contractTime)" +
                                                                " VALUES (?,?);");
                st.setString(1, username);
                st.setDate(2, m.getContractTimeAsDate());

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

    public boolean doUpdate(Moderator m) {
        UserDAO uDAO = new UserDAO();
        String username = m.getUsername();

        // checks if the 'user' external-key exists in DB.
        if (uDAO.doRetrieveByUsername(username) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("UPDATE moderator SET contractTime = ?" +
                                                                " WHERE moderator.user = ?");

                st.setDate(1, m.getContractTimeAsDate());
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

    public boolean doDeleteByUsername(String username) {
        UserDAO uDAO = new UserDAO();

        // checks if the 'user' external-key exists in DB.
        if (uDAO.doRetrieveByUsername(username) != null) {
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

    public ArrayList<Moderator> doRetrieveAll() {
        UserDAO uDAO = new UserDAO();
        ArrayList<Moderator> moderators = new ArrayList<>();

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM moderator");
            ResultSet rs = st.executeQuery();

            Moderator m = null;
            String username = null;
            Date contractTime = null;
            User user = null;
            GregorianCalendar contractTimeAsCalendar = null;

            while (rs.next()) {
                contractTime = rs.getDate(1);
                username = rs.getString(2);

                user = uDAO.doRetrieveByUsername(username);
                // we need to convert 'sql.Date' to 'GregorianCalendar'
                contractTimeAsCalendar = new GregorianCalendar();
                contractTimeAsCalendar.setTime(contractTime);

                m = new Moderator(user, contractTimeAsCalendar);
                moderators.add(m);
            }
            rs.close();
            st.close();
            cn.close();
            return moderators;
        } catch (SQLException e) {
            return null;
        }
    }

    public Moderator doRetriveByUsername(String username) {
        UserDAO uDAO = new UserDAO();
        User user = null;

        if ((user = uDAO.doRetrieveByUsername(username)) != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement st = cn.prepareStatement("SELECT * FROM moderator WHERE moderator.user=?;");
                st.setString(1, username);
                ResultSet rs = st.executeQuery();

                Date contractTime = null;
                GregorianCalendar contractTimeAsCalendar= null;
                Moderator m = null;

                if (rs.next()) {
                    contractTime = rs.getDate(1);
                    contractTimeAsCalendar = new GregorianCalendar();
                    contractTimeAsCalendar.setTime(contractTime);

                    m = new Moderator(user, contractTimeAsCalendar);
                }
                rs.close();
                st.close();
                cn.close();
                return m;
            } catch (SQLException e) {
                return null;
            }
        }

        return null;
    }
}
