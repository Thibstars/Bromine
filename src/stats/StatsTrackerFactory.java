package stats;

/**
 * Factory responsible for creating the StatsTracker.
 * @author Thibault Helsmoortel
 */
public final class StatsTrackerFactory {

    /**
     * Creates and returns a default StatsTracker
     * @return the newly created default StatsTracker
     */
    public static StatsTracker createDefault() {
        StatsTracker statsTracker = StatsTracker.getInstance();

        //Register default plugins
        statsTracker.registerPlugin(new ClickStats());

        //Enable tracking on all the plugins straight away
        statsTracker.enableTracking();

        return statsTracker;
    }
}
