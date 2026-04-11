package seedu.homechef.model.common;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Represents an Order's food name in HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidFood(String)}
 */
public class Food {

    public static final String MESSAGE_CONSTRAINTS =
            "Food name should only contain letters or digits (including international characters),"
                    + " spaces, apostrophes ('), typographic apostrophes, slashes (/),"
                    + " ampersands (&), commas (,), periods (.), plus signs (+), parentheses,"
                    + " square brackets, at signs (@), or hyphens (-), and should not be blank";

    /*
     * The first character of the food name must be a letter or digit,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{L}\\p{M}\\p{N}][\\p{L}\\p{M}\\p{N} ()\\[\\]'/&,.+@\\-\\u2019]*";

    private final String foodName;

    /**
     * Constructs a {@code Food}.
     *
     * @param name A valid food name.
     */
    public Food(String name) {
        requireNonNull(name);
        String normalizedName = Normalizer.normalize(name, Normalizer.Form.NFC);
        checkArgument(isValidFood(normalizedName), MESSAGE_CONSTRAINTS);
        foodName = normalizedName;
    }

    /**
     * Returns true if a given string is a valid food name.
     */
    public static boolean isValidFood(String test) {
        return Normalizer.normalize(test, Normalizer.Form.NFC).matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return foodName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Food)) {
            return false;
        }
        Food otherFood = (Food) other;
        return foodName.toLowerCase(Locale.ROOT).equals(otherFood.foodName.toLowerCase(Locale.ROOT));
    }

    /**
     * Returns true if this food name contains {@code other}, ignoring case.
     *
     * @param other String to search for within this food name.
     * @return True if this food name contains {@code other}; false otherwise.
     */
    public boolean nameContains(String other) {
        requireNonNull(other);
        String normalizedOther = Normalizer.normalize(other, Normalizer.Form.NFC);
        return foodName.toLowerCase(Locale.ROOT).contains(normalizedOther.toLowerCase(Locale.ROOT));
    }

    @Override
    public int hashCode() {
        return foodName.toLowerCase(Locale.ROOT).hashCode();
    }
}


