package stats;

import java.util.List;

/**
 * Class responsible for providing summaries of statistics.
 * @author Thibault Helsmoortel
 */
public final class StatsSummary {

    /**
     * Summarizes all registered plugins.
     * @return a summary of all registered plugins
     */
    public static String summarizeAll() {
        return summarize(StatsTracker.getPlugins());
    }

    /**
     * Summarizes all plugins from a given list.
     * @param plugins the plugins to summarize
     * @return a summary of all given plugins
     */
    public static String summarize(List<StatsPlugin> plugins) {
        String summary = "Statistics Summary\n";
        for (int i = 0; i < summary.length(); i++) {
            summary += "=";
        }
        summary += "\n";
        for (StatsPlugin plugin : plugins) summary += plugin.represent() + "\n";
        return summary;
    }
}
