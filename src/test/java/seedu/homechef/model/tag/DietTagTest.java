package seedu.homechef.model.tag;

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
        assertThrows(IllegalArgumentException.class, () -> new DietTag(invalidTagName)); // empty string, boundary value
    }


    @Test
    public void isValidTagName() {
        assertThrows(NullPointerException.class, () -> DietTag.isValidTagName(null));
    }
}
