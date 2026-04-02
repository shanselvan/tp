package seedu.homechef.model.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Price(""));
        assertThrows(IllegalArgumentException.class, () -> new Price("0"));
        assertThrows(IllegalArgumentException.class, () -> new Price("0.00"));
        assertThrows(IllegalArgumentException.class, () -> new Price("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Price("abc"));
        assertThrows(IllegalArgumentException.class, () -> new Price("1.999"));
        assertThrows(IllegalArgumentException.class, () -> new Price("1."));
        assertThrows(IllegalArgumentException.class, () -> new Price(".50"));
    }

    @Test
    public void isValidPrice() {
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        assertFalse(Price.isValidPrice(""));
        assertFalse(Price.isValidPrice("0"));
        assertFalse(Price.isValidPrice("0.00"));
        assertFalse(Price.isValidPrice("-5"));
        assertFalse(Price.isValidPrice("abc"));
        assertFalse(Price.isValidPrice("1.999"));

        assertTrue(Price.isValidPrice("0.01"));
        assertTrue(Price.isValidPrice("1"));
        assertTrue(Price.isValidPrice("5.50"));
        assertTrue(Price.isValidPrice("100"));
        assertTrue(Price.isValidPrice("1.5"));
        assertTrue(Price.isValidPrice("99.99"));
    }

    @Test
    public void equals() {
        Price price = new Price("5.50");
        assertTrue(price.equals(new Price("5.50")));
        assertTrue(price.equals(new Price("5.5")));
        assertTrue(price.equals(price));
        assertFalse(price.equals(null));
        assertFalse(price.equals(new Price("3.00")));
    }

    @Test
    public void constructor_validPriceWithOneDecimal_normalizesToTwoDecimalPlaces() {
        // EP: valid one-decimal input should be accepted and normalized for display consistency.
        assertEquals("5.50", new Price("5.5").value);
    }

    @Test
    public void constructor_validIntegerPrice_normalizesToTwoDecimalPlaces() {
        // EP: valid integer input should be accepted and normalized for display consistency.
        assertEquals("12.00", new Price("12").value);
    }
}
