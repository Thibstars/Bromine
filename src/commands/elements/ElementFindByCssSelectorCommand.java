package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Command for finding WebElements by cssSelector.
 *
 * @author Thibault Helsmoortel
 */
public class ElementFindByCssSelectorCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ElementFindByCssSelectorCommand.class);

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
        LOGGER.debug("Finding element using selector: " + selector);
        return Navigator.getInstance().fluentWait(By.cssSelector(selector));
    }
}
