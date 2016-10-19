import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import util.LogUtil;

import static org.junit.Assert.assertEquals;

/**
 * Test class testing the LogUtil.
 *
 * @author Thibault Helsmoortel
 */
public class LogUtilTestCase {

    /**
     * Tests if the logs can be properly turned of.
     */
    @Test
    public void shouldTurnOff() {
        LogUtil.stopLog();
        assertEquals(Level.OFF, Logger.getRootLogger().getLevel());
    }

    /**
     * Tests if the logs can be properly set to the debug level.
     */
    @Test
    public void shouldDebug() {
        LogUtil.debugLog();
        assertEquals(Level.DEBUG, Logger.getRootLogger().getLevel());
    }

    /**
     * Tests if the level of the logs can be changed correctly.
     */
    @Test
    public void shouldChangeLevel() {
        LogUtil.changeLevel(Level.ALL);
        assertEquals(Level.ALL, Logger.getRootLogger().getLevel());
    }

    /**
     * Destroys the setup. Reverts settings back to debug level.
     */
    @AfterClass
    public static void tearDown() {
        LogUtil.debugLog();
    }
}
