package stats;

import org.apache.log4j.Logger;

/**
 * Factory responsible for creating the StatsTracker.
 * @author Thibault Helsmoortel
 */
public final class StatsTrackerFactory {

    private static final Logger LOGGER = Logger.getLogger(StatsTrackerFactory.class);

    /**
     * Creates and returns a default StatsTracker
     * @return the newly created default StatsTracker
     */
    public static StatsTracker createDefault() {
        LOGGER.debug("Creating default StatsTracker");

        StatsTracker statsTracker = StatsTracker.getInstance();

        LOGGER.debug("Registering default plugins");
        //Register default plugins
        statsTracker.registerPlugin(new LMBClickStats());
        statsTracker.registerPlugin(new LMBDoubleClickStats());
        statsTracker.registerPlugin(new SendKeyStats());
        statsTracker.registerPlugin(new WaitStats());
        statsTracker.registerPlugin(new ImplicitWaitStats());
        statsTracker.registerPlugin(new ExplicitWaitStats());

        //Enable tracking on all the plugins straight away
        statsTracker.enableTracking();

        return statsTracker;
    }
}
