package stats;

/**
 * StatsPlugin tracking only LMB double clicks.
 * @author Thibault Helsmoortel
 */
public class LMBDoubleClickStats extends LMBClickStats {

    @Override
    public void track(StatsAction statsAction) {
        if (statsAction.equals(StatsAction.MOUSE_LMB_DOUBLE_CLICK) && trackingEnabled) clicks++;
    }

    @Override
    public String represent() {
        return "Double clicks: " + clicks;
    }
}
