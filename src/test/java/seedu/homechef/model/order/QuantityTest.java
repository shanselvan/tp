package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuantityTest {

    @Test
    public void constructor_validValues_success() {
        // EP: valud values
        // boundary: minimum allowed
        new Quantity(1);
        // boundary: maximum allowed
        new Quantity(999);
        // mid-range
        new Quantity(5);
    }

    @Test
    public void constructor_zero_throwsIllegalArgumentException() {
        // EP: invalid value
        assertThrows(IllegalArgumentException.class, () -> new Quantity(0));
    }

    @Test
    public void constructor_negative_throwsIllegalArgumentException() {
        // EP: invalid value
        assertThrows(IllegalArgumentException.class, () -> new Quantity(-1));
    }

    @Test
    public void constructor_exceedsMax_throwsIllegalArgumentException() {
        // EP: invalid value
        assertThrows(IllegalArgumentException.class, () -> new Quantity(1000));
    }

    @Test
    public void isValidQuantity_validStrings_returnsTrue() {
        // EP: valid value
        assertTrue(Quantity.isValidQuantity("1"));
        assertTrue(Quantity.isValidQuantity("5"));
        assertTrue(Quantity.isValidQuantity("999"));
    }

    @Test
    public void isValidQuantity_invalidStrings_returnsFalse() {
        // EP: invalid value
        assertFalse(Quantity.isValidQuantity("0")); // zero value, boundary value
        assertFalse(Quantity.isValidQuantity("-1"));
        assertFalse(Quantity.isValidQuantity("abc"));
        assertFalse(Quantity.isValidQuantity("")); // empty string, boundary value
        assertFalse(Quantity.isValidQuantity("1.5"));
        assertFalse(Quantity.isValidQuantity("1000"));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Quantity quantity = new Quantity(5);

        assertTrue(quantity.equals(new Quantity(5)));
        assertTrue(quantity.equals(quantity));
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Quantity quantity = new Quantity(5);

        assertFalse(quantity.equals(new Quantity(6)));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(new Quantity(1).equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(new Quantity(1).equals("1"));
    }

    @Test
    public void toString_returnsValueAsString() {
        // EP: equal string
        assertEquals("1", new Quantity(1).toString());
        assertEquals("999", new Quantity(999).toString());
        assertEquals("42", new Quantity(42).toString());
    }
}
