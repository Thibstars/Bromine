package rules;

import navigation.NavigatorFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Rule responsible for destroying the navigator.
 *
 * @author Thibault Helsmoortel
 */
public class DestroyNavigator implements TestRule {

    @Override
    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                statement.evaluate();
                //Destroy the navigator
                NavigatorFactory.destroyNavigator();
            }
        };
    }
}
