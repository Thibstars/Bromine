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

import static org.junit.Assert.*;

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

    @Test
    public void shouldGetCookie() {
        assertNotNull(CookiesManager.getCookie("_ga"));
    }

    @Test
    public void shouldAddCookie() {
        addTestCookie();
        assertNotNull(CookiesManager.getCookie("testCookie"));
    }

    @Test
    public void shouldDeleteCookie() {
        Cookie cookie = addTestCookie();

        CookiesManager.deleteCookie(cookie);
        assertNull(CookiesManager.getCookie(cookie.getName()));

        cookie = addTestCookie();

        CookiesManager.deleteCookie(cookie.getName());
        assertNull(CookiesManager.getCookie(cookie.getName()));
    }

    @Test
    public void shouldDeleteAllCookies() {
        Cookie cookie = addTestCookie();

        CookiesManager.deleteAllCookies();
        assertTrue(CookiesManager.getCookies() == null || CookiesManager.getCookies().size() == 0);
        assertNull(CookiesManager.getCookie(cookie.getName()));
    }

    private Cookie addTestCookie() {
        Cookie cookie = new Cookie("testCookie", "1", "/");
        CookiesManager.addCookie(cookie);
        return cookie;
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
