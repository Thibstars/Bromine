package commands.elements;

import commands.Command;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static navigation.Navigator.getWait;

/**
 * Created by Thibault on 29/08/2016.
 */
public class ElementFindByIdCommand implements Command {

    private String id;

    public ElementFindByIdCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object execute() {
        //Wait till the element is visible
        //WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));

        //Wait till the element is present
        //TODO replace with Navigator wait method
        WebElement element = getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        return element;
    }
}
