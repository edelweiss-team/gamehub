package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jep.Run;
import model.bean.Category;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  CategoryDAO is used to do operation inside the table 'category' of database.
 *  CategoryDAO allow to do the CRUD operation on database (create, read, update, delete)
 *  It's possible to add a Category, update a Category, delete a Category, read all the categories
 *  saved into the database, read a Category given its name.
 *
 */

public class CategoryDAO {

    /**
     * This method allow to find a Category given its name.
     *
     * @param name a String that it's a key for a search into the database, must be not null
     * @return a Category that corresponds to the name given from param, null otherwise
     *
     * @throws RuntimeException if an exception is occurred
     */
    public @Nullable Category doRetrieveByName(@NotNull String name) {
        try {
            Connection cn = ConPool.getConnection();
            Statement st = cn.createStatement();
            Category category = null;
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM category C WHERE C.name=?;");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
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

    /**
     * This method allow to remove a Category from the database.
     *
     * @param name an unique String that identify a Category
     * @throws RuntimeException if an exception is occurred
     */

    public void doDeleteByName(@NotNull String name) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("DELETE FROM category WHERE name=?;");
            st.setString(1, name);
            if (st.executeUpdate() != 1) {
                throw new RuntimeException();
            }
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allow to save a Category into the database.
     *
     * @param c the object Category to save
     * @throws RuntimeException if an exception is occurred
     */
    public void doSave(@NotNull Category c) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO category (name, description, image) VALUES (?,?, ?);");
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

    /**
     * This method allows to update a Category into the database.
     *
     * @param c the object Category to update
     * @param oldName the oldname of the category
     */
    public void doUpdateByName(Category c, @NotNull String oldName) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement ps = cn.prepareStatement(
                    "update category set name=?, description=?, image=? where name=?");
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

    /**
     * This method allows to find all the Categories saved into the database.
     *
     * @return an ArrayList formed by Categories, if there are categories saved into the database
     *         it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     */

    public @NotNull ArrayList<Category> doRetrieveAll() {
        try {
            Connection cn = ConPool.getConnection();
            Statement st = cn.createStatement();
            ArrayList<Category> categories = new ArrayList<>();
            ResultSet rs = st.executeQuery("SELECT * FROM category;");
            while (rs.next()) {
                Category category = new Category(rs.getString(1), rs.getString(2),
                        rs.getString(3));
                categories.add(category);
            }
            st.close();
            cn.close();
            return categories;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allows to find all the Categories saved into the database
     * given a fragment of its name.
     *
     * @param name a fragment of the category name used to search some categories
     * @param offset the offset from which the database search is started
     * @param limit the max number of categories must be returned
     * @return an ArrayList formed by Categories, if there are categories
     *         that match the search parameter it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     */
    public @NotNull ArrayList<Category> doRetrieveByNameFragment(@NotNull String name, int offset, int limit)  {
        try {
            Connection cn = ConPool.getConnection();
            ArrayList<Category> categories = new ArrayList<>();
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM category "
                    + "where name like ? limit ? offset ?;");
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
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
