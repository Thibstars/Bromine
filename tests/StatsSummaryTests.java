import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import stats.StatsSummary;
import stats.StatsTrackerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class testing StatsSummary.
 *
 * @author Thibault Helsmoortel
 */
public class StatsSummaryTests {

    private static final Logger LOGGER = Logger.getLogger(StatsSummaryTests.class);

    /**
     * Initializes the test class.
     */
    @BeforeClass
    public static void init() {
        StatsTrackerFactory.createDefault();
    }

    /**
     * Tests if summary is properly formed from the currently registered plugins.
     */
    @Test
    public void shouldSummarizeAllRegisteredPlugins() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.debug(StatsSummary.summarizeAll());
        assertNotNull(StatsSummary.summarizeAll());
        assertTrue(StatsSummary.summarizeAll().length() > 0);
    }
}
