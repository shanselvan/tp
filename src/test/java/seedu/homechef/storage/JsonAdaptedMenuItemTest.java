package seedu.homechef.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.MenuItemName;
import seedu.homechef.model.menu.Price;

public class JsonAdaptedMenuItemTest {

    @Test
    public void toModelType_validFields_success() throws Exception {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", "5.50", true);
        MenuItem expected = new MenuItem(new MenuItemName("Chicken Rice"), new Price("5.50"), true);
        assertEquals(expected, adapted.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem(null, "5.50", true);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("", "5.50", true);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", null, true);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", "0", true);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_nullAvailable_defaultsToTrue() throws Exception {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", "5.50", null);
        MenuItem result = adapted.toModelType();
        assertEquals(true, result.isAvailable());
    }
}
