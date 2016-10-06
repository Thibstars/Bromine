package stats.defaultPlugins;

import stats.StatsAction;

/**
 * StatsPlugin tracking implicit waits
 * @author Thibault Helsmoortel
 */
public class ImplicitWaitStats extends WaitStats {

    @Override
    public void track(StatsAction statsAction) {
        if (statsAction.equals(StatsAction.WAIT_IMPLICIT) && isTrackingEnabled()) timesWaited++;
    }

    @Override
    public String represent() {
        return "Times implicitly waited: " + timesWaited;
    }
}
