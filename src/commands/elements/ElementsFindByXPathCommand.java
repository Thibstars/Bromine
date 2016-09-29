package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Command for finding WebElements by XPath.
 * @author Thibault Helsmoortel
 */
public class ElementsFindByXPathCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ElementsFindByXPathCommand.class);

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
        LOGGER.debug("Finding element using XPath: " + xPath);
        List<WebElement> elements = new ArrayList<>();
        elements.addAll(Navigator.getInstance().getDriver().findElements(new By.ByXPath(xPath)));
        return elements;
    }
}
