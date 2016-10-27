import org.junit.Assert;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Features;

import java.io.IOException;

/**
 * Test class testing PackageUtil.
 *
 * @author Thibault Helsmoortel
 */
@Features("Utils")
public class PackageUtilTestCase {

    /**
     * Tests if classes can successfully be retrieved from a package.
     *
     * @throws IOException            thrown when the package couldn't be read
     * @throws ClassNotFoundException thrown when a class was not found
     */
    @Test
    public void shouldGetClasses() throws IOException, ClassNotFoundException {
        Class[] classes = util.PackageUtil.getClasses("navigation");

        Assert.assertNotNull(classes);
        Assert.assertTrue(classes.length > 0);
    }
}
