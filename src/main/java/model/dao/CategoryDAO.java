package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.bean.Category;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class CategoryDAO {

    public @Nullable Category doRetrieveByName(@NotNull String name) {
        try {
            Connection cn = ConPool.getConnection();
            Statement st = cn.createStatement();
            Category category = null;
            ResultSet rs = st.executeQuery("SELECT * FROM Category C WHERE C.name='" + name + "';");
            if (rs.next()) {
                category = new Category(rs.getString(1), rs.getString(2), rs.getString(3));
            }
            st.close();
            cn.close();
            return category;
        } catch (SQLException e) {
            return null;
        }
    }

    public void doDeleteByName(@NotNull String name) {
        try {
            Connection cn = ConPool.getConnection();
            Statement st = cn.createStatement();
            st.executeUpdate("DELETE FROM Category WHERE name='" + name + "';");
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(Category c) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO Category (name, description, image) VALUES (?,?, ?);");
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getImage());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ps.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateByName(Category c, @NotNull String oldName) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement ps = cn.prepareStatement(
                    "update Category set name=?, description=?, image=? where name=?");
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getImage());
            ps.setString(4, oldName);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ps.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull ArrayList<Category> doRetrieveAll() throws SQLException {
        Connection cn = ConPool.getConnection();
        Statement st = cn.createStatement();
        ArrayList<Category> categories = new ArrayList<>();
        ResultSet rs = st.executeQuery("SELECT * FROM Category;");
        while (rs.next()) {
            Category category = new Category(rs.getString(1), rs.getString(2),
                    rs.getString(3));
            categories.add(category);
        }
        st.close();
        cn.close();
        return categories;
    }

    public @NotNull ArrayList<Category> doRetrieveByNameFragment(@NotNull String name, int offset,
                                                                 int limit) throws SQLException {
        Connection cn = ConPool.getConnection();
        ArrayList<Category> categories = new ArrayList<>();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM Category where name like ? limit ? offset ?;");
        String likeString = "%" + name + "%";
        ps.setString(1, likeString);
        ps.setInt(2, limit);
        ps.setInt(3, offset);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Category category = new Category(rs.getString(1), rs.getString(2),
                    rs.getString(3));
            categories.add(category);
        }
        ps.close();
        cn.close();
        return categories;
    }
}
