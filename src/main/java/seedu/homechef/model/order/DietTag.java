package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents a DietTag in the HomeChef.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class DietTag {

    public static final String MESSAGE_CONSTRAINTS =
            "Tag names must start and end with a letter or digit, "
            + "and may contain single spaces or hyphens between words (e.g. gluten-free, no peanuts).";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+(?:[ -]\\p{Alnum}+)*";

    private final String tagName;

    /**
     * Constructs a {@code DietTag}.
     *
     * @param tagName A valid tag name.
     */
    public DietTag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the other object is a {@code DietTag} with the same tag name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DietTag)) {
            return false;
        }

        DietTag otherDietTag = (DietTag) other;
        return tagName.equals(otherDietTag.tagName);
    }

    /**
     * Returns the hash code for this diet tag.
     */
    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return tagName;
    }

}
