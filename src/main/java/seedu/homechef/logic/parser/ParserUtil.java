package seedu.homechef.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.StringUtil;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.BankPayment;
import seedu.homechef.model.order.CashPayment;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.PayNowPayment;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_AVAILABILITY =
            "Availability must be 'yes' or 'no'.";
    public static final String MESSAGE_MULTIPLE_PAYMENT_PREFIXES =
            "Only one payment prefix may be provided: bank/, paynow/, or cash/.";
    public static final String MESSAGE_CASH_PAYMENT_REQUIRED =
            "cash/ requires yes or no.";
    public static final String MESSAGE_BANK_PAYMENT_REQUIRED =
            "bank/ requires a non-blank bank reference/details value.";
    public static final String MESSAGE_PAYNOW_PAYMENT_REQUIRED =
            "paynow/ requires a non-blank PayNow identifier (e.g. phone number, UEN, or handle).";

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
    public static seedu.homechef.model.common.Price parseMenuPrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = normalizeWhitespace(price);
        if (!seedu.homechef.model.common.Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(seedu.homechef.model.common.Price.MESSAGE_CONSTRAINTS);
        }
        return new seedu.homechef.model.common.Price(trimmedPrice);
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
     * Parses payment info from the three optional payment prefixes.
     * Returns empty if no payment prefixes were provided.
     */
    public static Optional<PaymentInfo> parsePaymentInfo(
            Optional<String> bankPayment,
            Optional<String> payNowPayment,
            Optional<String> cashPayment) throws ParseException {
        int prefixCount = countPresent(bankPayment, payNowPayment, cashPayment);
        if (prefixCount == 0) {
            return Optional.empty();
        }
        if (prefixCount > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_PAYMENT_PREFIXES);
        }

        if (cashPayment.isPresent()) {
            return parseCashPayment(cashPayment.get());
        }

        if (bankPayment.isPresent()) {
            if (bankPayment.get().isBlank()) {
                throw new ParseException(MESSAGE_BANK_PAYMENT_REQUIRED);
            }
            try {
                return Optional.of(new BankPayment(normalizeWhitespace(bankPayment.get())));
            } catch (IllegalArgumentException e) {
                throw new ParseException(MESSAGE_BANK_PAYMENT_REQUIRED);
            }
        }

        if (payNowPayment.get().isBlank()) {
            throw new ParseException(MESSAGE_PAYNOW_PAYMENT_REQUIRED);
        }
        try {
            return Optional.of(new PayNowPayment(normalizeWhitespace(payNowPayment.get())));
        } catch (IllegalArgumentException e) {
            throw new ParseException(PayNowPayment.MESSAGE_INVALID_REFERENCE);
        }
    }

    /**
     * Parses the cash payment prefix value into either cash payment or no payment info.
     *
     * @param cashPayment Value supplied after the cash/ prefix.
     * @return Cash payment if the value is yes; empty if the value is no.
     * @throws ParseException if the supplied value is not yes or no.
     */
    public static Optional<PaymentInfo> parseCashPayment(String cashPayment) throws ParseException {
        String normalizedCashPayment = normalizeWhitespace(cashPayment).toLowerCase();
        switch (normalizedCashPayment) {
        case "yes":
            return Optional.of(new CashPayment());
        case "no":
            return Optional.empty();
        default:
            throw new ParseException(MESSAGE_CASH_PAYMENT_REQUIRED);
        }
    }

    @SafeVarargs
    private static int countPresent(Optional<String>... values) {
        int count = 0;
        for (Optional<String> value : values) {
            if (value.isPresent()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Parses a {@code String availability} and returns the enum value.
     *
     * @param availability Availability string to parse
     * @return The parsed availability value.
     * @throws ParseException if the value is invalid.
     */
    public static Availability parseAvailability(String availability) throws ParseException {
        requireNonNull(availability);
        String trimmed = normalizeWhitespace(availability);
        try {
            return Availability.fromString(trimmed);
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_AVAILABILITY);
        }
    }
}

