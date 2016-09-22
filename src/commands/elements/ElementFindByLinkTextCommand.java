package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Command for finding WebElements by link text.
 * @author Thibault Helsmoortel
 */
public class ElementFindByLinkTextCommand implements Command {

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
        Navigator.getInstance().explicitlyWaitForElementPresent(By.linkText(linkText));
        return Navigator.getInstance().getDriver().findElement(By.linkText(linkText));
    }
}
