package model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TagDAO {

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

    @Nullable
    public Tag doRetrieveByName(@NotNull String name) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM tag T WHERE T.name=?;");
            st.setString(1, name);
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
