package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for providing time stamps.
 *
 * @author Thibault Helsmoortel
 */
public final class TimeStampUtil {

    /**
     * Returns a timestamp String of the current system time.
     *
     * @return a timestamp String of the current system time
     */
    public static String getTimeStamp() {
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        String timestamp = time.toString();
        String sysTime = timestamp.replace(":", "-");
        return sysTime;
    }

    /**
     * Returns a short timestamp String of the current system time.
     *
     * @return a timestamp String of the current system time
     */
    public static String getShortTimeStamp() {
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("[HH:mm:ss]");
        String formatTime = df.format(time);
        return formatTime;
    }
}
