import commands.InitFrameworkCommand;
import navigation.Browser;
import navigation.BrowserHistory;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pages.Page;
import pages.Pages;
import stats.StatsTrackerFactory;
import sut.Environment;

import static org.junit.Assert.assertTrue;

public class HistoryTests {

    private static final Logger LOGGER = Logger.getLogger(HistoryTests.class);

    /*
    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure();
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

    @Test
    public void shouldHaveAHistory() {
        Pages.getPage("/index.html").goTo();
        Pages.getPage("/repos.html").goTo();

        Navigator.getInstance().explicitlyWaitForPageLoaded();

        assertTrue(BrowserHistory.hasHistory());
        assertTrue(BrowserHistory.getHistoryLength() > 0);
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
