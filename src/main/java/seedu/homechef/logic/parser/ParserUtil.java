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
import seedu.homechef.model.menu.MenuItemName;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Price;
import seedu.homechef.model.order.Quantity;
import seedu.homechef.model.tag.DietTag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_AVAILABILITY = "Availability must be 'true' or 'false'.";

    private static final String VALID_PAYMENT_TYPE_NAMES = Arrays.stream(PaymentType.values())
            .map(Enum::name)
            .collect(Collectors.joining(", "));

    public static final String MESSAGE_METHOD_REQUIRED_FOR_DETAILS =
            "m/ required when payment details (r/, b/, w/) are provided.";
    public static final String MESSAGE_INVALID_PAYMENT_METHOD =
            "Invalid payment method: '%s'. Valid types: " + VALID_PAYMENT_TYPE_NAMES + ".";
    public static final String MESSAGE_UNEXPECTED_FIELDS_FOR_CASH =
            "r/, b/, w/ are not accepted for CASH.";
    public static final String MESSAGE_REF_REQUIRED_FOR =
            "r/ required for %s.";
    public static final String MESSAGE_BANK_NAME_REQUIRED =
            "b/ required for BANK.";
    public static final String MESSAGE_BANK_NAME_NOT_VALID =
            "b/ is only valid for BANK payment type.";
    public static final String MESSAGE_WALLET_PROVIDER_REQUIRED =
            "w/ required for EWALLET.";
    public static final String MESSAGE_WALLET_PROVIDER_NOT_VALID =
            "w/ is only valid for EWALLET payment type.";
    public static final String MESSAGE_CARD_REF_INVALID =
            "r/ for CARD must be exactly 4 numeric digits (e.g. r/4321).";

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
     * Parses a {@code String menuItemName} into a {@code MenuItemName}.
     * String will be normalized by trimming and replacing whitespace with a single space.
     *
     * @param menuItemName The menu item name string to parse.
     * @return A MenuItemName object representing the parsed menu item name.
     * @throws ParseException if the given {@code menuItemName} is invalid.
     */
    public static MenuItemName parseMenuItemName(String menuItemName) throws ParseException {
        requireNonNull(menuItemName);
        String trimmedName = normalizeWhitespace(menuItemName);
        if (!MenuItemName.isValidMenuItemName(trimmedName)) {
            throw new ParseException(MenuItemName.MESSAGE_CONSTRAINTS);
        }
        return new MenuItemName(trimmedName);
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
     * @param method the payment method type (m/ prefix value), or empty if not provided
     * @param ref the payment reference (r/ prefix value), or empty if not provided
     * @param bankName the bank name (b/ prefix value), or empty if not provided
     * @param walletProvider the wallet provider (w/ prefix value), or empty if not provided
     * @return an {@code Optional} containing the parsed {@code PaymentInfo}, or empty if no payment fields were provided
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
                throw new ParseException(MESSAGE_METHOD_REQUIRED_FOR_DETAILS);
            }
            return Optional.empty();
        }

        PaymentType type = parsePaymentType(method.get());

        PaymentInfo paymentInfo;
        switch (type) {
        case CASH:
            paymentInfo = parseCashPayment(hasRef, hasBankName, hasWalletProvider);
            break;
        case PAYNOW:
            paymentInfo = parsePayNowPayment(ref, hasBankName, hasWalletProvider);
            break;
        case BANK:
            paymentInfo = parseBankPayment(ref, bankName, hasWalletProvider);
            break;
        case CARD:
            paymentInfo = parseCardPayment(ref, hasBankName, hasWalletProvider);
            break;
        case EWALLET:
            paymentInfo = parseEWalletPayment(ref, walletProvider, hasBankName);
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_PAYMENT_METHOD, method.get().trim()));
        }

        return Optional.of(paymentInfo);
    }

    private static PaymentType parsePaymentType(String method) throws ParseException {
        try {
            return PaymentType.valueOf(method.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_PAYMENT_METHOD, method.trim()));
        }
    }

    private static PaymentInfo parseCashPayment(
            boolean hasRef, boolean hasBankName, boolean hasWalletProvider) throws ParseException {
        if (hasRef || hasBankName || hasWalletProvider) {
            throw new ParseException(MESSAGE_UNEXPECTED_FIELDS_FOR_CASH);
        }
        return new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null);
    }

    private static PaymentInfo parsePayNowPayment(
            Optional<String> ref, boolean hasBankName, boolean hasWalletProvider) throws ParseException {
        if (ref.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_REF_REQUIRED_FOR, "PAYNOW"));
        }
        if (hasBankName) {
            throw new ParseException(MESSAGE_BANK_NAME_NOT_VALID);
        }
        if (hasWalletProvider) {
            throw new ParseException(MESSAGE_WALLET_PROVIDER_NOT_VALID);
        }
        if (ref.get().startsWith("+") && !ref.get().matches(PaymentInfo.PAYNOW_PHONE_REGEX)) {
            throw new ParseException(PaymentInfo.MESSAGE_INVALID_PAYNOW_PHONE);
        }
        return new PaymentInfo(PaymentType.PAYNOW, ref.get(), null, null, null, null, null);
    }

    private static PaymentInfo parseBankPayment(
            Optional<String> ref, Optional<String> bankName, boolean hasWalletProvider) throws ParseException {
        if (ref.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_REF_REQUIRED_FOR, "BANK"));
        }
        if (bankName.isEmpty()) {
            throw new ParseException(MESSAGE_BANK_NAME_REQUIRED);
        }
        if (hasWalletProvider) {
            throw new ParseException(MESSAGE_WALLET_PROVIDER_NOT_VALID);
        }
        return new PaymentInfo(PaymentType.BANK, null, bankName.get(), ref.get(), null, null, null);
    }

    private static PaymentInfo parseCardPayment(
            Optional<String> ref, boolean hasBankName, boolean hasWalletProvider) throws ParseException {
        if (ref.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_REF_REQUIRED_FOR, "CARD"));
        }
        if (hasBankName) {
            throw new ParseException(MESSAGE_BANK_NAME_NOT_VALID);
        }
        if (hasWalletProvider) {
            throw new ParseException(MESSAGE_WALLET_PROVIDER_NOT_VALID);
        }
        if (!PaymentInfo.isValidLastFourDigits(ref.get())) {
            throw new ParseException(MESSAGE_CARD_REF_INVALID);
        }
        return new PaymentInfo(PaymentType.CARD, null, null, null, ref.get(), null, null);
    }

    private static PaymentInfo parseEWalletPayment(
            Optional<String> ref, Optional<String> walletProvider, boolean hasBankName) throws ParseException {
        if (ref.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_REF_REQUIRED_FOR, "EWALLET"));
        }
        if (walletProvider.isEmpty()) {
            throw new ParseException(MESSAGE_WALLET_PROVIDER_REQUIRED);
        }
        if (hasBankName) {
            throw new ParseException(MESSAGE_BANK_NAME_NOT_VALID);
        }
        return new PaymentInfo(
                PaymentType.EWALLET, null, null, null, null, walletProvider.get(), ref.get());
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
