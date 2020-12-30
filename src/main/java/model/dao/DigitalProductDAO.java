package model.dao;

import java.sql.*;
import java.util.ArrayList;
import model.bean.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  DigitalProductDAO is used to do operation inside the table 'digitalproduct','digitalbelonging',
 *  and 'digitalcharacteristic' of database.
 *  DigitalProductDAO allow to do the CRUD operation on database (create, read, update, delete)
 *  It's possible to add a Digital Product, update a Digital Product, delete a Digital Product,
 *  read all the Digital Products, or just retrieve one Digital Product by id saved in the database.
 *  Also, it's possible to retrieve all Categories or Tags from a Digital Product's id, or retrieve
 *  all Digital Products giving one Tag or Category.
 *
 */

public class DigitalProductDAO {

    /**
     * This method allows to find all the Digital Product saved into the database,
     * using an offset and a limit.
     *
     * @param offset to select the starting range value of the Digital Product to retrieve
     * @param limit to select the ending range value of the Digital Product to retrieve
     * @return ArrayList formed by Digital Products, if these exist,
     *         it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     *
     */

    @NotNull
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

    /**
     * This method allow to find a Digital Product given its id.
     *
     * @param id an integer that it's a key for a search into the database
     * @return a Digital Product that corresponds to the id given from param, null otherwise
     *
     * @throws RuntimeException if an exception is occurred
     */

    @Nullable
    public DigitalProduct doRetrieveById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from digitalproduct WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            DigitalProduct p = null;

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

    /**
     * This method allow to save a Digital Product into the database.
     * Then, if the object is saved correctly, inserts into digitalbelonging table the id of the
     * saved Digital Product and its Categories. Finally, inserts into digitalcharacteristic table
     * the id of the saved Digital Product and its tags
     *
     * @param p the Digital Product object to save. It cannot be null
     * @return the Digital Product that has been saved in database
     * @throws RuntimeException if an exception is occurred
     */

    @Nullable
    public DigitalProduct doSave(@NotNull DigitalProduct p) {
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

    /**
     * This method allow to remove a Digital Product from the database.
     *
     * @param id an integer that it's a key for a search into the database
     * @throws RuntimeException if an exception is occurred
     */

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

    /**
     * This method allows to find all the Categories of a Digital Product saved into the database.
     *
     * @param id an integer that it's a key for a search into the database
     * @return an ArrayList formed by Categories, if there are categories saved into the database
     *          related to the Digital Product id given by param
     * @throws RuntimeException if an exception is occurred
     *
     */

    @NotNull
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
     * This method allows to find all the Tags of a Digital Product saved into the database.
     *
     * @param id an integer that it's a key for a search into the database
     * @return an ArrayList formed by Tags, if there are tags saved into the database
     *          related to the Digital Product's id given by param
     * @throws RuntimeException if an exception is occurred
     *
     */

    @NotNull
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
     * This method allows to find all the Digital Products of a Category, saved into the database.
     *
     * @param categoryName String that it's a key for a search into the database. It cannot be null
     * @param offset to select the starting range value of the Digital Product to retrieve
     * @param limit to select the ending range value of the Digital Product to retrieve
     * @return an ArrayList formed by Digital Products saved into the database
     *          related to the Category name given by param
     * @throws RuntimeException if an exception is occurred
     *
     */

    @NotNull
    public ArrayList<DigitalProduct> doRetrieveAllByCategory(
            @NotNull String categoryName, int offset, int limit) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select id, name, price,"
                    + " description, image, platform,"
                    + "releaseDate, requiredAge, softwareHouse, publisher, quantity "
                    + "from digitalbelonging, digitalproduct "
                    + "where digitalbelonging.digitalProduct = digitalproduct.id "
                    + "and digitalbelonging.category = ? LIMIT ?, ?;");

            ps.setString(1, categoryName);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
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

    /**
     * This method allows to find all the Digital Products of a Tag, saved into the database.
     *
     * @param tagName String that it's a key for a search into the database. It cannot be null
     * @param offset to select the starting range value of the Digital Product to retrieve
     * @param limit to select the ending range value of the Digital Product to retrieve
     * @return an ArrayList formed by Digital Products saved into the database
     *          related to the Tag name given by param
     * @throws RuntimeException if an exception is occurred
     *
     */

    @NotNull
    public ArrayList<DigitalProduct> doRetrieveAllByTag(
            @NotNull String tagName, int offset, int limit) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select id, name, price,"
                    + " description, image, platform,"
                    + "releaseDate, requiredAge, softwareHouse, publisher, quantity "
                    + "from digitalcharacteristic, digitalproduct "
                    + "where digitalcharacteristic.digitalProduct = digitalproduct.id "
                    + "and digitalcharacteristic.tag = ? LIMIT ?, ?;");

            ps.setString(1, tagName);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
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

    /**
     * This method allows to update a Digital Product into the database, if it exists.
     * Then, if updated, updates also every relations in
     * digitalbelonging and digitalcharacteristic tables.
     *
     * @param p the Digital Product object to update. It cannot be null
     * @throws RuntimeException if an exception is occurred
     *
     */

    public void doUpdate(@NotNull DigitalProduct p) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE digitalproduct SET name=?, "
                    + "price=?, description=?, image=?, platform=?, releaseDate=?,"
                    + " requiredAge=?, softwareHouse=?, publisher=?, quantity=? WHERE id=?;");

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getImage());
            ps.setString(5, p.getPlatform());
            ps.setString(6, p.getReleaseDate());
            ps.setInt(7, p.getRequiredAge());
            ps.setString(8, p.getSoftwareHouse());
            ps.setString(9, p.getPublisher());
            ps.setInt(10, p.getQuantity());
            ps.setInt(11, p.getId());

            ps.executeUpdate();

            ps = con.prepareStatement("DELETE FROM digitalbelonging WHERE digitalProduct=?;");
            ps.setInt(1, p.getId());
            ps.executeUpdate();

            for (Category c : p.getCategories()) {
                ps = con.prepareStatement("INSERT INTO "
                        + "digitalbelonging(digitalProduct, category) VALUES (?,?)");
                ps.setInt(1, p.getId());
                ps.setString(2, c.getName());
                ps.executeUpdate();
            }

            ps = con.prepareStatement("DELETE FROM digitalcharacteristic WHERE digitalProduct=?;");
            ps.setInt(1, p.getId());
            ps.executeUpdate();

            for (Tag c : p.getTags()) {
                ps = con.prepareStatement("INSERT INTO "
                        + "digitalcharacteristic(digitalProduct, tag) VALUES (?,?)");
                ps.setInt(1, p.getId());
                ps.setString(2, c.getName());
                ps.executeUpdate();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * This method allows to find all the Digital Product saved into the database,
     * using the following parameters.
     *
     * @param name String name of a Digital Product. It cannot be null
     * @param desc String description of a Digital Product. It cannot be null
     * @param price Double price value of a Digital Product. It cannot be null
     * @param softHouse String Software House name of a Digital Product. It cannot be null
     * @param nameTag String tag name of a Digital Product. It cannot be null
     * @param nameCategory String category name of a Digital Product. It cannot be null
     * @param offset to select the starting range value of the Digital Product to retrieve
     * @param limit to select the ending range value of the Digital Product to retrieve
     * @return an ArrayList formed by Digital Products, if there are digital product saved
     *          into the database, it returns the ArrayList else an empty ArrayList
     * @throws RuntimeException if an exception is occurred
     *
     */

    @NotNull
    public ArrayList<DigitalProduct> doRetrieveByAllFragment(
             @NotNull String name, @NotNull String desc, @NotNull Double price,
             @NotNull String softHouse, @NotNull String nameTag, @NotNull String nameCategory,
             int offset, int limit) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT dp.id, dp.name, dp.price, dp.description, "
                    + "dp.image, dp.platform, dp.releaseDate, dp.requiredAge, dp.softwareHouse, "
                    + "dp.publisher, dp.quantity"
                    + " FROM digitalbelonging db,digitalcharacteristic dc,digitalproduct dp "
                    + " where db.digitalProduct=dp.id and dc.digitalProduct=dp.id "
                    + "       and LOWER(dp.name) LIKE ? and LOWER(dp.description) LIKE ? "
                    + "       and dp.price <= ? and LOWER(dp.softwareHouse) LIKE ? "
                    + "       and LOWER(dc.tag) LIKE ? and LOWER(db.category) LIKE ?"
                    + " group by dp.id, dp.description, dp.image, dp.name, dp.platform, "
                    + "          dp.price, dp.publisher, dp.quantity, dp.releaseDate,"
                    + " dp.requiredAge, dp.softwareHouse "
                    + "ORDER BY dp.id desc LIMIT ?,?; ");

            ps.setString(1, "%" + name.toLowerCase() + "%");
            ps.setString(2, "%" + desc.toLowerCase() + "%");
            ps.setDouble(3, price);
            ps.setString(4, "%" + softHouse.toLowerCase() + "%");
            ps.setString(5, "%" + nameTag.toLowerCase() + "%");
            ps.setString(6, "%" + nameCategory.toLowerCase() + "%");
            ps.setInt(7, offset);
            ps.setInt(8, limit);

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
}
