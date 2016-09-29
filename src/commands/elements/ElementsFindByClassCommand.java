package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Command for finding WebElements by class.
 * @author Thibault Helsmoortel
 */
public class ElementsFindByClassCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ElementsFindByClassCommand.class);

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
        LOGGER.debug("Finding element using class: " + clazz);
        List<WebElement> elements = new ArrayList<WebElement>();
        elements.addAll(Navigator.getInstance().getDriver().findElements(new By.ByClassName(clazz)));
        return elements;
    }
}
