package sut;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Representation of the environment under test.
 *
 * @author Thibault Helsmoortel
 */
public class Environment {
    private String name;
    private URL url;

    /**
     * Class constructor specifying name and url.
     *
     * @param name the environment's name
     * @param url  the environment's url
     */
    public Environment(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    /**
     * Class constructor specifying name and url (in String form).
     *
     * @param name the environment's name
     * @param url  the environment's url
     */
    public Environment(String name, String url) {
        this.name = name;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * Returns a String representation of this environment.
     *
     * @return a String representation of this environment
     */
    @Override
    public String toString() {
        return name + ": " + url.toString();
    }
}
