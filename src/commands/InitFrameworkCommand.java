package commands;

import java.net.URL;

/**
 * Class responsible for initialization of the framework.
 *
 * @author Thibault Helsmoortel
 */
public class InitFrameworkCommand implements Command {

    private static boolean isInitialised = false;

    @Override
    public Object execute() {
        if (!isInitialised) {
            URL chromeDriverURL;
            URL geckoDriverURL;

            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                chromeDriverURL = Thread.currentThread().getContextClassLoader().getResource("win/chromedriver.exe");
                geckoDriverURL = Thread.currentThread().getContextClassLoader().getResource("win/geckodriver.exe");
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                chromeDriverURL = Thread.currentThread().getContextClassLoader().getResource("mac/chromedriver");
                geckoDriverURL = Thread.currentThread().getContextClassLoader().getResource("mac/geckodriver");
            } else {
                chromeDriverURL = Thread.currentThread().getContextClassLoader().getResource("linux/chromedriver");
                geckoDriverURL = Thread.currentThread().getContextClassLoader().getResource("linux/geckodriver");
            }

            assert chromeDriverURL != null;
            System.setProperty("webdriver.chrome.driver", chromeDriverURL.getPath());
            assert geckoDriverURL != null;
            System.setProperty("webdriver.gecko.driver", geckoDriverURL.getPath());

            isInitialised = true;
        }

        return isInitialised;
    }
}
