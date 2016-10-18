package reporting.cases;

import util.TimeStampUtil;

/**
 * Class representing a case step.
 *
 * @author Thibault Helsmoortel
 */
public class CaseStep {

    private final String title;

    /**
     * Class constructor specifying the title.
     *
     * @param title the step title
     */
    public CaseStep(String title) {
        this.title = title;
    }

    /**
     * Returns a String representation of this case step.
     *
     * @return a String representation of this case step
     */
    @Override
    public String toString() {
        return TimeStampUtil.getShortTimeStamp() + " " + title;
    }
}
