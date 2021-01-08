package model.dao;




import java.sql.*;
import java.util.ArrayList;
import model.bean.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  OrderDAO is used to do operations inside the table 'order', 'digitalpurchasing'
 *  and 'physicalpurchasing' of database.
 *  OrderDAO allow to do the CRUD operation on database (create, read, update, delete)
 *  It's possible to store a Order, update a Order, delete a Order, read a Order given its owner.
 */

public class OrderDAO {

    @NotNull
    private final UserDAO ud = new UserDAO();
    @NotNull
    private final DigitalProductDAO dpd = new DigitalProductDAO();
    @NotNull
    private final PhysicalProductDAO ppd = new PhysicalProductDAO();
    @NotNull
    private final OperatorDAO opd = new OperatorDAO();

    /**
     * This method allows to get a Order starting from its owner's username.
     *
     * @param username the owner of the searched order, must be not null
     * @return if exists a Order that is owned by the username given from param, null otherwise
     */

    @Nullable
    public ArrayList<Order> doRetrieveByUsername(@NotNull String username) {
        try {
            Connection cn =  ConPool.getConnection();
            PreparedStatement st;
            User u = ud.doRetrieveByUsername(username);
            Order o = null;
            Operator op = null;
            ArrayList<Order> orders = new ArrayList<>();
            if (u != null) {
                st = cn.prepareStatement("SELECT * FROM `order` O WHERE O.user=?;");
                st.setString(1, username);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    o = new Order();
                    o.setUser(u);
                    o.setId(rs.getInt(2));
                    o.setTotPrice(rs.getDouble(3));
                    o.setNumberOfItems(rs.getInt(4));
                    o.setData(rs.getString(5));
                    if (rs.getString(6) != null) {
                        op = opd.doRetrieveByUsername(rs.getString(6));
                    } else {
                        op = null;
                    }
                    o.setOperator(op);
                    st = cn.prepareStatement("SELECT * FROM digitalpurchasing D WHERE D.order=?;");
                    st.setInt(1, o.getId());
                    ResultSet rs2 = st.executeQuery();
                    DigitalProduct dp = null;
                    while (rs2.next()) {
                        dp = dpd.doRetrieveById(rs2.getInt(1));
                        o.addProduct(dp, rs2.getInt(3));
                    }
                    st = cn.prepareStatement("SELECT * FROM physicalpurchasing P WHERE P.order=?;");
                    st.setInt(1, o.getId());
                    rs2 = st.executeQuery();
                    PhysicalProduct pp = null;
                    while (rs2.next()) {
                        pp = ppd.doRetrieveById(rs2.getInt(1));
                        o.addProduct(pp, rs2.getInt(3));
                    }
                    orders.add(o);
                }
                st.close();
                cn.close();
            }
            return orders;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Gets a list of the non-approved orders in the database.
     *
     * @param limit max number of results in the list
     * @param offset the starting index of the result list
     * @return a list of all the non-approved orders of the database.
     */
    @Nullable
    public ArrayList<Order> doRetrieveNonApproved(int offset, int limit) {
        try {
            Connection cn =  ConPool.getConnection();
            PreparedStatement st;
            Order o = null;
            User u = null;
            ArrayList<Order> orders = new ArrayList<>();
            st = cn.prepareStatement(
                    "SELECT * FROM `order` O WHERE O.operator IS NULL limit ? offset ?;"
            );
            st.setInt(1, limit);
            st.setInt(2, offset);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                o = new Order();
                o.setOperator(null);
                o.setId(rs.getInt(2));
                o.setTotPrice(rs.getDouble(3));
                o.setNumberOfItems(rs.getInt(4));
                o.setData(rs.getString(5));
                u = ud.doRetrieveByUsername(rs.getString(1));
                o.setUser(u);
                st = cn.prepareStatement(
                        "SELECT * FROM digitalpurchasing D WHERE D.order=?;"
                );
                st.setInt(1, o.getId());
                ResultSet rs2 = st.executeQuery();
                DigitalProduct dp = null;
                while (rs2.next()) {
                    dp = dpd.doRetrieveById(rs2.getInt(1));
                    o.addProduct(dp, rs2.getInt(3));
                }
                st = cn.prepareStatement(
                        "SELECT * FROM physicalpurchasing P WHERE P.order=?;"
                );
                st.setInt(1, o.getId());
                rs2 = st.executeQuery();
                PhysicalProduct pp = null;
                while (rs2.next()) {
                    pp = ppd.doRetrieveById(rs2.getInt(1));
                    o.addProduct(pp, rs2.getInt(3));
                }
                orders.add(o);
            }
            st.close();
            cn.close();

            return orders;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method allows to get a list of order starting from its operator.
     *
     * @param operatorUsername that approved the orders
     * @return if exists a list of Order that is approved by the operator's username
     *      given from param, null otherwise
     */
    @Nullable
    public ArrayList<Order> doRetrieveByOperator(@NotNull String operatorUsername) {
        try {
            Connection cn =  ConPool.getConnection();
            PreparedStatement st;
            Operator op = opd.doRetrieveByUsername(operatorUsername);
            Order o = null;
            User u = null;
            ArrayList<Order> orders = new ArrayList<>();
            if (op != null) {
                st = cn.prepareStatement("SELECT * FROM `order` O WHERE O.operator=?;");
                st.setString(1, operatorUsername);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    o = new Order();
                    o.setOperator(op);
                    o.setId(rs.getInt(2));
                    o.setTotPrice(rs.getDouble(3));
                    o.setNumberOfItems(rs.getInt(4));
                    o.setData(rs.getString(5));
                    u = ud.doRetrieveByUsername(rs.getString(1));
                    o.setUser(u);
                    st = cn.prepareStatement(
                            "SELECT * FROM digitalpurchasing D WHERE D.order=?;"
                    );
                    st.setInt(1, o.getId());
                    ResultSet rs2 = st.executeQuery();
                    DigitalProduct dp = null;
                    while (rs2.next()) {
                        dp = dpd.doRetrieveById(rs2.getInt(1));
                        o.addProduct(dp, rs2.getInt(3));
                    }
                    st = cn.prepareStatement(
                            "SELECT * FROM physicalpurchasing P WHERE P.order=?;"
                    );
                    st.setInt(1, o.getId());
                    rs2 = st.executeQuery();
                    PhysicalProduct pp = null;
                    while (rs2.next()) {
                        pp = ppd.doRetrieveById(rs2.getInt(1));
                        o.addProduct(pp, rs2.getInt(3));
                    }
                    orders.add(o);
                }
                st.close();
                cn.close();
            }
            return orders;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method allows to get a Order starting from its id.
     *
     * @param id the id of the searched order
     * @return if exists a Order with that id given from param, null otherwise
     */
    @Nullable
    public Order doRetrieveById(int id) {
        try {
            Connection cn =  ConPool.getConnection();
            PreparedStatement st;
            Operator op = null;
            Order o = null;
            User u = null;

            st = cn.prepareStatement("SELECT * FROM `order` O WHERE O.id=?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                o = new Order();
                if (rs.getString(6) != null) {
                    op = opd.doRetrieveByUsername(rs.getString(6));
                } else {
                    op = null;
                }
                o.setOperator(op);
                o.setId(rs.getInt(2));
                o.setTotPrice(rs.getDouble(3));
                o.setNumberOfItems(rs.getInt(4));
                o.setData(rs.getString(5));
                u = ud.doRetrieveByUsername(rs.getString(1));
                o.setUser(u);
                st = cn.prepareStatement("SELECT * FROM digitalpurchasing D WHERE D.order=?;");
                st.setInt(1, o.getId());
                ResultSet rs2 = st.executeQuery();
                DigitalProduct dp = null;
                while (rs2.next()) {
                    dp = dpd.doRetrieveById(rs2.getInt(1));
                    o.addProduct(dp, rs2.getInt(3));
                }
                st = cn.prepareStatement("SELECT * FROM physicalpurchasing P WHERE P.order=?;");
                st.setInt(1, o.getId());
                rs2 = st.executeQuery();
                PhysicalProduct pp = null;
                while (rs2.next()) {
                    pp = ppd.doRetrieveById(rs2.getInt(1));
                    o.addProduct(pp, rs2.getInt(3));
                }

            }
            st.close();
            cn.close();

            return o;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method allows to save an Order into the database.
     *
     * @param o the object Order to save, must be not null. If order.getId()=-1, then the order
     *          will have auto incremented key.
     * @return the order that has been saved
     * @throws RuntimeException if the an error has occurred during the transaction.
     */
    @NotNull
    public Order doSave(@NotNull Order o) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st;
            if (o.getOperator() != null) {
                if (o.getId() < 1) {
                    st = cn.prepareStatement(
                            "INSERT INTO `order` (user, totalPrice, "
                            + "numberOfProduct, date, operator) values (?,?,?,?,?);",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    st.setString(1, o.getUser().getUsername());
                    st.setDouble(2, o.getTotPrice());
                    st.setInt(3, o.getNumberOfItems());
                    st.setString(4, o.getData());
                    st.setString(5, o.getOperator().getUsername());
                } else {
                    st = cn.prepareStatement("INSERT INTO `order`( user, id,"
                            + " totalprice, numberOfProduct, date, operator)"
                            + " values (?,?,?,?,?,?);");
                    st.setString(1, o.getUser().getUsername());
                    st.setInt(2, o.getId());
                    st.setDouble(3, o.getTotPrice());
                    st.setInt(4, o.getNumberOfItems());
                    st.setString(5, o.getData());
                    st.setString(6, o.getOperator().getUsername());
                }
            } else {
                if (o.getId() < 1) {
                    st = cn.prepareStatement(
                            "INSERT INTO `order` (user, totalPrice, "
                                    + "numberOfProduct, date) values (?,?,?,?);",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    st.setString(1, o.getUser().getUsername());
                    st.setDouble(2, o.getTotPrice());
                    st.setInt(3, o.getNumberOfItems());
                    st.setString(4, o.getData());
                } else {
                    st = cn.prepareStatement("INSERT INTO `order`( user, id,"
                            + " totalprice, numberOfProduct, date)"
                            + " values (?,?,?,?,?);");
                    st.setString(1, o.getUser().getUsername());
                    st.setInt(2, o.getId());
                    st.setDouble(3, o.getTotPrice());
                    st.setInt(4, o.getNumberOfItems());
                    st.setString(5, o.getData());
                }
            }

            st.executeUpdate();
            if (o.getId() < 1) {
                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                o.setId(rs.getInt(1));
            }
            for (Product p : o.getAllProducts()) {
                if (p instanceof DigitalProduct) {
                    st = cn.prepareStatement(
                            "INSERT INTO digitalpurchasing (digitalProduct, `order`, quantity) "
                                    + "VALUES (?,?, ?);");
                    st.setInt(1, p.getId());
                    st.setInt(2, o.getId());
                    st.setInt(3, o.getQuantitySingleProduct(p.getId(), p.getClass()));

                    if (st.executeUpdate() != 1) {
                        throw new RuntimeException("INSERT error.");
                    }
                } else if (p instanceof PhysicalProduct) {
                    st = cn.prepareStatement(
                            "INSERT INTO physicalpurchasing (physicalProduct, `order`, quantity) "
                                    + "VALUES (?,?, ?);");
                    st.setInt(1, p.getId());
                    st.setInt(2, o.getId());
                    st.setInt(3, o.getQuantitySingleProduct(p.getId(), p.getClass()));
                    if (st.executeUpdate() != 1) {
                        throw new RuntimeException("INSERT error.");
                    }
                }
            }
            st.close();
            cn.close();
            return o;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  This method allows to remove an order from the database.
     *
     * @param id the id of the searched order, must be not null
     *
     * @throws RuntimeException if an exception is occurred
     */
    public void doDelete(int id) {

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("DELETE FROM `order` WHERE id=?;");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *  This method allow to update the operator's username of a specific order.
     *
     * @param id the id of the searched order
     * @param operator the operator's username to update
     */
    public void doUpdateOperator(int id, @NotNull String operator) {

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement(
                    "update `order`set operator =? WHERE id=?;"
            );
            st.setString(1, operator);
            st.setInt(2, id);
            if (st.executeUpdate() != 1) {
                throw new RuntimeException();

            }
            st.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
