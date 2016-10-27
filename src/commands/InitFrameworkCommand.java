package commands;

import navigation.Navigator;
import navigation.bots.ActionBot;
import navigation.bots.WaiterBot;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import stats.StatsTrackerFactory;

import java.net.URL;

/**
 * Class responsible for initialization of the framework.
 *
 * @author Thibault Helsmoortel
 */
public class InitFrameworkCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(InitFrameworkCommand.class);

    private static boolean isInitialised = false;

    @Override
    public Object execute() {
        if (!isInitialised) {
            LOGGER.debug("Initialising the framework...");

            URL chromeDriverURL;
            URL geckoDriverURL;

            LOGGER.debug("Initialising OS dependant files... [" + System.getProperty("os.name") + "]");
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

            LOGGER.debug("Silencing noise...");
            //Remove noisy debug logs
            Logger.getLogger("org.apache.http")                                                     .setLevel(Level.WARN);
            Logger.getLogger("org.apache.http.wire")                                                .setLevel(Level.WARN);
            Logger.getLogger("org.apache.http.headers")                                             .setLevel(Level.WARN);
            Logger.getLogger("org.apache.http.impl.conn.PoolingHttpClientConnectionManager")        .setLevel(Level.WARN);
            Logger.getLogger("org.apache.http.impl.conn.DefaultHttpClientConnectionOperator")       .setLevel(Level.WARN);
            Logger.getLogger("org.apache.http.impl.conn.DefaultManagedHttpClientConnection")        .setLevel(Level.WARN);
            Logger.getLogger("org.apache.http.impl.execchain.MainClientExec")                       .setLevel(Level.WARN);
            Logger.getLogger("org.apache.http.client.protocol.RequestAddCookies")                   .setLevel(Level.WARN);
            Logger.getLogger("org.apache.http.client.protocol.RequestAuthCache")                    .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.BooleanConverter")            .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.ByteConverter")               .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.CharacterConverter")          .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.DoubleConverter")             .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.FloatConverter")              .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.IntegerConverter")            .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.BigIntegerConverter")         .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.ShortConverter")              .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.BigDecimalConverter")         .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.LongConverter")               .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.StringConverter")             .setLevel(Level.WARN);
            Logger.getLogger("org.apache.commons.beanutils.converters.ArrayConverter")              .setLevel(Level.WARN);

            //Create default StatsTracker
            StatsTrackerFactory.createDefault();

            if (Navigator.getInstance().getWaiterBot() == null) Navigator.getInstance().setWaiterBot(new WaiterBot());
            if (Navigator.getInstance().getActionBot() == null) Navigator.getInstance().setActionBot(new ActionBot());

            LOGGER.debug("Framework initialised");
            isInitialised = true;
        }
        else LOGGER.debug("The framework was already initialised.");

        return null;
    }
}
