package seedu.homechef.model.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalMenuItems.BIRTHDAY_CAKE;
import static seedu.homechef.testutil.TypicalMenuItems.CUPCAKES;
import static seedu.homechef.testutil.TypicalMenuItems.getTypicalMenuBook;

import org.junit.jupiter.api.Test;

public class MenuBookTest {

    @Test
    public void constructor_readOnlyMenuBook_copiesData() {
        MenuBook source = getTypicalMenuBook();
        MenuBook copy = new MenuBook(source);

        // EP: copy constructor with populated menu book
        assertEquals(source, copy);
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        MenuBook menuBook = new MenuBook();

        // EP: null read-only menu book
        assertThrows(NullPointerException.class, () -> menuBook.resetData(null));
    }

    @Test
    public void resetData_withReadOnlyMenuBook_replacesContents() {
        MenuBook menuBook = new MenuBook();
        MenuBook replacement = getTypicalMenuBook();

        menuBook.addMenuItem(BIRTHDAY_CAKE);
        menuBook.resetData(replacement);

        // EP: non-empty replacement data overwrites existing contents
        assertEquals(replacement, menuBook);
    }

    @Test
    public void hasMenuItem_existingItem_returnsTrue() {
        MenuBook menuBook = new MenuBook();
        menuBook.addMenuItem(BIRTHDAY_CAKE);

        // EP: existing menu item
        assertTrue(menuBook.hasMenuItem(BIRTHDAY_CAKE));
    }

    @Test
    public void hasMenuItem_missingItem_returnsFalse() {
        MenuBook menuBook = new MenuBook();
        menuBook.addMenuItem(BIRTHDAY_CAKE);

        // EP: missing menu item
        assertFalse(menuBook.hasMenuItem(CUPCAKES));
    }

    @Test
    public void hasMenuItem_null_throwsNullPointerException() {
        MenuBook menuBook = new MenuBook();

        // EP: null menu item
        assertThrows(NullPointerException.class, () -> menuBook.hasMenuItem(null));
    }

    @Test
    public void removeMenuItem_existingItem_success() {
        MenuBook menuBook = new MenuBook();
        menuBook.addMenuItem(BIRTHDAY_CAKE);

        menuBook.removeMenuItem(BIRTHDAY_CAKE);

        // EP: remove existing menu item
        assertFalse(menuBook.hasMenuItem(BIRTHDAY_CAKE));
    }

    @Test
    public void equals() {
        MenuBook menuBook = getTypicalMenuBook();
        MenuBook sameMenuBook = new MenuBook(menuBook);
        MenuBook differentMenuBook = new MenuBook();
        differentMenuBook.addMenuItem(BIRTHDAY_CAKE);

        // EP: same object -> returns true
        assertTrue(menuBook.equals(menuBook));
        // EP: same values -> returns true
        assertTrue(menuBook.equals(sameMenuBook));
        // EP: different values -> returns false
        assertFalse(menuBook.equals(differentMenuBook));
        // EP: null -> returns false
        assertFalse(menuBook.equals(null));
        // EP: different types -> returns false
        assertFalse(menuBook.equals(1));
    }

    @Test
    public void toStringMethod() {
        MenuBook menuBook = new MenuBook();
        menuBook.addMenuItem(BIRTHDAY_CAKE);

        String expected = MenuBook.class.getCanonicalName()
                + "{menuItems=[" + BIRTHDAY_CAKE + "]}";

        // EP: string representation with populated menu items
        assertEquals(expected, menuBook.toString());
    }
}
