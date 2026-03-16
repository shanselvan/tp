package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents the payment information for an Order in HomeChef.
 * Guarantees: immutable; fields are validated for the given PaymentType.
 */
public class PaymentInfo {

    public static final String MESSAGE_INVALID_PAYNOW =
            "PayNow payment requires a non-blank handle (phone number or UEN).";
    public static final String MESSAGE_INVALID_PAYNOW_PHONE =
            "PayNow phone number must separate the country code with a space (e.g. +65 91234567).";
    public static final String MESSAGE_INVALID_BANK =
            "Bank transfer payment requires a non-blank bank name and reference number.";
    public static final String MESSAGE_INVALID_CARD =
            "Card payment requires exactly 4 numeric digits for last four digits.";
    public static final String MESSAGE_INVALID_EWALLET =
            "E-Wallet payment requires a non-blank wallet provider and account ID.";
    public static final String MESSAGE_UNEXPECTED_FIELD =
            "Unexpected payment detail fields provided for payment type: %s";
    public static final String VALIDATION_REGEX_LAST_FOUR = "[0-9]{4}";
    public static final String PAYNOW_PHONE_REGEX = "\\+\\d{1,3} \\d{3,}";

    private final PaymentType type;
    private final String handle; // PAYNOW only
    private final String bankName; // BANK only
    private final String referenceNumber; // BANK only
    private final String lastFourDigits; // CARD only
    private final String walletProvider; // EWALLET only
    private final String walletAccountId; // EWALLET only

    /**
     * Constructs a {@code PaymentInfo}.
     * Null must be passed for fields that are not applicable to the given type.
     *
     * @throws IllegalArgumentException if required fields are missing or unexpected fields are set.
     */
    public PaymentInfo(PaymentType type, String handle, String bankName, String referenceNumber,
                       String lastFourDigits, String walletProvider, String walletAccountId) {
        requireNonNull(type);
        validate(type, handle, bankName, referenceNumber, lastFourDigits, walletProvider, walletAccountId);
        this.type = type;
        this.handle = handle;
        this.bankName = bankName;
        this.referenceNumber = referenceNumber;
        this.lastFourDigits = lastFourDigits;
        this.walletProvider = walletProvider;
        this.walletAccountId = walletAccountId;
    }

    private static void validate(PaymentType type, String handle, String bankName, String referenceNumber,
                                  String lastFourDigits, String walletProvider, String walletAccountId) {
        boolean hasHandle = handle != null;
        boolean hasBankName = bankName != null;
        boolean hasRef = referenceNumber != null;
        boolean hasLastFour = lastFourDigits != null;
        boolean hasWalletProvider = walletProvider != null;
        boolean hasWalletAccountId = walletAccountId != null;

        switch (type) {
        case CASH:
            checkArgument(!hasHandle && !hasBankName && !hasRef && !hasLastFour
                    && !hasWalletProvider && !hasWalletAccountId,
                    String.format(MESSAGE_UNEXPECTED_FIELD, "CASH"));
            break;
        case PAYNOW:
            checkArgument(hasHandle && !handle.isBlank(), MESSAGE_INVALID_PAYNOW);
            checkArgument(!handle.startsWith("+") || handle.matches(PAYNOW_PHONE_REGEX),
                    MESSAGE_INVALID_PAYNOW_PHONE);
            checkArgument(!hasBankName && !hasRef && !hasLastFour && !hasWalletProvider && !hasWalletAccountId,
                    String.format(MESSAGE_UNEXPECTED_FIELD, "PAYNOW"));
            break;
        case BANK:
            checkArgument(hasBankName && !bankName.isBlank() && hasRef && !referenceNumber.isBlank(),
                    MESSAGE_INVALID_BANK);
            checkArgument(!hasHandle && !hasLastFour && !hasWalletProvider && !hasWalletAccountId,
                    String.format(MESSAGE_UNEXPECTED_FIELD, "BANK"));
            break;
        case CARD:
            checkArgument(isValidLastFourDigits(lastFourDigits), MESSAGE_INVALID_CARD);
            checkArgument(!hasHandle && !hasBankName && !hasRef && !hasWalletProvider && !hasWalletAccountId,
                    String.format(MESSAGE_UNEXPECTED_FIELD, "CARD"));
            break;
        case EWALLET:
            checkArgument(hasWalletProvider && !walletProvider.isBlank()
                    && hasWalletAccountId && !walletAccountId.isBlank(), MESSAGE_INVALID_EWALLET);
            checkArgument(!hasHandle && !hasBankName && !hasRef && !hasLastFour,
                    String.format(MESSAGE_UNEXPECTED_FIELD, "EWALLET"));
            break;
        default:
            break;
        }
    }

    /**
     * Returns true if {@code test} is a valid last-four-digits value (exactly 4 numeric digits).
     */
    public static boolean isValidLastFourDigits(String test) {
        return test != null && test.matches(VALIDATION_REGEX_LAST_FOUR);
    }

    /** Returns the payment type. */
    public PaymentType getType() {
        return type;
    }

    /** Returns the PayNow handle, or null if not applicable. */
    public String getHandle() {
        return handle;
    }

    /** Returns the bank name, or null if not applicable. */
    public String getBankName() {
        return bankName;
    }

    /** Returns the bank reference number, or null if not applicable. */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /** Returns the card last four digits, or null if not applicable. */
    public String getLastFourDigits() {
        return lastFourDigits;
    }

    /** Returns the e-wallet provider, or null if not applicable. */
    public String getWalletProvider() {
        return walletProvider;
    }

    /** Returns the e-wallet account ID, or null if not applicable. */
    public String getWalletAccountId() {
        return walletAccountId;
    }

    @Override
    public String toString() {
        switch (type) {
        case CASH:
            return "CASH";
        case PAYNOW:
            return "PAYNOW (handle: " + handle + ")";
        case BANK:
            return "BANK (bank: " + bankName + ", ref: " + referenceNumber + ")";
        case CARD:
            return "CARD (last 4: " + lastFourDigits + ")";
        case EWALLET:
            return "EWALLET (provider: " + walletProvider + ", account: " + walletAccountId + ")";
        default:
            return type.name();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PaymentInfo)) {
            return false;
        }
        PaymentInfo o = (PaymentInfo) other;
        return type == o.type
                && Objects.equals(handle, o.handle)
                && Objects.equals(bankName, o.bankName)
                && Objects.equals(referenceNumber, o.referenceNumber)
                && Objects.equals(lastFourDigits, o.lastFourDigits)
                && Objects.equals(walletProvider, o.walletProvider)
                && Objects.equals(walletAccountId, o.walletAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, handle, bankName, referenceNumber,
                lastFourDigits, walletProvider, walletAccountId);
    }
}
