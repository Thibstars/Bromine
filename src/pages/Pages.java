package pages;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Representation of all the registered pages.
 * @author Thibault Helsmoortel
 */
public final class Pages {

    private static final List<Page> pageList = new ArrayList<>();

    /**
     * Registers a page in the list.
     * @param page the page to register in the list
     */
    public static void registerPage(Page page) {
        if (pageList.contains(page)) throw new IllegalArgumentException("Page was already registered.");
        else pageList.add(page);
    }

    /**
     * Deregisters a page from the list.
     * @param page the page to deregister from the list
     */
    public static void deregisterPage(Page page) {
        if (!pageList.contains(page)) throw new IllegalArgumentException("Page was not previously registered.");
        else pageList.remove(page);
    }

    /**
     * Searches for a page based on a given url and returns it if it was found.
     * @param url the url of the page to find
     * @return the page if found
     */
    public static Page getPage(String url) {
        Page res = null;
        for (Page page : pageList) {
            if (page.getUrl().equals(url)) res = page;
        }
        return res;
    }

    /**
     * Searches for a page based on a given url and returns it if it was found.
     * @param pageClazz the class of the page to find
     * @return the page if found
     */
    public static Page getPage(Class pageClazz) {
        Page res = null;
        for (Page page : pageList) {
            if (page.getClass().equals(pageClazz)) res = page;
        }
        return res;
    }

    public static int size() {
        return pageList.size();
    }

    public static boolean isEmpty() {
        return pageList.isEmpty();
    }

    public static boolean contains(Object o) {
        return pageList.contains(o);
    }

    public static boolean containsAll(Collection<?> c) {
        return pageList.containsAll(c);
    }
}
