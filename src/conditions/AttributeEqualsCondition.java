package conditions;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * ExpectedCondition evaluating whether a WebElement attribute equals an expected value.
 *
 * @author Thibault Helsmoortel
 */
public class AttributeEqualsCondition implements ExpectedCondition<Boolean> {

    private final WebElement element;
    private final String attributeName, expectedValue;

    /**
     * Class constructor specifying the element, attribute name and expected value.
     *
     * @param element       the element to evaluate
     * @param attributeName the attribute value to evaluate
     * @param expectedValue the expected attribute value
     */
    public AttributeEqualsCondition(WebElement element, String attributeName, String expectedValue) {
        this.element = element;
        this.attributeName = attributeName;
        this.expectedValue = expectedValue;
    }

    @Override
    public Boolean apply(WebDriver input) {
        return StringUtils.equals(element.getAttribute(attributeName), expectedValue);
    }
}