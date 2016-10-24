package navigation.bots;

import navigation.Navigator;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.yandex.qatools.allure.annotations.Step;
import stats.StatsAction;
import stats.StatsTracker;

/**
 * Class responsible for performing Selenium actions.
 *
 * @author Thibault Helsmoortel
 */
public class ActionBot {

    private static final Logger LOGGER = Logger.getLogger(ActionBot.class);

    /**
     * Clicks on a specified element.
     *
     * @param element the element to click on
     */
    @Step("Click on element: {0}")
    public void click(WebElement element) {
        LOGGER.debug("Performing click on " + element.toString());
        element.click();
        StatsTracker.getInstance().track(StatsAction.MOUSE_LMB_CLICK);
    }

    /**
     * Clicks on a specified element and waits for the page to load.
     *
     * @param element the element to click on
     */
    public void clickAndWait(WebElement element) {
        click(element);
        Navigator.getInstance().explicitlyWaitForPageLoaded();
    }


    /**
     * Clicks on a specified element using ng-click (Angular).
     * Using this method in stead of the default one has a slight
     * performance impact compared to the default click method.
     *
     * @param element the element to click on
     */
    @Step("Click on element: {0}")
    public void NGClick(WebElement element) {
        LOGGER.debug("Performing ng-click on " + element.toString());

        Actions actions = new Actions(Navigator.getInstance().getDriver());
        actions.moveToElement(element).perform();

        //Implicit wait is needed because Selenium doesn't know how angular loads and works
        Navigator.getInstance().implicitlyWait(1);

        Navigator.getInstance().explicitlyWaitForElementClickable(element);
        actions.moveToElement(element).click().perform();
        StatsTracker.getInstance().track(StatsAction.MOUSE_LMB_CLICK);
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
        NGClick(element);
        Navigator.getInstance().explicitlyWaitForPageLoaded();
    }

    /**
     * Double clicks on a specified element.
     *
     * @param element the element to double click
     */
    @Step("Double click on element: {0}")
    public void doubleClick(WebElement element) {
        LOGGER.debug("Performing double click on " + element.toString());

        Actions actions = new Actions(Navigator.getInstance().getDriver());
        actions.doubleClick(element).perform();
        StatsTracker.getInstance().track(StatsAction.MOUSE_LMB_DOUBLE_CLICK);
    }

    /**
     * Sends keys on a specified element.
     *
     * @param element      the element to send keys to
     * @param charSequence the keys to send
     */
    @Step("Send keys: {1} to element: {0} ")
    public void sendKeys(WebElement element, String charSequence) {
        LOGGER.debug("Sending keys [" + charSequence + "] to " + element.toString());

        Actions actions = new Actions(Navigator.getInstance().getDriver());
        actions.sendKeys(element, charSequence).perform();
        StatsTracker.getInstance().track(StatsAction.KEYBOARD_TYPE);
    }

    /**
     * Sends specified keys.
     *
     * @param charSequence the keys to send
     */
    @Step("Send keys: {1}")
    public void sendKeys(String charSequence) {
        LOGGER.debug("Sending keys [" + charSequence + "]");
        Actions actions = new Actions(Navigator.getInstance().getDriver());
        actions.sendKeys(charSequence).perform();
        StatsTracker.getInstance().track(StatsAction.KEYBOARD_TYPE);
    }

    /**
     * Uploads a file from a specified file path to a target element.
     *
     * @param element  the element accepting the upload
     * @param filePath the path of the file to upload
     */
    @Step("Upload file: {1} via element: {0}")
    public void uploadFile(WebElement element, String filePath) {
        LOGGER.debug("Uploading file: " + filePath);
        element.sendKeys(filePath);
    }

    /**
     * Scrolls the specified element into the view.
     *
     * @param element the element to scroll in the view
     */
    @Step("Scroll element: {0} into view")
    public void scrollElementIntoView(WebElement element) {
        LOGGER.debug("Scrolling element " + element.toString() + " into view");

        ((JavascriptExecutor) Navigator.getInstance().getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Moves te mouse to the specified element.
     *
     * @param element the element to which the mouse should move
     */
    @Step("Move to element: {0}")
    public void moveToElement(WebElement element) {
        LOGGER.debug("Moving to element: " + element.toString());

        Actions actions = new Actions(Navigator.getInstance().getDriver());
        actions.moveToElement(element).build().perform();
    }

    /**
     * Drags an element with an offset of the current position.
     *
     * @param element the element to drag
     * @param xOffset the x-axis offset
     * @param yOffset the y-axis offset
     */
    @Step("Dragging element: {0} with offsets: {1},{2}")
    public void dragElement(WebElement element, int xOffset, int yOffset) {
        LOGGER.debug("Dragging element: " + element.toString());

        Actions action = new Actions(Navigator.getInstance().getDriver());
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
    @Step("Dragging element: {0} to element: {1}")
    public void dragAndDropElement(WebElement source, WebElement target) {
        LOGGER.debug("Dragging element: " + source.toString() + " and dropping on: " + target.toString());

        Actions actions = new Actions(Navigator.getInstance().getDriver());
        actions.dragAndDrop(source, target);
    }

    /**
     * Focuses a specified element.
     *
     * @param element the element to focus
     */
    @Step("Focusing element: {0}")
    public void focusElement(WebElement element) {
        LOGGER.debug("Focusing element: " + element.toString());
        ((JavascriptExecutor) Navigator.getInstance().getDriver()).executeScript("arguments[0].focus();", element);
    }
}
