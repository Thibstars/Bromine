package util;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Utility class for logging adjustments.
 *
 * @author Thibault Helsmoortel
 */
public final class LogUtil {

    /**
     * Stops the log output.
     */
    public static void stopLog() {
        changeLevel(Level.OFF);
    }

    /**
     * Enables debug output.
     */
    public static void debugLog() {
        changeLevel(Level.DEBUG);
    }

    /**
     * Changes the level of the log output.
     *
     * @param level the level of the log output.
     */
    public static void changeLevel(Level level) {
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for (Logger logger : loggers) {
            logger.setLevel(level);
        }
    }
}
