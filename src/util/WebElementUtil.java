package util;

import org.openqa.selenium.WebElement;

/**
 * Util class for WebElements.
 *
 * @author Thibault Helsmoortel
 */
public final class WebElementUtil {

    /**
     * Returns the specified element's text if any, the toString if none.
     *
     * @param element the element of which to get the text or toString of
     * @return the text if any, the toString if none
     */
    public static String getTextOrTagOrToString(WebElement element) {
        return element.getText() == null
                ? element.toString() == null
                    ? element.getTagName() : element.toString()
                : element.getText();
    }
}
