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
import stats.*;
import sut.Environment;

import static org.junit.Assert.*;

public class NavigatorTests {

    private static Logger LOGGER = Logger.getLogger(NavigatorTests.class);

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
    }

    @Test
    public void shouldBeAtOrGotoPage() {
        Page repos = Pages.getPage("/repos.html");

        if (!repos.isAt()) repos.goTo();
        assertTrue(repos.isAt());
    }

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
            if (plugin instanceof LMBClickStats && !(plugin instanceof LMBDoubleClickStats)) clicks = (LMBClickStats) plugin;
        }
        LOGGER.debug(clicks.represent());
        assertEquals(1, clicks.getClicks());
    }

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
            if (plugin instanceof LMBClickStats && !(plugin instanceof LMBDoubleClickStats)) clicks = (LMBClickStats) plugin;
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
            if (plugin instanceof  SendKeyStats) keys = (SendKeyStats) plugin;
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
            if (plugin instanceof  SendKeyStats) keys = (SendKeyStats) plugin;
        }
        LOGGER.debug(keys.represent());
        assertEquals(1, keys.getTimesKeysSent());
    }

    @Test
    public void shouldNavigateBack() {
        Page homePage = Pages.getPage("/index.html");
        homePage.goTo();
        Pages.getPage("/repos.html").goTo();
        Navigator.getInstance().navigateBack();

        assertTrue(homePage.isAt());
    }

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

        assertTrue(nameField.getText().isEmpty()|| nameField.getText() == null);
    }

    @Test
    public void shouldScrollElementIntoView() {
        Pages.getPage("/index.html").goTo();
        WebElement nameField = Navigator.getInstance().getDriver().findElement(By.id("name"));

        if (!nameField.isDisplayed()) Navigator.getInstance().scrollElementIntoView(nameField);

        assertTrue(nameField.isDisplayed());
    }

    @Test
    public void shouldGetParentElement() {
        Pages.getPage("/index.html").goTo();
        WebElement element = Navigator.getInstance().getDriver().findElement(By.id("name"));

        WebElement parent = Navigator.getInstance().getParent(element);

        assertNotNull(parent);
        assertNotNull(parent.findElement(By.id("name")));
        assertEquals(element, parent.findElement(By.id("name")));
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
