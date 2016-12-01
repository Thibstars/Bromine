package stats.defaultPlugins;

import stats.StatsAction;
import stats.StatsPlugin;

/**
 * StatsPlugin tracking only LMB clicks.
 *
 * @author Thibault Helsmoortel
 */
public class LMBClickStats implements StatsPlugin {

    boolean trackingEnabled;
    protected int clicks;

    /**
     * Class constructor. Initializes state.
     */
    public LMBClickStats() {
        this.trackingEnabled = false;
        this.clicks = 0;
    }

    public int getClicks() {
        return clicks;
    }

    @Override
    public void enableTracking() {
        this.trackingEnabled = true;
    }

    @Override
    public void disableTracking() {
        this.trackingEnabled = false;
    }

    @Override
    public boolean isTrackingEnabled() {
        return trackingEnabled;
    }

    @Override
    public void track(StatsAction statsAction) {
        if (statsAction.equals(StatsAction.MOUSE_LMB_CLICK) && trackingEnabled) clicks++;
    }

    @Override
    public void reset() {
        this.clicks = 0;
    }

    @Override
    public String represent() {
        return "Clicks: " + clicks;
    }
}
