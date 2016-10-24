package pages;

import navigation.Navigator;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The abstract representation of a page.
 *
 * @author Thibault Helsmoortel
 */
public abstract class Page {

    private static final Logger LOGGER = Logger.getLogger(Page.class);

    private String url;

    private List<Section> sections;

    /**
     * Class constructor specifying the url (example: '/page').
     *
     * @param url the url of the page
     */
    public Page(String url) {
        this.url = url;
        //Use CopyOnWriteArrayList to prevent ConcurrentModificationException
        this.sections = new CopyOnWriteArrayList<>();
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String pageUrl) {
        this.url = pageUrl;
    }

    public void addSection(Section section) {
        if (sections.contains(section)) throw new IllegalArgumentException("Section was already added.");
        LOGGER.debug("Adding section: " + section.getName());
        sections.add(section);
    }

    /**
     * Removes a section with the given name, if it could be found.
     *
     * @param name the name of the section to be removed
     */
    public void removeSection(String name) {
        synchronized (this) {
            boolean found = false;
            for (Section section : sections) {
                if (section.getName().equals(name)) {
                    found = true;
                    LOGGER.debug("Removing section: " + section.getName());
                    sections.remove(section);
                }
            }
            if (!found) throw new IllegalArgumentException("Section could not be found.");
        }
    }

    /**
     * Removes a section from the page, if it could be found.
     *
     * @param section the section to be removed
     */
    public void removeSection(Section section) {
        removeSection(section.getName());
    }

    /**
     * Returns a section with the given name, if it could be found.
     *
     * @param name the name of the section to be found
     * @return the section if found, null if not found
     */
    public Section getSection(String name) {
        Section result = null;
        for (Section section : sections) {
            if (section.getName().equals(name)) result = section;
        }

        return result;
    }

    /**
     * Returns the base of this page's url, in String form.
     *
     * @return the base of this page's url
     */
    protected String getBaseURL() {
        String base = "";
        try {
            URL navURL = new URL(Navigator.getInstance().getUrl());
            String path = navURL.getFile().substring(0, navURL.getFile().lastIndexOf('/'));
            base = navURL.getProtocol() + "://" + navURL.getHost() + path;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return base;
    }

    /**
     * Returns the complete url of the page.
     *
     * @return the complete url of the page
     */
    public URL getCompleteURL() {
        URL url = null;
        try {
            url = new URL(getBaseURL() + this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Navigates the WebDriver to this page.
     */
    public void goTo() {
        String base = getBaseURL();
        try {
            Navigator.getInstance().navigateTo(new URL(base + this.url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if the WebDriver is currently located on the page, else if otherwise.
     *
     * @return true if the WebDriver is currently located on the page, else if otherwise
     */
    public boolean isAt() {
        return url != null && Navigator.getInstance().getUrl().equals(getCompleteURL().toString());
    }

    /**
     * Returns a String representation of this page.
     *
     * @return a String representation of this page
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + url;
    }
}
