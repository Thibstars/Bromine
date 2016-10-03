package navigation;

import org.openqa.selenium.JavascriptExecutor;

/**
 * Class representing the browser history.
 *
 * @author Thibault Helsmoortel
 */
public final class BrowserHistory {

    /**
     * Returns true when the browser has a history, else if otherwise.
     * @return true when the browser has a history, else if otherwise
     */
    public static boolean hasHistory() {
        return getHistoryLength() > 0;
    }

    /**
     * Returns the size of the browser history.
     * @return the size of the browser history
     */
    public static long getHistoryLength() {
        JavascriptExecutor js = (JavascriptExecutor) Navigator.getInstance().getDriver();
        return (Long) js.executeScript("return window.history.length;");
    }
}
