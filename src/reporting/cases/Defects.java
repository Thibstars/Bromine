package reporting.cases;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a list of failed and broken cases.
 * <p>
 * Failed cases should contain cases with AssertionErrors (product logic defect).
 * Broken cases should contain cases with Exceptions (broken test logic).
 *
 * @author Thibault Helsmoortel
 */
public final class Defects {

    private static List<Case> failedDefects = new ArrayList<>();
    private static List<Case> brokenDefects = new ArrayList<>();

    /**
     * Adds a case to the list of failed cases.
     *
     * @param failed the case to add
     * @return true if the case is added, false if otherwise
     * @throws IllegalArgumentException when the case status is incorrect
     */
    public static boolean addFailed(Case failed) {
        if (!failed.getStatus().equals(CaseStatus.FAILED)) throw new IllegalArgumentException("Incorrect case status");
        return failedDefects.add(failed);
    }

    /**
     * Removes a case from the list of failed cases.
     *
     * @param failed the case to remove
     * @return true if the case is removed, false if otherwise
     * @throws IllegalArgumentException when the case status is incorrect
     */
    public static boolean removeFailed(Case failed) {
        if (failed.getStatus().equals(CaseStatus.FAILED)) throw new IllegalArgumentException("Incorrect case status");
        return failedDefects.remove(failed);
    }

    /**
     * Adds a case to the list of broken cases.
     *
     * @param broken the case to add
     * @return true if the case is added, false if otherwise
     * @throws IllegalArgumentException when the case status is incorrect
     */
    public static boolean addBroken(Case broken) {
        if (!broken.getStatus().equals(CaseStatus.BROKEN)) throw new IllegalArgumentException("Incorrect case status");
        return brokenDefects.add(broken);
    }

    /**
     * Removes a case from the list of broken cases.
     *
     * @param broken the case to remove
     * @return true if the case is removed, false if otherwise
     * @throws IllegalArgumentException when the case status is incorrect
     */
    public static boolean removeBroken(Case broken) {
        if (broken.getStatus().equals(CaseStatus.BROKEN)) throw new IllegalArgumentException("Incorrect case status");
        return brokenDefects.remove(broken);
    }

    /**
     * Returns the list of failed defects.
     *
     * @return the list of failed defects.
     */
    public static List<Case> getFailedDefects() {
        return failedDefects;
    }

    /**
     * Returns the list of broken defects.
     *
     * @return the list of broken defects.
     */
    public static List<Case> getBrokenDefects() {
        return brokenDefects;
    }
}
