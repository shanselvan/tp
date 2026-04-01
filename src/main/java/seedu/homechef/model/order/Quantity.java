package seedu.homechef.model.order;

/**
 * Represents an Order's quantity in HomeChef Helper.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {

    public static final String MESSAGE_CONSTRAINTS =
            "Quantity must be a positive integer between 1 and 999.";

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 999;

    public final int value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param value A valid quantity integer.
     * @throws IllegalArgumentException if value is not between 1 and 999 inclusive.
     */
    public Quantity(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = value;
    }

    /**
     * Returns true if the given string represents a valid quantity.
     * A valid quantity is an integer in the range 1 to 999 inclusive.
     * Returns false for non-numeric strings rather than throwing an exception.
     *
     * @param test The string to validate.
     * @return true if {@code test} parses to an integer between 1 and 999.
     */
    public static boolean isValidQuantity(String test) {
        try {
            int parsed = Integer.parseInt(test);
            return parsed >= MIN_VALUE && parsed <= MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Quantity)) {
            return false;
        }

        Quantity otherQuantity = (Quantity) other;
        return value == otherQuantity.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
