import commands.InitFrameworkCommand;
import commands.TakeScreenshotCommand;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import sut.Environment;

import java.io.IOException;

/**
 * Test class testing screenshot functionality.
 * @author Thibault Helsmoortel
 */
public class ScreenshotTests {

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
     * Tests if a screenshot is successfully captured.
     * @throws IOException thrown when the file couldn't be saved
     */
    @Test
    public void shouldCaptureScreenshot() throws IOException {
        temporaryFolder.create();
        Navigator.getInstance().explicitlyWaitForPageLoaded();
        Assert.assertNotNull(new TakeScreenshotCommand(temporaryFolder.getRoot().getPath(), "testScreenshot").execute());
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
