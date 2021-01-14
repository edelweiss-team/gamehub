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
    private static final @NotNull UserDAO udao = new UserDAO();

    private static @NotNull Operator operator;
    private static final @NotNull OperatorDAO opdao = new OperatorDAO();

    private static @NotNull Order order;
    private static final @NotNull OrderDAO odao = new OrderDAO();

    private static @NotNull Order orderByUnregisteredUser;

    @BeforeAll
    static public void init() {
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

        operator = new Operator(
                operatorUser,
                "12/12/12",
                "sono laureato alla Pegaso");
        opdao.doSave(operator);


        order = new Order(
                1,
                buyerUser,
                operator,
                "11/11/11");
        odao.doSave(order);


        orderByUnregisteredUser = new Order(
                2,
                unregisteredUser,
                operator,
                "10/10/10"
        );
        odao.doSave(orderByUnregisteredUser);
    }

    @AfterAll
    static public void terminate() {
        odao.doDelete(order.getId());
        odao.doDelete(orderByUnregisteredUser.getId());

        opdao.doDeleteByUsername(operator.getUsername());

        udao.doDeleteFromUsername(buyerUser.getUsername());
        udao.doDeleteFromUsername(unregisteredUser.getUsername());
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
    public void OrderIsByUnregisteredUser() throws ServletException, IOException {
        request.addParameter("approveOrder", "2");
        servlet.doPost(request, response);
        assertNull(odao.doRetrieveById(2));
    }

    @Test
    public void OrderIsByRegisteredUser() throws ServletException, IOException {
        request.addParameter("approveOrder", "1");
        servlet.doPost(request,response);
        assertEquals(((odao.doRetrieveById(1)).getOperator()).getUsername(), operator.getUsername());
    }
}