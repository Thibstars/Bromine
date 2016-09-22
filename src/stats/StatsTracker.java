package stats;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class responsible for tracking multiple events.
 * @author Thibault Helsmoortel
 */
public final class StatsTracker {
    private static final StatsTracker statsTrackerInstance = new StatsTracker();

    private static List<StatsPlugin> plugins;

    /**
     * Class constructor. Publicly unavailable.
     */
    private StatsTracker() {
        plugins = new ArrayList<>();
    }

    /**
     * Registers a plugin.
     * @param plugin the plugin to register
     */
    public void registerPlugin(StatsPlugin plugin) {
        if (plugins.contains(plugin)) throw new IllegalArgumentException("Plugin was already added.");
        else plugins.add(plugin);
    }

    /**
     * Deregisters a plugin.
     * @param plugin the plugin to deregister
     */
    public void deregisterPlugin(StatsPlugin plugin) {
        if (!plugins.contains(plugin)) throw new IllegalArgumentException("Plugin wasn't previously registered.");
        else plugins.remove(plugin);
    }

    /**
     * Means to track a given action in all registered plugins.
     * @param action the action to track in all registered plugins
     */
    public void track(StatsAction action) {
        for (StatsPlugin plugin : plugins) plugin.track(action);
    }

    /**
     * Enables tracking on all registered plugins.
     */
    public void enableTracking() {
        plugins.forEach(StatsPlugin::enableTracking);
    }

    /**
     * Disables tracking on all registered plugins.
     */
    public void disableTracking() {
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
