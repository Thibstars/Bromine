import commands.Command;
import commands.InitFrameworkCommand;
import commands.elements.*;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import pages.Page;
import pages.Pages;
import sut.Environment;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class testing the element find commands.
 *
 * @author Thibault Helsmoortel
 */
@RunWith(Theories.class)
public class ElementFindCommandsTests {

    private static final Logger LOGGER = Logger.getLogger(ElementFindCommandsTests.class);

    //Set of test data, commands finding elements
    @DataPoints
    public static Command[] commands = {
            new ElementFindByCssSelectorCommand("#page-top > nav > div > div.navbar-header.page-scroll > a"),
            new ElementFindByIdCommand("page-top"),
            new ElementFindByLinkTextCommand("THIBAULT"),
            new ElementsFindByClassCommand("navbar-brand"),
            new ElementsFindByXPathCommand("//*[@id=\"page-top\"]/nav/div/div[1]/a"),
    };

    /**
     * Initializes the test class.
     */
    @BeforeClass
    public static void init() {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createHighlightingNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"), Browser.CHROME);
        Page homePage = new Page("/index.html") {
        };
        Pages.registerPage(homePage);
        if (!homePage.isAt()) {
            homePage.goTo();
            Navigator.getInstance().explicitlyWaitForPageLoaded();
        }
    }

    /**
     * Tests if at least one element could be found using a specified command.
     *
     * @param command the command to use to find the element
     */
    @Theory
    public void shouldFindAtLeastOneElement(Command command) {
        LOGGER.debug("Testing execution of command: " + command.getClass().getSimpleName());

        Object result = command.execute();
        assertNotNull(result);

        //When a list of WebElements is returned, check if the size of the list is greater than 0
        if (result instanceof List) {
            assertTrue(((List) result).size() > 0);
        }
    }

    /**
     * Destroys the setup.
     */
    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
