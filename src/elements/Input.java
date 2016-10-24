package elements;

import org.openqa.selenium.WebElement;

/**
 * Class representing an input field.
 *
 * @author Thibault Helsmoortel
 */
public class Input extends ElementImpl {

    /**
     * Class constructor specifying the actual element.
     *
     * @param element the actual element
     */
    public Input(WebElement element) {
        super(element);
    }

    @Override
    public void clear() {
        super.clear();
    }

    /**
     * Sets the value of an input field.
     *
     * @param text the value of the field
     */
    public void set(String text) {
        super.clear();
        super.sendKeys(text);
    }

    /**
     * Gets the value of an input field.
     *
     * @return String with the value of the field
     */
    @Override
    public String getText() {
        return super.getAttribute("value");
    }
}
