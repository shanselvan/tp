package seedu.homechef.model.menu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;

public class MenuItemTest {

    private static final Food NAME_CHICKEN = new Food("Chicken Rice");
    private static final Food NAME_NASI = new Food("Nasi Goreng");
    private static final Price PRICE_5 = new Price("5.50");
    private static final Price PRICE_8 = new Price("8.00");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MenuItem(null, PRICE_5, Availability.YES));
        assertThrows(NullPointerException.class, () -> new MenuItem(NAME_CHICKEN, null, Availability.YES));
    }

    @Test
    public void isSameMenuItem_sameName_returnsTrue() {
        MenuItem item = new MenuItem(NAME_CHICKEN, PRICE_5, Availability.YES);
        MenuItem sameNameDiffPrice = new MenuItem(NAME_CHICKEN, PRICE_8, Availability.NO);
        assertTrue(item.isSameMenuItem(sameNameDiffPrice));
    }

    @Test
    public void isSameMenuItem_differentName_returnsFalse() {
        MenuItem item = new MenuItem(NAME_CHICKEN, PRICE_5, Availability.YES);
        MenuItem different = new MenuItem(NAME_NASI, PRICE_5, Availability.YES);
        assertFalse(item.isSameMenuItem(different));
    }

    @Test
    public void isSameMenuItem_caseInsensitive_returnsTrue() {
        MenuItem lower = new MenuItem(new Food("chicken rice"), PRICE_5, Availability.YES);
        MenuItem upper = new MenuItem(new Food("Chicken Rice"), PRICE_8, Availability.NO);
        assertTrue(lower.isSameMenuItem(upper));
    }

    @Test
    public void equals_allFieldsSame_returnsTrue() {
        MenuItem a = new MenuItem(NAME_CHICKEN, PRICE_5, Availability.YES);
        MenuItem b = new MenuItem(NAME_CHICKEN, PRICE_5, Availability.YES);
        assertTrue(a.equals(b));
    }

    @Test
    public void equals_differentAvailability_returnsFalse() {
        MenuItem a = new MenuItem(NAME_CHICKEN, PRICE_5, Availability.YES);
        MenuItem b = new MenuItem(NAME_CHICKEN, PRICE_5, Availability.NO);
        assertFalse(a.equals(b));
    }

    @Test
    public void equals_differentPrice_returnsFalse() {
        MenuItem a = new MenuItem(NAME_CHICKEN, PRICE_5, Availability.YES);
        MenuItem b = new MenuItem(NAME_CHICKEN, PRICE_8, Availability.YES);
        assertFalse(a.equals(b));
    }
}
