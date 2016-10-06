package session;

import navigation.Navigator;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;

import java.util.Set;

/**
 * Class responsible for the management of cookies.
 * @author Thibault Helsmoortel
 */
public final class CookiesManager {

    private static final Logger LOGGER = Logger.getLogger(CookiesManager.class);

    /**
     * Returns a set of cookies present in the current session.
     * @return a set of cookies present in the current session
     */
    public static Set<Cookie> getCookies() {
        return Navigator.getInstance().getDriver().manage().getCookies();
    }

    /**
     * Returns a cookie with the specified name, if any.
     * @param name the cookie name
     * @return the cookie with the specified name, if any
     */
    public static Cookie getCookie(String name) {
        return Navigator.getInstance().getDriver().manage().getCookieNamed(name);
    }

    /**
     * Adds a specified cookie to the current session.
     * @param cookie the cookie to add
     */
    public static void addCookie(Cookie cookie) {
        LOGGER.debug("Adding cookie: " + cookie.getName());
        Navigator.getInstance().getDriver().manage().addCookie(cookie);
    }

    /**
     * Delete a specified cookie from the current session.
     * @param cookie the cookie to delete
     */
    public static void deleteCookie(Cookie cookie) {
        deleteCookie(cookie.getName());
    }

    /**
     * Delete a cookie, based on the specified name, from the current session.
     * @param name the name of the cookie to delete
     */
    public static void deleteCookie(String name) {
        LOGGER.debug("Deleting cookie: " + name);
        Navigator.getInstance().getDriver().manage().deleteCookieNamed(name);
    }

    /**
     * Deletes all cookies from the current session.
     */
    public static void deleteAllCookies() {
        LOGGER.debug("Deleting all cookies");
        Navigator.getInstance().getDriver().manage().deleteAllCookies();
    }
}
