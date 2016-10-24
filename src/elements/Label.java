package elements;

import org.openqa.selenium.WebElement;

/**
 * Class representing a label.
 *
 * @author Thibault Helsmoortel
 */
public class Label extends ElementImpl{

    /**
     * Class constructor specifying the actual element.
     *
     * @param element the actual element
     */
    public Label(WebElement element) {
        super(element);
    }

    public String getFor() {
        return super.getAttribute("for");
    }
}
