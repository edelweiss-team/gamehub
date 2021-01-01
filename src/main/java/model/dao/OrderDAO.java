package model.dao;


import model.bean.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                st = cn.prepareStatement("SELECT * FROM order O WHERE O.user=?;");
                st.setString(1, username);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    o = new Order();
                    o.setUser(u);
                    o.setId(rs.getInt(2));
                    o.setTotPrice(rs.getDouble(3));
                    o.setNumberOfItems(rs.getInt(4));
                    o.setData(rs.getString(5));
                    op = opd.doRetrieveByUsername(rs.getString(6));
                    o.setOperator(op);
                    st = cn.prepareStatement("SELECT * FROM digitalpurchasing D WHERE D.order=?;");
                    st.setInt(1,o.getId());
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
                st = cn.prepareStatement("SELECT * FROM order O WHERE O.operator=?;");
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
                    st.setInt(1,o.getId());
                    ResultSet rs2 = st.executeQuery();
                    DigitalProduct dp = null;
                    while (rs2.next()) {
                        dp = dpd.doRetrieveById(rs2.getInt(1));
                        o.addProduct(dp, rs.getInt(3));
                    }
                    st = cn.prepareStatement("SELECT * FROM physicalpurchasing P WHERE P.oder=?;");
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


    // doRetrieveByOperator
    // doRetrieveById
    // doSave
    // doUpdate ??
    // doDelete
}
