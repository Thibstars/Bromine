package navigation;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Page;
import stats.StatsAction;
import stats.StatsTracker;
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

    private static final Logger LOGGER = Logger.getLogger(Navigator.class);

    private WebDriver driver;
    private Wait<WebDriver> wait;
    private Environment environment;
    private boolean highlightingEnabled = false;

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
     * Clicks on a specified element.
     * @param element the element to click on
     */
    public void click(WebElement element) {
        LOGGER.debug("Performing click on " + element.toString());
        if (highlightingEnabled) Highlighter.highlightElement(element);
        element.click();
        StatsTracker.getInstance().track(StatsAction.MOUSE_LMB_CLICK);
    }

    /**
     * Clicks on a specified element and waits for the page to load.
     * @param element the element to click on
     */
    public void clickAndWait(WebElement element) {
        click(element);
        explicitlyWaitForPageLoaded();
    }

    /**
     * Clicks on a specified element using ng-click (Angular).
     * Using this method in stead of the default one has a slight
     * performance impact compared to the default click method.
     * @param element the element to click on
     */
    public void NGClick(WebElement element) {
        LOGGER.debug("Performing ng-click on " + element.toString());

        if (highlightingEnabled) Highlighter.highlightElement(element);

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        //Implicit wait is needed because Selenium doesn't know how angular loads and works
        implicitlyWait(1);

        explicitlyWaitForElementClickable(element);
        actions.moveToElement(element).click().perform();
        StatsTracker.getInstance().track(StatsAction.MOUSE_LMB_CLICK);
    }

    /**
     * Clicks on a specified element using ng-click (Angular).
     * Using this method in stead of the default one has a slight
     * performance impact compared to the default click method.
     *
     * After the click it will wait untill the page is loaded.
     * @param element the element to click on
     */
    public void NGClickAndWait(WebElement element) {
        NGClick(element);
        explicitlyWaitForPageLoaded();
    }

    /**
     * Double clicks on a specified element.
     * @param element the element to double click
     */
    public void doubleClick(WebElement element) {
        LOGGER.debug("Performing double click on " + element.toString());

        if (highlightingEnabled) Highlighter.highlightElement(element);

        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
        StatsTracker.getInstance().track(StatsAction.MOUSE_LMB_DOUBLE_CLICK);
    }

    /**
     * Sends keys on a specified element.
     * @param element the element to send keys to
     * @param charSequence the keys to send
     */
    public void sendKeys(WebElement element, String charSequence) {
        LOGGER.debug("Sending keys [" + charSequence + "] to " + element.toString());

        if (highlightingEnabled) Highlighter.highlightElement(element);

        Actions actions = new Actions(driver);
        actions.sendKeys(element, charSequence).perform();
        StatsTracker.getInstance().track(StatsAction.KEYBOARD_TYPE);
    }

    /**
     * Sends specified keys.
     * @param charSequence the keys to send
     */
    public void sendKeys(String charSequence) {
        LOGGER.debug("Sending keys [" + charSequence + "]");
        Actions actions = new Actions(driver);
        actions.sendKeys(charSequence).perform();
        StatsTracker.getInstance().track(StatsAction.KEYBOARD_TYPE);
    }

    /**
     * Navigates to a specified url.
     * @param url the url to navigate to
     */
    public void navigateTo(URL url) {
        LOGGER.debug("Navigating to " + url.toString());
        driver.navigate().to(url);
    }

    /**
     * Navigates to a specified page.
     * @param page the page to navigate to
     */
    public void navigateTo(Page page) {
        LOGGER.debug("Navigating to " + page.toString());
        driver.navigate().to(page.getCompleteURL());
    }

    /**
     * Navigates back to the previous page.
     */
    public void navigateBack() {
        LOGGER.debug("Navigating back");
        driver.navigate().back();
    }

    /**
     * Navigates to the next page.
     */
    public void navigateForward() {
        LOGGER.debug("Navigating forward");
        driver.navigate().forward();
    }

    /**
     * Refreshes the current page.
     */
    public void navigateRefresh() {
        LOGGER.debug("Performing refresh");
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
        LOGGER.debug("Implicitly wait for " + value + " " + timeUnit.toString().toLowerCase());
        driver.manage().timeouts().implicitlyWait(value, timeUnit);
        StatsTracker.getInstance().track(StatsAction.WAIT_IMPLICIT);
    }

    /**
     * Performs an explicit wait for an element until it is present on the page.
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementPresent(By locator) {
        LOGGER.debug("Explicitly waiting for an element to be present");
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit, fluent wait until an element is found, or a timeout (30 seconds) occurs.
     * Returns the found element, if any.
     * @param locator the method used to find the element
     * @return the found element, if any
     */
    public WebElement fluentWait(By locator) {
        return fluentWait(locator, 30, 5);
    }

    /**
     * Performs an explicit, fluent wait until an element is found, or a timeout occurs.
     * Returns the found element, if any.
     * @param locator the method used to find the element
     * @param timeout the timeout in seconds
     * @param pollEvery the amount of seconds between each poll
     * @return the found element, if any
     */
    public WebElement fluentWait(By locator, int timeout, int pollEvery) {
        return fluentWait(locator, timeout, pollEvery, TimeUnit.SECONDS);
    }

    /**
     * Performs an explicit, fluent wait until an element is found, or a timeout occurs.
     * Returns the found element, if any.
     * @param locator the method used to find the element
     * @param timeout the timeout in seconds
     * @param pollEvery the amount of seconds between each poll
     * @param timeUnit the time unit used for the timeout and polling
     * @return the found element, if any
     */
    public WebElement fluentWait(By locator, int timeout, int pollEvery, TimeUnit timeUnit) {
        LOGGER.debug("Fluently waiting until an element is found with locator: " + locator);

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeout, timeUnit)
                .pollingEvery(pollEvery, timeUnit)
                .ignoring(NoSuchElementException.class);

        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);

        return wait.until(driver1 -> driver1.findElement(locator));
    }


    /**
     * Performs an explicit wait for an element until it is visible on the page.
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementVisible(By locator) {
        LOGGER.debug("Explicitly waiting for an element to be visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait for an element until it is invisible on the page.
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementInvisible(By locator) {
        LOGGER.debug("Explicitly waiting for an element to be invisible");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait for an element until it is clickable.
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementClickable(By locator) {
        LOGGER.debug("Explicitly waiting for an element to be clickable");
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait for given WebElement until it is clickable.
     * @param element the element for which to wait until it is clickable
     */
    public void explicitlyWaitForElementClickable(WebElement element) {
        LOGGER.debug("Explicitly waiting for element " + element.toString() + " to be clickable");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait until the page is loaded.
     */
    public void explicitlyWaitForPageLoaded() {
        LOGGER.debug("Explicitly waiting for the page to be loaded.");
        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(d -> {
            LOGGER.debug("Current Window State: "
                    + String.valueOf(((JavascriptExecutor) d).executeScript("return document.readyState")));
            return String
                    .valueOf(((JavascriptExecutor) d).executeScript("return document.readyState"))
                    .equals("complete");
        });
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Scrolls the specified element into the view.
     *
     * @param element the element to scroll in the view
     */
    public void scrollElementIntoView(WebElement element) {
        LOGGER.debug("Scrolling element " + element.toString() + " into view");

        if (highlightingEnabled) Highlighter.highlightElement(element);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Moves te mouse to the specified element.
     *
     * @param element the element to which the mouse should move
     */
    public void moveToElement(WebElement element) {
        LOGGER.debug("Moving to element: " + element.toString());

        if (highlightingEnabled) Highlighter.highlightElement(element);

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
        LOGGER.debug("Retrieving parent element of: " + element.toString());
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
        LOGGER.debug("Dragging element: " + element.toString());

        if (highlightingEnabled) Highlighter.highlightElement(element);

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
        LOGGER.debug("Dragging element: " + source.toString() + " and dropping on: " + target.toString());

        if (highlightingEnabled) Highlighter.highlightElement(source);

        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target);

        if (highlightingEnabled) Highlighter.highlightElement(target);
    }

    public boolean isHighlightingEnabled() {
        return highlightingEnabled;
    }

    public void setHighlightingEnabled(boolean highlightingEnabled) {
        this.highlightingEnabled = highlightingEnabled;
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
