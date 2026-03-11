package seedu.homechef.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DietTagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DietTag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new DietTag(invalidTagName));
    }


    @Test
    public void isValidTagName() {
        assertThrows(NullPointerException.class, () -> DietTag.isValidTagName(null));
        assertTrue(DietTag.isValidTagName("spicy"));
        assertTrue(DietTag.isValidTagName("dairy-free"));
        assertTrue(DietTag.isValidTagName("no peanuts"));
        assertTrue(DietTag.isValidTagName("a1 b2-c3"));
        assertFalse(DietTag.isValidTagName(""));
        assertFalse(DietTag.isValidTagName("-gluten"));
        assertFalse(DietTag.isValidTagName("gluten-"));
        assertFalse(DietTag.isValidTagName("no  peanuts"));
        assertFalse(DietTag.isValidTagName("no--peanuts"));
        assertFalse(DietTag.isValidTagName("no_peanuts"));
        assertFalse(DietTag.isValidTagName("peanuts!"));
    }
}
