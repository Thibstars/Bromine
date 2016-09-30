package commands;

import navigation.Navigator;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Command responsible for taking screenshots.
 * @author Thibault Helsmoortel
 */
public class TakeScreenshotCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(TakeScreenshotCommand.class);

    private String packageName;
    private String name;

    /**
     * Class constructor specifying the name of the screenshot to take.
     * @param packagePath the name of the destination package
     * @param name the name of the screenshot to take
     */
    public TakeScreenshotCommand(String packagePath, String name) {
        this.packageName = packagePath;
        this.name = name;
    }

    @Override
    public Object execute() {
        LOGGER.debug("Initiating screenshot capture...");
        File scrFile = ((TakesScreenshot) Navigator.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        String fileName;
        fileName = name + "_" + getTimeStampValue() + ".png";
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
        else LOGGER.debug("Something went wrong capturing the screenshot.");
        return targetFile;
    }

    /**
     * Creates an overlay on the screenshot with the current URL.
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

    /**
     * Returns a timestamp String of the current system time.
     * @return a timestamp String of the current system time
     */
    private String getTimeStampValue() {
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        String timestamp = time.toString();
        String sysTime = timestamp.replace(":", "-");
        return sysTime;
    }
}
