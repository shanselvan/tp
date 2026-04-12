package seedu.homechef.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;

public class JsonAdaptedMenuItemTest {

    @Test
    public void toModelType_validFields_success() throws Exception {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", "5.50", "Yes");
        MenuItem expected = new MenuItem(new Food("Chicken Rice"), new Price("5.50"), Availability.YES);
        assertEquals(expected, adapted.toModelType());
    }

    @Test
    public void toModelType_zeroPrice_success() throws Exception {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Free Sample", "0", "Yes");
        MenuItem expected = new MenuItem(new Food("Free Sample"), new Price("0.00"), Availability.YES);
        assertEquals(expected, adapted.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem(null, "5.50", "Yes");
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("", "5.50", "Yes");
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", null, "Yes");
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", "00.50", "Yes");
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_nullAvailability_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", "5.50", null);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidAvailability_throwsIllegalValueException() {
        JsonAdaptedMenuItem adapted = new JsonAdaptedMenuItem("Chicken Rice", "5.50", "maybe");
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }
}
