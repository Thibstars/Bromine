import commands.InitTestFrameworkCommand;
import navigation.NavigatorFactory;
import org.junit.Rule;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Page;
import rules.ScreenShotOnFailure;
import sut.Environment;

import static org.junit.Assert.assertTrue;

public class NavigatorTests {

    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure();

    @BeforeClass
    public static void init() {
        new InitTestFrameworkCommand().execute();
        NavigatorFactory.createNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"));
    }

    @Test
    public void shouldBeOrGotoHomePage() {
        Page homePage = new Page("/repos") {
        };

        if (!homePage.isAt()) homePage.goTo();

        assertTrue(homePage.isAt());
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
