import commands.InitFrameworkCommand;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import pages.Page;
import pages.Pages;
import pages.Section;
import sut.Environment;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * Test class testing Pages.
 * @author Thibault Helsmoortel
 */
public class PagesTests {

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
        Pages.deregisterAll();
    }

    /**
     * Tests if a page is properly registered.
     */
    @Test
    public void shouldRegisterPage() {
        Page reposPage = new Page("/repos.html") {};
        Pages.registerPage(reposPage);
        assertFalse(Pages.isEmpty());
        Pages.deregisterAll();
    }

    /**
     * Tests if multiple pages are properly registered.
     */
    @Test
    public void shouldRegisterPages() {
        Page homePage = new Page("/index.html") {};
        Page reposPage = new Page("/repos.html") {};
        List<Page> pages = new ArrayList<>(2);
        pages.add(homePage);
        pages.add(reposPage);
        Pages.registerAllPages(pages);

        assertNotNull(Pages.getPage(homePage.getUrl()));
        assertNotNull(Pages.getPage(reposPage.getUrl()));
        assertTrue(Pages.size() > 0);
    }

    /**
     * Tests if pages are properly deregistered.
     */
    @Test
    public void shouldDeregisterPage() {
        Pages.deregisterAll();
        Page indexPage = new Page("/index.html") {};
        Pages.registerPage(indexPage);
        Pages.deregisterPage(indexPage);
        assertNull(Pages.getPage("/index.html"));
    }

    /**
     * Tests if section can be added, displayed and removed.
     */
    @Test
    public void shouldAddDisplayAndRemoveSection() {

        Page indexPage = new Page("/index.html") {};

        Pages.registerPage(indexPage);

        if (!Pages.getPage(indexPage).isAt()) Navigator.getInstance().navigateTo(indexPage);


        Section portfolio;
        try {
            portfolio =
                    new Section("portfolio", Navigator.getInstance().getDriver().findElement(By.id("portfolio"))) {};
        } catch (StaleElementReferenceException e) {
            portfolio =
                    new Section("portfolio", Navigator.getInstance().getDriver().findElement(By.id("portfolio"))) {};
        }

        assertTrue(portfolio.getRoot().isDisplayed());

        indexPage.addSection(portfolio);
        assertNotNull(indexPage.getSection("portfolio"));

        indexPage.removeSection(portfolio);
        assertNull(indexPage.getSection("portoflio"));
    }

    /**
     * Destroys the setup.
     */
    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
