package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Proxy class of the WebElement.
 *
 * @author Thibault Helsmoortel
 */
public class WebElementByProxy extends ElementImpl {

    private final By by;

    public WebElementByProxy(WebElement element, By by) {
        super(element);
        this.by = by;
    }

    public By getBy() {
        return by;
    }

    @Override
    public String toString() {
        return by.toString();
    }
}
