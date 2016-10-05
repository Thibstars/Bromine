import commands.InitFrameworkCommand;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.Page;
import pages.Pages;
import stats.*;
import sut.Environment;

import static org.junit.Assert.*;

public class StatsTrackerTests {

    private static final Logger LOGGER = Logger.getLogger(StatsTrackerTests.class);

    @BeforeClass
    public static void init() {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createHighlightingNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"), Browser.CHROME);
        Pages.registerPage(new Page("/index.html") {
        });
        Pages.registerPage(new Page("/repos.html") {
        });

        StatsTrackerFactory.createDefault();
        StatsTracker.getInstance().reset();
    }

    @Test
    public void shouldEnableTracking() {
        StatsTracker.getInstance().disableTracking();
        StatsTracker.getInstance().enableTracking();

        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            assertTrue(plugin.isTrackingEnabled());
        }
    }

    @Test
    public void shouldDisableTracking() {
        StatsTracker.getInstance().enableTracking();
        StatsTracker.getInstance().disableTracking();

        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            assertFalse(plugin.isTrackingEnabled());
        }
    }

    @Test
    public void shouldRegisterPlugin() {
        StatsPlugin customClicks = new LMBClickStats();
        StatsTracker.getInstance().registerPlugin(customClicks);

        assertTrue(StatsTracker.getPlugins().contains(customClicks));
    }

    @Test
    public void shouldDeregisterPlugin() {
        StatsPlugin customClicks = new LMBClickStats();
        StatsTracker.getInstance().registerPlugin(customClicks);
        StatsTracker.getInstance().deregisterPlugin(customClicks);

        assertFalse(StatsTracker.getPlugins().contains(customClicks));
    }

    @Test
    public void shouldReset() {
        Pages.getPage("/index.html").goTo();

        //Find the button that should lead to the home page
        WebElement btnElement = Navigator.getInstance().getDriver().findElement(
                By.xpath("//*[@id='page-top']/nav/div/div[1]/a"));

        //Click on the homepage
        Navigator.getInstance().click(btnElement);

        LMBClickStats clicks = null;
        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            if (plugin instanceof LMBClickStats && !(plugin instanceof LMBDoubleClickStats)) clicks = (LMBClickStats) plugin;
        }

        StatsTracker.getInstance().reset();
        LOGGER.debug(clicks.represent());
        assertEquals(0, clicks.getClicks());
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
