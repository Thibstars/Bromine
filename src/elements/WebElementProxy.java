package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Proxy class of the WebElement.
 *
 * @author Thibault Helsmoortel
 */
public class WebElementProxy extends ElementImpl {

    private final By by;

    public WebElementProxy(WebElement element, By by) {
        super(element);
        this.by = by;
    }

    public By getBy() {
        return by;
    }
}
