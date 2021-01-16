package controller.userManagement.adminManagement;

import controller.RequestParametersException;
import controller.userManagement.userProfileManagement.LogoutServlet;
import model.bean.*;
import model.dao.*;
import org.apache.log4j.BasicConfigurator;
import org.jetbrains.annotations.NotNull;
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

public class ShowAdminAreaServletTest {

    private ShowAdminAreaServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private static UserDAO dao = new UserDAO();
    private User u2;
    private Admin a;
    private static DigitalProduct pd1,pd2,pd3,pd4;
    private static PhysicalProduct pp1,pp2,pp3,pp4;

    @BeforeEach
    public void setUp() {
        servlet = new ShowAdminAreaServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        u2 = new User("MyUsername", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente@gmail.it", 'm',
                "3281883997");
        a = new Admin(u2,"");

        request.setSession(session);
        BasicConfigurator.configure();
    }

    @BeforeAll
    public static void populate(){
        User u1 = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        User u2 = new User("MyUsername12", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente12@gmail.it", 'm',
                "3281883997");
        User u3 = new User("MyUsername13", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente13@gmail.it", 'm',
                "3281883997");
        User u4 = new User("MyUsername14", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente14@gmail.it", 'm',
                "3281883997");
        UserDAO ud = new UserDAO();
        ud.doSave(u1);
        ud.doSave(u2);
        ud.doSave(u3);
        ud.doSave(u4);
        pd1 = new DigitalProduct(50, "NuovoProdottoTesting1", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        pd2 = new DigitalProduct(51, "NuovoProdottoTesting2", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        pd3 = new DigitalProduct(52, "NuovoProdottoTesting3", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        pd4 = new DigitalProduct(53, "NuovoProdottoTesting4", 23.56, "testing", "imagetesting", new ArrayList<Category>() , new ArrayList<Tag>(), 330,
                "xbox", "1999-05-05", 18, "testing", "testingpub");
        pp1 = new PhysicalProduct(60, "NuovoProdottoTesting4", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "2x2x2", 20.05);
        pp2 = new PhysicalProduct(61, "NuovoProdottoTesting5", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "2x2x2", 20.05);
        pp3 = new PhysicalProduct(62, "NuovoProdottoTesting6", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "2x2x2", 20.05);
        pp4 = new PhysicalProduct(63, "NuovoProdottoTesting7", 23.56,
                "testing", "imagetest", new ArrayList<Category>() , new ArrayList<Tag>(),
                200, "2x2x2", 20.05);
        DigitalProductDAO digdao = new DigitalProductDAO();
        PhysicalProductDAO phydao = new PhysicalProductDAO();
        digdao.doSave(pd1);
        digdao.doSave(pd2);
        digdao.doSave(pd3);
        digdao.doSave(pd4);
        phydao.doSave(pp1);
        phydao.doSave(pp2);
        phydao.doSave(pp3);
        phydao.doSave(pp4);
        Category cat1 = new Category("Sparatutto1","sparatutto per tutti","path");
        Category cat2 = new Category("Sparatutto2","sparatutto per tutti","path");
        Category cat3 = new Category("Sparatutto3","sparatutto per tutti","path");
        Category cat4 = new Category("Sparatutto4","sparatutto per tutti","path");
        CategoryDAO catdao = new CategoryDAO();
        catdao.doSave(cat1);
        catdao.doSave(cat2);
        catdao.doSave(cat3);
        catdao.doSave(cat4);
        Tag tag1 = new Tag("Tag1");
        Tag tag2 = new Tag("Tag2");
        Tag tag3 = new Tag("Tag3");
        Tag tag4 = new Tag("Tag4");
        TagDAO tagdao = new TagDAO();
        tagdao.doSave(tag1);
        tagdao.doSave(tag2);
        tagdao.doSave(tag3);
        tagdao.doSave(tag4);
        User userop1 = new User("userop1","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "userop1@gmail.com", 'M', "1111111111");
        Operator o1 = new Operator(userop1, "2020-11-11", "cv");
        User userop2 = new User("userop2","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "userop2@gmail.com", 'M', "1111111111");
        Operator o2 = new Operator(userop2, "2020-11-11", "cv");
        User userop3 = new User("userop3","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "userop3@gmail.com", 'M', "1111111111");
        Operator o3 = new Operator(userop3, "2020-11-11", "cv");
        User userop4 = new User("userop4","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "userop4@gmail.com", 'M', "1111111111");
        Operator o4 = new Operator(userop4, "2020-11-11", "cv");
        OperatorDAO operdao = new OperatorDAO();
        operdao.doSave(o1);
        operdao.doSave(o2);
        operdao.doSave(o3);
        operdao.doSave(o4);
        User usermod1 = new User("usernamemod1", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "usernamemod1@gmail.com", 'M', "1111111111");
        Moderator mod1 = new Moderator(usermod1, "2020-11-11");
        User usermod2 = new User("usernamemod2", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "usernamemod2@gmail.com", 'M', "1111111111");
        Moderator mod2 = new Moderator(usermod2, "2020-11-11");
        User usermod3 = new User("usernamemod3", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "usernamemod3@gmail.com", 'M', "1111111111");
        Moderator mod3 = new Moderator(usermod3, "2020-11-11");
        User usermod4 = new User("usernamemod4", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "usernamemod4@gmail.com", 'M', "1111111111");
        Moderator mod4 = new Moderator(usermod4, "2020-11-11");
        ModeratorDAO moddao = new ModeratorDAO();
        moddao.doSave(mod1);
        moddao.doSave(mod2);
        moddao.doSave(mod3);
        moddao.doSave(mod4);
        Admin admin1 = new Admin(mod1, false);
        Admin admin2 = new Admin(mod2, false);
        Admin admin3 = new Admin(mod3, false);
        Admin admin4 = new Admin(mod4, false);
        AdminDAO addao = new AdminDAO();
        addao.doSave(admin1);
        addao.doSave(admin2);
        addao.doSave(admin3);
        addao.doSave(admin4);
    }

    @Test
    public void logoutNotOk() throws ServletException, IOException {
        session = new MockHttpSession();
        request.setSession(session);
        assertThrows(RequestParametersException.class,() -> servlet.doPost(request, response));
    }

    @Test
    public void logoutOk() throws ServletException, IOException {
        session = new MockHttpSession();
        session.setAttribute("loggedUser" , a);
        request.setSession(session);
        assertDoesNotThrow(() -> servlet.doPost(request, response));
    }

    @Test
    public void ShowAdminAreaEntersIf() throws ServletException, IOException{
        session = new MockHttpSession();
        session.setAttribute("loggedUser" , a);
        request.setSession(session);
        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/AdminArea.jsp", response.getForwardedUrl());
    }

    @Test
    public void ShowAdminAreaNotEntersIf() throws ServletException, IOException{
        session = new MockHttpSession();
        session.setAttribute("loggedUser" , a);
        request.setSession(session);

        servlet.doPost(request, response);
        assertEquals("/WEB-INF/view/AdminArea.jsp", response.getForwardedUrl());
    }

    @AfterAll
    public static void clearAll(){
        User u1 = new User("MyUsername11", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente11@gmail.it", 'm',
                "3281883997");
        User u2 = new User("MyUsername12", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente12@gmail.it", 'm',
                "3281883997");
        User u3 = new User("MyUsername13", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente13@gmail.it", 'm',
                "3281883997");
        User u4 = new User("MyUsername14", "Password1","Nomenuovo", "Cognomenuovo",
                "Inidirizzo","Città","Nazione",
                "1999-05-22", "Utente14@gmail.it", 'm',
                "3281883997");
        UserDAO ud = new UserDAO();
        ud.doDeleteFromUsername(u1.getUsername());
        ud.doDeleteFromUsername(u2.getUsername());
        ud.doDeleteFromUsername(u3.getUsername());
        ud.doDeleteFromUsername(u4.getUsername());
        DigitalProductDAO digdao = new DigitalProductDAO();
        PhysicalProductDAO phydao = new PhysicalProductDAO();
        digdao.doDelete(pd1.getId());
        digdao.doDelete(pd2.getId());
        digdao.doDelete(pd3.getId());
        digdao.doDelete(pd4.getId());
        phydao.doDelete(pp1.getId());
        phydao.doDelete(pp2.getId());
        phydao.doDelete(pp3.getId());
        phydao.doDelete(pp4.getId());
        Category cat1 = new Category("Sparatutto1","sparatutto per tutti","path");
        Category cat2 = new Category("Sparatutto2","sparatutto per tutti","path");
        Category cat3 = new Category("Sparatutto3","sparatutto per tutti","path");
        Category cat4 = new Category("Sparatutto4","sparatutto per tutti","path");
        CategoryDAO catdao = new CategoryDAO();
        catdao.doDeleteByName(cat1.getName());
        catdao.doDeleteByName(cat2.getName());
        catdao.doDeleteByName(cat3.getName());
        catdao.doDeleteByName(cat4.getName());
        Tag tag1 = new Tag("Tag1");
        Tag tag2 = new Tag("Tag2");
        Tag tag3 = new Tag("Tag3");
        Tag tag4 = new Tag("Tag4");
        TagDAO tagdao = new TagDAO();
        tagdao.doDelete(tag1.getName());
        tagdao.doDelete(tag2.getName());
        tagdao.doDelete(tag3.getName());
        tagdao.doDelete(tag4.getName());
        User userop1 = new User("userop1","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "userop1@gmail.com", 'M', "1111111111");
        Operator o1 = new Operator(userop1, "2020-11-11", "cv");
        User userop2 = new User("userop2","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "userop2@gmail.com", 'M', "1111111111");
        Operator o2 = new Operator(userop2, "2020-11-11", "cv");
        User userop3 = new User("userop3","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "userop3@gmail.com", 'M', "1111111111");
        Operator o3 = new Operator(userop3, "2020-11-11", "cv");
        User userop4 = new User("userop4","password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "userop4@gmail.com", 'M', "1111111111");
        Operator o4 = new Operator(userop4, "2020-11-11", "cv");
        OperatorDAO operdao = new OperatorDAO();
        operdao.doDeleteByUsername(o1.getUsername());
        operdao.doDeleteByUsername(o2.getUsername());
        operdao.doDeleteByUsername(o3.getUsername());
        operdao.doDeleteByUsername(o4.getUsername());
        User usermod1 = new User("usernamemod1", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "usernamemod1@gmail.com", 'M', "1111111111");
        Moderator mod1 = new Moderator(usermod1, "2020-11-11");
        User usermod2 = new User("usernamemod2", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "usernamemod2@gmail.com", 'M', "1111111111");
        Moderator mod2 = new Moderator(usermod2, "2020-11-11");
        User usermod3 = new User("usernamemod3", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "usernamemod3@gmail.com", 'M', "1111111111");
        Moderator mod3 = new Moderator(usermod3, "2020-11-11");
        User usermod4 = new User("usernamemod4", "password", "Name", "Surname", "Address", "City", "Country", "2020-11-16", "usernamemod4@gmail.com", 'M', "1111111111");
        Moderator mod4 = new Moderator(usermod4, "2020-11-11");
        ModeratorDAO moddao = new ModeratorDAO();
        moddao.doDeleteByUsername(mod1.getUsername());
        moddao.doDeleteByUsername(mod2.getUsername());
        moddao.doDeleteByUsername(mod3.getUsername());
        moddao.doDeleteByUsername(mod4.getUsername());
        Admin admin1 = new Admin(mod1, false);
        Admin admin2 = new Admin(mod2, false);
        Admin admin3 = new Admin(mod3, false);
        Admin admin4 = new Admin(mod4, false);
        AdminDAO addao = new AdminDAO();
        addao.doDeleteByUsername(admin1.getUsername());
        addao.doDeleteByUsername(admin2.getUsername());
        addao.doDeleteByUsername(admin3.getUsername());
        addao.doDeleteByUsername(admin4.getUsername());
    }

}
