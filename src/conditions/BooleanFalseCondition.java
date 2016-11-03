package conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * ExpectedCondition evaluating whether a boolean is false.
 *
 * @author Thibault Helsmoortel
 */
public class BooleanFalseCondition implements ExpectedCondition<Boolean> {

    private boolean bool;

    /**
     * Class constructor specifying the boolean to evaluate.
     *
     * @param bool the boolean to evaluate
     */
    public BooleanFalseCondition(boolean bool) {
        this.bool = bool;
    }

    @Override
    public Boolean apply(WebDriver webDriver) {
        return !bool;
    }
}
