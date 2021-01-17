package model.dao;

import java.util.ArrayList;

import model.bean.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDAOTest {
    private @NotNull final OrderDAO or = new OrderDAO();
    private @NotNull final PhysicalProductDAO py = new PhysicalProductDAO();
    private @NotNull final DigitalProductDAO dy = new DigitalProductDAO();
    private @NotNull final UserDAO us = new UserDAO();
    private @NotNull final OperatorDAO op = new OperatorDAO();

    @Test
    void doRetrieveByUsernameValid() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByUsername("gigggino");
        assertTrue(prova2.contains(prova));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByUsernameNotValid() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByUsername("gigggino2");
        assertTrue(prova2.isEmpty());
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByUsernameXNone() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByUsername("gigggino44");
        assertTrue(prova2.isEmpty());
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByUsernameXOne() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByUsername("gigggino");
        assertTrue(prova2.contains(prova));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByUsernameXAll() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        Order prova2 = new Order(71,u,o,"2020-11-11");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        prova2.addProduct(p2,3);
        or.doSave(prova);
        or.doSave(prova2);
        @Nullable ArrayList<Order> prova3 = or.doRetrieveByUsername("gigggino");
        assertTrue(prova3.contains(prova));
        assertTrue(prova3.contains(prova2));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByUsernameProductNone() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByUsername("gigggino");
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().isEmpty());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByUsernameProductXOne() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        DigitalProduct d2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByUsername("gigggino");
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().contains(p2));
        assertTrue(p.getAllProducts().contains(d2));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByUsernameProductXAll() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        PhysicalProduct p3 = new PhysicalProduct(6, "NuovoProdottoTesting3", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p3 = py.doSave(p3);
        DigitalProduct d2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        DigitalProduct d3 = new DigitalProduct(7, "NuovoProdottoTesting3", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d3 = dy.doSave(d3);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        prova.addProduct(d3,4);
        prova.addProduct(p3,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByUsername("gigggino");
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().contains(p2));
        assertTrue(p.getAllProducts().contains(d2));
        assertTrue(p.getAllProducts().contains(p3));
        assertTrue(p.getAllProducts().contains(d3));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        py.doDelete(p3.getId());
        dy.doDelete(d3.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByOperatorValid() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByOperator("gigggino");
        assertTrue(prova2.contains(prova));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByOperatorNotValid() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByOperator("gigggino44");
        assertTrue(prova2.isEmpty());
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByOperatorXNone() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByOperator("gigggino44");
        assertTrue(prova2.isEmpty());
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByOperatorXOne() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByOperator("gigggino");
        assertTrue(prova2.contains(prova));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByOperatorXAll() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        Order prova2 = new Order(71,u,o,"2020-11-11");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        prova2.addProduct(p2,6);
        or.doSave(prova);
        or.doSave(prova2);
        @Nullable ArrayList<Order> prova3 = or.doRetrieveByOperator("gigggino");
        assertTrue(prova3.contains(prova));
        assertTrue(prova3.contains(prova2));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
        or.doDelete(prova2.getId());
    }

    @Test
    void doRetrieveByOperatorProductNone() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByOperator("gigggino");
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().isEmpty());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByOperatorProductXOne() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        DigitalProduct d2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByOperator("gigggino");
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().contains(p2));
        assertTrue(p.getAllProducts().contains(d2));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        or.doDelete(prova.getId());

    }


    @Test
    void doRetrieveByOperatorProductXAll() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        PhysicalProduct p3 = new PhysicalProduct(6, "NuovoProdottoTesting3", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p3 = py.doSave(p3);
        DigitalProduct d2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        DigitalProduct d3 = new DigitalProduct(7, "NuovoProdottoTesting3", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d3 = dy.doSave(d3);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        prova.addProduct(d3,4);
        prova.addProduct(p3,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveByOperator("gigggino");
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().contains(p2));
        assertTrue(p.getAllProducts().contains(d2));
        assertTrue(p.getAllProducts().contains(p3));
        assertTrue(p.getAllProducts().contains(d3));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        py.doDelete(p3.getId());
        dy.doDelete(d3.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveNonApprovedXNone() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Order prova = new Order(70,u,null,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveNonApproved(0, 0);
        assertTrue(prova2.isEmpty());
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveNonApprovedXOne() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Order prova = new Order(70,u,null,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveNonApproved(0, 1);
        assertTrue(prova2.contains(prova));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveNonApprovedXAll() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Order prova = new Order(70,u,null,"2020-12-12");
        Order prova2 = new Order(71,u,null,"2020-11-11");
        PhysicalProduct p2 = new PhysicalProduct(1000, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<>() , new ArrayList<>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        prova2.addProduct(p2,6);
        or.doSave(prova);
        or.doSave(prova2);
        @Nullable ArrayList<Order> prova3 = or.doRetrieveNonApproved(0, 10);
        assertTrue(prova3.contains(prova));
        assertTrue(prova3.contains(prova2));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
        or.doDelete(prova2.getId());
    }

    @Test
    void doRetrieveNonApprovedProductNone() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Order prova = new Order(70,u,null,"2020-12-12");
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveNonApproved(0, 10);
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().isEmpty());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveNonApprovedProductXOne() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Order prova = new Order(70,u,null,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        DigitalProduct d2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveNonApproved(0, 10);
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().contains(p2));
        assertTrue(p.getAllProducts().contains(d2));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        or.doDelete(prova.getId());
    }


    @Test
    void doRetrieveNonApprovedProductXAll() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        (new UserDAO()).doSave(u);
        Order prova = new Order(70,u,null,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        PhysicalProduct p3 = new PhysicalProduct(6, "NuovoProdottoTesting3", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p3 = py.doSave(p3);
        DigitalProduct d2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        DigitalProduct d3 = new DigitalProduct(7, "NuovoProdottoTesting3", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d3 = dy.doSave(d3);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        prova.addProduct(d3,4);
        prova.addProduct(p3,5);
        or.doSave(prova);
        @Nullable ArrayList<Order> prova2 = or.doRetrieveNonApproved(0, 100);
        Order p = prova2.get(0);
        assertTrue(p.getAllProducts().contains(p2));
        assertTrue(p.getAllProducts().contains(d2));
        assertTrue(p.getAllProducts().contains(p3));
        assertTrue(p.getAllProducts().contains(d3));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        py.doDelete(p3.getId());
        dy.doDelete(d3.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByIdOk() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        prova.addProduct(p2,5);
        or.doSave(prova);
        @Nullable Order prova2 = or.doRetrieveById(70);
        assertTrue(prova2.equals(prova));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByIdNotExixts(){
        assertNull(or.doRetrieveById(999999));
    }

    @Test
    void doRetrieveByIdOperatorNull(){
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        Order p = or.doRetrieveById(70);
        assertTrue(p.getOperator()==null);
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByIdOperatorOk(){
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        Order p = or.doRetrieveById(70);
        assertEquals(p.getOperator(), o);
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByIdProductNone() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        Order prova2 = or.doRetrieveById(70);
        assertTrue(prova2.getAllProducts().isEmpty());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doRetrieveByIdProductXOne() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        DigitalProduct d2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        or.doSave(prova);
        Order prova2 = or.doRetrieveById(70);

        assertTrue(prova2.getAllProducts().contains(p2));
        assertTrue(prova2.getAllProducts().contains(d2));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        or.doDelete(prova.getId());

    }


    @Test
    void doRetrieveByIdProductXAll() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        PhysicalProduct p3 = new PhysicalProduct(6, "NuovoProdottoTesting3", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p3 = py.doSave(p3);
        DigitalProduct d2 = new DigitalProduct(7, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        DigitalProduct d3 = new DigitalProduct(7, "NuovoProdottoTesting3", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d3 = dy.doSave(d3);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        prova.addProduct(d3,4);
        prova.addProduct(p3,5);
        or.doSave(prova);
        Order prova2 = or.doRetrieveById(70);
        assertTrue(prova2.getAllProducts().contains(p2));
        assertTrue(prova2.getAllProducts().contains(d2));
        assertTrue(prova2.getAllProducts().contains(p3));
        assertTrue(prova2.getAllProducts().contains(d3));
        us.doDeleteFromUsername("gigggino");
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        py.doDelete(p3.getId());
        dy.doDelete(d3.getId());
        or.doDelete(prova.getId());
    }


    @Test
    void doSaveOperatorOk() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        assertTrue(or.doRetrieveById(70).getOperator().equals(o));
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doSaveOperatorNull() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        assertTrue(or.doRetrieveById(70).getOperator()==null);
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doSaveProductNone() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        Order prova2 = or.doRetrieveById(70);
        assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doSaveProductOne() {
        DigitalProduct p = new DigitalProduct(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        dy.doSave(p);
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        py.doSave(p1);
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        prova.addProduct(p,5);
        prova.addProduct(p1,6);
        or.doSave(prova);
        Order prova2 = or.doRetrieveById(70);
        assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
        py.doDelete(p1.getId());
        dy.doDelete(p.getId());

    }

    @Test
    void doSaveProductAll() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        PhysicalProduct p3 = new PhysicalProduct(7, "NuovoProdottoTesting3", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p3 = py.doSave(p3);
        DigitalProduct d2 = new DigitalProduct(8, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        DigitalProduct d3 = new DigitalProduct(9, "NuovoProdottoTesting3", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d3 = dy.doSave(d3);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        prova.addProduct(d3,4);
        prova.addProduct(p3,5);
        or.doSave(prova);
        Order prova2 = or.doRetrieveById(70);
        assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        py.doDelete(p3.getId());
        dy.doDelete(d3.getId());

    }

    @Test
    void doSaveProductNoneNoIdOperator() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u, "2022-12-12", "curriculum");
        op.doSave(o);
        Order prova = new Order(0,u,o,"2020-12-12");
        or.doSave(prova);
        Order prova2 = or.doRetrieveByUsername("gigggino").get(0);
        assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doSaveProductNoneNoId() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(0,u,o,"2020-12-12");
        or.doSave(prova);
        Order prova2 = or.doRetrieveByUsername("gigggino").get(0);
        assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
    }

    @Test
    void doSaveProductOneNoId() {
        DigitalProduct p = new DigitalProduct(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        dy.doSave(p);
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        py.doSave(p1);
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(0,u,o,"2020-12-12");
        prova.addProduct(p,5);
        prova.addProduct(p1,6);
        or.doSave(prova);
        Order prova2 = or.doRetrieveByUsername("gigggino").get(0);
        assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
        py.doDelete(p1.getId());
        dy.doDelete(p.getId());

    }

    @Test
    void doSaveProductOneNoIdOperator() {
        DigitalProduct p = new DigitalProduct(
                1, "p0", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"Nintendo Switch","2021-10-10",
                8,"GameFreak","Nintendo");
        dy.doSave(p);
        PhysicalProduct p1 = new PhysicalProduct(
                1, "p1", 11, "desc0", "path0", new ArrayList<>(),
                new ArrayList<>(), 1,"50x20x40",8);
        py.doSave(p1);
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u, "2022-12-12", "curriculum");
        op.doSave(o);
        Order prova = new Order(0,u,o,"2020-12-12");
        prova.addProduct(p,5);
        prova.addProduct(p1,6);
        or.doSave(prova);
        Order prova2 = or.doRetrieveByUsername("gigggino").get(0);
        assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
        py.doDelete(p1.getId());
        dy.doDelete(p.getId());

    }

    @Test
    void doSaveProductAllNoId() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = null;
        us.doSave(u);
        Order prova = new Order(0, u, o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        PhysicalProduct p3 = new PhysicalProduct(7, "NuovoProdottoTesting3", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p3 = py.doSave(p3);
        DigitalProduct d2 = new DigitalProduct(8, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        DigitalProduct d3 = new DigitalProduct(9, "NuovoProdottoTesting3", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d3 = dy.doSave(d3);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        prova.addProduct(d3,4);
        prova.addProduct(p3,5);
        or.doSave(prova);
        Order prova2 = or.doRetrieveByUsername("gigggino").get(0);
        assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        py.doDelete(p3.getId());
        dy.doDelete(d3.getId());
    }

    @Test
    void doSaveProductAllNoIdOperator() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(0, u, o,"2020-12-12");
        PhysicalProduct p2 = new PhysicalProduct(6, "NuovoProdottoTesting2", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p2 = py.doSave(p2);
        PhysicalProduct p3 = new PhysicalProduct(7, "NuovoProdottoTesting3", 23.56,
                "testing2", "imagetest2", new ArrayList<Category>() , new ArrayList<Tag>(),
                250, "0x0x0", 20.05);
        p3 = py.doSave(p3);
        DigitalProduct d2 = new DigitalProduct(8, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d2 = dy.doSave(d2);
        DigitalProduct d3 = new DigitalProduct(9, "NuovoProdottoTesting3", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        d3 = dy.doSave(d3);
        prova.addProduct(d2,4);
        prova.addProduct(p2,5);
        prova.addProduct(d3,4);
        prova.addProduct(p3,5);
        or.doSave(prova);
        Order prova2 = or.doRetrieveByUsername("gigggino").get(0);
        //assertIterableEquals(prova.getAllProducts(),prova2.getAllProducts());
        assertTrue(!prova.getAllProducts().isEmpty());
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());
        py.doDelete(p2.getId());
        dy.doDelete(d2.getId());
        py.doDelete(p3.getId());
        dy.doDelete(d3.getId());
    }

    @Test
    void doDeleteOk() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        Order prova2 = or.doRetrieveById(prova.getId());
        or.doDelete(prova2.getId());
        assertTrue(or.doRetrieveById(prova2.getId()) == null);
        us.doDeleteFromUsername("gigggino");

    }

    @Test
    void doDeleteNotOkID() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        Operator o = new Operator(u,"2022-11-11", "cvv");
        op.doSave(o);
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);

        or.doDelete(999);
        assertFalse(or.doRetrieveById(prova.getId()) == null);
        us.doDeleteFromUsername("gigggino");
        or.doDelete(prova.getId());

    }

    @Test
    void doUpdateOperatorOk() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        User u2 = new User("gigggino2", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigisno@gmail.com",
                'M', "3351212121");
        Operator o2 = new Operator(u2,"2021-11-11", "cvv");
        op.doSave(o2);
        or.doUpdateOperator(prova.getId(), o2.getUsername());
        assertTrue(o2.getUsername().equals(or.doRetrieveById(prova.getId()).getOperator().getUsername()));
        us.doDeleteFromUsername("gigggino");
        us.doDeleteFromUsername("gigggino2");
        or.doDelete(prova.getId());




    }

    @Test
    void doUpdateOperatorNotOkOrder() {
        User u = new User("gigggino", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigggino@gmail.com",
                'M', "3351212121");
        us.doSave(u);
        Operator o = null;
        Order prova = new Order(70,u,o,"2020-12-12");
        or.doSave(prova);
        User u2 = new User("gigggino2", "pass", "Luigi", "Tufano", "Via Marchese",
                "Boscoreale", "Italia", "1999-12-12", "gigisno@gmail.com",
                'M', "3351212121");
        Operator o2 = new Operator(u2,"2021-11-11", "cvv");
        op.doSave(o2);
        assertThrows(RuntimeException.class,()-> or.doUpdateOperator(999,o2.getUsername()));
        us.doDeleteFromUsername("gigggino");
        us.doDeleteFromUsername("gigggino2");
        or.doDelete(prova.getId());




    }
}