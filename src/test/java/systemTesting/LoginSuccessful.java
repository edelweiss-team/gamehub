package systemTesting;

import java.util.concurrent.TimeUnit;

import model.bean.User;
import model.dao.UserDAO;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
/*
 * TC_2.1_4
 */
public class LoginSuccessful {


    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private UserDAO us = new UserDAO();

    @Before
    public void setUp() throws Exception {
        System.setProperty(SetupTesting.DRIVER,SetupTesting.PATH);
        driver = SetupTesting.WEB_DRIVER;
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        User u = new User("MyUsername", "Password1", "Test","Test", "Via caaa", "Napoli", "IT", "2000-01-26", "test@hotmail.com", 'm', "3279919265");
        us.doSave(u);
    }


    @Test
    public void LoginSuccessful() throws Exception {
        driver.get("http://localhost:8080/gamehub_war_exploded/index.html");
        Thread.sleep(1000);
        driver.findElement(By.linkText("Sign up")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("fromSignUpToSignIn")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("usernameField")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("usernameField")).clear();
        Thread.sleep(1000);
        driver.findElement(By.id("usernameField")).sendKeys("MyUsername");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//body")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("passwordField")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("passwordField")).clear();
        Thread.sleep(1000);
        driver.findElement(By.id("passwordField")).sendKeys("Password1");
        Thread.sleep(1000);
        driver.findElement(By.id("submitBtn")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
        us.doDeleteFromUsername("MyUsername");
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
