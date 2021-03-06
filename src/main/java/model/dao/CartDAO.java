package model.dao;

import java.sql.*;
import model.bean.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  CartDAO is used to do operation inside the table 'cart', 'digitalcontaining'
 *  and 'physicalcontaining' of database.
 *  CartDAO allow to do the CRUD operation on database (create, read, update, delete)
 *  It's possible to store a Cart, update a Cart, delete a Cart, read a Cart given its owner.
 */

public class CartDAO {
    @NotNull
    private final UserDAO udao = new UserDAO();
    @NotNull
    private final DigitalProductDAO dcd = new DigitalProductDAO();
    @NotNull
    private final PhysicalProductDAO pcd = new PhysicalProductDAO();

    /**
     * This method allows to save or update a Cart into the database.
     *
     * @param c the object Cart to save or update, must be not null
     */
    public void doSaveOrUpdate(@NotNull Cart c) {
        if (c.getUser() != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement ps = cn.prepareStatement(
                        "INSERT INTO cart (user, totalPrice, numberOfProduct) VALUES (?,?, ?) "
                                + "ON DUPLICATE KEY UPDATE numberOfProduct=numberOfProduct;");
                ps.setString(1, c.getUser().getUsername());
                ps.setDouble(2, 0);
                ps.setInt(3, 0);
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
                ps = cn.prepareStatement("DELETE FROM digitalcontaining WHERE cart=?;");
                ps.setString(1, c.getUser().getUsername());
                ps.executeUpdate();
                ps = cn.prepareStatement("DELETE FROM physicalcontaining WHERE cart=?;");
                ps.setString(1, c.getUser().getUsername());
                ps.executeUpdate();
                for (Product p : c.getAllProducts()) {
                    if (p instanceof DigitalProduct) {
                        ps = cn.prepareStatement(
                                "INSERT INTO digitalcontaining (digitalProduct, cart, quantity) "
                                        + "VALUES (?,?, ?) ON DUPLICATE KEY UPDATE quantity=?;");
                        ps.setInt(1, p.getId());
                        ps.setString(2, c.getUser().getUsername());
                        ps.setInt(3, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                        ps.setInt(4, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                        if (ps.executeUpdate() != 1) {
                            throw new RuntimeException("INSERT error.");
                        }
                    } else if (p instanceof PhysicalProduct) {
                        ps = cn.prepareStatement(
                                "INSERT INTO physicalcontaining (physicalProduct, cart, quantity) "
                                        + "VALUES (?,?, ?) ON DUPLICATE KEY UPDATE quantity=?;");
                        ps.setInt(1, p.getId());
                        ps.setString(2, c.getUser().getUsername());
                        ps.setInt(3, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                        ps.setInt(4, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                        if (ps.executeUpdate() != 1) {
                            throw new RuntimeException("INSERT error.");
                        }
                    }
                }
                ps.close();
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("User is null!");
        }
    }

    /**
     * This method allows to get a Cart starting from its owner's username.
     *
     * @param username the owner of the searched cart, must be not null
     * @return if exists a Cart that is owned by the username given from param, null otherwise
     */
    @Nullable
    public Cart doRetrieveByUsername(@NotNull String username) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            User u = udao.doRetrieveByUsername(username);
            Cart c = null;
            if (u != null) {
                ps = cn.prepareStatement("SELECT * FROM cart C WHERE C.user=?;");
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next()) {
                    c = new Cart(u);
                    ps = cn.prepareStatement("SELECT * FROM digitalcontaining C WHERE C.cart=?;");
                    ps.setString(1, username);
                    rs = ps.executeQuery();
                    DigitalProduct dp;
                    while (rs.next()) {
                        dp = dcd.doRetrieveById(rs.getInt(1));
                        c.addProduct(dp, rs.getInt(3));
                    }
                    ps = cn.prepareStatement("SELECT * FROM physicalcontaining C WHERE C.cart=?;");
                    ps.setString(1, username);
                    rs = ps.executeQuery();
                    PhysicalProduct pp;
                    while (rs.next()) {
                        pp = pcd.doRetrieveById(rs.getInt(1));
                        c.addProduct(pp, rs.getInt(3));
                    }
                }
                ps.close();
                cn.close();
            }
            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allows to add product in a cart stored into the database.
     *
     * @param c the cart that needs to be modified, must be not null
     * @param p the product that need to be add to the cart, must be not null
     * @return the product if it was successfully added, otherwise null
     */
    @Nullable
    public Product doAddProduct(@NotNull Cart c, @NotNull Product p) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement ps;
            User u = c.getUser();

            if (u == null) {
                return null;
            } else if (p instanceof DigitalProduct) {
                ps = cn.prepareStatement("INSERT INTO digitalcontaining values "
                        + "(?, ?, ?) on duplicate key update quantity=?;");
                ps.setInt(1, p.getId());
                ps.setString(2, u.getUsername());
                ps.setInt(3, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                ps.setInt(4, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                ps.executeUpdate();
            } else if (p instanceof PhysicalProduct) {
                ps = cn.prepareStatement("INSERT INTO physicalcontaining values "
                        + "(?, ?, ?) on duplicate key update quantity=?;");
                ps.setInt(1, p.getId());
                ps.setString(2, u.getUsername());
                ps.setInt(3, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                ps.setInt(4, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                ps.executeUpdate();
            }
            return p;

        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method allows to remove a product from a cart stored into the database.
     *
     * @param c the cart that needs to be modified, must be not null
     * @param p the product that need to be removed from the cart, must be not null
     * @return the product if it was successfully removed, otherwise null
     */
    @Nullable
    public Product doRemoveProduct(@NotNull Cart c, @NotNull Product p) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement ps;
            User u = c.getUser();

            if (u == null) {
                return null;
            } else if (p instanceof DigitalProduct) {
                ps = cn.prepareStatement(
                        "DELETE FROM digitalcontaining where cart=? AND digitalProduct=?;"
                );
                ps.setString(1, u.getUsername());
                ps.setInt(2, p.getId());
                if (ps.executeUpdate() < 1) {
                    throw new RuntimeException("Product is not contained in the cart.");
                }
                if (c.getQuantitySingleProduct(p.getId(), p.getClass()) > 0) {
                    ps = cn.prepareStatement("INSERT INTO digitalcontaining values "
                            + "(?, ?, ?) on duplicate key update quantity=?;");
                    ps.setInt(1, p.getId());
                    ps.setString(2, u.getUsername());
                    ps.setInt(3, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                    ps.setInt(4, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                    ps.executeUpdate();
                }
            } else if (p instanceof PhysicalProduct) {
                ps = cn.prepareStatement(
                        "DELETE FROM physicalcontaining where cart=? AND physicalProduct=?;"
                );
                ps.setString(1, u.getUsername());
                ps.setInt(2, p.getId());
                if (ps.executeUpdate() < 1) {
                    throw new RuntimeException("Product is not contained in the cart.");
                }
                ps = cn.prepareStatement("INSERT INTO physicalcontaining values "
                        + "(?, ?, ?) on duplicate key update quantity=?;");
                ps.setInt(1, p.getId());
                ps.setString(2, u.getUsername());
                ps.setInt(3, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                ps.setInt(4, c.getQuantitySingleProduct(p.getId(), p.getClass()));
                ps.executeUpdate();
            }
            return p;

        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method allows to empty a cart store into the database.
     *
     * @param c the cart that needs to be emptied, must be not null
     */
    public void doRemoveAllUserCartProducts(@NotNull Cart c) {
        if (c.getUser() != null) {
            try {
                Connection cn = ConPool.getConnection();
                PreparedStatement ps1;
                PreparedStatement ps2;

                ps1 = cn.prepareStatement("DELETE FROM digitalcontaining WHERE cart=?;");
                ps1.setString(1, c.getUser().getUsername());
                ps2 = cn.prepareStatement("DELETE FROM physicalcontaining WHERE cart=?;");
                ps2.setString(1, c.getUser().getUsername());
                if (ps1.executeUpdate() == 0 && ps2.executeUpdate() == 0) {
                    throw new RuntimeException("The cart doesn't exist.");
                }
                ps1.close();
                ps2.close();
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("User is null!");
        }
    }
}
