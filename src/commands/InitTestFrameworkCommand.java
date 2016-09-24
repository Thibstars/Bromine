package commands;

import java.io.File;

/**
 * Class responsible for initialization of the test framework.
 * @author Thibault Helsmoortel
 */
public class InitTestFrameworkCommand implements Command {

    private static boolean isInitialised = false;

    @Override
    public Object execute() {
        if (!isInitialised) {
            File chromeDriverFile;
            File geckoDriverFile;

            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                chromeDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("win/chromedriver.exe").getFile());
                geckoDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("win/geckodriver.exe").getFile());

                System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
                System.setProperty("webdriver.gecko.driver", geckoDriverFile.getAbsolutePath());
            }
            else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                chromeDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("mac/chromedriver").getFile());
                geckoDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("mac/geckodriver").getFile());

                System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
                System.setProperty("webdriver.gecko.driver", geckoDriverFile.getAbsolutePath());
            }
            else {
                chromeDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("linux/chromedriver").getFile());
                geckoDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("linux/geckodriver").getFile());

                System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
                System.setProperty("webdriver.gecko.driver", geckoDriverFile.getAbsolutePath());
            }

            isInitialised = true;
        }
        return isInitialised;
    }
}
