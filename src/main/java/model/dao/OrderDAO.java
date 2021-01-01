package model.dao;




import java.sql.*;
import java.util.ArrayList;
import model.bean.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class OrderDAO {

    @NotNull
    private final UserDAO ud = new UserDAO();
    @NotNull
    private final DigitalProductDAO dpd = new DigitalProductDAO();
    @NotNull
    private final PhysicalProductDAO ppd = new PhysicalProductDAO();
    @NotNull
    private final OperatorDAO opd = new OperatorDAO();

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
                        o.addProduct(dp, rs.getInt(3));
                    }
                    st = cn.prepareStatement("SELECT * FROM physicalpurchasing P WHERE P.order=?;");
                    st.setInt(1, o.getId());
                    rs2 = st.executeQuery();
                    PhysicalProduct pp = null;
                    while (rs2.next()) {
                        pp = ppd.doRetrieveById(rs2.getInt(1));
                        o.addProduct(pp, rs.getInt(3));
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

    @Nullable
    public ArrayList<Order> doRetrieveByOperator(@NotNull String operatorname) {
        try {
            Connection cn =  ConPool.getConnection();
            PreparedStatement st;
            Operator op = opd.doRetrieveByUsername(operatorname);
            Order o = null;
            User u = null;
            ArrayList<Order> orders = new ArrayList<>();
            if (op != null) {
                st = cn.prepareStatement("SELECT * FROM `order` O WHERE O.operator=?;");
                st.setString(1, operatorname);
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
                    st = cn.prepareStatement("SELECT * FROM digitalpurchasing D WHERE D.order=?;");
                    st.setInt(1, o.getId());
                    ResultSet rs2 = st.executeQuery();
                    DigitalProduct dp = null;
                    while (rs2.next()) {
                        dp = dpd.doRetrieveById(rs2.getInt(1));
                        o.addProduct(dp, rs.getInt(3));
                    }
                    st = cn.prepareStatement("SELECT * FROM physicalpurchasing P WHERE P.order=?;");
                    st.setInt(1, o.getId());
                    rs2 = st.executeQuery();
                    PhysicalProduct pp = null;
                    while (rs2.next()) {
                        pp = ppd.doRetrieveById(rs2.getInt(1));
                        o.addProduct(pp, rs.getInt(3));
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

    @Nullable
    public Order doRetrieveById(@NotNull int id) {
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
                    o.addProduct(dp, rs.getInt(3));
                }
                st = cn.prepareStatement("SELECT * FROM physicalpurchasing P WHERE P.order=?;");
                st.setInt(1, o.getId());
                rs2 = st.executeQuery();
                PhysicalProduct pp = null;
                while (rs2.next()) {
                    pp = ppd.doRetrieveById(rs2.getInt(1));
                    o.addProduct(pp, rs.getInt(3));
                }


            }
            st.close();
            cn.close();

            return o;
        } catch (SQLException e) {
            return null;
        }
    }


    @Nullable
    public void doSave(@NotNull Order o) {
        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st;
            if (o.getOperator() != null) {
                st = cn.prepareStatement("INSERT INTO `order`( user, id,"
                        + " totalprice, numberOfProduct, date, operator)"
                        + " values (?,?,?,?,?,?);");
                st.setString(1, o.getUser().getUsername());
                st.setInt(2, o.getId());
                st.setDouble(3, o.getTotPrice());
                st.setInt(4, o.getNumberOfItems());
                st.setString(5, o.getData());
                st.setString(6, o.getOperator().getUsername());
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

            st.executeUpdate();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDelete(@NotNull int id) {

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


    public void doUpdateOperator(@NotNull int id, @NotNull String operator) {

        try {
            Connection cn = ConPool.getConnection();
            PreparedStatement st = cn.prepareStatement("update `order`set operator =? WHERE id=?;");
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
