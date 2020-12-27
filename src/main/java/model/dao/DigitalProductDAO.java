package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.bean.Category;
import model.bean.DigitalProduct;
import model.bean.Product;
import model.bean.Tag;


public class DigitalProductDAO {

    public ArrayList<DigitalProduct> doRetrieveAll(int offset, int limit) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from digitalproduct LIMIT ?, ?");
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();

            ArrayList<DigitalProduct> prodotti = new ArrayList<>();
            while (rs.next()) {
                DigitalProduct p = new DigitalProduct();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getDouble(3));
                p.setDescription(rs.getString(4));
                p.setImage(rs.getString(5));
                p.setPlatform(rs.getString(6));
                p.setReleaseDate(rs.getString(7));
                p.setRequiredAge(rs.getInt(8));
                p.setSoftwareHouse(rs.getString(9));
                p.setPublisher(rs.getString(10));
                p.setQuantity(rs.getInt(11));


                p.setCategories(doRetrieveAllProdCatById(p.getId()));
                p.setTags(doRetrieveAllProdTagById(p.getId()));

                prodotti.add(p);

            }

            return prodotti;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public DigitalProduct doRetrieveById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from digitalproduct WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            DigitalProduct p =null;

            if (rs.next()) {
                p = new DigitalProduct();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getDouble(3));
                p.setDescription(rs.getString(4));
                p.setImage(rs.getString(5));
                p.setPlatform(rs.getString(6));
                p.setReleaseDate(rs.getString(7));
                p.setRequiredAge(rs.getInt(8));
                p.setSoftwareHouse(rs.getString(9));
                p.setPublisher(rs.getString(10));
                p.setQuantity(rs.getInt(11));


                p.setCategories(doRetrieveAllProdCatById(p.getId()));
                p.setTags(doRetrieveAllProdTagById(p.getId()));



            }

            return p;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


    public DigitalProduct doSave(DigitalProduct p) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("INSERT INTO digitalproduct(name, price,"
                    + " description, image, platform, releaseDate, requiredAge, "
                    + "softwareHouse, publisher, quantity)"
                    + "values (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, p.getName());
            st.setDouble(2, p.getPrice());
            st.setString(3, p.getDescription());
            st.setString(4, p.getImage());
            st.setString(5, p.getPlatform());
            st.setString(6, p.getReleaseDate());
            st.setInt(7, p.getRequiredAge());
            st.setString(8, p.getSoftwareHouse());
            st.setString(9, p.getPublisher());
            st.setInt(10, p.getQuantity());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            p.setId(id);

            st.close();

            for (Category c : p.getCategories()) {
                st = cn.prepareStatement("INSERT INTO "
                        + "digitalbelonging(digitalProduct, category) VALUES (?,?)");
                st.setInt(1, id);
                st.setString(2, c.getName());
                st.executeUpdate();
            }

            st.close();

            for (Tag c : p.getTags()) {
                st = cn.prepareStatement("INSERT INTO "
                        + "digitalcharacteristic(digitalProduct, tag) VALUES (?,?)");
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
            PreparedStatement st = cn.prepareStatement("DELETE FROM digitalproduct WHERE id=?;");
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
                    + "from digitalbelonging, digitalproduct "
                    + "where digitalbelonging.digitalProduct = digitalproduct.id "
                    + "and digitalproduct.id = ?;");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            ArrayList<Category> categories = new ArrayList<>();

            while (rs.next()) {
                CategoryDAO categoryDAO = new CategoryDAO();
                Category temp;
                temp = categoryDAO.doRetrieveByName(rs.getString(1));
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
                    + "from digitalcharacteristic, digitalproduct "
                    + "where digitalcharacteristic.digitalProduct = digitalproduct.id "
                    + "and digitalproduct.id = ?;");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            ArrayList<Tag> tags = new ArrayList<>();

            while (rs.next()) {
                TagDAO tagDAO = new TagDAO();
                Tag temp;
                temp = tagDAO.doRetrieveByName(rs.getString(1));
                tags.add(temp);
            }

            return tags;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

}
