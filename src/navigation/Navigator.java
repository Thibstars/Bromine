package navigation;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Thibault on 29/08/2016.
 */
public final class Navigator {
    private static WebDriver driver = new ChromeDriver();
    private static Wait<WebDriver> wait = new WebDriverWait(driver, 30);

    public Navigator() {
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().window().setPosition(new Point(2000, 1)); // To 2nd monitor.
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        Navigator.driver = driver;
    }

    public static Wait<WebDriver> getWait() {
        return wait;
    }

    public static void setWait(Wait<WebDriver> wait) {
        Navigator.wait = wait;
    }

    public static void navigateTo(URL url) {
        driver.navigate().to(url);
    }

    public static void navigateBack() {
        driver.navigate().back();
    }

    public static void navigateForward() {
        driver.navigate().forward();
    }

    public static void navigateRefresh() {
        driver.navigate().refresh();
    }

    public static void implicitlyWait(int second) {
        implicitlyWait(second, TimeUnit.SECONDS);
    }

    public static void implicitlyWait(long value, TimeUnit timeUnit) {
        driver.manage().timeouts().implicitlyWait(value, timeUnit);
    }

    public static void explicitlyWaitForElementPresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void explicitlyWaitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void explicitlyWaitForElementInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void explicitylyWaitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void explicitlyWaitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static String getUrl() {
        return driver.getCurrentUrl();
    }

    public static String getTitle() {
        return driver.getTitle();
    }
}
