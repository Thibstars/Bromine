import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import stats.StatsSummary;
import stats.StatsTrackerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StatsSummaryTests {

    private static final Logger LOGGER = Logger.getLogger(StatsSummaryTests.class);

    @BeforeClass
    public static void init() {
        StatsTrackerFactory.createDefault();
    }

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

    @AfterClass
    public static void tearDown() {
    }
}
