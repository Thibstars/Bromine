package elements;

import org.openqa.selenium.WebElement;

/**
 * Class representing a check box.
 *
 * @author Thibault Helsmoortel
 */
public class CheckBox extends ElementImpl {

    /**
     * Class constructor specifying the WebElement.
     *
     * @param element the actual WebElement
     */
    public CheckBox(WebElement element) {
        super(element);
    }

    /**
     * Toggles the check box.
     */
    public void toggle() {
        super.click();
    }

    /**
     * Checks the check box if it wasn't checked already.
     */
    public void check() {
        if (!isChecked()) {
            toggle();
        }
    }

    /**
     * Unchecks the check box if it was checked before.
     */
    public void uncheck() {
        if (isChecked()) {
            toggle();
        }
    }

    /**
     * Returns true if the check box is selected, false if otherwise.
     *
     * @return true if the check box is selected, false if otherwise
     */
    public boolean isChecked() {
        return super.isSelected();
    }

}
