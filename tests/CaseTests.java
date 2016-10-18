import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import reporting.cases.Case;
import reporting.cases.CaseEnvironment;
import reporting.cases.CaseStep;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

/**
 * Test class testing cases.
 *
 * @author Thibault Helsmoortel
 */
public class CaseTests {

    private static final Logger LOGGER = Logger.getLogger(CaseTests.class);

    private static Case testCase;

    /**
     * Test initialization.
     *
     * @throws MalformedURLException thrown when the environment url is malformed
     */
    @BeforeClass
    public static void init() throws MalformedURLException {
        testCase = new Case("Test Case",
                new CaseEnvironment("Google Chrome", new URL("http://www.thibaulthelsmoortel.be")),
                "Just a test case");
    }

    /**
     * Tests whether a link is properly added.
     *
     * @throws MalformedURLException thrown when the link is malformed
     */
    @Test
    public void shouldAddLink() throws MalformedURLException {
        LOGGER.debug("Adding link...");
        URL url = new URL("http://www.thibaulthelsmoortel.be/repos.html");
        testCase.addLink(url);
        assertEquals(url, testCase.getLinks().get(0));
    }

    @Test
    public void shouldAddCaseStep() {
        LOGGER.debug("Adding case step...");
        CaseStep step = new CaseStep("Test case");
        testCase.addCaseStep(step);
        assertEquals(step, testCase.getCaseSteps().get(0));
    }

    /**
     * Tests whether an attachment is properly added.
     */
    @Test
    public void shouldAddAttachment() {
        LOGGER.debug("Adding attachment...");
        File file = new File("all.log");
        testCase.addAttachment(file);
        assertEquals(file, testCase.getAttachments().get(0));
    }
}
