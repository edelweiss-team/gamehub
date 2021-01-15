package systemTesting;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import model.dao.UserDAO;
import model.bean.User;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckOutLoggedUserCVVWrong {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    UserDAO us = new UserDAO();

    @Before
    public void setUp() throws Exception {
        System.setProperty(SetupTesting.DRIVER,SetupTesting.PATH);
        driver = SetupTesting.WEB_DRIVER;
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        User u = new User("usertest","password", "Name", "Surname", "Address", "City", "IT", "2020-11-16", "Utenteprova@libero.it", 'M', "1111111111");
        us.doSave(u);

    }

    @Test
    public void CheckOutLoggedUserCVVWrong() throws Exception {
        driver.get("http://localhost:8080/gamehub_war_exploded/");
        Thread.sleep(2000);
        driver.findElement(By.linkText("Login")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("usernameField")).click();
        driver.findElement(By.id("usernameField")).clear();
        driver.findElement(By.id("usernameField")).sendKeys("usertest");
        driver.findElement(By.id("passwordField")).click();
        driver.findElement(By.id("passwordField")).clear();
        driver.findElement(By.id("passwordField")).sendKeys("password");
        Thread.sleep(2000);
        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Shop")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@id='category-button'])[2]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@type='submit'])[6]")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("cart")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//label")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("cc-name")).click();
        driver.findElement(By.id("cc-name")).clear();
        driver.findElement(By.id("cc-name")).sendKeys("Nomecarta");
        driver.findElement(By.id("cc-number")).click();
        driver.findElement(By.id("cc-number")).clear();
        driver.findElement(By.id("cc-number")).sendKeys("4916551444956962");
        driver.findElement(By.id("cc-expiration")).click();
        driver.findElement(By.id("cc-expiration")).clear();
        driver.findElement(By.id("cc-expiration")).sendKeys("12/31");
        driver.findElement(By.id("cc-cvv")).click();
        driver.findElement(By.id("cc-cvv")).clear();
        driver.findElement(By.id("cc-cvv")).sendKeys("34a");
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        Thread.sleep(2000);


    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
        us.doDeleteFromUsername("usertest");


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