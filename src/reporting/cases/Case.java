package reporting.cases;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a test case.
 *
 * @author Thibault Helsmoortel
 */
public class Case {

    private final String title;
    private String errorTrace;
    private final CaseEnvironment environment;
    private final String description;
    private long duration;
    private List<URL> links;
    private List<CaseStep> steps;
    private List<File> attachments;
    private CaseStatus status;

    public Case(String title, CaseEnvironment environment, String description) {
        this.title = title;
        this.environment = environment;
        this.description = description;
        this.attachments = new ArrayList<>();
        this.links = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.status = CaseStatus.PENDING;
    }

    /**
     * Sets the error trace.
     *
     * @param errorTrace the new error trace.
     */
    public void setErrorTrace(String errorTrace) {
        this.errorTrace = errorTrace;
    }

    /**
     * Sets the status.
     * Adds this case to the defects list if failed or broken.
     * Removes this case if not failed or broken anymore.
     *
     * @param status the new status
     */
    public void setStatus(CaseStatus status) {
        this.status = status;
        if (status.equals(CaseStatus.FAILED)) Defects.addFailed(this);
        else if (status.equals(CaseStatus.BROKEN)) Defects.addBroken(this);
        else if (Defects.getFailedDefects().contains(this)) Defects.removeFailed(this);
        else if (Defects.getBrokenDefects().contains(this)) Defects.removeBroken(this);
    }

    /**
     * Adds a link to the list.
     *
     * @param url the link to add
     * @return true if the link was added, false if otherwise
     */
    public boolean addLink(URL url) {
        return links.add(url);
    }

    /**
     * Adds a case step to the list.
     *
     * @param step the step to add
     * @return true if the link was added, false if otherwise
     */
    public boolean addCaseStep(CaseStep step) {
        return steps.add(step);
    }

    /**
     * Adds an attachment to the list
     *
     * @param attachment the attachment to add
     * @return true if the link was added, false if otherwise
     */
    public boolean addAttachment(File attachment) {
        return attachments.add(attachment);
    }

    /**
     * Returns the title of this case.
     *
     * @return the title of this case
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the error trace of this case.
     *
     * @return the error trace of this case
     */
    public String getErrorTrace() {
        return errorTrace;
    }

    /**
     * Returns the environment of this case.
     *
     * @return the environment of this case
     */
    public CaseEnvironment getEnvironment() {
        return environment;
    }

    /**
     * Returns the description of this case.
     *
     * @return the description of this case
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the duration of this case.
     *
     * @return the duration of this case
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Sets the duration of this case.
     *
     * @param duration the duration of this case
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Returns the links of this case.
     *
     * @return the links of this case
     */
    public List<URL> getLinks() {
        return links;
    }

    /**
     * Returns the steps of this case.
     *
     * @return the steps of this case
     */
    public List<CaseStep> getCaseSteps() {
        return steps;
    }

    /**
     * Returns the attachments of this case.
     *
     * @return the attachments of this case
     */
    public List<File> getAttachments() {
        return attachments;
    }

    /**
     * Returns the status of this case.
     *
     * @return the status of this case
     */
    public CaseStatus getStatus() {
        return status;
    }
}
