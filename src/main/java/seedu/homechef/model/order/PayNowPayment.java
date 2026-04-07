package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a PayNow payment.
 */
public final class PayNowPayment implements PaymentInfo {
    public static final String MESSAGE_INVALID_PAYNOW_HANDLE =
            "PayNow payment requires a non-blank phone number or handle.";

    private final String handle;

    /**
     *  Creates a PayNow payment with the specified handle.
     * @param handle
     */
    public PayNowPayment(String handle) {
        requireNonNull(handle);
        if (handle.isBlank()) {
            throw new IllegalArgumentException(MESSAGE_INVALID_PAYNOW_HANDLE);
        }
        this.handle = handle;
    }

    @Override
    public String getHandle() {
        return handle;
    }

    @Override
    public String getMethod() {
        return METHOD_PAYNOW;
    }

    @Override
    public String toString() {
        return "PAYNOW (handle: " + handle + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PayNowPayment)) {
            return false;
        }
        PayNowPayment otherPayment = (PayNowPayment) other;
        return Objects.equals(handle, otherPayment.handle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(METHOD_PAYNOW, handle);
    }
}
