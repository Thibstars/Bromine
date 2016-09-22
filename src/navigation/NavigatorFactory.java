package navigation;

import commands.InitTestFrameworkCommand;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import sut.Environment;

import java.util.concurrent.TimeUnit;

/**
 * Responsible class for creating and destroying the Navigator.
 * @author Thibault Helsmoortel
 */
public final class NavigatorFactory {

    /**
     * (Re)creates and returns the Navigator based on the given Environment.
     * @param environment the environment for the Navigator to operate on
     * @return the (re)created Navigator instance
     */
    public static Navigator createNavigator(Environment environment) {
        //Destroy previous navigator
        destroyNavigator();

        new InitTestFrameworkCommand().execute();
        Navigator navigator = Navigator.getInstance();
        navigator.setDriver(new ChromeDriver());
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
        if (Navigator.getInstance().getDriver() != null) Navigator.getInstance().getDriver().close();
        Navigator.getInstance().setDriver(null);
        Navigator.getInstance().setWait(null);
        Navigator.getInstance().setEnvironment(null);
    }
}
