package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Thibault on 30/08/2016.
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
        //TODO replace with Navigator wait method
        return Navigator.getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
    }
}
