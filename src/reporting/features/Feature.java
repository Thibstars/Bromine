package reporting.features;

import reporting.cases.Case;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representation of features.
 * A feature holds a set of cases and possibly a set of sub features.
 *
 * @author Thibault Helsmoortel
 */
public class Feature {

    private String title;
    private List<Case> cases;
    private List<Feature> features;

    /**
     * Class constructor specifying the title.
     *
     * @param title the title
     */
    public Feature(String title) {
        this.title = title;
        this.cases = new ArrayList<>();
        this.features = new ArrayList<>();
    }

    /**
     * Adds a case to this feature.
     *
     * @param c the case to add
     * @return true if the case was added, false if otherwise
     */
    public boolean addCase(Case c) {
        return cases.add(c);
    }

    /**
     * Adds a sub feature to this feature.
     *
     * @param feature the sub feature to add
     * @return true if the case was added, false if otherwise
     * @throws IllegalArgumentException thrown when a feature is added to itself
     */
    public boolean addFeature(Feature feature) {
        if (feature.equals(this)) throw  new IllegalArgumentException("Feature cannot be a sub feature of its own.");
        return features.add(feature);
    }

    /**
     * Returns this feature's cases.
     *
     * @return this feature's cases
     */
    public List<Case> getCases() {
        return cases;
    }

    /**
     * Returns this feature's sub features.
     *
     * @return this feature's sub features.
     */
    public List<Feature> getFeatures() {
        return features;
    }

    /**
     * Returns a String representation of this feature.
     *
     * @return a String representation of this feature
     */
    @Override
    public String toString() {
        return title;
    }
}
