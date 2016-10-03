package rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ExpectedFailure implements TestRule {

    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch (Throwable e) {
                    if (description.getAnnotation(Deprecated.class) != null) {
                        // you can do whatever you like here.
                        System.err.println("test failed, but that's ok:");
                    } else {
                        throw e;
                    }
                }
            }
        };
    }
}
