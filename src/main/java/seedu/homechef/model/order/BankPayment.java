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
    public static final String MESSAGE_INVALID_REFERENCE = MESSAGE_CONSTRAINTS;

    private static final int MAX_REFERENCE_LENGTH = 50;
    private static final Pattern ALLOWED_CHARACTERS =
            Pattern.compile("^[A-Za-z0-9\\s\\-_/().,:+&@#'\\[\\]]+$");
    private static final Pattern HAS_ALPHANUMERIC = Pattern.compile(".*[A-Za-z0-9].*");
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
        return !test.isEmpty()
                && test.length() <= MAX_REFERENCE_LENGTH
                && ALLOWED_CHARACTERS.matcher(test).matches()
                && HAS_ALPHANUMERIC.matcher(test).matches();
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return "Bank: " + reference;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(BankPayment.class, reference);
    }
}
