package pages;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a section on a page. This could perfectly be a separate feature.
 * A section could also be reused on different pages.
 *
 * @author Thibault Helsmoortel
 */
public abstract class Section {

    private String name;
    private WebElement root;
    private List<Section> subSections;

    /**
     * Class constructor specifying the section's name.
     *
     * @param name the section's name
     */
    public Section(String name) {
        this.name = name;
        this.subSections = new ArrayList<>();
    }

    /**
     * Class constructor specifying the section's name and root element.
     *
     * @param name the section's name
     * @param root the section's root element
     */
    public Section(String name, WebElement root) {
        this.name = name;
        this.root = root;
        this.subSections = new ArrayList<>();
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

    /**
     * Adds a specified sub section to this section.
     *
     * @param section the section to add to this section
     */
    public void addSubSection(Section section) {
        subSections.add(section);
    }

    /**
     * Removes a specified section from this section's sub sections.
     *
     * @param section the section to remove from this section
     */
    public void removeSubSection(Section section) {
        subSections.remove(section);
    }

    /**
     * Returns the list of this section's sub sections.
     *
     * @return the list of this section's sub sections
     */
    public List<Section> getSubSections() {
        return subSections;
    }
}
