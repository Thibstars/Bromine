package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Command for finding WebElements by link text.
 * @author Thibault Helsmoortel
 */
public class ElementFindByLinkTextCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ElementFindByLinkTextCommand.class);

    private String linkText;

    public ElementFindByLinkTextCommand(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    @Override
    public Object execute() {
        LOGGER.debug("Finding element using link text: " + linkText);
        Navigator.getInstance().explicitlyWaitForElementPresent(By.linkText(linkText));
        return Navigator.getInstance().getDriver().findElement(By.linkText(linkText));
    }
}
