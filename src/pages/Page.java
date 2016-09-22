package pages;

import navigation.Navigator;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The abstract representation of a page.
 * @author Thibault Helsmoortel
 */
public abstract class Page {

    protected  String url;

    /**
     * Class constructor specifying the url (example: '/page').
     * @param url the url of the page
     */
    public Page(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String pageUrl) {
        url = pageUrl;
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
