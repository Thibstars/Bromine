import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import reporting.cases.Case;
import reporting.cases.CaseEnvironment;
import reporting.cases.CaseStatus;
import reporting.cases.Defects;

import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Test class testing Defects.
 *
 * @author Thibault Helsmoortel
 */
public class DefectsTests {

    private static final Logger LOGGER = Logger.getLogger(DefectsTests.class);

    private static Case failedCase;
    private static Case brokenCase;

    /**
     * Test initialization.
     * @throws MalformedURLException thrown when environment url is malformed
     */
    @BeforeClass
    public static void init() throws MalformedURLException {
        failedCase = new Case("Failed Case",
                new CaseEnvironment("Google Chrome", new URL("http://www.thibaulthelsmoortel.be")),
                "Just a failed case");

        brokenCase = new Case("Broken Case",
                new CaseEnvironment("Google Chrome", new URL("http://www.thibaulthelsmoortel.be")),
                "Just a broken case");
    }

    private static void addCases() {
        LOGGER.debug("Adding defects...");
        failedCase.setStatus(CaseStatus.FAILED);
        brokenCase.setStatus(CaseStatus.BROKEN);
    }

    @Test
    public void shouldAddDefects() {
        addCases();
        assertEquals(failedCase, Defects.getFailedDefects().get(0));
        assertEquals(brokenCase, Defects.getBrokenDefects().get(0));
    }

    @Test
    public void shouldRemoveDefects() {
        addCases();

        LOGGER.debug("Removing defects...");
        failedCase.setStatus(CaseStatus.PENDING);
        brokenCase.setStatus(CaseStatus.PENDING);

        assertTrue(Defects.getFailedDefects().isEmpty());
        assertTrue(Defects.getBrokenDefects().isEmpty());
    }
}
