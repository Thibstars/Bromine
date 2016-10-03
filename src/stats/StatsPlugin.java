package stats;

/**
 * Interface defining means to track certain actions.
 * @author Thibault Helsmoortel
 */
public interface StatsPlugin {

    void enableTracking();
    void disableTracking();
    void track(StatsAction statsAction);
    void reset();
    default String represent() { return toString();}
}
