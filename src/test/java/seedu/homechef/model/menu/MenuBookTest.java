package seedu.homechef.model.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalMenuItems.BIRTHDAY;
import static seedu.homechef.testutil.TypicalMenuItems.CUPCAKES;
import static seedu.homechef.testutil.TypicalMenuItems.THREETIER;
import static seedu.homechef.testutil.TypicalMenuItems.WEDDING;
import static seedu.homechef.testutil.TypicalMenuItems.getTypicalMenuBook;

import java.util.List;
import java.util.Optional;

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

        menuBook.addMenuItem(BIRTHDAY);
        menuBook.resetData(replacement);

        // EP: non-empty replacement data overwrites existing contents
        assertEquals(replacement, menuBook);
    }

    @Test
    public void hasMenuItem_existingItem_returnsTrue() {
        MenuBook menuBook = new MenuBook();
        menuBook.addMenuItem(BIRTHDAY);

        // EP: existing menu item
        assertTrue(menuBook.hasMenuItem(BIRTHDAY));
    }

    @Test
    public void hasMenuItem_missingItem_returnsFalse() {
        MenuBook menuBook = new MenuBook();
        menuBook.addMenuItem(BIRTHDAY);

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
        menuBook.addMenuItem(BIRTHDAY);

        menuBook.removeMenuItem(BIRTHDAY);

        // EP: remove existing menu item
        assertFalse(menuBook.hasMenuItem(BIRTHDAY));
    }

    @Test
    public void findExact_exactName_returnsItem() {
        MenuBook menuBook = getTypicalMenuBook();

        // EP: exact name match (canonical casing)
        Optional<MenuItem> result = menuBook.findExact("Birthday Cake");
        assertTrue(result.isPresent());
        assertEquals(BIRTHDAY, result.get());
    }

    @Test
    public void findExact_caseInsensitive_returnsItem() {
        MenuBook menuBook = getTypicalMenuBook();

        // EP: exact name match with different casing
        Optional<MenuItem> result = menuBook.findExact("birthday cake");
        assertTrue(result.isPresent());
        assertEquals(BIRTHDAY, result.get());
    }

    @Test
    public void findExact_nonExistent_returnsEmpty() {
        MenuBook menuBook = getTypicalMenuBook();

        // EP: name not in menu
        Optional<MenuItem> result = menuBook.findExact("Nonexistent Item");
        assertFalse(result.isPresent());
    }

    @Test
    public void findExact_partialName_returnsEmpty() {
        MenuBook menuBook = getTypicalMenuBook();

        // EP: substring of a menu item name is not an exact match
        Optional<MenuItem> result = menuBook.findExact("Cake");
        assertFalse(result.isPresent());
    }

    @Test
    public void findBySubstring_matchingSubstring_returnsMatchingItems() {
        MenuBook menuBook = getTypicalMenuBook();

        // EP: "Cake" is a substring of "Birthday Cake", "Cupcakes (24pcs)", "Wedding Cake - 3 Tier", "Wedding Cake"
        List<MenuItem> results = menuBook.findBySubstring("Cake");
        assertTrue(results.contains(BIRTHDAY));
        assertTrue(results.contains(CUPCAKES));
        assertTrue(results.contains(THREETIER));
        assertTrue(results.contains(WEDDING));
    }

    @Test
    public void findBySubstring_exactName_returnsSingleItem() {
        MenuBook menuBook = getTypicalMenuBook();

        // EP: exact name is also a valid substring match
        List<MenuItem> results = menuBook.findBySubstring("Birthday Cake");
        assertEquals(1, results.size());
        assertEquals(BIRTHDAY, results.get(0));
    }

    @Test
    public void findBySubstring_noMatch_returnsEmptyList() {
        MenuBook menuBook = getTypicalMenuBook();

        // EP: no items contain the substring
        List<MenuItem> results = menuBook.findBySubstring("Nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    public void equals() {
        MenuBook menuBook = getTypicalMenuBook();
        MenuBook sameMenuBook = new MenuBook(menuBook);
        MenuBook differentMenuBook = new MenuBook();
        differentMenuBook.addMenuItem(BIRTHDAY);

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
        menuBook.addMenuItem(BIRTHDAY);

        String expected = MenuBook.class.getCanonicalName()
                + "{menuItems=[" + BIRTHDAY + "]}";

        // EP: string representation with populated menu items
        assertEquals(expected, menuBook.toString());
    }
}
