package model.dao;


import java.sql.*;
import java.util.ArrayList;
import model.bean.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  PhysicalProductDAO is used to do operation inside 'physicalproduct','physicalbelonging',
 *  and 'physicalcharacteristic' tables of the database.
 *  PhysicalProductDAO allow to do the CRUD operation on database (create, read, update, delete)
 *  It's possible to add a Physical Product, update a Physical Product, delete a Physical Product,
 *  read all the Physical Products, or just retrieve one Physical Product by id saved in database.
 *  Also, it's possible to retrieve all Categories or Tags from a Physical Product's id, or retrieve
 *  all Physical Products giving one Tag or Category.
 *
 */

public class PhysicalProductDAO {

    /**
     * This method allows to find all the Physical Product saved into the database,
     * using an offset and a limit.
     *
     * @param offset to select the starting range value of the Physical Product to retrieve
     * @param limit to select the ending range value of the Physical Product to retrieve
     * @return ArrayList formed by Physical Products, if these exist,
     *         it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     *
     */

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

    /**
     * This method allow to find a Physical Product given its id.
     *
     * @param id an integer that it's a key for a search into the database
     * @return a Physical Product that corresponds to the id given from param, null otherwise
     *
     * @throws RuntimeException if an exception is occurred
     */

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

    /**
     * This method allow to save a Physical Product into the database.
     * Then, if the object is saved correctly, inserts into physicalbelonging table the id of the
     * saved Physical Product and its Categories. Finally, inserts into physicalcharacteristic table
     * the id of the saved Physical Product and its tags
     *
     * @param p the Physical Product object to save. It cannot be null
     * @return the Physical Product that has been saved in database
     * @throws RuntimeException if an exception is occurred
     */

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

    /**
     * This method allow to remove a Physical Product from the database.
     *
     * @param id an integer that it's a key for a search into the database
     * @throws RuntimeException if an exception is occurred
     */

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

    /**
     * This method allows to find all the Categories of a Physical Product saved into the database.
     *
     * @param id an integer that it's a key for a search into the database
     * @return an ArrayList formed by Categories, if there are categories saved into the database
     *          related to the Physical Product id given by param
     * @throws RuntimeException if an exception is occurred
     *
     */

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

    /**
     * This method allows to find all the Tags of a Physical Product saved into the database.
     *
     * @param id an integer that it's a key for a search into the database
     * @return an ArrayList formed by Tags, if there are tags saved into the database
     *          related to the Physical Product's id given by param
     * @throws RuntimeException if an exception is occurred
     *
     */

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

    /**
     * This method allows to find all the Physical Products of a Category, saved into the database.
     *
     * @param categoryName String that it's a key for a search into the database. It cannot be null
     * @param offset to select the starting range value of the Physical Product to retrieve
     * @param limit to select the ending range value of the Physical Product to retrieve
     * @return an ArrayList formed by Physical Products saved into the database
     *          related to the Category name given by param
     * @throws RuntimeException if an exception is occurred
     *
     */

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

    /**
     * This method allows to update a Physical Product into the database, if it exists.
     * Then, if updated, updates also every relations in
     * physicalbelonging and physicalcharacteristic tables.
     *
     * @param p the Physical Product object to update. It cannot be null
     * @throws RuntimeException if an exception is occurred
     *
     */

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

    /**
     * This method allows to find all the Physical Product saved into the database,
     * using the following parameters.
     *
     * @param name String name of a Physical Product. It cannot be null
     * @param desc String description of a Physical Product. It cannot be null
     * @param price Double price value of a Physical Product. It cannot be null
     * @param nameTag String tag name of a Physical Product. It cannot be null
     * @param nameCategory String category name of a Physical Product. It cannot be null
     * @param offset to select the starting range value of the Physical Product to retrieve
     * @param limit to select the ending range value of the Physical Product to retrieve
     * @return an ArrayList formed by Physical Products, if there are Physical product saved
     *          into the database, it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     *
     */

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
