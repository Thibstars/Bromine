package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a table element.
 *
 * @author Thibault Helsmoortel
 */
public class Table extends ElementImpl {

    /**
     * Class constructor specifying the actual element.
     *
     * @param element the actual element
     */
    public Table(WebElement element) {
        super(element);
    }

    /**
     * Returns the amount of rows.
     *
     * @return the amount of rows
     */
    public int getRowCount() {
        return getRows().size();
    }

    /**
     * Returns the amount of columns.
     *
     * @return the amount of columns
     */
    public int getColumnCount() {

        return findElement(By.cssSelector("tr")).findElements(
                By.cssSelector("*")).size();
    }

    /**
     * Returns the cell at the given indexes
     *
     * @param rowIndex the index of the row
     * @param colIndex the index of the column
     * @return the cell at the given indexes
     */
    public WebElement getCellAtIndex(int rowIndex, int colIndex) {
        //Get the row at the specified index
        WebElement row = getRows().get(rowIndex);

        List<WebElement> cells;

        //Cells are most likely to be td tags
        if (0 < (cells = row.findElements(By.tagName("td"))).size()) {
            return cells.get(colIndex);
        }
        //Failing that try th tags
        else if ((cells = row.findElements(By.tagName("th"))).size() > 0) {
            return cells.get(colIndex);
        } else {
            final String error = String
                    .format("Could not find cell at row: %s column: %s",
                            rowIndex, colIndex);
            throw new RuntimeException(error);
        }
    }

    /**
     * Returns all rows in the table in order header > body > footer.
     *
     * @return list of row WebElements
     */
    private List<WebElement> getRows() {
        List<WebElement> rows = new ArrayList<>();

        //Header rows
        rows.addAll(findElements(By.cssSelector("thead tr")));

        //Body rows
        rows.addAll(findElements(By.cssSelector("tbody tr")));

        //Footer rows
        rows.addAll(findElements(By.cssSelector("tfoot tr")));

        return rows;
    }
}
