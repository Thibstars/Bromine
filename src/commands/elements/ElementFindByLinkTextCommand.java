package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Thibault on 30/08/2016.
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
        //TODO replace with Navigator wait method
        return Navigator.getWait().until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
    }
}
