import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PackageUtilTests {
    @Test
    public void shouldGetClasses() throws IOException, ClassNotFoundException {
        Class[] classes = util.PackageUtil.getClasses("navigation");

        Assert.assertNotNull(classes);
        Assert.assertTrue(classes.length > 0);
    }
}
