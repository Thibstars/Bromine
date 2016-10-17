package util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for providing time stamps.
 * @author Thibault Helsmoortel
 */
public final class TimeStampUtil {

    /**
     * Returns a timestamp String of the current system time.
     *
     * @return a timestamp String of the current system time
     */
    public static String getTimeStampValue() {
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        String timestamp = time.toString();
        String sysTime = timestamp.replace(":", "-");
        return sysTime;
    }
}
