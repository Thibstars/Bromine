package commands.elements;

import commands.Command;
import navigation.Navigator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Command for finding WebElements by id.
 *
 * @author Thibault Helsmoortel
 */
public class ElementFindByIdCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ElementFindByIdCommand.class);

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
        LOGGER.debug("Finding element using id: " + id);
        return Navigator.getInstance().fluentWait(By.id(id));
    }
}
