package commands;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import ru.yandex.qatools.allure.annotations.Attachment;
import util.TimeStampUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Command responsible for taking screenshots
 */
public class CaptureLogsCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CaptureLogsCommand.class);

    private String packageName;
    private String name;

    /**
     * Class constructor specifying the name of the logs to capture.
     * @param packageName the name of the destination package
     * @param name the name of the logs to capture
     */
    public CaptureLogsCommand(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }

    @Override
    public Object execute() {
        LOGGER.debug("Initiating log capture...");

        File scrFile = new File("all.log");
        String fileName;
        fileName = name + "_" + TimeStampUtil.getTimeStamp() + ".log";
        File targetFile = new File(packageName + fileName);
        boolean success = true;
        try {
            LOGGER.debug("Copying log file to target destination...");
            FileUtils.copyFile(scrFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            //Take the byte logs for the attachment
            try {
                takeLogs(name, targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOGGER.debug("Logs successfully captured.");
        }
        else LOGGER.error("Something went wrong capturing the logs.");
        return targetFile;
    }

    /**
     * Takes the logs and returns a byte array of it.
     * Gets attached to a test case in Allure
     *
     * @param name the log file name
     * @param logFile the actual log file
     * @return the byte array of the log file
     * @throws IOException thrown when the file couldn't be read
     */
    @Attachment("{0}")
    public byte[] takeLogs(String name, File logFile) throws IOException {
        return IOUtils.toByteArray(new FileInputStream(logFile));
    }
}
