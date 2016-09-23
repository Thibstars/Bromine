import commands.InitTestFrameworkCommand;
import navigation.NavigatorFactory;
import org.junit.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.Page;
import pages.Pages;
import sut.Environment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class PagesTests {

    /*
    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure();
    */

    @BeforeClass
    public static void init() {
        new InitTestFrameworkCommand().execute();
        NavigatorFactory.createNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"));
        Pages.deregisterAll();
    }

    @Test
    public void shouldRegisterPage() {
        Page reposPage = new Page("/repos.html") {};
        Pages.registerPage(reposPage);
        assertFalse(Pages.isEmpty());
        Pages.deregisterAll();
    }

    @Test
    public void shouldDeregisterPage() {
        Pages.deregisterAll();
        Page indexPage = new Page("/index.html") {};
        Pages.registerPage(indexPage);
        Pages.deregisterPage(indexPage);
        assertNull(Pages.getPage("/index.html"));
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
