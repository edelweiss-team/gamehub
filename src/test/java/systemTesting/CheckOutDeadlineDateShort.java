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

public class CheckOutDeadlineDateShort {

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
    public void CheckOutDeadlineDateShort() throws Exception {
        driver.get("http://localhost:8080/gamehub_war_exploded/index.html");
        Thread.sleep(1500);
        driver.findElement(By.linkText("Shop")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("(//button[@id='category-button'])[2]")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("(//button[@type='submit'])[6]")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[@id='cart']/i")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
        Thread.sleep(1500);
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Gerardo");
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Brescia");
        driver.findElement(By.id("mail")).click();
        driver.findElement(By.id("mail")).clear();
        driver.findElement(By.id("mail")).sendKeys("Gerardo@gmail.com");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).clear();
        driver.findElement(By.id("address")).sendKeys("Via Castello");
        driver.findElement(By.id("country")).click();
        driver.findElement(By.id("country")).clear();
        driver.findElement(By.id("country")).sendKeys("FR");
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).clear();
        driver.findElement(By.id("city")).sendKeys("Fisciano");
        driver.findElement(By.id("telephone")).click();
        driver.findElement(By.id("telephone")).clear();
        driver.findElement(By.id("telephone")).sendKeys("3328985488");
        driver.findElement(By.id("cc-name")).click();
        driver.findElement(By.id("cc-name")).clear();
        driver.findElement(By.id("cc-name")).sendKeys("Nomecarta");
        driver.findElement(By.id("cc-number")).click();
        driver.findElement(By.id("cc-number")).clear();
        driver.findElement(By.id("cc-number")).sendKeys("4916551444956962");
        driver.findElement(By.id("cc-expiration")).click();
        driver.findElement(By.id("cc-expiration")).clear();
        driver.findElement(By.id("cc-expiration")).sendKeys("2/03");
        driver.findElement(By.id("cc-cvv")).click();
        driver.findElement(By.id("cc-cvv")).clear();
        driver.findElement(By.id("cc-cvv")).sendKeys("347");
        Thread.sleep(1500);
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
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