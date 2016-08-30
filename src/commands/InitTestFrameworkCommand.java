package commands;

import navigation.Navigator;

import java.io.File;

/**
 * Created by Thibault on 30/08/2016.
 */
public class InitTestFrameworkCommand implements Command {

    private static boolean isInitialised = false;

    @Override
    public Object execute() {
        if (!isInitialised) {
            File chromeDriverFile;
            File geckoDriverFile;

            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                chromeDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("chromedriver.exe").getFile());
                geckoDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("geckodriver.exe").getFile());

                System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
                System.setProperty("webdriver.gecko.driver", geckoDriverFile.getAbsolutePath());
            }
            else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                chromeDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("chromedriver").getFile());
                geckoDriverFile = new File(InitTestFrameworkCommand.class.getClassLoader().getResource("geckodriver").getFile());
                System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
                System.setProperty("webdriver.gecko.driver", geckoDriverFile.getAbsolutePath());
            }

            new Navigator();

            isInitialised = true;
        }
        return null;
    }
}
