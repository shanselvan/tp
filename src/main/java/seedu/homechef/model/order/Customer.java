package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Represents an Order's customer name in the HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidCustomer(String)}
 */
public class Customer {

    public static final String MESSAGE_CONSTRAINTS =
            "Customer names should only contain letters or digits (including international characters),"
                    + " spaces, apostrophes ('), typographic apostrophes, slashes (/), at signs (@),"
                    + " periods (.), or hyphens (-),"
                    + " and should not be blank.";

    /*
     * The first character of the customer name must be a letter or digit,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{L}\\p{M}\\p{N}][\\p{L}\\p{M}\\p{N} '/@.\\-\\u2019]*";

    private final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Customer(String name) {
        requireNonNull(name);
        String normalizedName = Normalizer.normalize(name, Normalizer.Form.NFC);
        checkArgument(isValidCustomer(normalizedName), MESSAGE_CONSTRAINTS);
        fullName = normalizedName;
    }

    /**
     * Returns true if a given string is a valid Customer name.
     */
    public static boolean isValidCustomer(String test) {
        return Normalizer.normalize(test, Normalizer.Form.NFC).matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return fullName.toLowerCase(Locale.ROOT).equals(otherCustomer.fullName.toLowerCase(Locale.ROOT));
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase(Locale.ROOT).hashCode();
    }

}
