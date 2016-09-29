package navigation;

import commands.InitFrameworkCommand;
import org.apache.log4j.Logger;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import sut.Environment;

import java.util.concurrent.TimeUnit;

/**
 * Responsible class for creating and destroying the Navigator.
 * @author Thibault Helsmoortel
 */
public final class NavigatorFactory {

    private static final Logger LOGGER = Logger.getLogger(NavigatorFactory.class);

    /**
     * (Re)creates and returns the default Navigator based on the given Environment.
     * @param environment the environment for the Navigator to operate on
     * @return the (re)created Navigator instance
     */
    public static Navigator createNavigator(Environment environment) {
        return createNavigator(environment, Browser.CHROME);
    }

    /**
     * (Re)creates and returns the Navigator based on the given Environment and desired Browser.
     * @param environment the environment for the Navigator to operate on
     * @param browser the browser to use
     * @return the (re)created Navigator instance
     */
    public static Navigator createNavigator(Environment environment, Browser browser) {
        Navigator navigator;
        switch (browser) {
            case CHROME:
                navigator = createChromeNavigator(environment, true);
                break;
            case FIREFOX:
                navigator = createFirefoxNavigator(environment);
                break;
            default:
                navigator = createChromeNavigator(environment, true);
        }

        return navigator;
    }

    /**
     * (Re)creates and returns the Navigator based on the given Environment and the desired incognito mode.
     * @param environment the environment for the Navigator to operate on
     * @param incognito boolean value indicating whether or not to use incognito mode
     * @return the (re)created Navigator instance
     */
    public static Navigator createChromeNavigator(Environment environment, boolean incognito) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        if (incognito){
            //Set incognito driver options
            options.addArguments("--incognito");
        }

        return createChromeNavigator(environment, options);
    }

    /**
     * (Re)creates and returns the Navigator based on the given Environment and the desired ChromeOptions
     * @param environment the environment fo the Navigator to operate on
     * @param chromeOptions the chrome options to be used
     * @return the (re)created Navigator instance
     */
    public static Navigator createChromeNavigator(Environment environment, ChromeOptions chromeOptions) {
        LOGGER.debug("Creating a Chrome Navigator for environment: " + environment.getName());
        //Destroy previous navigator
        destroyNavigator();

        new InitFrameworkCommand().execute();
        Navigator navigator = Navigator.getInstance();
        navigator.setDriver(new ChromeDriver(chromeOptions));
        navigator.setWait(new WebDriverWait(navigator.getDriver(), 30));
        WebDriver driver = navigator.getDriver();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        //Open the window maximized on a second monitor
        driver.manage().window().setPosition(new Point(2000, 1));
        driver.manage().window().maximize();

        //Navigate to the given environment
        driver.get(environment.getUrl().toString());
        return navigator;
    }

    /**
     * (Re)creates and returns the Navigator based on the given Environment.
     * @param environment the environment for the Navigator to operate on
     * @return  the (re)created Navigator instance
     */
    public static Navigator createFirefoxNavigator(Environment environment) {
        LOGGER.debug("Creating a Firefox Navigator for environment: " + environment.getName());
        //Destroy previous navigator
        destroyNavigator();

        new InitFrameworkCommand().execute();
        Navigator navigator = Navigator.getInstance();
        navigator.setDriver(new FirefoxDriver());
        navigator.setWait(new WebDriverWait(navigator.getDriver(), 30));
        WebDriver driver = navigator.getDriver();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        //Open the window maximized on a second monitor
        driver.manage().window().setPosition(new Point(2000, 1));
        driver.manage().window().maximize();

        //Navigate to the given environment
        driver.get(environment.getUrl().toString());
        return navigator;
    }

    /**
     * Destroys the current Navigator instance.
     * After calling this method, the Navigator will be rendered useless.
     */
    public static void destroyNavigator() {
        LOGGER.debug("Destroying the Navigator to a useless state");
        if (Navigator.getInstance().getDriver() != null) Navigator.getInstance().getDriver().close();
        Navigator.getInstance().setDriver(null);
        Navigator.getInstance().setWait(null);
        Navigator.getInstance().setEnvironment(null);
    }
}
