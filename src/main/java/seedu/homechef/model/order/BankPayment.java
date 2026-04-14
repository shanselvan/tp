package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a bank transfer payment.
 */
public final class BankPayment implements PaymentInfo {
    public static final String MESSAGE_CONSTRAINTS =
            "Bank payment requires 1-50 characters, at least one letter or digit, and only supported symbols"
                    + " (space, -_/().,:+&@#'[]).";
    private static final Pattern VALID_REFERENCE =
            Pattern.compile("^(?=.*[A-Za-z0-9])[A-Za-z0-9\\s\\-_/().,:+&@#'\\[\\]]{1,50}$");
    private final String reference;

    /**
     * Creates a bank transfer payment with the specified reference.
     *
     * @param reference Bank transfer reference.
     */
    public BankPayment(String reference) {
        requireNonNull(reference);
        String trimmedReference = reference.trim();
        checkArgument(isValidBankPayment(trimmedReference), MESSAGE_CONSTRAINTS);
        this.reference = trimmedReference;
    }

    /**
     * Returns true if the supplied bank payment reference is valid.
     */
    public static boolean isValidBankPayment(String test) {
        return VALID_REFERENCE.matcher(test).matches();
    }

    /**
     * Returns the bank transfer reference.
     */
    @Override
    public String getReference() {
        return reference;
    }

    /**
     * Returns a human-readable string representation of this payment.
     */
    @Override
    public String toString() {
        return "Bank: " + reference;
    }

    /**
     * Returns true if the other object is a {@code BankPayment} with the same reference.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BankPayment)) {
            return false;
        }
        BankPayment otherPayment = (BankPayment) other;
        return reference.equals(otherPayment.reference);
    }

    /**
     * Returns the hash code for this bank payment.
     */
    @Override
    public int hashCode() {
        return Objects.hash(BankPayment.class, reference);
    }
}
