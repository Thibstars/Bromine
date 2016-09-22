package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Command for finding WebElements by cssSelector.
 * @author Thibault Helsmoortel
 */
public class ElementFindByCssSelectorCommand implements Command {

    private String selector;

    public ElementFindByCssSelectorCommand(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    @Override
    public Object execute() {
        Navigator.getInstance().explicitlyWaitForElementPresent(By.cssSelector(selector));
        return Navigator.getInstance().getDriver().findElement(By.cssSelector(selector));
    }
}
