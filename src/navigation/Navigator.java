package navigation;

import navigation.bots.ActionBot;
import navigation.bots.WaiterBot;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import pages.Page;
import ru.yandex.qatools.allure.annotations.Step;
import sut.Environment;
import elements.WebElementByProxy;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Representation of a Navigator. This (Singleton) class is responsible for
 * properly using the WebDriver and some additional functionality.
 *
 * @author Thibault Helsmoortel
 */
public final class Navigator {
    private static Navigator navigatorInstance = new Navigator();

    private static final Logger LOGGER = Logger.getLogger(Navigator.class);

    private WebDriver driver;
    private Wait<WebDriver> wait;
    private Environment environment;
    private ActionBot actionBot;
    private WaiterBot waiterBot;
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
     * Navigates to a specified url.
     *
     * @param url the url to navigate to
     */
    @Step("Navigating to url: {0}")
    public void navigateTo(URL url) {
        LOGGER.debug("Navigating to " + url.toString());
        driver.navigate().to(url);
    }

    /**
     * Navigates to a specified url.
     *
     * @param url the url to navigate to
     */
    @Step("Navigating to url: {0}")
    public void navigateTo(String url) {
        LOGGER.debug("Navigating to " + url);
        driver.navigate().to(url);
    }

    /**
     * Navigates to a specified page.
     *
     * @param page the page to navigate to
     */
    @Step("Navigating to page: {0}")
    public void navigateTo(Page page) {
        LOGGER.debug("Navigating to " + page.toString());
        driver.navigate().to(page.getCompleteURL());
    }

    /**
     * Navigates back to the previous page.
     */
    @Step("Navigating back")
    public void navigateBack() {
        LOGGER.debug("Navigating back");
        driver.navigate().back();
    }

    /**
     * Navigates to the next page.
     */
    @Step("Navigating forward")
    public void navigateForward() {
        LOGGER.debug("Navigating forward");
        driver.navigate().forward();
    }

    /**
     * Refreshes the current page.
     */
    @Step("Performing refresh")
    public void navigateRefresh() {
        LOGGER.debug("Performing refresh");
        driver.navigate().refresh();
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
     * Returns the found element using the specified locator.
     *
     * @param by locator used to find the element
     * @return the found WebElement (actually a proxy)
     */
    public WebElement findElement(By by) {
        return new WebElementByProxy(driver.findElement(by), by);
    }

    /**
     * Returns the found elements using the specified locator.
     *
     * @param by locator used to find the elements
     * @return the found WebElements (actually proxies)
     */
    public List<WebElement> findElements(By by) {
        return driver.findElements(by).stream().map(
                element -> new WebElementByProxy(element, by)).collect(Collectors.toList());
    }

    /**
     * Clicks on a specified element.
     *
     * @param element the element to click on
     */
    public void click(WebElement element) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.click(element);
    }

    /**
     * Clicks on a specified element and waits for the page to load.
     *
     * @param element the element to click on
     */
    public void clickAndWait(WebElement element) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.clickAndWait(element);
    }

    /**
     * Clicks on a specified element using ng-click (Angular).
     * Using this method in stead of the default one has a slight
     * performance impact compared to the default click method.
     *
     * @param element the element to click on
     */
    public void NGClick(WebElement element) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.NGClick(element);
    }

    /**
     * Clicks on a specified element using ng-click (Angular).
     * Using this method in stead of the default one has a slight
     * performance impact compared to the default click method.
     * <p>
     * After the click it will wait untill the page is loaded.
     *
     * @param element the element to click on
     */
    public void NGClickAndWait(WebElement element) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.NGClickAndWait(element);
    }

    /**
     * Double clicks on a specified element.
     *
     * @param element the element to double click
     */
    public void doubleClick(WebElement element) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.doubleClick(element);
    }

    /**
     * Sends keys on a specified element.
     *
     * @param element      the element to send keys to
     * @param charSequence the keys to send
     */
    public void sendKeys(WebElement element, String charSequence) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.sendKeys(element, charSequence);
    }

    /**
     * Sends specified keys.
     *
     * @param charSequence the keys to send
     */
    public void sendKeys(String charSequence) {
        actionBot.sendKeys(charSequence);
    }

    /**
     * Uploads a file from a specified file path to a target element.
     *
     * @param element  the element accepting the upload
     * @param filePath the path of the file to upload
     */
    public void uploadFile(WebElement element, String filePath) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.uploadFile(element, filePath);
    }

    /**
     * Scrolls the specified element into the view.
     *
     * @param element the element to scroll in the view
     */
    public void scrollElementIntoView(WebElement element) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.scrollElementIntoView(element);
    }

    /**
     * Moves te mouse to the specified element.
     *
     * @param element the element to which the mouse should move
     */
    public void moveToElement(WebElement element) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.moveToElement(element);
    }

    /**
     * Drags an element with an offset of the current position.
     *
     * @param element the element to drag
     * @param xOffset the x-axis offset
     * @param yOffset the y-axis offset
     */
    public void dragElement(WebElement element, int xOffset, int yOffset) {
        if (highlightingEnabled) Highlighter.highlightElement(element);
        actionBot.dragElement(element, xOffset, yOffset);
    }

    /**
     * Drags and drops a source element onto a target element.
     *
     * @param source the source element to drag to the target element
     * @param target the target element that will accept the source element
     */
    public void dragAndDropElement(WebElement source, WebElement target) {
        if (highlightingEnabled) Highlighter.highlightElement(source);
        actionBot.dragAndDropElement(source, target);
        if (highlightingEnabled) Highlighter.highlightElement(target);
    }

    /**
     * Focuses a specified element.
     *
     * @param element the element to focus
     */
    public void focusElement(WebElement element) {
        actionBot.focusElement(element);
    }

    /**
     * Performs an implicit wait until a given expected condition is met.
     *
     * @param expectedCondition the expected condition
     */
    public void implicitlyWait(ExpectedCondition expectedCondition) {
        waiterBot.implicitlyWait(expectedCondition);
    }

    /**
     * Performs an implicit wait for a given amount of seconds.
     *
     * @param seconds the amount of seconds to implicitly wait
     */
    public void implicitlyWait(int seconds) {
        waiterBot.implicitlyWait(seconds);
    }

    /**
     * Performs an implicit wait for a given amount of time and the corresponding time unit.
     *
     * @param value    the amount of time to wait
     * @param timeUnit the time unit for the given amount to wait
     */
    public void implicitlyWait(long value, TimeUnit timeUnit) {
        waiterBot.implicitlyWait(value, timeUnit);
    }

    /**
     * Performs an explicit wait for an element until it is present on the page.
     *
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementPresent(By locator) {
        waiterBot.explicitlyWaitForElementPresent(locator);
    }

    /**
     * Performs an explicit wait for an element until it is visible on the page.
     *
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementVisible(By locator) {
        waiterBot.explicitlyWaitForElementVisible(locator);
    }

    /**
     * Performs an explicit wait for an element until it is invisible on the page.
     *
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementInvisible(By locator) {
        waiterBot.explicitlyWaitForElementInvisible(locator);
    }

    /**
     * Performs an explicit wait for an element until it is clickable.
     *
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementClickable(By locator) {
        waiterBot.explicitlyWaitForElementClickable(locator);
    }

    /**
     * Performs an explicit wait for given WebElement until it is clickable.
     *
     * @param element the element for which to wait until it is clickable
     */
    public void explicitlyWaitForElementClickable(WebElement element) {
        waiterBot.explicitlyWaitForElementClickable(element);
    }

    /**
     * Performs an explicit wait until the page is loaded.
     */
    public void explicitlyWaitForPageLoaded() {
        waiterBot.explicitlyWaitForPageLoaded();
    }

    /**
     * Performs an explicit, fluent wait until an element is found, or a timeout (30 seconds) occurs.
     * Returns the found element, if any.
     *
     * @param locator the method used to find the element
     * @return the found element, if any
     */
    public WebElement fluentWait(By locator) {
        return waiterBot.fluentWait(locator);
    }

    /**
     * Performs an explicit, fluent wait until an element is found, or a timeout occurs.
     * Returns the found element, if any.
     *
     * @param locator   the method used to find the element
     * @param timeout   the timeout in seconds
     * @param pollEvery the amount of seconds between each poll
     * @return the found element, if any
     */
    public WebElement fluentWait(By locator, int timeout, int pollEvery) {
        return waiterBot.fluentWait(locator, timeout, pollEvery);
    }

    /**
     * Performs an explicit, fluent wait until an element is found, or a timeout occurs.
     * Returns the found element, if any.
     *
     * @param locator   the method used to find the element
     * @param timeout   the timeout in seconds
     * @param pollEvery the amount of seconds between each poll
     * @param timeUnit  the time unit used for the timeout and polling
     * @return the found element, if any
     */
    public WebElement fluentWait(By locator, int timeout, int pollEvery, TimeUnit timeUnit) {
        return waiterBot.fluentWait(locator, timeout, pollEvery, timeUnit);
    }

    public ActionBot getActionBot() {
        return actionBot;
    }

    public void setActionBot(ActionBot actionBot) {
        this.actionBot = actionBot;
    }

    public WaiterBot getWaiterBot() {
        return waiterBot;
    }

    public void setWaiterBot(WaiterBot waiterBot) {
        this.waiterBot = waiterBot;
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
     *
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
