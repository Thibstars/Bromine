package stats.defaultPlugins;

import stats.StatsAction;

/**
 * StatsPlugin tracking explicit waits.
 *
 * @author Thibault Helsmoortel
 */
public class ExplicitWaitStats extends WaitStats {

    @Override
    public void track(StatsAction statsAction) {
        if (statsAction.equals(StatsAction.WAIT_EXPLICIT) && isTrackingEnabled()) timesWaited++;
    }

    @Override
    public String represent() {
        return "Times explicitly waited: " + timesWaited;
    }
}
