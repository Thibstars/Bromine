import commands.InitFrameworkCommand;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NavigatorTests {

    private static Logger LOGGER = Logger.getLogger(NavigatorTests.class);

    /*
    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure();
    */

    @BeforeClass
    public static void init() {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"));
        Pages.registerPage(new Page("/index.html") {
        });
        Pages.registerPage(new Page("/repos.html") {
        });

        StatsTrackerFactory.createDefault();
    }

    @Test
    public void shouldBeOrGotoHomePage() {
        Page repos = Pages.getPage("/repos.html");

        if (!repos.isAt()) repos.goTo();
        assertTrue(repos.isAt());
    }

    @Test
    public void shouldClickHome() {
        Page repos = Pages.getPage("/repos.html");

        if (!repos.isAt()) repos.goTo();

        //Find the button that should lead to the home page
        WebElement btnElement = Navigator.getInstance().getDriver().findElement(
                By.xpath("//*[@id=\"page-top\"]/nav/div/div[1]/a"));

        //Click on the homepage
        Navigator.getInstance().click(btnElement);

        Page homePage = Pages.getPage("/index.html");
        assertTrue(homePage.isAt());
        LMBClickStats clicks = null;
        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            if (plugin instanceof LMBClickStats && !(plugin instanceof LMBDoubleClickStats)) clicks = (LMBClickStats) plugin;
        }
        LOGGER.debug(clicks.represent());
        assertEquals(1, clicks.getClicks());
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
