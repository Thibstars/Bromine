package reporting.cases;

import java.net.URL;

/**
 * Class representing a case environment.
 *
 * @author Thibault Helsmoortel
 */
public class CaseEnvironment {

    private final String browser;
    private final URL host;

    /**
     * Class constructor specifying browser and host.
     *
     * @param browser the browser name
     * @param host    the host url
     */
    public CaseEnvironment(String browser, URL host) {
        this.browser = browser;
        this.host = host;
    }

    public String getBrowser() {
        return browser;
    }

    public URL getHost() {
        return host;
    }

    /**
     * Returns a String representation of this case environment.
     *
     * @return a String representation of this case environment.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Browser: ").append(browser)
                .append("Host: ").append(host.toString());
        return sb.toString();
    }
}
