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

public class ModifyUserDataCountryShort {


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
        User u = new User("MyUsername","password", "Name", "Surname", "Address", "City", "IT", "2020-11-16", "Utenteprova@libero.it", 'M', "3111111111");
        us.doSave(u);

    }


    @Test
    public void ModifyUserDataCountryShort() throws Exception {
        driver.get("http://localhost:8080/gamehub_war_exploded/");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Login")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//form[@id='loginForm']/div")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("usernameField")).click();
        driver.findElement(By.id("usernameField")).clear();
        driver.findElement(By.id("usernameField")).sendKeys("MyUsername");
        driver.findElement(By.id("passwordField")).click();
        driver.findElement(By.id("passwordField")).clear();
        driver.findElement(By.id("passwordField")).sendKeys("password");
        Thread.sleep(2000);
        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Reserved Area")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("2")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tr[@id='2tr']/td[6]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tr[@id='2tr']/td[6]")).clear();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tr[@id='2tr']/td[6]")).sendKeys("F");
        Thread.sleep(2000);
        driver.findElement(By.id("2")).click();
        Thread.sleep(2000);
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