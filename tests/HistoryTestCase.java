import commands.InitFrameworkCommand;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pages.Page;
import pages.Pages;
import ru.yandex.qatools.allure.annotations.Features;
import session.BrowserHistory;
import stats.StatsTrackerFactory;
import sut.Environment;

import static org.junit.Assert.assertTrue;

/**
 * Test class testing the BrowserHistory.
 *
 * @author Thibault Helsmoortel
 */
@Features("Session")
public class HistoryTestCase {

    private static final Logger LOGGER = Logger.getLogger(HistoryTestCase.class);

    /*
    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure();
    */

    /**
     * Initialization of the test class.
     */
    @BeforeClass
    public static void init() {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createHighlightingNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"), Browser.CHROME);
        Pages.registerPage(new Page("/index.html") {
        });
        Pages.registerPage(new Page("/repos.html") {
        });

        StatsTrackerFactory.createDefault();
    }

    /**
     * Tests if the history is correctly tracked.
     */
    @Test
    public void shouldHaveAHistory() {
        Pages.getPage("/index.html").goTo();
        Pages.getPage("/repos.html").goTo();

        Navigator.getInstance().explicitlyWaitForPageLoaded();

        assertTrue(BrowserHistory.hasHistory());
        assertTrue(BrowserHistory.getHistoryLength() > 0);
    }

    /**
     * Destroys the test setup.
     */
    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
