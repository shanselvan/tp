package seedu.homechef.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.StringUtil;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Name;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.tag.DietTag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static final String VALID_PAYMENT_TYPE_NAMES = Arrays.stream(PaymentType.values())
            .map(Enum::name)
            .collect(Collectors.joining(", "));

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String foodName} into a {@code Food}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code foodName} is invalid.
     */
    public static Food parseFood(String foodName) throws ParseException {
        requireNonNull(foodName);
        String trimmedName = foodName.trim();
        if (!Food.isValidFood(trimmedName)) {
            throw new ParseException(Food.MESSAGE_CONSTRAINTS);
        }
        return new Food(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedName = date.trim();
        if (!Date.isValidDate(trimmedName)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedName);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code DietTag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static DietTag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!DietTag.isValidTagName(trimmedTag)) {
            throw new ParseException(DietTag.MESSAGE_CONSTRAINTS);
        }
        return new DietTag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<DietTag>}.
     */
    public static Set<DietTag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<DietTag> dietTagSet = new HashSet<>();
        for (String tagName : tags) {
            dietTagSet.add(parseTag(tagName));
        }
        return dietTagSet;
    }

    /**
     * Parses payment info from the four optional payment prefix values.
     * Returns empty if no payment prefixes were provided.
     * The {@code ref} parameter maps to different semantic fields depending on the type:
     * PayNow handle, bank reference number, card last-4 digits, or wallet account ID.
     *
     * @throws ParseException if the combination of provided values is invalid for any payment type.
     */
    public static Optional<PaymentInfo> parsePaymentInfo(
            Optional<String> method,
            Optional<String> ref,
            Optional<String> bankName,
            Optional<String> walletProvider) throws ParseException {

        boolean hasRef = ref.isPresent();
        boolean hasBankName = bankName.isPresent();
        boolean hasWalletProvider = walletProvider.isPresent();

        if (method.isEmpty()) {
            if (hasRef || hasBankName || hasWalletProvider) {
                throw new ParseException(
                        "Payment method (m/) required when payment details are provided.");
            }
            return Optional.empty();
        }

        PaymentType type;
        try {
            type = PaymentType.valueOf(method.get().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    "Invalid payment method: " + method.get() + ". Valid types: " + VALID_PAYMENT_TYPE_NAMES + ".");
        }

        PaymentInfo paymentInfo;
        switch (type) {
        case CASH:
            if (hasRef || hasBankName || hasWalletProvider) {
                throw new ParseException(
                        "No additional payment details (r/, b/, w/) are expected for CASH.");
            }
            paymentInfo = new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
            break;
        case PAYNOW:
            if (!hasRef) {
                throw new ParseException("r/ required for PAYNOW (provide phone number or UEN).");
            }
            if (hasBankName) {
                throw new ParseException("b/ only valid for BANK payment type.");
            }
            if (hasWalletProvider) {
                throw new ParseException("w/ only valid for EWALLET payment type.");
            }
            if (ref.get().startsWith("+") && !ref.get().matches(PaymentInfo.PAYNOW_PHONE_REGEX)) {
                throw new ParseException(PaymentInfo.MESSAGE_INVALID_PAYNOW_PHONE);
            }
            paymentInfo = new PaymentInfo(PaymentType.PAYNOW, ref.get(), null, null, null, null, null);
            break;
        case BANK:
            if (!hasRef) {
                throw new ParseException("r/ required for BANK (provide reference number).");
            }
            if (!hasBankName) {
                throw new ParseException("b/ required for BANK (provide bank name).");
            }
            if (hasWalletProvider) {
                throw new ParseException("w/ only valid for EWALLET payment type.");
            }
            paymentInfo = new PaymentInfo(PaymentType.BANK, null, bankName.get(), ref.get(), null, null, null);
            break;
        case CARD:
            if (!hasRef) {
                throw new ParseException("r/ required for CARD (provide last 4 digits).");
            }
            if (hasBankName) {
                throw new ParseException("b/ only valid for BANK payment type.");
            }
            if (hasWalletProvider) {
                throw new ParseException("w/ only valid for EWALLET payment type.");
            }
            if (!PaymentInfo.isValidLastFourDigits(ref.get())) {
                throw new ParseException("Card last 4 digits must be exactly 4 numeric digits (e.g. r/4321).");
            }
            paymentInfo = new PaymentInfo(PaymentType.CARD, null, null, null, ref.get(), null, null);
            break;
        case EWALLET:
            if (!hasRef) {
                throw new ParseException("r/ required for EWALLET (provide account ID).");
            }
            if (!hasWalletProvider) {
                throw new ParseException("w/ required for EWALLET (provide wallet provider).");
            }
            if (hasBankName) {
                throw new ParseException("b/ only valid for BANK payment type.");
            }
            paymentInfo = new PaymentInfo(
                    PaymentType.EWALLET, null, null, null, null, walletProvider.get(), ref.get());
            break;
        default:
            throw new ParseException("Unknown payment type: " + type);
        }

        return Optional.of(paymentInfo);
    }
}
