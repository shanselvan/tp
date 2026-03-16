package seedu.homechef.model.order;

/**
 * Represents an Order's payment status in the HomeChef.
 * Guarantees: immutable; status is non-null
 */
public class PaymentStatus {

    public final boolean status;

    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_UNPAID = "UNPAID";

    public static final String MESSAGE_CONSTRAINTS =
            "Payment status should either be PAID or UNPAID";

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

    public static boolean isValidStatus(String test) {
        return STATUS_PAID.equals(test) || STATUS_UNPAID.equals(test);
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(status);
    }

}
