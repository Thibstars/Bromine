import org.junit.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class NavigatorTests {

    @BeforeClass
    public static void init() {
        //new Navigator();
    }

    @Test
    public void shouldOpenGoogle() {
        try {
            Navigator.navigateTo(new URL("http://google.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertTrue(Navigator.getDriver().getCurrentUrl().equals("http://google.com"));
    }
}
