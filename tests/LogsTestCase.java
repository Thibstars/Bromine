import commands.CaptureLogsCommand;
import commands.InitFrameworkCommand;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import sut.Environment;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Test class testing log capturing functionality.
 *
 * @author Thibault Helsmoortel
 */
public class LogsTestCase {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    /**
     * Initializes the test class.
     */
    @BeforeClass
    public static void init() {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createHighlightingNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"), Browser.CHROME);
    }

    /**
     * Tests if logs are successfully captured.
     *
     * @throws IOException thrown when the temporary folder couldn't be created
     */
    @Test
    public void shouldCaptureLogs() throws IOException {
        temporaryFolder.create();
        Navigator.getInstance().explicitlyWaitForPageLoaded();
        assertNotNull(new CaptureLogsCommand(temporaryFolder.getRoot().getPath(), "testLogs").execute());
        temporaryFolder.delete();
    }

    /**
     * Destroys the setup.
     */
    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
