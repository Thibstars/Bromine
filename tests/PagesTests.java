import commands.InitTestFrameworkCommand;
import navigation.NavigatorFactory;
import org.junit.Rule;
import org.junit.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.Page;
import pages.Pages;
import rules.ScreenShotOnFailure;
import sut.Environment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PagesTests {

    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure();

    @BeforeClass
    public static void init() {
        new InitTestFrameworkCommand().execute();
        NavigatorFactory.createNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"));
    }

    @Test
    public void shouldRegisterPage() {
        Page homePage = new Page("/repos") {};
        Pages.registerPage(homePage);
        assertFalse(Pages.isEmpty());
    }

    @Test
    public void shouldDeregisterPage() {
        Page homePage = new Page("/repos") {};
        Pages.registerPage(homePage);
        Pages.deregisterPage(homePage);
        assertTrue(Pages.isEmpty());
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
