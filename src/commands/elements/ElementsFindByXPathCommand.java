package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Command for finding WebElements by XPath.
 * @author Thibault Helsmoortel
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
        elements.addAll(Navigator.getInstance().getDriver().findElements(new By.ByXPath(xPath)));
        return elements;
    }
}
