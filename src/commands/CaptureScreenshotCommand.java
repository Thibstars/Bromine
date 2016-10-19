package commands;

import navigation.Navigator;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import util.TimeStampUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Command responsible for taking screenshots.
 *
 * @author Thibault Helsmoortel
 */
public class CaptureScreenshotCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CaptureScreenshotCommand.class);

    private String packageName;
    private String name;

    /**
     * Class constructor specifying the name of the screenshot to take.
     *
     * @param packagePath the name of the destination package
     * @param name        the name of the screenshot to take
     */
    public CaptureScreenshotCommand(String packagePath, String name) {
        this.packageName = packagePath;
        this.name = name;
    }

    @Override
    public Object execute() {
        LOGGER.debug("Initiating screenshot capture...");
        //Take the byte screenshot for the attachment
        takeScreenShot(name);

        File scrFile = ((TakesScreenshot) Navigator.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        String fileName;
        fileName = name + "_" + TimeStampUtil.getTimeStamp() + ".png";
        File targetFile = new File(packageName + fileName);
        boolean success = true;
        try {
            LOGGER.debug("Copying screenshot file to target destination...");
            FileUtils.copyFile(scrFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        try {
            createOverlay(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        if (success) LOGGER.debug("Screenshot successfully captured.");
        else LOGGER.error("Something went wrong capturing the screenshot.");
        return targetFile;
    }

    /**
     * Takes a screenshot and returns a byte array of it.
     * Gets attached to a test case in Allure.
     *
     * @param name the screenshot name
     * @return the byte array of the screenshot
     */
    @Attachment("{0}")
    public byte[] takeScreenShot(String name) {
        return ((TakesScreenshot) Navigator.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Creates an overlay on the screenshot with the current URL.
     *
     * @param targetFile the file on which to draw the overlay
     * @throws IOException when the image could not be read/written
     */
    private void createOverlay(File targetFile) throws IOException {
        LOGGER.debug("Creating overlay...");
        String overlay = "URL: " + Navigator.getInstance().getUrl();
        BufferedImage bufferedImage = ImageIO.read(targetFile);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        graphics.setFont(new Font("Arial Black", Font.PLAIN, 20));
        graphics.fillRect(0, 0, graphics.getFontMetrics().stringWidth(overlay) + 20, 30);
        graphics.setColor(Color.BLACK);
        graphics.drawString(overlay, 10, 20);
        ImageIO.write(bufferedImage, "png", targetFile);
        LOGGER.debug("Overlay created.");
    }
}
