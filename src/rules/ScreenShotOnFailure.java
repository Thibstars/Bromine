package rules;

import commands.TakeScreenshotCommand;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.io.IOException;

/**
 * Rule responsible for taking a screenshot right before test failure.
 * @author Thibault Helsmoortel
 */
public class ScreenShotOnFailure implements MethodRule {

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
                new TakeScreenshotCommand(fileName).execute();
            }
        };
    }
}
