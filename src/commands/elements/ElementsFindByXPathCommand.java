package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibault on 29/08/2016.
 */
public class ElementsFindByXPathCommand implements Command {

    private String xPath;

    public ElementsFindByXPathCommand(String xPath) {
        this.xPath = xPath;
    }

    public String getxPath() {
        return xPath;
    }

    public void setxPath(String xPath) {
        this.xPath = xPath;
    }

    @Override
    public Object execute() {
        List<WebElement> elements = new ArrayList<WebElement>();
        for (WebElement element : Navigator.getDriver().findElements(new By.ByXPath(xPath))) {
            elements.add(element);
        }
        return elements;
    }
}
