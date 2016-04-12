
import core.CustomConditions;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static core.CustomConditions.minimumSizeOf;
import static core.CustomConditions.sizeOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;


public class GoogleSearchTest {

    public static WebDriver driver = new FirefoxDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 6);
    public static String listResult = ".srg>.g";


    @AfterClass
    public static void tearDown() {
        driver.quit();
    }


    @Test
    public void testSearchLifeCycle() {
        driver.get("http://google.com/ncr");

        driver.manage().window().maximize();

        driver.findElement(By.name("q")).sendKeys("Selenium automates browsers" + Keys.ENTER);

        wait.until(sizeOf(By.cssSelector(listResult), 10));
        wait.until(textToBePresentInElementLocated(By.cssSelector(listResult + ":nth-child(1)"), "Selenium automates browsers"));

        followNthLink(0);
        wait.until(urlContains("http://www.seleniumhq.org/"));
    }

    @Test
    public void followResultLink() {
        searchResult("Selenium automates browsers");
        followNthLink(0);
        wait.until(urlContains("http://www.seleniumhq.org/"));
    }

    public void followNthLink(int index) {
        wait.until(minimumSizeOf(By.cssSelector(listResult), index + 1));
        driver.findElements(By.cssSelector(listResult)).get(index).findElement(By.className("r")).click();
    }

    public void searchResult(String searchText) {
        driver.get("https://www.google.com.ua/search?q=" + searchText);
    }


}