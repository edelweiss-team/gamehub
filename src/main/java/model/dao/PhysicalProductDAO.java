package model.dao;


import java.sql.*;
import java.util.ArrayList;
import model.bean.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PhysicalProductDAO {

    @NotNull
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

    @Nullable
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

    @Nullable
    public PhysicalProduct doSave(@NotNull PhysicalProduct p) {
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
    @NotNull
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
    @NotNull
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

    @NotNull
    public ArrayList<PhysicalProduct> doRetrieveAllByCategory(
            @NotNull String categoryName, int offset, int limit) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select id, name, price, "
                    + "description, image, weight, size, quantity "
                    + "from physicalbelonging, physicalproduct "
                    + "where physicalbelonging.physicalProduct = physicalproduct.id "
                    + "and physicalbelonging.category = ? LIMIT ?, ?;");

            ps.setString(1, categoryName);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
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

    public void doUpdate(@NotNull PhysicalProduct p) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE physicalproduct SET name=?, "
                    + "price=?, description=?, image=?, weight=?, size=?, "
                    + "quantity=? WHERE id=?;");

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getImage());
            ps.setDouble(5, p.getWeight());
            ps.setString(6, p.getSize());
            ps.setInt(7, p.getQuantity());
            ps.setInt(8, p.getId());

            ps.executeUpdate();



            ps = con.prepareStatement("DELETE FROM physicalbelonging WHERE physicalProduct=?;");
            ps.setInt(1, p.getId());
            ps.executeUpdate();

            for (Category c : p.getCategories()) {
                ps = con.prepareStatement("INSERT INTO "
                        + "physicalbelonging(physicalProduct, category) VALUES (?,?)");
                ps.setInt(1, p.getId());
                ps.setString(2, c.getName());
                ps.executeUpdate();
            }

            ps = con.prepareStatement("DELETE FROM "
                    + "physicalcharacteristic WHERE physicalProduct=?;");
            ps.setInt(1, p.getId());
            ps.executeUpdate();

            for (Tag c : p.getTags()) {
                ps = con.prepareStatement("INSERT INTO "
                        + "physicalcharacteristic(physicalProduct, tag) VALUES (?,?)");
                ps.setInt(1, p.getId());
                ps.setString(2, c.getName());
                ps.executeUpdate();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @NotNull
    public ArrayList<PhysicalProduct> doRetrieveByAllFragment(
            @NotNull String name, @NotNull String desc, @NotNull Double price,
            @NotNull String nameTag, @NotNull String nameCategory,
            int offset, int limit) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT dp.id, dp.name, dp.price, dp.description, "
                            + "dp.image, dp.weight, dp.size, dp.quantity"
                            + " FROM physicalbelonging db,physicalcharacteristic dc,"
                            + "physicalproduct dp "
                            + " where db.physicalProduct=dp.id and dc.physicalProduct=dp.id "
                            + "       and LOWER(dp.name) LIKE ? and LOWER(dp.description) LIKE ? "
                            + "       and dp.price <= ? "
                            + "       and LOWER(dc.tag) LIKE ? and LOWER(db.category) LIKE ?"
                            + " group by  dp.id, dp.name, dp.price, dp.description, "
                            + "dp.image, dp.weight, dp.size, dp.quantity "
                            + "LIMIT ?,?; ");

            ps.setString(1, "%" + name.toLowerCase() + "%");
            ps.setString(2, "%" + desc.toLowerCase() + "%");
            ps.setDouble(3, price);
            ps.setString(4, "%" + nameTag.toLowerCase() + "%");
            ps.setString(5, "%" + nameCategory.toLowerCase() + "%");
            ps.setInt(6, offset);
            ps.setInt(7, limit);

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


}
