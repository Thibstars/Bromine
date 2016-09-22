import commands.InitTestFrameworkCommand;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import rules.ScreenShotOnFailure;
import sut.Environment;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class NavigatorTests {

    @Rule
    ScreenShotOnFailure failure = new ScreenShotOnFailure();

    @Test
    public void shouldOpenGoogle() {
        new InitTestFrameworkCommand().execute();
        NavigatorFactory.createNavigator(new Environment("Google", "http://google.com"));

        try {
            Navigator.getInstance().navigateTo(new URL("http://google.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assertTrue(Navigator.getInstance().getDriver().getCurrentUrl().equals("http://google.com"));
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
