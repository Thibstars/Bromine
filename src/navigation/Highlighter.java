package navigation;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;

import java.io.File;
import java.io.IOException;

/**
 * Class responsible for (un)highlighting elements.
 *
 * @author Thibault Helsmoortel
 */
public final class Highlighter {

    private static final Logger LOGGER = Logger.getLogger(Highlighter.class);

    //Assuming JS is enabled
    private static JavascriptExecutor js = (JavascriptExecutor) Navigator.getInstance().getDriver();
    private static WebElement lastElem = null;
    private static String lastBorder = null;

    //Load scripts
    private static String SCRIPT_GET_ELEMENT_BORDER = null;

    static {
        try {
            SCRIPT_GET_ELEMENT_BORDER = FileUtils.readFileToString(new File(Highlighter.class.getClassLoader().getResource("getElementBorder.js").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String SCRIPT_UNHIGHLIGHT_ELEMENT;

    static {
        try {
            SCRIPT_UNHIGHLIGHT_ELEMENT = FileUtils.readFileToString(new File(Highlighter.class.getClassLoader().getResource("unhighlightElement.js").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Highlights the specified element.
     *
     * @param elem the element to highlight
     */
    public static void highlightElement(WebElement elem) {
        unhighlightLast();

        LOGGER.debug("Highlighting element: " + elem.toString());
        // remember the new element
        lastElem = elem;
        try {
            lastBorder = (String) (js.executeScript(SCRIPT_GET_ELEMENT_BORDER, elem));
        } catch (SessionNotFoundException e) {
            //Should for some reason be handled. The exception was raised
            // in the shouldReset test in the StatsTrackerTests class
            LOGGER.debug("Session wasn't found, couldn't get the last border.");
            lastBorder = null;
        }
    }

    /**
     * Removes the highlight on the last element.
     */
    private static void unhighlightLast() {
        if (lastElem != null && lastBorder != null) {
            try {
                LOGGER.debug("Unhighlighting element: " + lastElem.toString());
                //If there already is a highlighted element, unhighlight it
                js.executeScript(SCRIPT_UNHIGHLIGHT_ELEMENT, lastElem, lastBorder);
            } catch (StaleElementReferenceException ignored) {
                //The page got reloaded, the element isn't there
            } catch (SessionNotFoundException e) {
                //Should for some reason be handled. The exception was raised
                // in the shouldReset test in the StatsTrackerTests class
                LOGGER.debug("Session wasn't found, couldn't unhighlight previous element.");
            } finally {
                //Element either restored or wasn't valid, nullify in both cases
                lastElem = null;
            }
        }
    }
}
