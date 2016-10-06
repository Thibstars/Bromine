import commands.InitFrameworkCommand;
import navigation.Browser;
import navigation.NavigatorFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import session.CookiesManager;
import sut.Environment;

import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CookiesManagerTests {

    private static final Logger LOGGER = Logger.getLogger(CookiesManagerTests.class);

    @BeforeClass
    public static void init() {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createHighlightingNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"), Browser.CHROME);
    }

    @Test
    public void shouldGetCookies() {
        Set<Cookie> cookies = CookiesManager.getCookies();

        for (Cookie cookie : cookies) {
            LOGGER.debug(cookie.toString());
        }

        assertNotNull(cookies);
        assertTrue(cookies.size() > 0);
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
