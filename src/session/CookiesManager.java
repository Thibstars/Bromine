package session;

import navigation.Navigator;
import org.openqa.selenium.Cookie;

import java.util.Set;

/**
 * Class responsible for the management of cookies.
 * @author Thibault Helsmoortel
 */
public final class CookiesManager {

    /**
     * Returns a set of cookies present in the current session.
     * @return a set of cookies present in the current session
     */
    public static Set<Cookie> getCookies() {
        return Navigator.getInstance().getDriver().manage().getCookies();
    }
}
