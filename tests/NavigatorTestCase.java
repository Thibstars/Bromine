import commands.InitFrameworkCommand;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.Page;
import pages.Pages;
import ru.yandex.qatools.allure.annotations.Features;
import stats.SendKeyStats;
import stats.StatsPlugin;
import stats.StatsTracker;
import stats.defaultPlugins.LMBClickStats;
import stats.defaultPlugins.LMBDoubleClickStats;
import sut.Environment;

import static org.junit.Assert.*;

/**
 * Test class testing the Navigator.
 */
@Features("Navigation")
public class NavigatorTestCase {

    private static Logger LOGGER = Logger.getLogger(NavigatorTestCase.class);

    /*
    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure();
    */

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
    }

    /**
     * Tests if we are at a specific page, if not we should properly go to that page.
     */
    @Test
    public void shouldBeAtOrGotoPage() {
        Page repos = Pages.getPage("/repos.html");

        if (!repos.isAt()) repos.goTo();
        assertTrue(repos.isAt());
    }

    /**
     * Tests if home is correctly clicked.
     */
    @Test
    public void shouldClickHome() {
        StatsTracker.getInstance().reset();

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
            if (plugin instanceof LMBClickStats && !(plugin instanceof LMBDoubleClickStats))
                clicks = (LMBClickStats) plugin;
        }
        LOGGER.debug(clicks.represent());
        assertEquals(1, clicks.getClicks());
    }

    /**
     * Tests if home is correctly clicked and that the wait is properly performed.
     */
    @Test
    public void shouldClickAndWaitHome() {
        StatsTracker.getInstance().reset();

        Page repos = Pages.getPage("/repos.html");

        if (!repos.isAt()) repos.goTo();

        //Find the button that should lead to the home page
        WebElement btnElement = Navigator.getInstance().getDriver().findElement(
                By.xpath("//*[@id=\"page-top\"]/nav/div/div[1]/a"));

        //Click on the homepage
        Navigator.getInstance().clickAndWait(btnElement);

        Page homePage = Pages.getPage("/index.html");
        assertTrue(homePage.isAt());
        LMBClickStats clicks = null;
        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            if (plugin instanceof LMBClickStats && !(plugin instanceof LMBDoubleClickStats))
                clicks = (LMBClickStats) plugin;
        }
        LOGGER.debug(clicks.represent());
        assertEquals(1, clicks.getClicks());
    }

    //Ignore until a working solution is found
    //TODO find working solution
    @Ignore
    @Test
    public void shouldSendKeys() {
        StatsTracker.getInstance().reset();

        Pages.getPage("/index.html").goTo();
        WebElement nameField = Navigator.getInstance().getDriver().findElement(By.id("name"));

        Navigator.getInstance().scrollElementIntoView(nameField);
        Navigator.getInstance().sendKeys(nameField, "MyName");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("MyName", nameField.getText());

        SendKeyStats keys = null;
        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            if (plugin instanceof SendKeyStats) keys = (SendKeyStats) plugin;
        }
        LOGGER.debug(keys.represent());
        assertEquals(1, keys.getTimesKeysSent());
    }

    //Ignore until a working solution is found
    //TODO find working solution
    @Ignore
    @Test
    public void shouldSendKeysWithoutElement() {
        StatsTracker.getInstance().reset();

        Pages.getPage("/index.html").goTo();
        WebElement nameField = Navigator.getInstance().getDriver().findElement(By.id("name"));

        Navigator.getInstance().scrollElementIntoView(nameField);
        Navigator.getInstance().moveToElement(nameField);
        Navigator.getInstance().click(nameField);
        Navigator.getInstance().sendKeys("MyName");

        assertEquals("MyName", nameField.getText());

        SendKeyStats keys = null;
        for (StatsPlugin plugin : StatsTracker.getPlugins()) {
            if (plugin instanceof SendKeyStats) keys = (SendKeyStats) plugin;
        }
        LOGGER.debug(keys.represent());
        assertEquals(1, keys.getTimesKeysSent());
    }

    /**
     * Tests if an element is properly focused.
     */
    @Test
    public void shouldFocusElement() {
        Page home = Pages.getPage("/index.html");
        if (!home.isAt()) home.goTo();
        WebElement nameField = Navigator.getInstance().getDriver().findElement(By.id("name"));
        Navigator.getInstance().scrollElementIntoView(nameField);

        Navigator.getInstance().focusElement(nameField);
        assertEquals(nameField, Navigator.getInstance().getDriver().switchTo().activeElement());
    }

    /**
     * Tests if the driver successfully navigates back.
     */
    @Test
    public void shouldNavigateBack() {
        Page homePage = Pages.getPage("/index.html");
        homePage.goTo();
        Pages.getPage("/repos.html").goTo();
        Navigator.getInstance().navigateBack();

        assertTrue(homePage.isAt());
    }

    /**
     * Tests if the driver successfully navigates forward.
     */
    @Test
    public void shouldNavigateForward() {
        Page homePage = Pages.getPage("/index.html");
        homePage.goTo();
        Page reposPage = Pages.getPage("/repos.html");
        reposPage.goTo();

        Navigator.getInstance().navigateBack();
        Navigator.getInstance().explicitlyWaitForPageLoaded();
        Navigator.getInstance().navigateForward();
        Navigator.getInstance().explicitlyWaitForPageLoaded();

        assertTrue(reposPage.isAt());
    }

    /**
     * Tests if the driver properly refreshes.
     */
    @Test
    public void shouldRefresh() {
        Pages.getPage("/index.html").goTo();
        WebElement nameField = Navigator.getInstance().getDriver().findElement(By.id("name"));

        Navigator.getInstance().scrollElementIntoView(nameField);
        Navigator.getInstance().sendKeys(nameField, "MyName");

        Navigator.getInstance().navigateRefresh();
        Navigator.getInstance().explicitlyWaitForPageLoaded();

        //Search again, since page is reloaded
        nameField = Navigator.getInstance().getDriver().findElement(By.id("name"));

        if (!nameField.isDisplayed()) Navigator.getInstance().scrollElementIntoView(nameField);

        assertTrue(nameField.getText().isEmpty() || nameField.getText() == null);
    }

    /**
     * Tests if an element is properly scrolled into view.
     */
    @Test
    public void shouldScrollElementIntoView() {
        Pages.getPage("/index.html").goTo();
        WebElement nameField = Navigator.getInstance().getDriver().findElement(By.id("name"));

        if (!nameField.isDisplayed()) Navigator.getInstance().scrollElementIntoView(nameField);

        assertTrue(nameField.isDisplayed());
    }

    /**
     * Tests if an element is properly found using a fluent wait.
     */
    @Test
    public void shouldFindElementUsingFluentWait() {
        StatsTracker.getInstance().reset();

        Page repos = Pages.getPage("/repos.html");

        if (!repos.isAt()) repos.goTo();

        //Find the button that should lead to the home page
        WebElement btnElement = Navigator.getInstance().fluentWait(By.xpath("//*[@id=\"page-top\"]/nav/div/div[1]/a"));
        assertNotNull(btnElement);

        //Click on the homepage
        Navigator.getInstance().click(btnElement);

        Page homePage = Pages.getPage("/index.html");
        assertTrue(homePage.isAt());
    }

    /**
     * Tests if the parent element is successfully fetched from a child element.
     */
    @Test
    public void shouldGetParentElement() {
        Pages.getPage("/index.html").goTo();
        WebElement element = Navigator.getInstance().getDriver().findElement(By.id("name"));

        WebElement parent = Navigator.getInstance().getParent(element);

        assertNotNull(parent);
        assertNotNull(parent.findElement(By.id("name")));
        assertEquals(element, parent.findElement(By.id("name")));
    }

    /**
     * Destroys the setup.
     */
    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
