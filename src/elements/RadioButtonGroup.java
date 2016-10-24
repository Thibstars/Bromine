package elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class representing a group of radio buttons.
 *
 * @author Thibault Helsmoortel
 */
public class RadioButtonGroup {

    private String name;
    private List<RadioButton> radioButtons;

    /**
     * Class constructor specifying the name.
     *
     * @param name the name of the radio button group
     */
    public RadioButtonGroup(String name) {
        this.name = name;
        this.radioButtons = new ArrayList<>();
    }

    /**
     * Checks a specified radio button, if it isn't already.
     *
     * @param radioButton the radio button to check
     */
    public void check(RadioButton radioButton) {
        if (!radioButton.isChecked()) radioButton.check();
    }

    /**
     * Checks a radio button at the specified index, if it isn't already.
     *
     * @param index the index of the radio button to check
     */
    public void check(int index) {
        check(radioButtons.get(index));
    }

    public boolean addButton(RadioButton radioButton) {
        return radioButtons.add(radioButton);
    }

    public boolean removeButton(Object o) {
        return radioButtons.remove(o);
    }

    public boolean addAllButtons(Collection<? extends RadioButton> c) {
        return radioButtons.addAll(c);
    }

    public boolean removeAllButtons(Collection<?> c) {
        return radioButtons.removeAll(c);
    }
}
