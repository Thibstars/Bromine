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
import ru.yandex.qatools.allure.annotations.Features;
import stats.StatsPlugin;
import stats.StatsTracker;
import stats.defaultPlugins.LMBClickStats;
import sut.Environment;

import static org.junit.Assert.*;

/**
 * Test class testing the StatsTracker.
 * @author Thibault Helsmoortel
 */
@Features("Stats")
public class StatsTrackerTestCase {

    private static final Logger LOGGER = Logger.getLogger(StatsTrackerTestCase.class);

    /**
     * Initializes the test class.
     */
    @BeforeClass
    public static void init() {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createHighlightingNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"), Browser.CHROME);
        Pages.registerPage(new Page("/index.html") {
        });
        Pages.registerPage(new Page("/repos.html") {
        });
        //StatsTracker.getInstance().reset();
    }

    /**
     * Tests if tracking is properly enabled.
     */
    @Test
    public void shouldEnableTracking() {
        StatsTracker.getInstance().disableTracking();
        StatsTracker.getInstance().enableTracking();

        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            assertTrue(plugin.isTrackingEnabled());
        }
    }

    /**
     * Tests if tracking is properly disabled.
     */
    @Test
    public void shouldDisableTracking() {
        StatsTracker.getInstance().enableTracking();
        StatsTracker.getInstance().disableTracking();

        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            assertFalse(plugin.isTrackingEnabled());
        }
    }

    /**
     * Tests if plugin is properly registered.
     */
    @Test
    public void shouldRegisterPlugin() {
        StatsPlugin customClicks = new LMBClickStats();
        StatsTracker.getInstance().registerPlugin(customClicks);

        assertTrue(StatsTracker.getPlugins().contains(customClicks));
    }

    /**
     * Tests if plugin is properly deregistered.
     */
    @Test
    public void shouldDeregisterPlugin() {
        StatsPlugin customClicks = new LMBClickStats();
        StatsTracker.getInstance().registerPlugin(customClicks);
        StatsTracker.getInstance().deregisterPlugin(customClicks);

        assertFalse(StatsTracker.getPlugins().contains(customClicks));
    }

    /**
     * Tests if tracker is properly reset.
     */
    @Test
    public void shouldReset() {
        Page homePage = Pages.getPage("/index.html");
        if (!homePage.isAt()) {
            homePage.goTo();
            Navigator.getInstance().explicitlyWaitForPageLoaded();
        }

        //Find the button that should lead to the home page
        WebElement btnElement = Navigator.getInstance().getDriver().findElement(
                By.xpath("//*[@id=\"page-top\"]/nav/div/div[1]/a"));

        Navigator.getInstance().explicitlyWaitForElementClickable(btnElement);
        if (!btnElement.isDisplayed()) Navigator.getInstance().scrollElementIntoView(btnElement);

        LMBClickStats clicks = new LMBClickStats();
        StatsTracker.getInstance().registerPlugin(clicks);
        StatsTracker.getInstance().enableTracking();

        //Click on the homepage
        Navigator.getInstance().click(btnElement);

        LOGGER.debug("Before reset: " + clicks.represent());
        StatsTracker.getInstance().reset();
        LOGGER.debug("After reset: " + clicks.represent());
        assertEquals(0, clicks.getClicks());
    }

    /**
     * Destroys the setup.
     */
    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
