package model.dao;


import java.sql.*;
import java.util.ArrayList;
import model.bean.Category;
import model.bean.PhysicalProduct;
import model.bean.Tag;


public class PhysicalProductDAO {

    public ArrayList<PhysicalProduct> doRetrieveAll(int offset, int limit) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from physicalproduct LIMIT ?, ?");
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();

            ArrayList<PhysicalProduct> prodotti = new ArrayList<>();
            while (rs.next()) {
                PhysicalProduct p = new PhysicalProduct();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getDouble(3));
                p.setDescription(rs.getString(4));
                p.setImage(rs.getString(5));
                p.setWeight(rs.getDouble(6));
                p.setSize(rs.getString(7));
                p.setQuantity(rs.getInt(8));


                p.setCategories(doRetrieveAllProdCatById(p.getId()));
                p.setTags(doRetrieveAllProdTagById(p.getId()));

                prodotti.add(p);

            }

            return prodotti;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public PhysicalProduct doRetrieveById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from physicalproduct WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            PhysicalProduct p = null;

            if (rs.next()) {
                p = new PhysicalProduct();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getDouble(3));
                p.setDescription(rs.getString(4));
                p.setImage(rs.getString(5));
                p.setWeight(rs.getDouble(6));
                p.setSize(rs.getString(7));
                p.setQuantity(rs.getInt(8));



                p.setCategories(doRetrieveAllProdCatById(p.getId()));
                p.setTags(doRetrieveAllProdTagById(p.getId()));



            }

            return p;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


    public PhysicalProduct doSave(PhysicalProduct p) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("INSERT INTO physicalproduct( name, price,"
                    + " description, image, weight, size, quantity )"
                    + "values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, p.getName());
            st.setDouble(2, p.getPrice());
            st.setString(3, p.getDescription());
            st.setString(4, p.getImage());
            st.setDouble(5, p.getWeight());
            st.setString(6, p.getSize());
            st.setInt(7, p.getQuantity());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            p.setId(id);

            st.close();

            for (Category c : p.getCategories()) {
                st = cn.prepareStatement("INSERT INTO "
                        + "physicalbelonging(physicalProduct, category) VALUES (?,?)");
                st.setInt(1, id);
                st.setString(2, c.getName());
                st.executeUpdate();
            }

            st.close();

            for (Tag c : p.getTags()) {
                st = cn.prepareStatement("INSERT INTO "
                        + "physicalcharacteristic(physicalProduct, tag) VALUES (?,?)");
                st.setInt(1, id);
                st.setString(2, c.getName());
                st.executeUpdate();
            }

            st.close();
            cn.close();

            return p;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDelete(int id) {

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("DELETE FROM physicalproduct WHERE id=?;");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Restituisce tutte le catgeorie di un prodotto
    public ArrayList<Category> doRetrieveAllProdCatById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select category "
                    + "from physicalbelonging, physicalproduct "
                    + "where physicalbelonging.physicalProduct = physicalproduct.id "
                    + "and physicalproduct.id = ?;");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            ArrayList<Category> categories = new ArrayList<>();

            while (rs.next()) {
                CategoryDAO categoryDao = new CategoryDAO();
                Category temp;
                temp = categoryDao.doRetrieveByName(rs.getString(1));
                categories.add(temp);
            }

            return categories;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


    //Restituisce tutti i tag di un prodotto
    public ArrayList<Tag> doRetrieveAllProdTagById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select tag "
                    + "from physicalcharacteristic, physicalproduct "
                    + "where physicalcharacteristic.physicalProduct = physicalproduct.id "
                    + "and physicalproduct.id = ?;");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            ArrayList<Tag> tags = new ArrayList<>();

            while (rs.next()) {
                TagDAO tagDao = new TagDAO();
                Tag temp;
                temp = tagDao.doRetrieveByName(rs.getString(1));
                tags.add(temp);
            }

            return tags;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

}
