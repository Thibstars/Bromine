import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import reporting.cases.Case;
import reporting.cases.CaseEnvironment;
import reporting.features.Feature;

import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class testing features.
 *
 * @author Thibault Helsmoortel
 */
public class FeatureTests {

    private static final Logger LOGGER = Logger.getLogger(FeatureTests.class);

    private static Feature feature;

    /**
     * Test initialization.
     */
    @BeforeClass
    public static void init() {
        feature = new Feature("Test Feature");
    }

    /**
     * Tests whether a case is properly added.
     *
     * @throws MalformedURLException thrown when the environment url is malformed
     */
    @Test
    public void shouldAddCase() throws MalformedURLException {
        LOGGER.debug("Adding case...");
        Case testCase = new Case("Test Case",
                new CaseEnvironment("Google Chrome", new URL("http://www.thibaulthelsmoortel.be")),
                "Just a test case");
        feature.addCase(testCase);
        assertEquals(testCase, feature.getCases().get(0));
    }

    /**
     * Tests whether a sub feature is properly added.
     */
    @Test
    public void shouldAddFeature() {
        LOGGER.debug("Adding feature...");
        Feature subFeature = new Feature("Sub feature");
        feature.addFeature(subFeature);
        assertEquals(subFeature, feature.getFeatures().get(0));
    }
}
