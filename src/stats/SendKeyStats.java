package stats;

/**
 * StatsPlugin tracking only sendKey events.
 * @author Thibault Helsmoortel
 */
public class SendKeyStats implements StatsPlugin {

    private boolean trackingEnabled;
    private int timesKeysSent;

    /**
     * Class constructor. Initializes state.
     */
    public SendKeyStats() {
        this.trackingEnabled = false;
        this.timesKeysSent = 0;
    }

    public boolean isTrackingEnabled() {
        return trackingEnabled;
    }

    public int getTimesKeysSent() {
        return timesKeysSent;
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
    public void track(StatsAction statsAction) {
        if (statsAction.equals(StatsAction.KEYBOARD_TYPE) && trackingEnabled) timesKeysSent++;
    }

    @Override
    public void reset() {
        this.timesKeysSent = 0;
    }

    @Override
    public String represent() {
        return "Times keys sent: " + timesKeysSent;
    }
}
