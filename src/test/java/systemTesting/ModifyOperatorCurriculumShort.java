package systemTesting;



import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import model.bean.Moderator;
import model.bean.Admin;
import model.bean.Operator;
import model.dao.AdminDAO;
import model.dao.ModeratorDAO;
import model.dao.OperatorDAO;
import model.dao.UserDAO;
import model.bean.User;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
/*
 * TC_11.1_1
 */
public class ModifyOperatorCurriculumShort {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    UserDAO us = new UserDAO();
    AdminDAO ad = new AdminDAO();
    ModeratorDAO mo = new ModeratorDAO();
    OperatorDAO op = new OperatorDAO();

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
        User u2 = new User("Antonio","password", "Name", "Surname", "Address", "City", "IT", "2020-11-16", "Prova@libero.it", 'M', "3311111121");
        Operator o = new Operator(u2,"2022-02-19", "Curriculum vitae");
        op.doSave(o);

    }

    @Test
    public void ModifyOperatorCurriculumShort() throws Exception {
        driver.get("http://localhost:8080/gamehub_war_exploded/index.html");
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
        driver.findElement(By.id("operator")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input[@value='📝'])[17]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//tr[@id='AntonioOperatorRow']/td[2]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//tr[@id='AntonioOperatorRow']/td[2]")).clear();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//tr[@id='AntonioOperatorRow']/td[2]")).sendKeys("C");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@value='Submit']")).click();
        Thread.sleep(2000);
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
        us.doDeleteFromUsername("Antonio");


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


