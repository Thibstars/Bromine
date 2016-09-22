package pages;

import org.openqa.selenium.WebElement;

/**
 * Representation of a section on a page. This could perfectly be a separate feature.
 * A section could also be reused on different pages.
 *
 * @author Thibault Helsmoortel
 */
public abstract class Section {

    private String name;
    private WebElement root;

    /**
     * Class constructor specifying the section's name.
     * @param name the section's name
     */
    public Section(String name) {
        this.name = name;
    }

    /**
     * Class constructor specifying the section's name and root element.
     * @param name the section's name
     * @param root the section's root element
     */
    public Section(String name, WebElement root) {
        this.name = name;
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebElement getRoot() {
        return root;
    }

    public void setRoot(WebElement root) {
        this.root = root;
    }
}
