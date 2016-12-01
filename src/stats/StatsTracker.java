package stats;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class responsible for tracking multiple events.
 *
 * @author Thibault Helsmoortel
 */
public final class StatsTracker {
    private static final StatsTracker statsTrackerInstance = new StatsTracker();

    private static final Logger LOGGER = Logger.getLogger(StatsTracker.class);

    private static List<StatsPlugin> plugins;

    /**
     * Class constructor. Publicly unavailable.
     */
    private StatsTracker() {
        plugins = new ArrayList<>();
    }

    /**
     * Registers a plugin.
     *
     * @param plugin the plugin to register
     */
    public void registerPlugin(StatsPlugin plugin) {
        if (plugins.contains(plugin)) throw new IllegalArgumentException("Plugin was already added.");
        else {
            LOGGER.debug("Registering plugin: " + plugin.getClass().getSimpleName());
            plugins.add(plugin);
        }
    }

    /**
     * Deregisters a plugin.
     *
     * @param plugin the plugin to deregister
     */
    public void deregisterPlugin(StatsPlugin plugin) {
        if (!plugins.contains(plugin)) throw new IllegalArgumentException("Plugin wasn't previously registered.");
        else {
            LOGGER.debug("Deregistering plugin " + plugin.getClass().getSimpleName());
            plugins.remove(plugin);
        }
    }

    /**
     * Returns an immutable list of the currently registered plugins.
     *
     * @return a list of the currently registered plugins
     */
    public static List<StatsPlugin> getPlugins() {
        return new ArrayList<>(plugins);
    }

    /**
     * Means to track a given action in all registered plugins.
     *
     * @param action the action to track in all registered plugins
     */
    public void track(StatsAction action) {
        LOGGER.debug("Tracking action: " + action.toString());
        plugins.forEach(plugin -> plugin.track(action));
    }

    /**
     * Resets all the registered plugins.
     */
    public void reset() {
        LOGGER.debug("Resetting all plugins");
        plugins.forEach(StatsPlugin::reset);
    }

    /**
     * Enables tracking on all registered plugins.
     */
    public void enableTracking() {
        LOGGER.debug("Enabling tracking");
        plugins.forEach(StatsPlugin::enableTracking);
    }

    /**
     * Disables tracking on all registered plugins.
     */
    public void disableTracking() {
        LOGGER.debug("Disabling tracking");
        plugins.forEach(StatsPlugin::disableTracking);
    }

    public synchronized static StatsTracker getInstance() {
        return statsTrackerInstance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
