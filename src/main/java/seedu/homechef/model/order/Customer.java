package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's customer name in the HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidCustomer(String)}
 */
public class Customer {

    public static final String MESSAGE_CONSTRAINTS =
            "Customer names should only contain alphanumeric characters and spaces, and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Customer(String name) {
        requireNonNull(name);
        checkArgument(isValidCustomer(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid Customer name.
     */
    public static boolean isValidCustomer(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return fullName.equals(otherCustomer.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
