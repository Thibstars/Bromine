package pages;

import org.apache.log4j.Logger;
import util.PackageUtil;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Representation of all the registered pages.
 * @author Thibault Helsmoortel
 */
public final class Pages {

    private static final Logger LOGGER = Logger.getLogger(Page.class);

    private static final List<Page> pageList = new CopyOnWriteArrayList<>();

    /**
     * Registers a page in the list.
     * @param page the page to register in the list
     */
    public static void registerPage(Page page) {
        if (pageList.contains(page)) throw new IllegalArgumentException("Page was already registered.");
        else pageList.add(page);
    }

    /**
     * Register all pages from a given list.
     * @param pages the list of pages to register
     */
    public static void registerAllPages(List<Page> pages) {
        LOGGER.debug("Registering all pages...");
        pages.forEach(Pages::registerPage);
    }

    /**
     * The package from which to register all pages from.
     * Note that this method instantiates all pages with their default constructors before registering them.
     * @param pack the package of the pages to register
     */
    public static void registerAllPagesFromPackage(String pack) {
        //TODO fix bug with nested packages (like 'platform/pages'
        LOGGER.debug("Registering all pages...");
        try {
            for (Class clazz : PackageUtil.getClasses(pack)) {
                if (clazz.getClass().isInstance(Page.class))
                    LOGGER.debug("Registering: " + clazz.getSimpleName());
                registerPage((Page) clazz.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deregisters a page from the list.
     * @param page the page to deregister from the list
     */
    public static void deregisterPage(Page page) {
        if (!pageList.contains(page)) throw new IllegalArgumentException("Page was not previously registered.");
        else{
            LOGGER.debug("Deregistering page: " + page.getClass().getSimpleName());
            pageList.remove(page);
        }
    }

    /**
     * Deregisters all pages from the list.
     */
    public static void deregisterAll() {
        LOGGER.debug("Deregistering all pages");
        if (!pageList.isEmpty()) for (Page page : pageList) deregisterPage(page);
    }

    /**
     * Searches for a page and returns it back if it was found.
     * @param page the page to find in the list
     * @return the page if found
     */
    public static Page getPage(Page page) {
        Page res = null;
        for (Page p : pageList) {
            if (p.equals(page)) res = p;
        }
        return res;
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
