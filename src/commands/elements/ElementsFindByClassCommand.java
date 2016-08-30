package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static navigation.Navigator.*;

/**
 * Created by Thibault on 29/08/2016.
 */
public class ElementsFindByClassCommand implements Command {

    private String clazz;

    public ElementsFindByClassCommand(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object execute() {
        List<WebElement> elements = new ArrayList<WebElement>();
        for (WebElement element : Navigator.getDriver().findElements(new By.ByClassName(clazz))) {
            elements.add(element);
        }
        return elements;
    }
}
