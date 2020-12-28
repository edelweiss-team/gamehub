package model.dao;

import model.bean.Cart;
import model.bean.DigitalProduct;
import model.bean.PhysicalProduct;
import model.bean.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartDAOTest {

    private @NotNull final CartDAO cd = new CartDAO();

    @Test
    void doSaveOrUpdateProductNone() {
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Cart c = new Cart(u);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        us.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doSaveOrUpdateSavingDigitalProductOne() {
        DigitalProduct p = new DigitalProduct(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        DigitalProductDAO digDAO = new DigitalProductDAO();
        p=digDAO.doSave(p);
        Cart c = new Cart(u);
        c.addProduct(p,1);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        cd.doRemoveAllUserCartProducts(c);
        us.doDeleteFromUsername(u.getUsername());
        digDAO.doDelete(p.getId());
    }

    @Test
    void doSaveOrUpdateSavingDigitalProductAll() {
        DigitalProduct p1 = new DigitalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        DigitalProduct p2 = new DigitalProduct(
                2, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        DigitalProductDAO digDAO = new DigitalProductDAO();
        p1=digDAO.doSave(p1);
        p2=digDAO.doSave(p2);
        Cart c = new Cart(u);
        c.addProduct(p1,1);
        c.addProduct(p2,1);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        cd.doRemoveAllUserCartProducts(c);
        us.doDeleteFromUsername(u.getUsername());
        digDAO.doDelete(p1.getId());
        digDAO.doDelete(p2.getId());
    }

    @Test
    void doSaveOrUpdateSavingPhysicalProductOne() {
        PhysicalProduct p = new PhysicalProduct(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        PhysicalProductDAO phyDAO = new PhysicalProductDAO();
        p=phyDAO.doSave(p);
        Cart c = new Cart(u);
        c.addProduct(p,1);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        cd.doRemoveAllUserCartProducts(c);
        us.doDeleteFromUsername(u.getUsername());
        phyDAO.doDelete(p.getId());
    }

    @Test
    void doSaveOrUpdateSavingPhysicalProductAll() {
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        PhysicalProduct p2 = new PhysicalProduct(
                2, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        PhysicalProductDAO phyDAO = new PhysicalProductDAO();
        p1=phyDAO.doSave(p1);
        p2=phyDAO.doSave(p2);
        Cart c = new Cart(u);
        c.addProduct(p1,1);
        c.addProduct(p2,1);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        cd.doRemoveAllUserCartProducts(c);
        us.doDeleteFromUsername(u.getUsername());
        phyDAO.doDelete(p1.getId());
        phyDAO.doDelete(p2.getId());
    }

    @Test
    void doSaveOrUpdateUpdatingDigitalProductOne() {
        DigitalProduct p = new DigitalProduct(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        DigitalProductDAO digDAO = new DigitalProductDAO();
        p=digDAO.doSave(p);
        Cart c = new Cart(u);
        c.addProduct(p,1);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        c2.removeProduct(p,c2.getQuantitySingleProduct(p.getId()));
        cd.doSaveOrUpdate(c2);
        c=cd.doRetrieveByUsername(u.getUsername());
        assert c != null;
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        if(!c.getAllProducts().isEmpty())
            cd.doRemoveAllUserCartProducts(c2);
        us.doDeleteFromUsername(u.getUsername());
        digDAO.doDelete(p.getId());
    }

    @Test
    void doSaveOrUpdateUpdatingDigitalProductAll() {
        DigitalProduct p1 = new DigitalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        DigitalProduct p2 = new DigitalProduct(
                2, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        DigitalProductDAO digDAO = new DigitalProductDAO();
        p1=digDAO.doSave(p1);
        p2=digDAO.doSave(p2);
        Cart c = new Cart(u);
        c.addProduct(p1,1);
        c.addProduct(p2,1);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        c2.removeProduct(p1,c2.getQuantitySingleProduct(p1.getId()));
        c2.removeProduct(p2,c2.getQuantitySingleProduct(p2.getId()));
        cd.doSaveOrUpdate(c2);
        c=cd.doRetrieveByUsername(u.getUsername());
        assert c != null;
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        if(!c.getAllProducts().isEmpty())
            cd.doRemoveAllUserCartProducts(c);
        us.doDeleteFromUsername(u.getUsername());
        digDAO.doDelete(p1.getId());
        digDAO.doDelete(p2.getId());
    }

    @Test
    void doSaveOrUpdateUpdatingPhysicalProductOne() {
        PhysicalProduct p = new PhysicalProduct(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        PhysicalProductDAO phyDAO = new PhysicalProductDAO();
        p=phyDAO.doSave(p);
        Cart c = new Cart(u);
        c.addProduct(p,1);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        c2.removeProduct(p,c2.getQuantitySingleProduct(p.getId()));
        cd.doSaveOrUpdate(c2);
        c=cd.doRetrieveByUsername(u.getUsername());
        assert c != null;
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        if(!c.getAllProducts().isEmpty())
            cd.doRemoveAllUserCartProducts(c);
        us.doDeleteFromUsername(u.getUsername());
        phyDAO.doDelete(p.getId());
    }

    @Test
    void doSaveOrUpdateUpdatingPhysicalProductAll() {
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        PhysicalProduct p2 = new PhysicalProduct(
                1, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        PhysicalProductDAO phyDAO = new PhysicalProductDAO();
        p1=phyDAO.doSave(p1);
        p2=phyDAO.doSave(p2);
        Cart c = new Cart(u);
        c.addProduct(p1,1);
        c.addProduct(p2,1);
        cd.doSaveOrUpdate(c);
        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        c2.removeProduct(p1,c2.getQuantitySingleProduct(p1.getId()));
        c2.removeProduct(p2,c2.getQuantitySingleProduct(p2.getId()));
        cd.doSaveOrUpdate(c2);
        c=cd.doRetrieveByUsername(u.getUsername());
        assert c != null;
        assertIterableEquals(c.getAllProducts(),c2.getAllProducts());
        if(!c.getAllProducts().isEmpty())
            cd.doRemoveAllUserCartProducts(c2);
        us.doDeleteFromUsername(u.getUsername());
        phyDAO.doDelete(p1.getId());
        phyDAO.doDelete(p2.getId());
    }

    @Test
    void doSaveOrUpdateUserIsNull() {
        Cart c = new Cart(null);
        assertThrows(IllegalArgumentException.class,()->cd.doSaveOrUpdate(c));
    }

    @Test
    void doSaveOrUpdateNotOk() {
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        String oldname = u.getUsername();
        UserDAO us = new UserDAO();
        us.doSave(u);
        Cart c = new Cart(u);
        u.setUsername("'limit");
        c.setUser(u);
        assertThrows(RuntimeException.class,()->cd.doSaveOrUpdate(c));
        us.doDeleteFromUsername(oldname);
    }

    @Test
    void doRetrieveByUsernameUserIsNull() {
        Cart c = new Cart(null);
        assertThrows(NullPointerException.class,()->cd.doRetrieveByUsername(c.getUser().getUsername()));
    }

    @Test
    void doRetrieveByUsernameCartIsNull() {
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        UserDAO us = new UserDAO();
        us.doSave(u);
        Cart c = new Cart(u);
        assertNull(cd.doRetrieveByUsername(c.getUser().getUsername()));
        us.doDeleteFromUsername(u.getUsername());
    }

    @Test
    void doRetrieveByUsernameNotOk() {
        assertNull(cd.doRetrieveByUsername("'121/limit"));
    }

    @Test
    void doRemoveAllUserCartProductsUserIsNull() {
        Cart c = new Cart(null);
        assertThrows(IllegalArgumentException.class,()->cd.doRemoveAllUserCartProducts(c));
    }

    @Test
    void doRemoveAllUserCartProductsNotOk() {
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        String oldname = u.getUsername();
        UserDAO us = new UserDAO();
        us.doSave(u);
        Cart c = new Cart(u);
        u.setUsername("'limit");
        c.setUser(u);
        assertThrows(RuntimeException.class,() -> cd.doRemoveAllUserCartProducts(c));
        us.doDeleteFromUsername(oldname);
    }

    @Test
    void doAddProductDigital() {
        DigitalProduct p1 = new DigitalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        DigitalProduct p2 = new DigitalProduct(
                2, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        DigitalProductDAO digDAO = new DigitalProductDAO();
        p1=digDAO.doSave(p1);
        p2=digDAO.doSave(p2);
        Cart c = new Cart(u);
        c.addProduct(p1,2);
        c.addProduct(p2,1);
        cd.doSaveOrUpdate(c);

        c.addProduct(p1, 1);
        cd.doAddProduct(c, p1);

        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        assertEquals(c.getQuantitySingleProduct(p1.getId()), c.getQuantitySingleProduct(p1.getId()));

        us.doDeleteFromUsername(u.getUsername());
        digDAO.doDelete(p1.getId());
        digDAO.doDelete(p2.getId());
    }

    @Test
    void doAddProductPhysical() {
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(), new ArrayList<>(),
                1,"22x22x22",22);
        DigitalProduct p2 = new DigitalProduct(
                2, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        DigitalProductDAO digDAO = new DigitalProductDAO();
        PhysicalProductDAO phyDAO = new PhysicalProductDAO();
        p1 = phyDAO.doSave(p1);
        p2 = digDAO.doSave(p2);
        Cart c = new Cart(u);
        c.addProduct(p1,2);
        c.addProduct(p2,1);
        cd.doSaveOrUpdate(c);

        c.addProduct(p1, 1);
        cd.doAddProduct(c, p1);

        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        assertEquals(c.getQuantitySingleProduct(p1.getId()), c.getQuantitySingleProduct(p1.getId()));

        us.doDeleteFromUsername(u.getUsername());
        digDAO.doDelete(p1.getId());
        digDAO.doDelete(p2.getId());
    }

    @Test
    void doAddProductUserNull() {
        Cart c = new Cart(null);
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(), new ArrayList<>(),
                1,"22x22x22",22
        );

        c.addProduct(p1, 1);
        assertNull(cd.doAddProduct(c, p1));
    }

    @Test
    void doRemoveProductUserNull() {
        Cart c = new Cart(null);
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(), new ArrayList<>(),
                1,"22x22x22",22
        );

        c.addProduct(p1, 2);
        c.removeProduct(p1, 1);
        assertNull(cd.doRemoveProduct(c, p1));
    }

    @Test
    void doRemoveProductPhysicalThrows(){
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(), new ArrayList<>(),
                1,"22x22x22",22
        );
        User u = new User(
                "gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121"
        );

        Cart c = new Cart(u);

        c.addProduct(p1, 3);
        //senza fare la doSave
        assertThrows(RuntimeException.class, () -> cd.doRemoveProduct(c, p1));
    }

    @Test
    void doRemoveProductDigitalThrows(){
        DigitalProduct p2 = new DigitalProduct(
                2, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo"
        );
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");

        Cart c = new Cart(u);

        c.addProduct(p2, 3);
        //senza fare la doSave
        assertThrows(RuntimeException.class, () -> cd.doRemoveProduct(c, p2));
    }

    @Test
    void doRemoveProductDigital() {
        DigitalProduct p1 = new DigitalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        DigitalProduct p2 = new DigitalProduct(
                2, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        DigitalProductDAO digDAO = new DigitalProductDAO();
        p1=digDAO.doSave(p1);
        p2=digDAO.doSave(p2);
        Cart c = new Cart(u);
        c.addProduct(p1,2);
        c.addProduct(p2,1);
        cd.doSaveOrUpdate(c);

        c.removeProduct(p1, 1);
        cd.doRemoveProduct(c, p1);

        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        assertEquals(c.getQuantitySingleProduct(p1.getId()), c.getQuantitySingleProduct(p1.getId()));

        us.doDeleteFromUsername(u.getUsername());
        digDAO.doDelete(p1.getId());
        digDAO.doDelete(p2.getId());
    }

    @Test
    void doRemoveProductPhysical() {
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(), new ArrayList<>(),
                1,"22x22x22",22);
        DigitalProduct p2 = new DigitalProduct(
                2, "p2", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        UserDAO us=new UserDAO();
        User u = new User("gigino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        DigitalProductDAO digDAO = new DigitalProductDAO();
        PhysicalProductDAO phyDAO = new PhysicalProductDAO();
        p1 = phyDAO.doSave(p1);
        p2 = digDAO.doSave(p2);
        Cart c = new Cart(u);
        c.addProduct(p1,2);
        c.addProduct(p2,1);
        cd.doSaveOrUpdate(c);

        c.removeProduct(p1, 1);
        cd.doRemoveProduct(c, p1);

        Cart c2 = cd.doRetrieveByUsername(u.getUsername());
        assert c2 != null;
        assertEquals(c.getQuantitySingleProduct(p1.getId()), c.getQuantitySingleProduct(p1.getId()));

        us.doDeleteFromUsername(u.getUsername());
        digDAO.doDelete(p1.getId());
        digDAO.doDelete(p2.getId());
    }
}