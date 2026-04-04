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
import seedu.homechef.model.common.Food;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Price;
import seedu.homechef.model.order.Quantity;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_AVAILABILITY = "Availability must be 'true' or 'false'.";

    private static final String VALID_PAYMENT_TYPE_NAMES = Arrays.stream(PaymentType.values())
            .map(Enum::name)
            .collect(Collectors.joining(", "));

    /**
     * Trims leading and trailing whitespaces in {@code String input} and
     * replaces sequences of multiple whitespaces with a single space.
     *
     * @param input String to normalize
     * @return Normalized string
     */
    public static String normalizeWhitespace(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param oneBasedIndex One-based index to parse
     * @return Index object representing the parsed index
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = normalizeWhitespace(oneBasedIndex);
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String foodName} into a {@code Food}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param foodName The food name string to parse.
     * @return A Food object representing the parsed food name.
     * @throws ParseException if the given {@code foodName} is invalid.
     */
    public static Food parseFood(String foodName) throws ParseException {
        requireNonNull(foodName);
        String trimmedName = normalizeWhitespace(foodName);
        if (!Food.isValidFood(trimmedName)) {
            throw new ParseException(Food.MESSAGE_CONSTRAINTS);
        }
        return new Food(trimmedName);
    }

    /**
     * Parses a {@code String customerName} into a {@code Customer}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param customerName The customer name string to parse.
     * @return A Customer object representing the parsed customer name.
     * @throws ParseException if the given {@code customerName} is invalid.
     */
    public static Customer parseCustomer(String customerName) throws ParseException {
        requireNonNull(customerName);
        String trimmedName = normalizeWhitespace(customerName);
        if (!Customer.isValidCustomer(trimmedName)) {
            throw new ParseException(Customer.MESSAGE_CONSTRAINTS);
        }
        return new Customer(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param phone Phone number string to parse
     * @return A Phone object representing the parsed phone number.
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = normalizeWhitespace(phone);
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param address Address string to parse
     * @return An Address object representing the parsed address.
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = normalizeWhitespace(address);
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param date Date string to parse
     * @return A Date object representing the parsed date.
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedName = normalizeWhitespace(date);
        if (!Date.isValidDate(trimmedName)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedName);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param email Email string to parse
     * @return An Email object representing the parsed email.
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = normalizeWhitespace(email);
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code DietTag}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param tag Tag string to parse
     * @return A DietTag object representing the parsed tag.
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static DietTag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = normalizeWhitespace(tag);
        if (!DietTag.isValidTagName(trimmedTag)) {
            throw new ParseException(DietTag.MESSAGE_CONSTRAINTS);
        }
        return new DietTag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<DietTag>}.
     *
     * @param tags Collection of tag strings to parse
     * @return A set of DietTag objects parsed from the collection.
     * @throws ParseException if any of the given tags are invalid.
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
     * Parses a {@code String price} into a {@code Price}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param price Price string to parse
     * @return A Price object representing the parsed order price.
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = normalizeWhitespace(price);
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String price} into a menu {@code Price}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param price Menu price string to parse
     * @return A menu Price object representing the parsed menu price.
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static seedu.homechef.model.menu.Price parseMenuPrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = normalizeWhitespace(price);
        if (!seedu.homechef.model.menu.Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(seedu.homechef.model.menu.Price.MESSAGE_CONSTRAINTS);
        }
        return new seedu.homechef.model.menu.Price(trimmedPrice);
    }

    /**
     * Parses a {@code String value} into a {@code Quantity}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param value Quantity string to parse
     * @return A Quantity object representing the parsed quantity.
     * @throws ParseException if the given {@code value} is not a valid quantity.
     */
    public static Quantity parseQuantity(String value) throws ParseException {
        requireNonNull(value);
        String trimmed = normalizeWhitespace(value);
        if (!Quantity.isValidQuantity(trimmed)) {
            throw new ParseException(Quantity.MESSAGE_CONSTRAINTS);
        }
        return new Quantity(Integer.parseInt(trimmed));
    }

    /**
     * Parses payment info from the four optional payment prefix values.
     * Returns empty if no payment prefixes were provided.
     * The {@code ref} parameter maps to different semantic fields depending on the type:
     * PayNow handle, bank reference number, card last-4 digits, or wallet account ID.
     *
     * @param method         Optional payment method value (m/)
     * @param ref            Optional payment reference value (r/)
     * @param bankName       Optional bank name value (b/)
     * @param walletProvider Optional wallet provider value (w/)
     * @return An optional PaymentInfo when payment fields are provided, or empty when none are provided.
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
            type = PaymentType.valueOf(normalizeWhitespace(method.get()).toUpperCase());
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

    /**
     * Parses a {@code String availability} and returns the boolean value.
     *
     * @param availability Availability string to parse
     * @return The parsed availability value.
     * @throws ParseException if the value is not "true" or "false" (case-insensitive)
     */
    public static boolean parseAvailability(String availability) throws ParseException {
        requireNonNull(availability);
        String trimmed = normalizeWhitespace(availability).toLowerCase();
        if (!trimmed.equals("true") && !trimmed.equals("false")) {
            throw new ParseException(MESSAGE_INVALID_AVAILABILITY);
        }
        return trimmed.equals("true");
    }
}
