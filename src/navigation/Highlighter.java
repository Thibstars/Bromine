package navigation;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;

/**
 * Class responsible for (un)highlighting elements.
 * @author Thibault Helsmoortel
 */
public final class Highlighter {

    private static final Logger LOGGER = Logger.getLogger(Highlighter.class);

    //Assuming JS is enabled
    private static JavascriptExecutor js = (JavascriptExecutor)Navigator.getInstance().getDriver();
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
     * @param elem the element to highlight
     */
    public static void highlightElement(WebElement elem) {
        unhighlightLast();

        LOGGER.debug("Highlighting element: " + elem.toString());
        // remember the new element
        lastElem = elem;
        lastBorder = (String)(js.executeScript(SCRIPT_GET_ELEMENT_BORDER, elem));
    }

    /**
     * Removes the highlight on the last element.
     */
    private static void unhighlightLast() {
        if (lastElem != null) {
            try {
                LOGGER.debug("Unhighlighting element: " + lastElem.toString());
                //If there already is a highlighted element, unhighlight it
                js.executeScript(SCRIPT_UNHIGHLIGHT_ELEMENT, lastElem, lastBorder);
            } catch (StaleElementReferenceException ignored) {
                //The page got reloaded, the element isn't there
            } finally {
                //Element either restored or wasn't valid, nullify in both cases
                lastElem = null;
            }
        }
    }
}
