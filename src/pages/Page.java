package pages;

import navigation.Navigator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The abstract representation of a page.
 * @author Thibault Helsmoortel
 */
public abstract class Page {

    private String url;

    private List<Section> sections;

    /**
     * Class constructor specifying the url (example: '/page').
     * @param url the url of the page
     */
    public Page(String url) {
        this.url = url;
        this.sections = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String pageUrl) {
        url = pageUrl;
    }

    public void addSection(Section section) {
        if (sections.contains(section)) throw new IllegalArgumentException("Section was already added.");
        sections.add(section);
    }

    /**
     * Removes a section with the given name, if it could be found.
     * @param name the name of the section to be removed
     */
    public void removeSection(String name) {
        boolean found = false;
        for (Section section : sections) {
            if (section.getName().equals(name)) {
                found = true;
                sections.remove(section);
            }
        }
        if (!found) throw new IllegalArgumentException("Section could not be found.");
    }

    /**
     * Returns a section with the given name, if it could be found.
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
            Navigator.getInstance().navigateTo(new URL(base + url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if the WebDriver is currently located on the page, else if otherwise.
     * @return true if the WebDriver is currently located on the page, else if otherwise
     */
    public  boolean isAt() {
        String base = getBaseURL();
        return url != null && Navigator.getInstance().getUrl().equals(getCompleteURL().toString());
    }

}
