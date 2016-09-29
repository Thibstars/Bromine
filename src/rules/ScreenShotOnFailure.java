package rules;

import commands.TakeScreenshotCommand;
import org.apache.log4j.Logger;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Rule responsible for taking a screenshot right before test failure.
 * @author Thibault Helsmoortel
 */
public class ScreenShotOnFailure implements MethodRule {

    private static final Logger LOGGER = Logger.getLogger(ScreenShotOnFailure.class);

    private String packagePath;

    /**
     * Class constructor specifying the screenshot destination package path.
     * @param packagePath the screenshot destination package path
     */
    public ScreenShotOnFailure(String packagePath) {
        this.packagePath = packagePath;
    }

    @Override
    public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    // exception will be thrown only when a test fails.
                    captureScreenShot(frameworkMethod.getName());
                    // rethrow to allow the failure to be reported by JUnit
                    throw t;
                }
            }

            /**
             * Captures the actual screenshot with a given name.
             * @param fileName the name of the screenshot to take
             */
            public void captureScreenShot(String fileName) {
                LOGGER.debug("Firing screenshot capture command due to test failure");
                new TakeScreenshotCommand(packagePath, fileName).execute();
            }
        };
    }
}
