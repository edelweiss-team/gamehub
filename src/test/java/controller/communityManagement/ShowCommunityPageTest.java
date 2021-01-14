package controller.communityManagement;

import controller.communityManagement.ShowCommunityPage;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShowCommunityPageTest {
    private ShowCommunityPage servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    @BeforeEach
    void setUp() {
        servlet = new ShowCommunityPage();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        BasicConfigurator.configure();
    }

    @Test
    public void showCommunityOk() throws ServletException, IOException {
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/Community.jsp", response.getForwardedUrl());
    }
}