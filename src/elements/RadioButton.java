package elements;

import org.openqa.selenium.WebElement;

/**
 * Class representing a radio button.
 *
 * @author Thibault Helsmoortel
 */
public class RadioButton extends WrappedElement {

    private String value;

    /**
     * Class constructor specifying the actual element.
     *
     * @param element the actual element
     * @param value the value of the radio button
     */
    public RadioButton(WebElement element, String value) {
        super(element);
        this.value = value;
    }

    /**
     * Toggles the radio button.
     */
    public void toggle() { super.click(); }

    /**
     * Checks the radio button if it wasn't checked already.
     */
    public void check() {
        if (!isChecked()) {
            toggle();
        }
    }

    /**
     * Returns true if the radio button is selected, false if otherwise.
     *
     * @return true if the radio button is selected, false if otherwise
     */
    public boolean isChecked() {
        return super.isSelected();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
