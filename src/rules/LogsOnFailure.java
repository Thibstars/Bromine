package rules;

import commands.CaptureLogsCommand;
import org.apache.log4j.Logger;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Rule responsible for taking a snapshot of the log output right before test failure.
 *
 * @author Thibault Helsmoortel
 */
public class LogsOnFailure implements MethodRule {

    private static final Logger LOGGER = Logger.getLogger(LogsOnFailure.class);

    private String packagePath;

    /**
     * Class constructor specifying the logs destination package path.
     * @param packagePath the logs destination package path
     */
    public LogsOnFailure(String packagePath) {
        this.packagePath = packagePath;
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    // exception will be thrown only when a test fails.
                    captureLogs(frameworkMethod.getName());
                    // rethrow to allow the failure to be reported by JUnit
                    throw t;
                }
            }

            /**
             * Captures the actual logs with a given name.
             * @param fileName the name of the logs to capture
             */
            private void captureLogs(String fileName) {
                LOGGER.debug("Firing screenshot capture command due to test failure");
                new CaptureLogsCommand(packagePath, fileName).execute();
            }
        };
    }
}