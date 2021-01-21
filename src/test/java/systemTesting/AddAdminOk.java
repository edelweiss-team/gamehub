package systemTesting;

import model.bean.Admin;
import model.bean.Moderator;
import model.bean.User;
import model.dao.AdminDAO;
import model.dao.ModeratorDAO;
import model.dao.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * TC_12.1_4
 */
public class AddAdminOk {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    UserDAO us = new UserDAO();
    AdminDAO ad = new AdminDAO();
    ModeratorDAO mo = new ModeratorDAO();

    @Before
    public void setUp() throws Exception {
        System.setProperty(SetupTesting.DRIVER,SetupTesting.PATH);
        driver = SetupTesting.WEB_DRIVER;
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        User u = new User("admintest","password", "Name", "Surname", "Address", "City", "IT", "2020-11-16", "Adminprova@libero.it", 'M', "3311111111");
        us.doSave(u);
        Moderator m = new Moderator(u, "2022-07-11");
        mo.doSave(m);
        Admin a = new Admin(u,"2022-07-11",true);
        ad.doSave(a);
        User u2 = new User("MyUsername","password", "Name", "Surname", "Address", "City", "IT", "2020-11-16", "Adminprova99@libero.it", 'M', "3311111111");
        us.doSave(u2);
        Moderator m2 = new Moderator(u2, "2022-07-11");
        mo.doSave(m2);
    }

    @Test
    public void testLoginnn() throws Exception {
        driver.get("http://localhost:8080/gamehub_war_exploded/");
        Thread.sleep(2000);
        driver.findElement(By.linkText("Login")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("usernameField")).click();
        driver.findElement(By.id("usernameField")).clear();
        driver.findElement(By.id("usernameField")).sendKeys("admintest");
        driver.findElement(By.id("passwordField")).click();
        driver.findElement(By.id("passwordField")).clear();
        driver.findElement(By.id("passwordField")).sendKeys("password");
        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Reserved Area")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@type='submit'])[4]")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("admin")).click();
        driver.findElement(By.id("adminName")).click();
        driver.findElement(By.id("adminName")).clear();
        driver.findElement(By.id("adminName")).sendKeys("MyUsername");
        driver.findElement(By.id("superRoot")).click();
        driver.findElement(By.id("superRoot")).clear();
        driver.findElement(By.id("superRoot")).sendKeys("true");
        Thread.sleep(2000);
        driver.findElement(By.id("submitAdminButtonContainerAddAdmin")).click();
        Thread.sleep(6000);
    }



    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
        us.doDeleteFromUsername("admintest");
        ad.doDeleteByUsername("admintest");
        mo.doDeleteByUsername("admintest");

        us.doDeleteFromUsername("MyUsername");
        ad.doDeleteByUsername("MyUsername");
        mo.doDeleteByUsername("MyUsername");

    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }



}
