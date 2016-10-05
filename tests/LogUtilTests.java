import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import util.LogUtil;

import static org.testng.Assert.assertEquals;

public class LogUtilTests {

    @Test
    public void shouldTurnOff() {
        LogUtil.stopLog();
        assertEquals(Level.OFF, Logger.getRootLogger().getLevel());
    }

    @Test
    public void shouldDebug() {
        LogUtil.debugLog();
        assertEquals(Level.DEBUG, Logger.getRootLogger().getLevel());
    }

    @Test
    public void shouldChangeLevel() {
        LogUtil.changeLevel(Level.ALL);
        assertEquals(Level.ALL, Logger.getRootLogger().getLevel());
    }

    @AfterClass
    public static void tearDown() {
        LogUtil.debugLog();
    }
}
