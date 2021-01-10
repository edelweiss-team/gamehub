package model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  TagDAO is used to do operation inside the table 'tag' of database.
 *  TagDAO allow to do the CRUD operation on database (create, read, update, delete)
 *  It's possible to add a Tag, update a Tag, delete a Tag, read all the Tags
 *  saved into the database, read a Tag given is name.
 *
 */

public class TagDAO {

    /**
     * This method allow to save a Tag into the database.
     *
     * @param t the object Tag to save
     * @throws RuntimeException if an exception is occurred
     */

    public void doSave(@NotNull Tag t) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("INSERT INTO tag(name)"
                    + "values (?);");
            st.setString(1, t.getName());
            st.executeUpdate();
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allow to find a Tag given its name.
     *
     * @param name a String that it's a key for a search into the database, must be not null
     * @return a Tag that corresponds to the name given from param, null otherwise
     *
     * @throws RuntimeException if an exception is occurred
     */
    @Nullable
    public Tag doRetrieveByName(@NotNull String name) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement(
                    "SELECT * FROM tag T WHERE lower(T.name)=?;"
            );
            st.setString(1, name.toLowerCase());
            ResultSet rs = st.executeQuery();
            Tag tag = null;
            if (rs.next()) {
                tag = new Tag();
                tag.setName(rs.getString(1));

            }
            rs.close();
            st.close();
            cn.close();
            return tag;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method allow to remove a Tag from the database.
     *
     * @param name an unique String that identify a Tag
     * @throws RuntimeException if an exception is occurred
     */
    public void doDelete(@NotNull String name) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("DELETE FROM tag WHERE name=?;");
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
     * This method allows to find all the Tags saved into the database
     * given a fragment of its name.
     *
     * @param nameFragment a fragment of the tag name used to search some tags
     * @param offset the offset from which the database search is started
     * @param limit the max number of tags must be returned
     * @return an ArrayList formed by Tags, if there are tags
     *         that match the search parameter it returns the ArrayList else an empty ArrayList
     */
    @NotNull
    public ArrayList<Tag> doRetrieveByNameFragment(
            @NotNull String nameFragment, int offset, int limit) {
        ArrayList<Tag> tags = new ArrayList<>();
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * "
                    + "FROM tag T WHERE LOWER(T.name) LIKE ? LIMIT ? OFFSET ?;");
            st.setString(1, "%" + nameFragment.toLowerCase() + "%");
            st.setInt(2, limit);
            st.setInt(3, offset);
            ResultSet rs = st.executeQuery();
            Tag t = null;
            while (rs.next()) {
                t = new Tag();
                t.setName(rs.getString(1));

                tags.add(t);
            }
            rs.close();
            st.close();
            cn.close();
            return tags;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
