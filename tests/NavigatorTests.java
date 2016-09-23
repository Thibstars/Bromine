import commands.InitTestFrameworkCommand;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.junit.Rule;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import rules.ScreenShotOnFailure;
import sut.Environment;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class NavigatorTests {

    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure();

    @Test
    public void shouldOpenGoogle() {
        new InitTestFrameworkCommand().execute();
        NavigatorFactory.createNavigator(new Environment("Google", "https://google.com"));

        try {
            Navigator.getInstance().navigateTo(new URL("https://google.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assertTrue(Navigator.getInstance().getUrl().startsWith("https://www.google."));
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
