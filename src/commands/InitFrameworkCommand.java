package commands;

import java.net.URL;

/**
 * Class responsible for initialization of the test framework.
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
                chromeDriverURL = InitFrameworkCommand.class.getClassLoader().getResource("win/chromedriver.exe");
                geckoDriverURL = InitFrameworkCommand.class.getClassLoader().getResource("win/geckodriver.exe");
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                chromeDriverURL = InitFrameworkCommand.class.getClassLoader().getResource("mac/chromedriver");
                geckoDriverURL = InitFrameworkCommand.class.getClassLoader().getResource("mac/geckodriver");
            } else {
                chromeDriverURL = InitFrameworkCommand.class.getClassLoader().getResource("linux/chromedriver");
                geckoDriverURL = InitFrameworkCommand.class.getClassLoader().getResource("linux/geckodriver");
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
