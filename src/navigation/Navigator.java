package navigation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import pages.Page;
import sut.Environment;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Representation of a Navigator. This (Singleton) class is responsible for
 * properly using the WebDriver and some additional functionality.
 * @author Thibault Helsmoortel
 */
public final class Navigator {
    private static Navigator navigatorInstance = new Navigator();

    private WebDriver driver;
    private Wait<WebDriver> wait;
    private Environment environment;

    /**
     * Class constructor. Publicly unavailable.
     */
    private Navigator() {
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public Wait<WebDriver> getWait() {
        return wait;
    }

    public void setWait(Wait<WebDriver> wait) {
        this.wait = wait;
    }

    /**
     * Navigates to a specified url.
     * @param url the url to navigate to
     */
    public void navigateTo(URL url) {
        driver.navigate().to(url);
    }

    /**
     * Navigates to a specified page.
     * @param page the page to navigate to
     */
    public void navigateTo(Page page) {
        driver.navigate().to(page.getCompleteURL());
    }

    /**
     * Navigates back to the previous page.
     */
    public void navigateBack() {
        driver.navigate().back();
    }

    /**
     * Navigates to the next page.
     */
    public void navigateForward() {
        driver.navigate().forward();
    }

    /**
     * Refreshes the current page.
     */
    public void navigateRefresh() {
        driver.navigate().refresh();
    }

    /**
     * Performs an implicit wait for a given amount of seconds.
     * @param seconds the amount of seconds to implicitly wait
     */
    public void implicitlyWait(int seconds) {
        implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Performs an implicit wait for a given amount of time and the corresponding time unit.
     * @param value the amount of time to wait
     * @param timeUnit the time unit for the given amount to wait
     */
    public void implicitlyWait(long value, TimeUnit timeUnit) {
        driver.manage().timeouts().implicitlyWait(value, timeUnit);
    }

    /**
     * Performs an explicit wait for an element until it is present on the page.
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementPresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Performs an explicit wait for an element until it is visible on the page.
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Performs an explicit wait for an element until it is invisible on the page.
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Performs an explicit wait for an element until it is clickable.
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Performs an explicit wait for given WebElement until it is clickable.
     * @param element the element for which to wait until it is clickable
     */
    public void explicitlyWaitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Scrolls the specified element into the view.
     *
     * @param element the element to scroll in the view
     */
    public void scrollElementIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Moves te mouse to the specified element.
     *
     * @param element the element to which the mouse should move
     */
    public void moveToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    /**
     * Returns the parent WebElement of the specified element.
     *
     * @param element the element from which to find the parent
     * @return the parent element of the specified element
     */
    public WebElement getParent(WebElement element) {
        return (WebElement) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].parentNode;", element);
    }

    /**
     * Drags an element with an offset of the current position.
     *
     * @param element the element to drag
     * @param xOffset the x-axis offset
     * @param yOffset the y-axis offset
     */
    public void dragElement(WebElement element, int xOffset, int yOffset) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.clickAndHold();
        action.moveByOffset(xOffset, yOffset);
        action.release();
        action.build().perform();
    }

    /**
     * Drags and drops a source element onto a target element.
     *
     * @param source the source element to drag to the target element
     * @param target the target element that will accept the source element
     */
    public void dragAndDropElement(WebElement source, WebElement target) {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Returns the title of the current page.
     * @return the title of the current page
     */
    public String getTitle() {
        return driver.getTitle();
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public static synchronized Navigator getInstance() {
        return navigatorInstance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
