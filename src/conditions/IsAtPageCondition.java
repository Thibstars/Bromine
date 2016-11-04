package conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import pages.Page;

/**
 * ExpectedCondition evaluating whether the Navigator is at a given page.
 *
 * @author Thibault Helsmoortel
 */
public class IsAtPageCondition implements ExpectedCondition<Boolean> {

    private Page page;

    /**
     * Class constructor specifying the page to evaluate.
     *
     * @param page the page to evaluate
     */
    public IsAtPageCondition(Page page) {
        this.page = page;
    }

    @Override
    public Boolean apply(WebDriver webDriver) {
        return page.isAt();
    }
}
