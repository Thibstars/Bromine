package reporting;

import ru.yandex.qatools.allure.annotations.Step;

/**
 * Class responsible for performing Allure steps.
 *
 * @author Thibault Helsmoortel
 */
public final class StepPerformer {

    //TODO make sure correct time spans are used (invoking perform takes only 0-1 ms)...

    /**
     * Performs an Allure Step using action and description.
     *
     * @param action the action
     * @param description the description
     */
    @Step("{0}: {1}")
    public static void perform(String action, String description){}

    /**
     * Performs an Allure Step using action.
     *
     * @param action the action
     */
    @Step("{0}")
    public static void perform(String action){}
}
