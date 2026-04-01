package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents an Order's price in the HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS =
            "Price must be a positive decimal with at most 2 decimal places (e.g. 5.50, 12, 0.01)";

    /**
     * Validation regex for a positive decimal number with up to 2 decimal places.
     * Accepts values >= 0.01 and integers >= 1.
     * Examples of valid inputs: 1, 3.5, 3.50, 0.01, 0.99
     * Examples of invalid inputs: 0, 00.5, 1.234, .5, 5.
     */
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

    /**
     * Returns a new {@code Price} whose value is {@code unitPrice} multiplied by {@code qty}.
     *
     * @param unitPrice the per-item price.
     * @param qty       the number of items.
     * @return a {@code Price} representing the total cost.
     */
    public static Price multiply(Price unitPrice, Quantity qty) {
        requireNonNull(unitPrice);
        requireNonNull(qty);
        BigDecimal total = new BigDecimal(unitPrice.value).multiply(BigDecimal.valueOf(qty.value));
        return new Price(total.setScale(2, RoundingMode.UNNECESSARY).toPlainString());
    }

    private static String formatToTwoDecimalPlaces(String price) {
        return new BigDecimal(price).setScale(2, RoundingMode.UNNECESSARY).toPlainString();
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
