package navigation.bots;

import navigation.Navigator;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import stats.StatsAction;
import stats.StatsTracker;

import java.util.concurrent.TimeUnit;

/**
 * Class responsible for performing Selenium wait actions.
 * @author Thibault Helsmoortel
 */
public class WaiterBot {

    private static final Logger LOGGER = Logger.getLogger(WaiterBot.class);

    /**
     * Performs an implicit wait until a given expected condition is met.
     *
     * @param expectedCondition the expected condition
     */
    public void implicitlyWait(ExpectedCondition expectedCondition) {
        LOGGER.debug("Implicitly waiting until a condition is met.");
        Navigator.getInstance().getWait().until(expectedCondition);
        StatsTracker.getInstance().track(StatsAction.WAIT_IMPLICIT);
    }

    /**
     * Performs an implicit wait for a given amount of seconds.
     *
     * @param seconds the amount of seconds to implicitly wait
     */
    public void implicitlyWait(int seconds) {
        implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Performs an implicit wait for a given amount of time and the corresponding time unit.
     *
     * @param value    the amount of time to wait
     * @param timeUnit the time unit for the given amount to wait
     */
    public void implicitlyWait(long value, TimeUnit timeUnit) {
        LOGGER.debug("Implicitly wait for " + value + " " + timeUnit.toString().toLowerCase());
        Navigator.getInstance().getDriver().manage().timeouts().implicitlyWait(value, timeUnit);
        StatsTracker.getInstance().track(StatsAction.WAIT_IMPLICIT);
    }

    /**
     * Performs an explicit wait for an element until it is present on the page.
     *
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementPresent(By locator) {
        LOGGER.debug("Explicitly waiting for an element to be present");
        Navigator.getInstance().getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait for an element until it is visible on the page.
     *
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementVisible(By locator) {
        LOGGER.debug("Explicitly waiting for an element to be visible");
        Navigator.getInstance().getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait for an element until it is invisible on the page.
     *
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementInvisible(By locator) {
        LOGGER.debug("Explicitly waiting for an element to be invisible");
        Navigator.getInstance().getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait for an element until it is clickable.
     *
     * @param locator the method used to find the element
     */
    public void explicitlyWaitForElementClickable(By locator) {
        LOGGER.debug("Explicitly waiting for an element to be clickable");
        Navigator.getInstance().getWait().until(ExpectedConditions.elementToBeClickable(locator));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait for given WebElement until it is clickable.
     *
     * @param element the element for which to wait until it is clickable
     */
    public void explicitlyWaitForElementClickable(WebElement element) {
        LOGGER.debug("Explicitly waiting for element " + element.toString() + " to be clickable");
        Navigator.getInstance().getWait().until(ExpectedConditions.elementToBeClickable(element));
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit wait until the page is loaded.
     */
    public void explicitlyWaitForPageLoaded() {
        LOGGER.debug("Explicitly waiting for the page to be loaded.");
        Wait<WebDriver> wait = new WebDriverWait(Navigator.getInstance().getDriver(), 30);
        wait.until(d -> {
            assert d != null;
            LOGGER.debug("Current Window State: "
                    + String.valueOf(((JavascriptExecutor) d).executeScript("return document.readyState")));
            return String
                    .valueOf(((JavascriptExecutor) d).executeScript("return document.readyState"))
                    .equals("complete");
        });
        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);
    }

    /**
     * Performs an explicit, fluent wait until an element is found, or a timeout (30 seconds) occurs.
     * Returns the found element, if any.
     *
     * @param locator the method used to find the element
     * @return the found element, if any
     */
    public WebElement fluentWait(By locator) {
        return fluentWait(locator, 30, 5);
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
        return fluentWait(locator, timeout, pollEvery, TimeUnit.SECONDS);
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
        LOGGER.debug("Fluently waiting until an element is found with locator: " + locator);

        Wait<WebDriver> wait = new FluentWait<>(Navigator.getInstance().getDriver())
                .withTimeout(timeout, timeUnit)
                .pollingEvery(pollEvery, timeUnit)
                .ignoring(NoSuchElementException.class);

        StatsTracker.getInstance().track(StatsAction.WAIT_EXPLICIT);

        return wait.until(driver1 -> driver1.findElement(locator));
    }
}
