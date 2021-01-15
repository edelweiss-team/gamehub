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

public class RegistrationFailedSexLong {

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

    }

    @Test
    public void registrationFailedSexLong() throws Exception {
        driver.get("http://localhost:8080/gamehub_war_exploded/index.html");
        Thread.sleep(1500);
        driver.findElement(By.linkText("Sign up")).click();
        Thread.sleep(1500);
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("MyUsername");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("Password1");
        driver.findElement(By.name("repeatPassword")).click();
        driver.findElement(By.name("repeatPassword")).clear();
        driver.findElement(By.name("repeatPassword")).sendKeys("Password1");
        driver.findElement(By.name("mail")).click();
        driver.findElement(By.name("mail")).clear();
        driver.findElement(By.name("mail")).sendKeys("Utente@gmail.it");
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Luigi");
        driver.findElement(By.name("surname")).click();
        driver.findElement(By.name("surname")).clear();
        driver.findElement(By.name("surname")).sendKeys("Rossi");
        driver.findElement(By.id("birthdate")).click();
        driver.findElement(By.id("birthdate")).clear();
        driver.findElement(By.id("birthdate")).sendKeys("1999-05-22");
        driver.findElement(By.xpath("//form[@id='signUpForm']/div[9]")).click();
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("Via Castello");
        driver.findElement(By.name("city")).clear();
        driver.findElement(By.name("city")).sendKeys("Fisciano");
        driver.findElement(By.name("country")).clear();
        driver.findElement(By.name("country")).sendKeys("Italia");
        driver.findElement(By.name("telephone")).clear();
        driver.findElement(By.name("telephone")).sendKeys("3281883997");
        driver.findElement(By.name("country")).click();
        driver.findElement(By.name("country")).clear();
        driver.findElement(By.name("country")).sendKeys("FR");
        driver.findElement(By.name("sex")).click();
        driver.findElement(By.name("sex")).clear();
        driver.findElement(By.name("sex")).sendKeys("paschio");
        Thread.sleep(1500);
        assertThrows(ElementClickInterceptedException.class, () -> driver.findElement(By.id("submitBtn")).click());
        Thread.sleep(1500);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }


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
