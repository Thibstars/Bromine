import org.apache.log4j.Logger;
import org.junit.Test;
import util.TimeStampUtil;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test class testing the TimeStampUtil
 *
 * @author Thibault Helsmoortel
 */
public class TimeStampUtilTests {

    private static final Logger LOGGER = Logger.getLogger(TimeStampUtilTests.class);

    /**
     * Tests if a time stamp is successfully generated and returned.
     */
    @Test
    public void shouldReturnTimeStampString() {
        LOGGER.debug("Time stamp: " + TimeStampUtil.getTimeStampValue());
        assertNotNull(TimeStampUtil.getTimeStampValue());
        assertTrue(TimeStampUtil.getTimeStampValue().length() > 0);

        String dateString = new Date().toString().substring(0, 10);
        assertEquals(dateString, TimeStampUtil.getTimeStampValue().substring(0, 10));
    }
}
