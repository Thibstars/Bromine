package rules;

import org.apache.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import stats.StatsSummary;

/**
 * Rule responsible for summarizing all stats.
 * @author Thibault Helsmoortel
 */
public class SummarizeAllStats implements TestRule {

    private static final Logger LOGGER = Logger.getLogger(SummarizeAllStats.class);

    @Override
    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                statement.evaluate();
                //Summarize the statistics
                LOGGER.debug(StatsSummary.summarizeAll());
            }
        };
    }
}
