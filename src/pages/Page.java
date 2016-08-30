package pages;

import navigation.Navigator;

import java.net.URL;

/**
 * Created by Thibault on 29/08/2016.
 */
public abstract class Page {

    protected  URL url;

    public Page(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL pageUrl) {
        url = pageUrl;
    }

    public void goTo() {
        Navigator.navigateTo(url);
    }

    public  boolean isAt() {
        return url != null && Navigator.getUrl().equals(url.toString());
    }

}
