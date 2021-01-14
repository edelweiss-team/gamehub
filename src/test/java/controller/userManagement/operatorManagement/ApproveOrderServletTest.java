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

import static org.junit.jupiter.api.Assertions.*;

public class ApproveOrderServletTest {
    private @NotNull ApproveOrderServlet servlet;
    private @NotNull MockHttpServletRequest request;
    private @NotNull MockHttpServletResponse response;
    private @Nullable MockHttpSession session;

    private static @NotNull User buyerUser;
    private static @NotNull User operatorUser;
    private static @NotNull User unregisteredUser;
    private static @NotNull User unregisteredUser2;
    private static final @NotNull UserDAO udao = new UserDAO();

    private static @NotNull Operator operator;
    private static final @NotNull OperatorDAO operatorDao = new OperatorDAO();

    private static @NotNull Order orderByRegisteredUser;
    private static @NotNull Order orderByUnregisteredUserSingleOrder;
    private static @NotNull Order orderByUnregisteredUserMultiOrder1;
    private static @NotNull Order orderByUnregisteredUserMultiOrder2;
    private static final @NotNull OrderDAO orderDao = new OrderDAO();



    @BeforeAll
    static public void init() {
        // ---------------------------------> user
        buyerUser = new User(
                "buyer",
                "password",
                "bob",
                "yer",
                "Via pozzopagnotti",
                "NewYork",
                "IT",
                "11/11/11",
                "b.yer@gmail.com",
                'M',
                "3391909067");
        udao.doSave(buyerUser);


        operatorUser = new User(
                "operator",
                "passwo",
                "Hopper",
                "tor",
                "Via pozzopagni",
                "NewOrleans",
                "IT",
                "11/10/11",
                "h.tor@gmail.com",
                'M',
                "3391909063");
        // this user will be saved by OperatorDAO.doSave(), don't save it here


        unregisteredUser = new User(
                "nonReg@gmail.com",
                "pa",
                "Unno",
                "Regini",
                "Via agni",
                "NewMexico",
                "IT",
                "11/10/11",
                "nonReg@gmail.com",
                'M',
                "3391919063"
        );
        udao.doSave(unregisteredUser);


        unregisteredUser2 = new User(
                "nonReg2@gmail.com",
                "p46464646464",
                "Unno",
                "Regini",
                "Via agni",
                "NewMexico",
                "IT",
                "11/10/11",
                "nonReg2@gmail.com",
                'M',
                "3331919063"
        );
        udao.doSave(unregisteredUser2);

        // ---------------------------------> operator
        operator = new Operator(
                operatorUser,
                "12/12/12",
                "sono laureato alla Pegaso");
        operatorDao.doSave(operator);

        // ---------------------------------> order
        orderByRegisteredUser = new Order(
                1,
                buyerUser,
                operator,
                "11/11/11");
        orderDao.doSave(orderByRegisteredUser);


        orderByUnregisteredUserSingleOrder = new Order(
                2,
                unregisteredUser,
                operator,
                "10/10/10"
        );
        orderDao.doSave(orderByUnregisteredUserSingleOrder);


        orderByUnregisteredUserMultiOrder1 = new Order(
                3,
                unregisteredUser2,
                operator,
                "8/8/8"
        );
        orderDao.doSave(orderByUnregisteredUserMultiOrder1);


        orderByUnregisteredUserMultiOrder2 = new Order(
                4,
                unregisteredUser2,
                operator,
                "9/9/9"
        );
        orderDao.doSave(orderByUnregisteredUserMultiOrder2);
    }

    @AfterAll
    static public void terminate() {
        orderDao.doDelete(orderByRegisteredUser.getId());
        orderDao.doDelete(orderByUnregisteredUserSingleOrder.getId());
        orderDao.doDelete(orderByUnregisteredUserMultiOrder1.getId());
        orderDao.doDelete(orderByUnregisteredUserMultiOrder2.getId());

        operatorDao.doDeleteByUsername(operator.getUsername());

        udao.doDeleteFromUsername(buyerUser.getUsername());
        udao.doDeleteFromUsername(unregisteredUser2.getUsername());
    }

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

    @Test
    public void IdProductNotOk() {
        request.addParameter("approveOrder", "1abc");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void idProductOkButOrderDoesNotExist() {
        request.addParameter("approveOrder", "9423427459");
        assertThrows(RequestParametersException.class, ()->servlet.doPost(request, response));
    }

    @Test
    public void OrderIsByUnregisteredUserAndOnlyOneOrder() throws ServletException, IOException {
        request.addParameter("approveOrder", "2");
        servlet.doPost(request, response);
        assertNull(orderDao.doRetrieveById(2));
    }

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
}