package controller.userManagement.operatorManagement;

import controller.RequestParametersException;
import model.bean.*;
import model.dao.*;
import org.apache.log4j.BasicConfigurator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ApproveOrderServletTest {
    private @NotNull ApproveOrderServlet servlet;
    private @NotNull MockHttpServletRequest request;
    private @NotNull MockHttpServletResponse response;
    private @Nullable MockHttpSession session;

    private static User buyerUser;
    private static User operatorUser;
    private static User unregisteredUser;
    private static User unregisteredUser2;
    private static UserDAO udao = new UserDAO();

    private static Operator operator;
    private static OperatorDAO operatorDao = new OperatorDAO();

    private static Order orderByRegisteredUser;
    private static Order orderByUnregisteredUserSingleOrder;
    private static Order orderByUnregisteredUserMultiOrder1;
    private static Order orderByUnregisteredUserMultiOrder2;
    private static OrderDAO orderDao = new OrderDAO();



    @BeforeEach
    public void setUp() {
        servlet = new ApproveOrderServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        session.setAttribute("loggedUser", operator);
        request.setSession(session);
        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void inizializeVariable() {
        udao.doDeleteAllUser();
        // ---------------------------------> user
        buyerUser = new User(
                "buyerize",
                "password1",
                "bob",
                "yer",
                "Via pozzopagnotti",
                "NewYork",
                "IT",
                "2000-11-11",
                "test100@ananana.it",
                'M',
                "3391909067");
        udao.doSave(buyerUser);


        operatorUser = new User(
                "operator",
                "password1",
                "Hopper",
                "tor",
                "Via pozzopagni",
                "NewOrleans",
                "IT",
                "2000-11-20",
                "test2@ananana.it",
                'M',
                "3391909063");
        // this user will be saved by OperatorDAO.doSave(), don't save it here


        unregisteredUser = new User(
                "test3@ananana.it",
                "password1",
                "Unno",
                "Regini",
                "Via agni",
                "Newmexico",
                "IT",
                "2000-12-12",
                "test3@ananana.it",
                'M',
                "3391919063"
        );
        udao.doSave(unregisteredUser);


        unregisteredUser2 = new User(
                "test1@ananana.it",
                "p46464646464",
                "Unno",
                "Regini",
                "Via agni",
                "Newmexico",
                "IT",
                "2000-12-10",
                "test1@ananana.it",
                'M',
                "3331919063"
        );
        udao.doSave(unregisteredUser2);

        // ---------------------------------> operator
        operator = new Operator(
                operatorUser,
                "2021-12-12",
                "sono laureato alla Pegaso");
        operatorDao.doSave(operator);

        // ---------------------------------> order
        orderByRegisteredUser = new Order(
                1,
                buyerUser,
                operator,
                "2021-12-12");
        orderDao.doSave(orderByRegisteredUser);


        orderByUnregisteredUserSingleOrder = new Order(
                2,
                unregisteredUser,
                operator,
                "2021-12-12"
        );
        PhysicalProduct pasquale = new PhysicalProduct(1000000, "prova", 2, "ciaociaociao", "pathhhhhhhhh", new ArrayList<>(), new ArrayList<>(), 10, "3x3x3", 2);
        (new PhysicalProductDAO()).doSave(pasquale);
        orderByUnregisteredUserSingleOrder.addProduct(pasquale,1);
        orderDao.doSave(orderByUnregisteredUserSingleOrder);


        orderByUnregisteredUserMultiOrder1 = new Order(
                3,
                unregisteredUser2,
                operator,
                "2021-12-12"
        );
        orderDao.doSave(orderByUnregisteredUserMultiOrder1);


        orderByUnregisteredUserMultiOrder2 = new Order(
                4,
                unregisteredUser2,
                operator,
                "2021-12-12"
        );
        orderDao.doSave(orderByUnregisteredUserMultiOrder2);
    }

    @Test
    public void IdProductNotOk() {
        request.addParameter("approveOrder", "1abc");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void IdNotExisting() {
        request.addParameter("approveOrder", "-1");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void idProductOkButOrderDoesNotExist() {
        request.addParameter("approveOrder", "9423427459");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }
/*
    @Test
    public void OrderIsByUnregisteredUserAndOnlyOneOrder() throws ServletException, IOException {
        request.addParameter("approveOrder", "2");
        servlet.doPost(request, response);
        assertNull(orderDao.doRetrieveById(2));
    }
*/
    @Test
    public void OrderIsByUnregisteredUserAndMultipleOrders() throws ServletException, IOException {
        request.addParameter("approveOrder", "4");
        servlet.doPost(request, response);

        // this should return a user instance (different from null) since the user linked to
        // the order with id = 4, has 2 order (orderByUnregisteredUserMultiOrder1 and
        // orderByUnregisteredUserMultiOrder2).
        assertNotNull(udao.doRetrieveByUsername(unregisteredUser2.getUsername()));
    }

    @Test
    public void OrderIsByRegisteredUser() throws ServletException, IOException {
        request.addParameter("approveOrder", "1");
        servlet.doPost(request,response);
        assertEquals(((orderDao.doRetrieveById(1)).getOperator()).getUsername(), operator.getUsername());
    }

    @AfterAll
    public static void DeSetup() {

        orderDao.doDelete(orderByRegisteredUser.getId());
        orderDao.doDelete(orderByUnregisteredUserSingleOrder.getId());
        orderDao.doDelete(orderByUnregisteredUserMultiOrder1.getId());
        orderDao.doDelete(orderByUnregisteredUserMultiOrder2.getId());
        operatorDao.doDeleteByUsername(operator.getUsername());
        udao.doDeleteFromUsername(buyerUser.getUsername());
        udao.doDeleteFromUsername(unregisteredUser.getUsername());
        udao.doDeleteFromUsername(unregisteredUser2.getUsername());
        udao.doDeleteAllUser();
        (new PhysicalProductDAO()).doDelete(1000000);
    }


}