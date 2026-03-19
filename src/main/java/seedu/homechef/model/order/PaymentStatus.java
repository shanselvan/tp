package seedu.homechef.model.order;

/**
 * Represents an Order's payment status in the HomeChef.
 * Guarantees: immutable; status is non-null
 */
public class PaymentStatus {

    public static final String STATUS_PAID = "$ PAID";
    public static final String STATUS_UNPAID = "$ UNPAID";

    public static final boolean IS_PAID = true;
    public static final boolean IS_UNPAID = false;

    public static final String STYLE_PAID = "-fx-text-fill: limegreen;";
    public static final String STYLE_UNPAID = "-fx-text-fill: orange;";

    public static final String MESSAGE_CONSTRAINTS =
            "Payment status should either be $ PAID or $ UNPAID";

    public final boolean status;

    /**
     * Constructs a {@code PaymentStatus}.
     */
    public PaymentStatus(boolean status) {
        this.status = status;
    }

    /**
     * Returns true if this order has been paid for.
     */
    public boolean isPaid() {
        return status;
    }

    @Override
    public String toString() {
        return status ? STATUS_PAID : STATUS_UNPAID;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PaymentStatus)) {
            return false;
        }

        PaymentStatus otherStatus = (PaymentStatus) other;
        return status == otherStatus.status;
    }

    /**
     * Returns if a given string is a valid payment status.
     */
    public static boolean isValidStatus(String test) {
        return STATUS_PAID.equals(test) || STATUS_UNPAID.equals(test);
    }

    /**
     * Returns the CSS style string representing the color of the payment status.
     * Paid statuses are styled in green, while unpaid statuses are styled in red.
     *
     * @return a CSS style string for the JavaFX Label
     */
    public String getStyle() {
        return status ? STYLE_PAID : STYLE_UNPAID;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(status);
    }

}
