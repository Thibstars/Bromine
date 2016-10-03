import commands.InitFrameworkCommand;
import commands.TakeScreenshotCommand;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import stats.StatsTrackerFactory;
import sut.Environment;

import java.io.IOException;

public class ScreenshotTests {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @BeforeClass
    public static void init() {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createHighlightingNavigator(new Environment("Website", "http://thibaulthelsmoortel.be"), Browser.CHROME);
        StatsTrackerFactory.createDefault();
    }

    @Test
    public void shouldCaptureScreenshot() throws IOException {
        temporaryFolder.create();
        Navigator.getInstance().explicitlyWaitForPageLoaded();
        Assert.assertNotNull(new TakeScreenshotCommand(temporaryFolder.getRoot().getPath(), "testScreenshot").execute());
        temporaryFolder.delete();
    }

    @AfterClass
    public static void tearDown() {
        NavigatorFactory.destroyNavigator();
    }
}
