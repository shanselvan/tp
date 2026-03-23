package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's price in the HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS =
            "Price must be a positive decimal with at most 2 decimal places (e.g. 5.50, 12, 0.01)";

    public static final String VALIDATION_REGEX =
            "^([1-9][0-9]*(\\.[0-9]{1,2})?|0\\.(0[1-9]|[1-9][0-9]?))$";

    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price string.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        this.value = formatToTwoDecimalPlaces(price);
    }

    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private static String formatToTwoDecimalPlaces(String price) {
        double parsedPrice = Double.parseDouble(price);
        return String.format("%.2f", parsedPrice);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Price)) {
            return false;
        }

        Price otherPrice = (Price) other;
        return value.equals(otherPrice.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
